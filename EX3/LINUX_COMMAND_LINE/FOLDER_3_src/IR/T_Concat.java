package IR;

import src.CGen;

public class T_Concat implements T_Exp {
    T_Temp s1, s2;

    public T_Concat(T_Temp s1, T_Temp s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    /**
     * creates a new string that is the result of concatenating s1 and s2.
     * pseudo code:
     *  validate s1 and s2 are not null
     *  len1 := [s1 + 0]
     *  len2 := [s2 + 0]
     *  totalLen := len1 + len2
     *  s := malloc((totalLen + 1) * 4)
     *  [s + 0] := totalLen
     *  count := 1
     *  Loop1:
     *      cjump count > len1 => GOTO Exit1
     *      readChar := [s1 + count * 4]
     *      [s + count * 4] := readChar
     *      count++
     *      jump Loop1
     *  Exit1:
     *  countS2 := 1
     *  Loop2:
     *      cjump count > totalLen => GOTO Exit2
     *      readChar := [s2 + countS2 * 4]
     *      [s + count * 4] := readChar
     *      count++
     *      countS2++
     *      jump Loop2
     *  Exit2:
     * @return temp holding address to the result string
     */
    @Override
    public T_Temp gen() {
        //check not null
        accessViolation(s1).gen();
        accessViolation(s2).gen();
        //get lengths of strings
        T_Temp len1 = new T_Temp("s1Len", true);
        T_Temp len2 = new T_Temp("s2Len", true);
        T_Seq getLens = new T_Seq(
                getByOffset(len1, s1, new T_Const(0)),
                getByOffset(len2, s2, new T_Const(0))
        );
        getLens.gen();
        T_Temp totalLen = new T_Temp("s1s2TotalLen", true);
        T_Move getTotalLen = new T_Move(
                totalLen,
                new T_Binop(
                        BINOPS.PLUS,
                        len1,
                        len2
                )
        );
        getTotalLen.gen();
        //alloc new string
        T_Temp s = new T_Temp("concatResult", true);
        new T_Move(s, new T_Malloc(mult(add(totalLen, 1), 4))).gen();
        //write len on start of result array
        new T_Move(new T_Mem(add(s, 0)), totalLen).gen();
        //write loops
        //copy s1
        T_Temp count = new T_Temp("loopCounter", true);
        new T_Move(count, new T_Const(1)).gen();
        T_Label loop = new T_Label("Loop1", true);
        T_Label exit = new T_Label("Exit1", true);
        T_CJump cJumpExit = new T_CJump(new T_Relop(RELOPS.GT, count, len1), exit);
        loop.gen();
        cJumpExit.gen();
        T_Binop readAddress = new T_Binop(BINOPS.PLUS, s1, mult(count, 4));
        T_Binop writeAddress = new T_Binop(BINOPS.PLUS, s, mult(count, 4));
        T_Temp readChar = new T_Temp();
        new T_Move(readChar, new T_Mem(readAddress)).gen();
        new T_Move(new T_Mem(writeAddress), readChar).gen();
        new T_Move(count, add(count, 1)).gen();
        new T_JumpLabel(loop).gen();
        exit.gen();
        //copy s2
        T_Temp countS2 = new T_Temp("loopCounterS2", true);
        new T_Move(countS2, new T_Const(1)).gen();
        loop = new T_Label("Loop2", true);
        exit = new T_Label("Exit2", true);
        cJumpExit = new T_CJump(new T_Relop(RELOPS.GT, count, totalLen), exit);
        loop.gen();
        cJumpExit.gen();
        readAddress = new T_Binop(BINOPS.PLUS, s2, mult(countS2, 4));
        writeAddress = new T_Binop(BINOPS.PLUS, s, mult(count, 4));
        new T_Move(readChar, new T_Mem(readAddress)).gen();
        new T_Move(new T_Mem(writeAddress), readChar).gen();
        new T_Move(count, add(count, 1)).gen();
        new T_Move(countS2, add(countS2, 1)).gen();
        new T_JumpLabel(loop).gen();
        exit.gen();
        //done with loops,
        //return string pointer
        return s;
    }

    private T_Move getByOffset(T_Temp to, T_Temp from, T_Exp offset) {
        return new T_Move(to, new T_Mem(new T_Binop(BINOPS.PLUS, from, offset)));
    }

    private T_Binop add(T_Exp t, int n) {
        return new T_Binop(BINOPS.PLUS, t, new T_Const(n));
    }

    private T_Binop mult(T_Exp t, int n) {
        return new T_Binop(BINOPS.TIMES, t, new T_Const(n));
    }

    private T_CJump accessViolation(T_Temp temp) {
        T_Relop relop = new T_Relop(RELOPS.EQUAL, temp, new T_Const(0));
        return new T_CJump(relop, new T_Label("access_violation"));
    }
}
