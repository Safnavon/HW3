package IR;


import src.CGen;

public class T_Label implements T_Exp {
    private String name;
    public T_Label(){
        this.name = null;
    }
    public T_Label(String name) {
        this.name = TempFactovider.newLabel(name);
    }

    public String getName() {
        if (this.name == null) {
            this.name = TempFactovider.newLabel(null);
        }
        return this.name;
    }

    @Override
    public T_Temp gen() {
        CGen.append(String.format(
                "%s:%n\t",
                this.getName()
        ));
        return null;
    }
}