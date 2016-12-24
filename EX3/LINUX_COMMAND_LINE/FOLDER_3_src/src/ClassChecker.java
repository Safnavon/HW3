package src;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import AST.AST_CLASS_DECLARE;
import AST.AST_FIELD;
import AST.AST_FORMAL;
import AST.AST_FORMAL_LIST;
import AST.AST_METHOD_DECLARE;
import AST.AST_TYPE;

public class ClassChecker {

	public static class Function{
		public AST_METHOD_DECLARE method;

		public Function(AST_METHOD_DECLARE method){
			this.method = method;
		}

		public boolean isSameArgsTypes(List<AST_TYPE> types){
			AST_FORMAL_LIST myRest = this.method.formals;
			AST_FORMAL myCurr;
			for(int i = 0; i < types.size(); i++){
				if(myRest.first != null){
					myCurr = myRest.first;
					myRest = myRest.rest;
					if(!types.get(i).isExtending(myCurr.type)){
						return false;
					}
				}
				else{
					return i + 1 == types.size();//same size of data structures
				}
			}
			return myRest.first == null;//same size of data structures
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
			if(!(other instanceof Field)){
				return false;
			}
			Field _other = (Field) other;
			return 	this.type.equals(_other.type) &&
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

		boolean hasFunction(AST_TYPE retType, String name, List<AST_TYPE> argTypes){
			Function f = null;
			ListIterator<Function> iter = this.funcs.listIterator(0);
			for(; iter.hasNext(); f = iter.next()){
					if(f.method.name.equals(name)){
						break;
					}
			}
			if(f == null ||
					!f.method.type.equals(retType)){
				return false;
			}
			return f.isSameArgsTypes(argTypes);
		}

		void addFields(AST_FIELD fs){
			for(int i = 0; i< fs.names.length; i++){
				this.fields.add(new Field(fs.type, fs.names[i]));
			}
		}
	}

	private static HashMap<String, Class> map = new HashMap<String, Class>();

	/*public Class get(String name){
		return this.map.get(name);
	}*/
	public static void newClass(AST_CLASS_DECLARE classDec){
		Class parent = null;
		if(classDec.extend != null){
			parent = map.get(classDec.extend);
		}
		Class c = new ClassChecker.Class(classDec,parent);
		map.put(c.name, c);
	}

	public static void addFunction(String className, AST_METHOD_DECLARE func) throws Exception{
		Class c = map.get(className);
		if(c == null){
			throw new Exception("Cant find class " + className);
		}
		c.addFunction(func);
	}







}
