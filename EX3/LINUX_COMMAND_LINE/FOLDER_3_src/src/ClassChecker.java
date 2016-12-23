package src;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import AST.AST_CLASS_DECLARE;
import AST.AST_FORMAL;
import AST.AST_FORMAL_LIST;
import AST.AST_METHOD_DECLARE;
import AST.AST_TYPE;

public class ClassChecker {
	public class Function{
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
	public class Class {
		public String name;
		public Class parent;
		LinkedList<Function> functions;
//		LinkedList<Property> properties;
//		
//		boolean isSuperChild(Class superParent){
//			//TODO
//		}
	}
	
	public ClassChecker(){
		
	}
	
	private HashMap<String, AST_CLASS_DECLARE> map = new HashMap<String, AST_CLASS_DECLARE>(); 
	public AST_CLASS_DECLARE get(String name){
		return this.map.get(name);
	}
	public void put(String name, AST_CLASS_DECLARE classDec){
		this.map.put(name, classDec);
	}
	
	
	
	
	
	
	
}
