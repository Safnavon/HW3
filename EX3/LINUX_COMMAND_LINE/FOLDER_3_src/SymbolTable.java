import java.util.HashMap;
import java.util.LinkedList;
import AST.AST_Node;

class SymbolTable {

    private static HashMap<String, LinkedList<SymbolNode>> symbols;
    private static LinkedList<SymbolNode> scopeStack;

    void init() {
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
    
    public static AST_Node get(String name) {
    	SymbolNode symbol = getSymbolNode(name);
    	return symbol != null ? symbol.node : null;
    }

    public static void put(String name, AST_Node node) {
    	SymbolNode newSymbol = new SymbolNode(name, node);
    	LinkedList<SymbolNode> lst = symbols.get(name);
        if (lst == null) {
           lst = new LinkedList<SymbolNode>();
        }
        lst.addFirst(newSymbol);
        scopeStack.addFirst(newSymbol);
    }
    
    private static void remove(SymbolNode symbol) {
    	symbols.remove(symbol);
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
    
}

class SymbolNode {
	String name;
	AST_Node node;
	SymbolNode(String name, AST_Node node) {
		this.name = name;
		this.node = node;
	}
}