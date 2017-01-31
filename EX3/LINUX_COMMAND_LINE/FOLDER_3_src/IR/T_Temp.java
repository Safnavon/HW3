package IR;

public class T_Temp implements T_Exp {
    private String name;

    public T_Temp() {
        this.name = null;
    }

    public T_Temp(String name) {
        this.name = name;
    }

    public String getName() {
        if (this.name == null) {
            this.name = TempFactovider.newTemp();
        }
        return this.name;
    }
}