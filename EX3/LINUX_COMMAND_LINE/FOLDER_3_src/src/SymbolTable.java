package src;
import java.util.HashMap;
import java.util.LinkedList;
import AST.AST_TYPE;

public class SymbolTable {

    private static HashMap<String, LinkedList<SymbolNode>> symbols;
    private static LinkedList<SymbolNode> scopeStack;

    public static void init() {
    	symbols = new HashMap<String, LinkedList<SymbolNode>>();
    	scopeStack = new LinkedList<SymbolNode>();
    }
    
    private static SymbolNode getSymbolNode(String key) {
        LinkedList<SymbolNode> matches = (LinkedList<SymbolNode>) symbols.get(key);
        SymbolNode symbol;
        for(int i=0; i < matches.size(); i++) {
        	if((symbol = matches.get(i)).name.equals(key)) {
              return symbol;
          }
        }
		return null;
    }
    
    public static AST_TYPE get(String name) {
    	SymbolNode symbol = getSymbolNode(name);
    	return symbol != null ? symbol.type : null;
    }

    public static void put(String name, AST_TYPE type) {
    	SymbolNode newSymbol = new SymbolNode(name, type);
    	LinkedList<SymbolNode> lst = symbols.get(name);
        if (lst == null) {
           lst = new LinkedList<SymbolNode>();
           symbols.put(name, lst);
        }
        lst.addFirst(newSymbol);
        scopeStack.addFirst(newSymbol);
    }
    
    private static void remove(SymbolNode symbol) {
    	symbols.get(symbol.name).remove(symbol);
    }

    public static void openScope() {
    	SymbolNode dollarSymbol = new SymbolNode("$", null);
    	scopeStack.addFirst(dollarSymbol);
    }
    
    /* pops all variables until $ */
    public static void closeScope() {
    	SymbolNode first = scopeStack.pop();
    	while (!first.name.equals("$")) {
    		remove(first);
    		first = scopeStack.pop();
    	}
    }
    
    public static void print() {
    	System.out.println(symbols);
    	System.out.println(scopeStack);
    }

	public static boolean isInCurrentScope(String string) {
		// TODO Auto-generated method stub
		return false;
	}
    
}

class SymbolNode {
	String name;
	AST_TYPE type;
	SymbolNode(String name, AST_TYPE type) {
		this.name = name;
		this.type = type;
	}
}