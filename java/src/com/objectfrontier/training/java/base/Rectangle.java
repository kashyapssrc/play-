package com.objectfrontier.training.java.base;
public class Rectangle {

    public int width;
    public int height;

    public int area() {
        return width * height;
    }

    public static void main(String[] args) {

        Rectangle myRect = new Rectangle();
        myRect.width = 40;
        myRect.height = 50;
        System.out.println("myRect's area is " + myRect.area());
    }
}
