package AST;


public class AST_CLASS_DECL extends AST_Node {
	public final String name;
	public final String parentName;
	public final AST_CLASS_ELEMENT_LIST elements;
	
	public int numTimesContained(String name) {
		int cnt = 0;
		if(elements==null)
			return 0;
		for(int i=0; i<elements.elems.size();i++){
			if(elements.elems.get(i) instanceof AST_CLASS_ELEMENT_METHOD){
				if(((AST_CLASS_ELEMENT_METHOD)elements.elems.get(i)).name.name.equals(name))
					cnt++;
			}
			else if(elements.elems.get(i) instanceof AST_CLASS_ELEMENT_FIELD){
				for(int j=0; j<((AST_CLASS_ELEMENT_FIELD)elements.elems.get(i)).ids.ids.size(); j++)
					if(((AST_CLASS_ELEMENT_FIELD)elements.elems.get(i)).ids.ids.get(j).name.equals(name))
						cnt++;
			}
			
		}
		return cnt;
	}
	
	public boolean containsDifferentNamesakeMethod(AST_CLASS_ELEMENT_METHOD mtd) {
		boolean contains = false;
		if(elements==null)
			return false;
		for(int i=0; i<elements.elems.size();i++){
			if(elements.elems.get(i) instanceof AST_CLASS_ELEMENT_METHOD){
				
				AST_CLASS_ELEMENT_METHOD localMtd = (AST_CLASS_ELEMENT_METHOD)elements.elems.get(i);
				
				if(localMtd.name.name.equals(mtd.name.name)){
					
						if((localMtd.tp == null && mtd.tp != null) || (localMtd.tp != null && mtd.tp == null))
							contains = true;
						
						if(localMtd.tp != null){
						
						if(!localMtd.tp.name.equals(mtd.tp.name))
							contains = true;
						
						if(localMtd.tp.dim != mtd.tp.dim)
							contains = true;
					
					}
					
					if((localMtd.fr == null && mtd.fr !=null) || (localMtd.fr != null && mtd.fr == null))
						contains = true;
					
					if(localMtd.fr != null){
						if(localMtd.fr.fmls.size() != mtd.fr.fmls.size())
							contains = true;
						
						for(int j=0; j<mtd.fr.fmls.size(); j++){
							
							AST_FORMAL arg1 = localMtd.fr.fmls.get(j);
							AST_FORMAL arg2 = mtd.fr.fmls.get(j);
							
							if(!arg1.name.name.equals(arg2.name.name))
								contains = true;
							
							if(!arg1.tp.name.equals(arg2.tp.name))
								contains = true;
							
							if(arg1.tp.dim != arg2.tp.dim)
								contains = true;
						}
					}
				}
			}
			else if(elements.elems.get(i) instanceof AST_CLASS_ELEMENT_FIELD){
				for(int j=0; j<((AST_CLASS_ELEMENT_FIELD)elements.elems.get(i)).ids.ids.size(); j++)
					if(((AST_CLASS_ELEMENT_FIELD)elements.elems.get(i)).ids.ids.get(j).name.equals(mtd.name.name))
						contains = true;
			}
			
		}
		return contains;
	}
	
	public AST_CLASS_DECL(int line, String name, String parentName, AST_CLASS_ELEMENT_LIST elements){
		super(line);
		this.name=name;
		this.parentName=parentName;
		this.elements=elements;
	}
	
	/**
	 * Accepts a visitor object as part of the visitor pattern.
	 * 
	 * @param visitor
	 *            A visitor.
	 * @throws Exception 
	 */
	@Override
	public void accept(Visitor visitor) throws Exception {
		visitor.visit(this);
	}
	
	public String toString(){
		String parent = parentName==null?"":", extends: "+parentName;
		return "Declaration of class: "+name+ parent;
	}

	/** Accepts a propagating visitor parameterized by two types.
	 * 
	 * @param <DownType> The type of the object holding the context.
	 * @param <UpType> The type of the result object.
	 * @param visitor A propagating visitor.
	 * @param context An object holding context information.
	 * @return The result of visiting this node.
	 */
	@Override
	public <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		return visitor.visit(this, context);
	}
}