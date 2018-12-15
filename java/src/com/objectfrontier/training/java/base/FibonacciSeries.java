package com.objectfrontier.training.java.base;
class Fibonacci {

    public void fibonacciFor() {

        System.out.println("for loop");
        int i, count = 10, previous = 0, next = 1;

        for(i = 0;i < count;i++) {

            System.out.println(previous);
            int sum = previous + next;
            previous = next;
            next = sum;
        }
    }

    public void fibonacciWhile() {

        System.out.println("while loop");
        int i = 0,count = 10,previous = 0, next = 1;

        while(i < count) {

            System.out.println(previous);
            int sum1 = previous + next;
            previous = next;
            next = sum1;
            i++;
        }
    }

    public void fibonacciRec(int count,int previous,int next) {

        if(count > 0) {
            System.out.println(previous);
            int sum = previous + next;
            previous = next;
            next = sum;
            fibonacciRec(count - 1, previous, next);
        }
    }
}

//class fibSeries{
public class FibonacciSeries {

    //void execute(){
    public static void main(String args[]) {

        Fibonacci fib = new Fibonacci();

        //program cp = generateFibonacciSeries()
        //Console console = getConsole()
        //console.display(FibonacciSeries)
        fib.fibonacciFor();
        fib.fibonacciWhile();
        System.out.println("recursion");
        fib.fibonacciRec(10, 0, 1);
    }
}

