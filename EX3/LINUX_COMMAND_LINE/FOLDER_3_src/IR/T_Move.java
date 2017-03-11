package IR;

import src.CGen;

public class T_Move implements T_Exp {
    T_Exp src, dest;

    public T_Move(T_Exp dest, T_Exp src) {
        this.src = src;
        this.dest = dest;
    }

    private T_CJump accessViolation(T_Temp temp) {
        T_Relop relop = new T_Relop(RELOPS.EQUAL, temp, new T_Const(0));
        return new T_CJump(relop, new T_Label("Label_0_access_violation"));
    }

    @Override
    public T_Temp gen() {
        //TODO if src is T_Label, use "la $t0, var1"
        //TODO  if src is T_Malloc, gen malloc (returns T_Temp) and use result as temp
        //TODO  if move.dst is a T_Mem, use "sw". if move.dst is a T_Temp, use "addi"
        T_Temp srcTemp;
        if (src instanceof T_Label) {
            srcTemp = new T_Temp();
            CGen.append(String.format(
                    "\tla\t%1$s, %2$s%n",
                    srcTemp, ((T_Label) src).getName()
            ));
        } else {
            srcTemp = src.gen();
        }
        if (dest instanceof T_Mem) {
            T_Temp destAddress = ((T_Mem) dest).location.gen();
            assert destAddress != null;
            accessViolation(destAddress).gen();
            CGen.append(String.format(
                    "\tsw\t%1$s, (%2$s)%n",
                    srcTemp, destAddress
            ));
            return null;
        } else {
            T_Temp destTemp = dest.gen();
            assert destTemp != null;
            CGen.append(String.format(
                    "\taddi\t%1$s, %2$s, 0%n",
                    srcTemp,destTemp
            ));
            return destTemp;
        }
    }
}