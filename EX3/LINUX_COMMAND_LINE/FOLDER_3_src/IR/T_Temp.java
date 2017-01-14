package IR;

public class T_Temp {
    String name;

    public T_Temp() {
        this.name = null;
    }

    public T_Temp(String name) {
        this.name = name;
    }

    public String getName() {
        if (this.name == null) {

            //TODO this.name = getFromTempProvider();
        }
        return this.name;
    }
}