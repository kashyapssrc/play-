package com.objectfrontier.training.java.base;
class VariableArguments {

    public void first(int var, String...stringvar) {
        System.out.println("overloading when int and string values are given " + var + stringvar[0] + stringvar[1]);
    }

    public void first(String stringvar,int...var) {
        System.out.println("overloading when string and int values are given " + stringvar + var[0] + var[1]);
    }

    public void first(int var, int...intvar) {
        System.out.println("overloading when int and int(varargs) are given" + var + intvar[0] + intvar[1] + intvar[2]);
    }

    public void first(int var, int intvar) {
        System.out.println("overloading when single int values are given" + var + intvar);
    }
}

public class VarArgsOverload {

    public static void main(String args[]) {

        VariableArguments varargs = new VariableArguments();
        varargs.first(1, "abhishek", "delhi");
        varargs.first("abhishek", 1, 2, 3);
        varargs.first(1, 1, 2, 3);
        varargs.first(1, 1);
    }
}

