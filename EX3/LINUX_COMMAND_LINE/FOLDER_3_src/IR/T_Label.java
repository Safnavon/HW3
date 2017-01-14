package IR;

public class T_Label implements T_Exp {
    String name;
    public T_Label(){
        this.name = null;
    }
    public T_Label(String name) {
        this.name = name;
    }

    public String getName() {
        if (this.name == null) {

            //TODO this.name = getFromLabelProvider();
        }
        return this.name;
    }
}