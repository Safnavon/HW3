


import java_cup.runtime.Symbol;

/** Adds line number and name information to scanner symbols.
 */
public class Token extends Symbol {
	private final int line;
	private final String name;

	public Token(int line, String name, int id, Object value) {
		super(id, line+1, line+1, value);
		this.name = name;
		this.line = line + 1;
	}
	
	public Token(int line, String name, int id) {
		super(id, line+1, line+1, null);
		this.name = name;
		this.line = line + 1;
	}
	public String toString() {
		String val = value != null ? "(" + value + ")" : "";
		return name +  val;
	}
	
	public int getLine() {
		return line;
	}
}