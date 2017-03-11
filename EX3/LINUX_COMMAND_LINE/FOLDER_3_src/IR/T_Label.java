package IR;

import src.CGen;

public class T_Label implements T_Exp {
    String name;

    public T_Label() {
        this.name = null;
    }

    public T_Label(String specialName) {
        this(specialName, false);
    }

    public T_Label(String specialName, boolean forceFactovider) {
        if (forceFactovider) {
            this.name = TempFactovider.newLabel(specialName);
        } else {
            this.name = "Label_0_" + specialName;
        }
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
                "%s:%n",
                this.getName()
        ));
        return null;
    }
}