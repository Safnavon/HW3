package IR_NODES;

public class Temp {
	String name;
	int index;
	static int counter=0;

	public Temp(String name) {
		this.name = name;
	}
	public Temp() {
		counter++;
		index=counter;
	}
	
	public String toString(){
		if(name==null){
			return "Temp_"+index;
		}else{
			return "$"+name;
		}
	}
}
