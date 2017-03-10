package IR;

import src.CGen;

public class T_Relop implements T_Exp {

    public RELOPS op;

    public T_Exp left, right;

    public T_Relop(RELOPS op, T_Exp left, T_Exp right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public T_Temp gen() {
        T_Temp leftGen = left.gen();
        T_Temp rightGen = right.gen();

        T_Temp zero = new T_Temp();
        T_Temp res = new T_Temp();
        T_Label skipSetFalse = new T_Label("TRUE",true);

        CGen.append(String.format(
                "\tli\t%1$s, %2$s%n",
                zero,0
        ));

        CGen.append(String.format(
                "\tli\t%1$s, %2$s%n",
                res,1
        ));



        switch (op){
            case EQUAL:
                CGen.append(String.format(
                        "\tbeq\t%1$s, %2$s, %3$s%n",
                        leftGen,rightGen,skipSetFalse.getName()
                ));
                break;

            case GT:
                CGen.append(String.format(
                        "\tbgt\t%1$s, %2$s, %3$s%n",
                        leftGen,rightGen,skipSetFalse.getName()
                ));
                break;

            case GTE:
                CGen.append(String.format(
                        "\tbge\t%1$s, %2$s, %3$s%n",
                        leftGen,rightGen,skipSetFalse.getName()
                ));
                break;
            case LT:
                CGen.append(String.format(
                        "\tblt\t%1$s, %2$s, %3$s%n",
                        leftGen,rightGen,skipSetFalse.getName()
                ));
                break;
            case LTE:
                CGen.append(String.format(
                        "\tble\t%1$s, %2$s, %3$s%n",
                        leftGen,rightGen,skipSetFalse.getName()
                ));
                break;
            case NEQUAL:
                CGen.append(String.format(
                        "\tbne\t%1$s, %2$s, %3$s%n",
                        leftGen,rightGen,skipSetFalse.getName()
                ));
                break;
        }
        
        CGen.append(String.format(
                "\taddi\t%1$s, %2$s, %3$s%n",
                res,zero,0
        ));

        skipSetFalse.gen();

        return  res;



    }
}