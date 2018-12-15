package com.objectfrontier.training.java.base;
public class NumberHolder {

    public int anInt;
    public float aFloat;

    public static void main(String args[]) {

        NumberHolder myNumber = new NumberHolder();
        myNumber.anInt = 3;
        myNumber.aFloat = 100.02f;
        System.out.println("the integer is " + myNumber.anInt);
        System.out.println("the float is" + myNumber.aFloat);
    }
}
