package com.objectfrontier.training.java.base;
//class DisplaySum
public class Adder {

    //void execute()
    public static void main (String[] args) throws Exception {

        int total = 0;

        //program.verifyIntArgs()
        if (args.length > 1) {

            //program.getSum();
            for(int i = 0; i < args.length; i++) {
                total = total + Integer.parseInt(args[i]);
            }

            //console console = System.getConsole()
            //console.displaySum()
            System.out.println(total);

            //console.Exit
            System.exit(1);

        //console.display(error message)
        } else {
            throw new Exception ("enter more than one argument");
        }
    }
}
