package IR;

import src.CGen;
/**
 * used for integer ops.
 */
public class T_Binop implements T_Exp {

	public BINOPS op;

	public T_Exp left,right;

	public T_Binop(BINOPS op, T_Exp left, T_Exp right){
		this.op =op;
		this.left=left;
		this.right=right;
	}

	@Override
	public T_Temp gen() {
	    T_Temp left = this.left.gen();
	    T_Temp right = this.right.gen();
	    T_Temp res = new T_Temp();

		switch (this.op){
            case PLUS:
                CGen.append(String.format(
                        "\tadd\t%s, %s, %s%n",
                        res,left,right
                ));
                break;
            case MINUS:
                CGen.append(String.format(
                        "\tsub\t%s, %s, %s%n",
                        res,left,right
                ));
                break;
            case TIMES:
                CGen.append(String.format(
                        "\tmul\t%2$s, %3$s%n"+
                        "\tmflo\t%1$s%n",//takes only first 32 bits of the result
                        res,left,right
                ));
                break;
            case DIVIDE:
                CGen.append(String.format(
                        "\tdiv\t%2$s, %3$s%n"+
                        "\tmflo\t%1$s%n",
                        res,left,right
                ));
                break;
        }
        return res;
	}


}