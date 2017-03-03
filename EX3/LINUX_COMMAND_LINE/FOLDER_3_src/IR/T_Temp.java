package IR;

public class T_Temp implements T_Exp {
    private String name;

    public T_Temp() {
        this.name = null;
    }

    public T_Temp(String specialName) {
        this.name = specialName;
    }

    public String getName() {
        if (this.name == null) {
            this.name = TempFactovider.newTemp();
        }
        return this.name;
    }

    @Override
    public T_Temp gen() {
        throw new Error("unimplemented");//Dror thinks this should stay like this
    }

    @Override
    public String toString(){
        return this.getName();
    }
}