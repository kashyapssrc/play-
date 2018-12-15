package com.objectfrontier.training.java.base;
public class MinVariablesDemo {

    public static void main(String args[]) {

        //integers
        byte largestByte = Byte.MIN_VALUE;
        short largestShort = Short.MIN_VALUE;
        int largestInteger = Integer.MIN_VALUE;
        long largestLong = Long.MIN_VALUE;

        //real numbers
        float largestFloat = Float.MIN_VALUE;
        double largestDouble = Double.MIN_VALUE;


        //Display them all.
        System.out.println("The Smallest byte value is " + largestByte + ".");
        System.out.println("The Smallest short value is " + largestShort + ".");
        System.out.println("The Smallest integer value is " + largestInteger + ".");
        System.out.println("The Smallest long value is " + largestLong + ".");

        System.out.println("The Smallest float value is " + largestFloat + ".");
        System.out.println("The Smallest double value is " + largestDouble + ".");
    }
}
