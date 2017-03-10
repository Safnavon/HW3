package IR;

public class T_Temp implements T_Exp {
    private String name;

    public T_Temp() {
        this.name = null;
    }

    public T_Temp(String specialName) {
        this(specialName, false);
    }

    public T_Temp(String specialName, boolean forceFactovider) {
        if (forceFactovider) {
            this.name = TempFactovider.newTemp();
        } else {
            this.name = specialName;
        }
    }

    public String getName() {
        if (this.name == null) {
            this.name = TempFactovider.newTemp();
        }
        return this.name;
    }

    @Override
    public T_Temp gen() {
        return this;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}