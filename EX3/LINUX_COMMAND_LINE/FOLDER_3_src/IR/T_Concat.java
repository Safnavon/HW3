package IR;

public class T_Concat implements T_Exp {
    T_Temp s1, s2;

    public T_Concat(T_Temp s1, T_Temp s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

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
        //write loops
        T_Temp count = new T_Temp("loopCounter",true);
        new T_Move(count, new T_Const(1)).gen();
        T_Label loop = new T_Label("Loop1", true);
        T_Label exit = new T_Label("Exit1", true);
        T_CJump cJumpExit = new T_CJump(new T_Relop(RELOPS.GT, count, len1), exit);
        T_JumpLabel jumpLoop = new T_JumpLabel(loop);
        loop.gen();
        cJumpExit.gen();
        throw new Error("WIP");

    }

    private T_Move getByOffset(T_Temp to, T_Temp from, T_Exp offset) {
        return new T_Move(to, new T_Mem(new T_Binop(BINOPS.PLUS, from, offset)));
    }

    private T_Binop plusOne(T_Exp t) {
        return new T_Binop(BINOPS.PLUS, t, new T_Const(1));
    }

    private T_CJump accessViolation(T_Temp temp) {
        T_Relop relop = new T_Relop(RELOPS.EQUAL, temp, new T_Const(0));
        return new T_CJump(relop, new T_Label("access_violation"));
    }
}
