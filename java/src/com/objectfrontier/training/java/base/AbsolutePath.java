package com.objectfrontier.training.java.base;
public class AbsolutePath {

    public static void main(String args[]) {

        String path;
        path = AbsolutePath.class
                           .getProtectionDomain()
                           .getCodeSource()
                           .getLocation()
                           .getPath();

        System.out.println ("the path of the current class is" + path);
    }
}

