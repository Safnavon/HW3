package IR;

public class T_Label implements T_Exp {
    private String name;
    public T_Label(){
        this.name = null;
    }
    public T_Label(String name) {
        this.name = name;
    }

    public String getName(String suffix) {
        if (this.name == null) {
            this.name = TempFactovider.newLabel(suffix);
        }
        return this.name;
    }

    public String getName(){
        return  getName(null);
    }
}