package com.objectfrontier.training.java.base;
public class FPAdder {

    public static void main (String args[]) throws Exception {

        float total = 0;

        if (args.length > 1) {

            for (String arg : args) {
                total = total + Float.parseFloat(arg);
            }

            System.out.println(String.format("%.2f",total));
            System.exit(1);
        } else {
            throw new Exception("enter more than one argument");
        }
    }
}


