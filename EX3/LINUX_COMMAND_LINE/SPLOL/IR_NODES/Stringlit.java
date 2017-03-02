package IR_NODES;

import java.util.ArrayList;

public class Stringlit {
	String str;
	int index;
	static int counter=0;
	public static ArrayList<Stringlit> strList=new ArrayList<>();
	
	public String getStr() {
		return str;
	}

	public int getIndex() {
		return index;
	}

	public Stringlit(String str){
		str=str.substring(1,str.length()-1);
		this.str=str;
		index=counter;
		counter++;
	}
}
