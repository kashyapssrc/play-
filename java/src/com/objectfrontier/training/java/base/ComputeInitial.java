/**
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.objectfrontier.training.java.base;

/**
 * @author abhishek.c
 * @since Sep 10, 2018
 */

//class ComputeInitial
public class ComputeInitial {

    public void getInitial(String firstName, String lastName) {

        String fullName = firstName + " " + lastName;
        System.out.println("Full Name : " + fullName);
        System.out.println("Initial : " + lastName.toUpperCase().charAt(0) + ".");
    }

    //void execute
    public static void main(String[] args) {

        //String aName = getFullName()
        ComputeInitial Name = new ComputeInitial();

        //char initial = aName.getInitial()
        //Console console = System.getConsole()
        //console.display(Initial)
        Name.getInitial("abhishek", "chandrashekar");
    }

}
