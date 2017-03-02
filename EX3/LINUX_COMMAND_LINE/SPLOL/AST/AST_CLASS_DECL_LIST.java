package AST;


import java.util.ArrayList;
import java.util.List;

public class AST_CLASS_DECL_LIST extends AST_Node {
	public final List<AST_CLASS_DECL> classes = new ArrayList<AST_CLASS_DECL>();
	
	public AST_CLASS_DECL_LIST(int line){
		super(line);
	}
	
	public void add(AST_CLASS_DECL clss){
		classes.add(clss);
	}
	
	public int countAppearances(String elemName, AST_CLASS_DECL cd){
		
		int numAppearances = 0;
		
		if(cd.parentName==null){
			numAppearances = cd.numTimesContained(elemName);
		}
		
		else{
			for(AST_CLASS_DECL cdp:classes){
				if(cd.parentName.equals(cdp.name)){
					numAppearances =  cd.numTimesContained(elemName) + countAppearances(elemName, cdp);
				}
			}
		}
		
		return numAppearances;
	}
	
	
	public boolean hasDifferentFieldOfSameName(AST_CLASS_ELEMENT_METHOD mtd, AST_CLASS_DECL cd){
		
		boolean has = cd.containsDifferentNamesakeMethod(mtd);
		
		if(has)
			return true;
		
		if(cd.parentName==null)
			return false;
		
		for(AST_CLASS_DECL cdp:classes){
			if(cd.parentName.equals(cdp.name)){
				has =  cd.containsDifferentNamesakeMethod(mtd) || hasDifferentFieldOfSameName(mtd, cdp);
			}
		}
		
		
		return has;
	}
	
	//containsDifferentNamesakeMethod
	
	
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