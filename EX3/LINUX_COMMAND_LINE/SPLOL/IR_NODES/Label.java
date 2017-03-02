package IR_NODES;

public class Label {
	static int counter=0;
	String name;
	int index;
	boolean noIndex;
	
	
	public Label(String name, boolean noIndex){
		counter++;
		index=counter;
		this.name=name;
		this.noIndex=noIndex;
	}
	
	public static int getCounter() {
		return counter;
	}

	public int getIndex() {
		return index;
	}
	
	public String toString(){
		if(!noIndex){
			return "Label_"+index+"_"+name;
		}else{
			return "Label_0_"+name;
		}
	}
	
	public String getName(){
		return name;
	}
	public void setIndex(int i){
		index=i;
	}
}
