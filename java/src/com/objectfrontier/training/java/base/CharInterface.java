package com.objectfrontier.training.java.base;
public class CharInterface implements CharSequence {

    public char charAt(int index) {

        String str = "java lang API is used here";
        return str.charAt(index);
    }

    public int length() {

        String str = "java lang API is used here";
        return str.length();
    }

    public CharSequence subSequence(int start, int end) {

        String str = "java lang API is used here";
        return str.subSequence(start,end);

    }

    public static void main(String args[]) {

        CharInterface charIn = new CharInterface();
        System.out.println("java lang API is used here");
        System.out.println(charIn.charAt(11));
        System.out.println(charIn.length());
        System.out.println(charIn.subSequence(0,4));
    }
}


