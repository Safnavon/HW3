package src;

import AST.AST_TYPE;

public class Var {
    public String name;
    public SCOPE_TYPE scope;
    AST_TYPE type;
    public int offset;

    public Var(String name, AST_TYPE type, SCOPE_TYPE scope, int offset) {
        this.name = name;
        this.scope = scope;
        this.offset = offset;
        this.type = type;
    }
}
