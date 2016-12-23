
public static class ClassChecker {
	public class Function{
		public AST_METHOD_DECLARE method;

		public Function(AST_METHOD_DECLARE method){
			this.method = method;
		}

		public boolean isSameArgsTypes(List<AST_TYPE> types){
			AST_FORMAL_LIST myRest = this.formals;
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


		public Class(AST_CLASS_DECLARE cDec, Class parent){
			this.name = cDec.name;
			this.parent = parent;
		}
	}

	public ClassChecker(){

	}

	private HashMap<String, Class> map = new HashMap<String, Class>();
	public Class get(String name){
		return this.map.get(name);
	}
	public void put(String name, AST_CLASS_DECLARE classDec){
		Class parent = null;
		if(classDec.extend != null){
			parent = this.map.get(classDec.extend);
		}
		Class c = new Class(classDec,parent);
		this.map.put(name, c);
	}







}
