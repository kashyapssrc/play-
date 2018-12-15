package com.objectfrontier.training.java.base;
class Animal {

    public void run() {

        System.out.println("running");

    }

    public void bite() {

        System.out.println("animal bites");

    }

    public void eat() {

        System.out.println("eating");

    }

}

class Dog extends Animal {

    public void bark() {

        System.out.println("barking");

        }

}

class Snake extends Animal {

    @Override
    public void bite() {

        System.out.println("venomous bite");

    }

    public void bite(int n) {

        System.out.println("The snake bit " + n + "times");

    }

}

class Cat extends Animal {

    @Override
    public void bite() {

        System.out.println("painful bite");

    }

}


public class OopsConcept {

    public static void main (String args[]) {

        Animal harry = new Dog();
        Dog barry = new Dog();
        Cat tom = new Cat();
        Snake kala = new Snake();
        kala.run();  //inheritance
        kala.bite();
        kala.bite(2);
        kala.bite(20);  //overload
        barry.bark();
        tom.bite();  //override
        harry.run();
    }

}
