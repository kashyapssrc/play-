package com.objectfrontier.training.java.base;
class Datatype {

    public void getClassName() {

        System.out.println(boolean.class);
        System.out.println(byte.class);
        System.out.println(short.class);
        System.out.println(char.class);
        System.out.println(int.class);
        System.out.println(long.class);
        System.out.println(float.class);
        System.out.println(double.class);
    }
}

public class Primitive {

    public static void main(String args[]) {

        Datatype primitive = new Datatype();
        primitive.getClassName();
        System.out.println(primitive.getClass().getSimpleName()); //To get classname of primitive object
    }
}

