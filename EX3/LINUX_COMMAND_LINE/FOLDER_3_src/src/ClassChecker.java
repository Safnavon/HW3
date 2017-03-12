package src;

import AST.AST_CLASS_DECLARE;
import AST.AST_FIELD;
import AST.AST_FORMAL;
import AST.AST_FORMAL_LIST;
import AST.AST_METHOD_DECLARE;
import AST.AST_TYPE;
import AST.AST_TYPE_ARRAY;
import AST.AST_TYPE_CLASS;
import AST.AST_TYPE_TERM;
import AST.TYPES;
import IR.T_Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ClassChecker {


    public static class Function {

        public AST_METHOD_DECLARE method;

        boolean inherited = false;//used for inheritance checking. this flag is changed according to context
        //IR
        public T_Label funcLabel;

        public Function(AST_METHOD_DECLARE method, String className) {
            this.method = method;
            //IR
            this.funcLabel = new T_Label(className + "_Function_" + method.name, true);
        }

        public boolean isSameArgsTypes(List<AST_TYPE> types) throws Exception {
            AST_FORMAL_LIST myRest = this.method.formals;
            AST_FORMAL myCurr;
            for (int i = 0; i < types.size(); i++) {
                if (myRest != null) {
                    myCurr = myRest.first;
                    myRest = myRest.rest;
                    if (!types.get(i).isExtending(myCurr.type)) {
                        return false;
                    }
                } else {
                    return i == types.size();//same size of data structures
                }
            }
            return myRest == null;//same size of data structures
        }

    }

    public static class Field {

        public AST_TYPE type;

        public String name;

        public Field(AST_TYPE type, String name) {

            this.type = type;
            this.name = name;
        }

        public boolean equals(Object other) {
            if (!(other instanceof Field)) {
                return false;
            }
            Field _other = (Field) other;
            return this.type.equals(_other.type) &&
                    this.name.equals(_other.name);
        }

    }

    public static class Class {

        public String name;

        public Class parent;
        public LinkedList<Function> funcs = new LinkedList<Function>();
        public ArrayList<Field> fields = new ArrayList<>();

        public Class(AST_CLASS_DECLARE cDec, Class parent) {
            this.name = cDec.name;
            this.parent = parent;
            if (parent != null) {
                this.funcs.addAll(parent.funcs);
                for (Function f : this.funcs) {//also changes parent functions, but that's ok
                    f.inherited = true;
                }
                this.fields.addAll(parent.fields);
            }
        }

        /**
         * this function should be called exactly once for each function declaration found in the user code
         *
         * @param f
         * @throws Exception
         */
        void addFunction(AST_METHOD_DECLARE f) throws Exception {
            final Exception dup = new Exception("Duplicate function declaration");
            final Exception typeMismatch = new Exception("Return type must be similar to parent");
            final Exception dupMain = new Exception("Found a second void main(String[]) declaration");
            Function func = new Function(f, this.name);
            Function fExisting = this.getFunctionByName(func.method.name);
            if (fExisting != null) {
                if (!fExisting.inherited) {
                    throw dup;
                }
                if (func.method.type != null) {
                    if (!func.method.type.isExtending(fExisting.method.type))
                        throw typeMismatch;
                } else {
                    if (fExisting.method.type != null)
                        throw typeMismatch;
                }
                int fLocation = this.funcs.indexOf(fExisting);
                assert fLocation != -1;
                this.funcs.set(fLocation, func);
            } else {
                this.funcs.add(func);
            }
            //check if main
            List<AST_TYPE> args = new ArrayList<AST_TYPE>();
            args.add(new AST_TYPE_ARRAY(new AST_TYPE_TERM(TYPES.STRING)));
            if (f.name.equals("main") && func.isSameArgsTypes(args) && f.type == null) {//if this function has a main signature
                if (ClassChecker.mainFunctionLabel != null) {
                    throw dupMain;
                } else {
                    ClassChecker.mainFunctionLabel = func.funcLabel;
                }
            }
            //end check if main
        }

        AST_TYPE hasFunction(String name, List<AST_TYPE> argTypes) throws Exception {
            Function f = getFunctionByName(name);
            if (f == null) {
                throw new Exception("Class " + Class.this.name + " doesnt have function " + name + " with these args");
            }
            if (f.isSameArgsTypes(argTypes)) {
                return f.method.type;
            } else {
                throw new Exception("Class " + Class.this.name + " doesnt have function " + name + " with these args");
            }
        }

        public Function getFunctionByName(String name) {
            for (Function function : this.funcs) {
                if (function.method.name.equals(name)) {
                    return function;
                }
            }
            return null;
        }

        void addFields(AST_FIELD fs) throws Exception {
            final Exception dup = new Exception("Duplicate field name (possibly from parent)");
            for (int i = 0; i < fs.names.length; i++) {
                final String name = fs.names[i];
                if (getFieldByName(name) != null) {
                    throw dup;
                }
                this.fields.add(new Field(fs.type, name));
            }
        }

        AST_TYPE hasField(String name) throws Exception {
            for (Field f : this.fields) {
                if (f.name.equals(name)) {
                    return f.type;
                }
            }
            throw new Exception("Class " + Class.this.name + " doesnt have field " + name + " with this type");
        }

        public Field getFieldByName(String fieldName) {
            for (Field field : this.fields) {
                if (field.name.equals(fieldName)) {
                    return field;
                }
            }
            return null;
        }


    }

    private static HashMap<String, Class> map = new HashMap<String, Class>();

    //
//    private static AST_TYPE isValidFieldInSpecificClass(String cName, String fName) throws Exception {
//        Class c = map.get(cName);
//        if (c == null) {
//            throw new Exception("Cant find class " + cName);
//        }
//        return c.hasField(fName);
//    private static void throwIfNotClass(String name) throws Exception {
//        if (map.get(name) == null) {
//            throw new Exception("Cant find class " + name);
//        }
    private static T_Label mainFunctionLabel = null;

    private static String currentClassName;

    public static Class getForIr(String className) {
        assert className != null;
        return map.get(className);
    }

    public static Class get(String className) throws Exception {
        if (className == null) {
            className = currentClassName;
        }
        assert className != null;
        Class c = map.get(className);
        if (c == null) {
            throw new Exception("Cant find class " + className);
        }
        return c;
    }

    public static Class newClass(AST_CLASS_DECLARE classDec) {
        Class parent = null;
        if (classDec.extend != null) {
            parent = map.get(classDec.extend);
        }
        Class c = new ClassChecker.Class(classDec, parent);
        currentClassName = c.name;
        map.put(c.name, c);
        return c;
    }

    public static void addFunction(String className, AST_METHOD_DECLARE func) throws Exception {
        Class c = get(className);
        c.addFunction(func);
    }

    public static void addFields(String className, AST_FIELD fields) throws Exception {
        Class c = get(className);
        c.addFields(fields);
    }

    public static AST_TYPE isValidMethod(AST_TYPE classType, String fName, List<AST_TYPE> argTypes) throws Exception {
        if (!(classType instanceof AST_TYPE_CLASS)) {
            throw new Exception("Cant convert to AST_TYPE_CLASS: " + classType);
        }
        AST_TYPE_CLASS cType = (AST_TYPE_CLASS) classType;
        final Exception notFound = new Exception("Cant find method " + fName + " on class " + cType.name + " or its parents.");
        Class c = map.get(cType.name);
        if (c == null) {
            throw notFound;
        }
        return c.hasFunction(fName, argTypes);
    }

//    }

    public static AST_TYPE isValidField(String cName, String fName) throws Exception {
        final Exception notFound = new Exception("Cant find field " + fName + " on class " + cName + " or its parents.");
        Class c = map.get(cName);
        if (c == null) {
            throw notFound;
        }
        return c.hasField(fName);
    }

//    }

    /**
     * @return label of main class
     * @throws Exception when program implements more or less than one main function
     */
    public static T_Label ensureOneMain() throws Exception {
        if (mainFunctionLabel == null) {
            throw new Exception("Must declare exactly one void main(String[]) in program");
        }
        return mainFunctionLabel;
    }

    /**
     * @param className
     * @param fieldName
     * @return offset from field table start. assumes function table pointer in at index 0 of field table
     */
    public static int getFieldOffset(String className, String fieldName) throws Exception {
        Class c = get(className);
        assert c != null : className;
        Field f = c.getFieldByName(fieldName);
        assert f != null : "className: " + className + "fieldName: " + fieldName;
        int i = c.fields.indexOf(f);
        assert i > -1;
        return (i + 1) * 4;//+1 for skipping function table
    }

    /**
     * @param className
     * @param funcName
     * @return offset from start of function table
     */
    public static int getFunctionOffset(String className, String funcName) throws Exception {
        Class c = get(className);
        assert c != null : className;
        Function f = c.getFunctionByName(funcName);
        assert f != null : "className: " + className + "funcName: " + funcName;
        int i = c.funcs.indexOf(f);
        assert i > -1;
        return (i) * 4;
    }

    public static String generateAllVFTables() {
        StringBuilder sb = new StringBuilder();
        String nl = String.format("%n");
        for (Class c : map.values()) {
            if (c.funcs.size()==0) continue;
            boolean hasFunctions = c.funcs.size() != 0;
            String VFTableLabel = "VFTable_" + c.name;
            //print VF table
            sb.append(VFTableLabel + ":" + nl)
                    .append(hasFunctions ? "\t.word " : "");
            for (Function function : c.funcs) {
                sb.append(function.funcLabel.getName() + "," + nl + "\t");
            }
            if (c.funcs.size() > 0) {
                sb.delete(sb.lastIndexOf(","), sb.length());//remove last (,\n\t)
            }
            sb.append(nl + nl);
        }
        return sb.append(nl).toString();
    }

    /**
     * @param className
     * @return number of bytes to malloc
     */
    public static int sizeOf(String className) {
        Class c = map.get(className);
        assert c != null;
        return (c.fields.size() + 1) * 4;
    }
}
