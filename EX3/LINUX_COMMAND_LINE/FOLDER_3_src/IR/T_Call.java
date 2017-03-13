package IR;

import src.CGen;

public class T_Call implements T_Exp {
    T_Temp thisTemp;
    public T_ExpList args;
    public T_Exp prologue, epilogue;

    public T_Call(T_ExpList args, T_Temp thisTemp, T_Exp prologue, T_Exp epilogue) {
        this.args = args;
        this.thisTemp = thisTemp;
        this.prologue = prologue;
        this.epilogue = epilogue;
    }

    @Override
    public T_Temp gen() {
        // set ra on sp - 4
        new T_Move(
                new T_Mem(
                        new T_Binop(
                                BINOPS.PLUS,
                                new T_Temp("$sp"),
                                new T_Const(-4)
                        )
                ),
                new T_Temp("$ra")
        ).gen();
        // set fp on sp - 8
        new T_Move(
                new T_Mem(
                        new T_Binop(
                                BINOPS.PLUS,
                                new T_Temp("$sp"),
                                new T_Const(-8)
                        )
                ),
                new T_Temp("$fp")
        ).gen();
        // set sp on sp - 12
        new T_Move(
                new T_Mem(
                        new T_Binop(
                                BINOPS.PLUS,
                                new T_Temp("$sp"),
                                new T_Const(-12)
                        )
                ),
                new T_Temp("$sp")
        ).gen();
        // set args on sp - 16 - (len - index) REVERSE ORDER
        int argsLength = 0;
        if (args != null) {
            //assume fp and sp are old
            args.gen();
            argsLength = args.size;
        }
        // set "this" on sp - 12 - args.length - 4
        new T_Move(
                new T_Mem(
                        new T_Binop(
                                BINOPS.PLUS,
                                new T_Temp("$sp"),
                                new T_Const(-12 - argsLength * 4 - 4)
                        )
                ),
                thisTemp
        ).gen();
        // set sp to sp - 12 - args.length - 4
        new T_Move(
                new T_Temp("$sp"),
                new T_Binop(
                        BINOPS.PLUS,
                        new T_Temp("$sp"),
                        new T_Const(-12 - argsLength * 4 - 4)
                )
        ).gen();
        // set fp to sp
        new T_Move(
                new T_Temp("$fp"),
                new T_Temp("$sp")
        ).gen();
        // jalr
        this.prologue.gen();
        CGen.append(String.format("\tjalr $a1%n"));
        //after return
        T_Temp fpTemp = new T_Temp("$fp");
        T_Temp spTemp = new T_Temp("$sp");
        T_Temp raTemp = new T_Temp("$ra");
        T_Binop address;

        // load sp from fp + args.length + 4
        address = new T_Binop(BINOPS.PLUS, fpTemp, new T_Const(4 + argsLength * 4));
        T_Move initSp = new T_Move(spTemp, new T_Mem(address));
        initSp.gen();

        // load ra from fp + args.length + 12
        address = new T_Binop(BINOPS.PLUS, fpTemp, new T_Const(12 + argsLength * 4));
        T_Move initRa = new T_Move(raTemp, new T_Mem(address));
        initRa.gen();

        // load fp from fp + args.length + 8
        address = new T_Binop(BINOPS.PLUS, fpTemp, new T_Const(8 + argsLength * 4));
        T_Move initFp = new T_Move(fpTemp, new T_Mem(address));
        initFp.gen();

        // consume return value
        T_Temp resTemp = new T_Temp("return_value", true);
        CGen.append(String.format("\taddi " + resTemp + ",$a2,0%n"));

        return resTemp; // holds the result
    }
}