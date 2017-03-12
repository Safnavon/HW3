package src;

import AST.AST_TYPE;
import java.util.HashMap;
import java.util.LinkedList;

public class IRUtils {

    private static int offset = 1;
    private static HashMap<String, LinkedList<Var> > varTable;
    private static LinkedList<Var> scopeStack;
    public static String currentClass;
    public static boolean isInMain = false;

    public static void init() {
        varTable = new HashMap<String, LinkedList<Var> >();
        scopeStack = new LinkedList<Var>();
    }

    public static Var getVar(String key) {
        LinkedList<Var> matches = (LinkedList<Var>) varTable.get(key);
        if (matches != null) {
            Var symbol;
            for (int i = 0; i < matches.size(); i++) {
                if ((symbol = matches.get(i)).name.equals(key)) {
                    return symbol;
                }
            }
        }
        return null;
    }

    public static void pushVar(String name, AST_TYPE type, SCOPE_TYPE scope, int offset) {
        Var newSymbol = new Var(name, type, scope, offset);
        LinkedList<Var> lst = varTable.get(name);
        if (lst == null) {
            lst = new LinkedList<Var>();
            varTable.put(name, lst);
        }
        lst.addFirst(newSymbol);
        scopeStack.addFirst(newSymbol);
    }

    public static void pushVar(String name, AST_TYPE type, SCOPE_TYPE scope) {
        int offset = IRUtils.getOffset();
        pushVar(name, type, scope, offset);
        offset++;
    }

    private static void remove(Var symbol) {
        varTable.get(symbol.name).remove(symbol);
    }

    public static void openScope() {
        Var dollar = new Var("$", null, null, 0);
        scopeStack.addFirst(dollar);
    }

    /* pops all variables until $ */
    public static void closeScope() {
        Var first = scopeStack.pop();
        while (!first.name.equals("$")) {
            remove(first);
            first = scopeStack.pop();
        }
    }

    public static int getOffset() {
        return offset;
    }

    public static void resetOffset() {
        offset = 1;
    }
}

