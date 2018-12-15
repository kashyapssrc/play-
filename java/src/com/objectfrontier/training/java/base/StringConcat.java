package com.objectfrontier.training.java.base;
//class StringConcat
public class StringConcat {

    //void execute()
    public static void main(String[] args) {

        //getStrings()
        String hi = "Hi, ";
        String mom = "mom.";

        //String concatString = getConcatString()
        //Console console = System.getConsole()
        System.out.println("concatenated using method\t" + hi.concat(mom));
        System.out.println("concatenated using operartor\t" + hi + mom);
    }
}
