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
import AST.AST_TYPE_CLASS;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ClassChecker {

  public static class Function {
    public AST_METHOD_DECLARE method;

    public Function(AST_METHOD_DECLARE method){
      this.method = method;
    }

    public boolean isSameArgsTypes(List<AST_TYPE> types){
      AST_FORMAL_LIST myRest = this.method.formals;
      AST_FORMAL myCurr;
      for(int i = 0; i < types.size(); i++) {
        if(myRest != null) {
          myCurr = myRest.first;
          myRest = myRest.rest;
          if(!types.get(i).isExtending(myCurr.type)) {
            return false;
          }
        }
        else{
          return i + 1 == types.size();          //same size of data structures
        }
      }
      return myRest == null;      //same size of data structures
    }
  }
  public static class Field {
    AST_TYPE type;
    String name;
    public Field(AST_TYPE type, String name){

      this.type = type;
      this.name = name;
    }

    public boolean equals(Object other){
      if(!(other instanceof Field)) {
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
    public LinkedList<Field> fields = new LinkedList<Field>();

    public Class(AST_CLASS_DECLARE cDec, Class parent){
      this.name = cDec.name;
      this.parent = parent;
    }
    void addFunction(AST_METHOD_DECLARE f){
      Function func = new Function(f);
      this.funcs.add(func);
    }

    AST_TYPE hasFunction(String name, List<AST_TYPE> argTypes) throws Exception {
      //TODO validate types are all good because i was tired
      ListIterator<Function> iter = this.funcs.listIterator(0);
      Function f = null;
      while(iter.hasNext()) {
        f =iter.next();
        if(f.method.name.equals(name)) {
          break;
        }
        else{
          f = null;
        }
      }
      if(f == null) {
        throw new Exception("Class "+Class.this.name+" doesnt have function "+name+" with these args");
      }
      if(f.isSameArgsTypes(argTypes)) {
        return f.method.type;
      }
      else{
        throw new Exception("Class "+Class.this.name+" doesnt have function "+name+" with these args");
      }
    }

    void addFields(AST_FIELD fs){
      for(int i = 0; i< fs.names.length; i++) {
        this.fields.add(new Field(fs.type, fs.names[i]));
      }
    }

    AST_TYPE hasField(String name) throws Exception {
      for (Field f : this.fields) {
        if(f.name.equals(name)) {
          return f.type;
        }
      }
      throw new Exception("Class "+Class.this.name+" doesnt have field "+name+" with this type");
    }
  }

  private static HashMap<String, Class> map = new HashMap<String, Class>();
  private static String currentClassName;

  public static Class get(String name){
    return map.get(name);
  }
  public static void newClass(AST_CLASS_DECLARE classDec){
    Class parent = null;
    if(classDec.extend != null) {
      parent = map.get(classDec.extend);
    }
    Class c = new ClassChecker.Class(classDec,parent);
    currentClassName = c.name;
    map.put(c.name, c);
  }
  public static void addFunction(String className, AST_METHOD_DECLARE func) throws Exception {
    Class c = map.get(className);
    if(c == null) {
      throw new Exception("Cant find class " + className);
    }
    c.addFunction(func);
  }
  public static void addFields(String className, AST_FIELD fields) throws Exception {
    if(className == null) {
      if(currentClassName == null) {
        throw new Exception("Compiler Error: currentClassName is null");
      }
      addFields(currentClassName,fields);
      return;
    }
    Class c = map.get(className);
    if(c == null) {
      throw new Exception("Cant find class " + className);
    }
    c.addFields(fields);
  }
  private static AST_TYPE isValidMethodInSpecificClass(String className, String fName, List<AST_TYPE> argTypes) throws Exception {
    Class c = map.get(className);
    if(c == null) {
      throw new Exception("Cant find class " + className);
    }
    return c.hasFunction(fName,argTypes);
  }

  public static AST_TYPE isValidMethod(AST_TYPE classType, String fName, List<AST_TYPE> argTypes) throws Exception {
    if(!(classType instanceof AST_TYPE_CLASS)) {
      throw new Exception("Cant convert to AST_TYPE_CLASS: " + classType);
    }
    AST_TYPE t = null;
    AST_TYPE_CLASS cType = (AST_TYPE_CLASS) classType;
    boolean found = false;
    for(String c = map.get(cType.name)!=null ? map.get(cType.name).name : null;
        c != null;
        c = ((map.get(c)!=null && map.get(c).parent!=null) ? map.get(c).parent.name : null)) {
      try{
        t = isValidMethodInSpecificClass(c, fName, argTypes);
        found = true;
      }
      catch(Exception e) {
        continue;
      };
      break;
    }
    if(found) {
      return t;
    }
    else{
      throw new Exception("Cant find method " + fName + " on class " +((AST_TYPE_CLASS)classType).name+ " or its parents." );
    }
  }
  private static AST_TYPE isValidFieldInSpecificClass(String cName, String fName) throws Exception {
    Class c = map.get(cName);
    if(c == null) {
      throw new Exception("Cant find class " + cName);
    }
    return c.hasField(fName);
  }
  public static AST_TYPE isValidField(String cName, String fName) throws Exception {
    AST_TYPE t = null;
    throwIfNotClass(cName);
    boolean found = false;
    for(String c = map.get(cName).name; c != null; c = ((map.get(c)!=null && map.get(c).parent!=null) ? map.get(c).parent.name : null)) {
      try{
        t = isValidFieldInSpecificClass(c, fName);
        found = true;
      }
      catch(Exception e) {
        continue;
      };
      break;
    }
    if(found) {
      return t;
    }
    else{
      throw new Exception("Cant find field " + fName + " on class " + cName + " or its parents." );
    }
  }

  private static void throwIfNotClass(String name) throws Exception {
    if(map.get(name) == null) {
      throw new Exception("Cant find class " + name);
    }
  }

  public static void ensureOneMain() throws Exception {
    List<AST_TYPE> args = new ArrayList<AST_TYPE>();
    args.add(new AST_TYPE_ARRAY(new AST_TYPE_TERM(TYPES.STRING)));
    boolean found = false;
    Exception e = new Exception("Must declare exactly one void main(String[]) in program");
    for(Map.Entry<String,Class> classTuple : map.entrySet()) {
      Class c = classTuple.getValue();
      try{
        AST_TYPE retType = c.hasFunction("main", args);
        if(retType != null){
          continue;
        }
        //if we're here it was found on a previous iteration
        if(found) {
          throw e;
        }
        else{//first time we see it
          found = true;
        }
      }
      catch(Exception er) {
        continue;
      }
    }
    if(!found){
      throw e;
    }
    //found exactly one
  }



}
