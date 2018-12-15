package com.objectfrontier.training.java.base;
public class EnumCompare {

    enum DaysOfAWeek {

        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY,
    }

    public static void main(String args[]) {

        DaysOfAWeek day1 = DaysOfAWeek.MONDAY;
        DaysOfAWeek day2 = DaysOfAWeek.TUESDAY;
        DaysOfAWeek day8 = DaysOfAWeek.MONDAY;
        Boolean dayone = (day1 == day2);  //false
        Boolean daytwo = (day1.equals(day2)); //false
        Boolean dayel = (day1.equals(day8)); //true
        Boolean dayto = (day1 == day8); //true
        System.out.println(dayone);
        System.out.println(daytwo);
        System.out.println(dayel);
        System.out.println(dayto);
    }
}


