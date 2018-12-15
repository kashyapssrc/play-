package com.objectfrontier.training.java.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

//class Myjavap
public class MyJavap {

//To get the  className
    private void getClassName(String[] args) throws Exception {

        String enteredClass = args[0];
        Class currentClass = Class.forName(enteredClass);
        System.out.println("compiled from " + currentClass.getSimpleName() + ".java ");
    }

//To get the class Details
    private void classDetails(String[] args) throws Exception {

        String enteredClass = args[0];
        Class currentClass = Class.forName(enteredClass);
        String modifier = getModifier(currentClass);
        String interfaceDet = getInterface(currentClass);
        String methodDet = getMethods(currentClass);
        String consDet = getConstructor(currentClass);

        //public final class java.lang.String implements java.io.Serializable, java.lang.Comparable<java.lang.String>, java.lang.CharSequence
        System.out.println(modifier + interfaceDet);
        System.out.println(consDet);
        System.out.println(methodDet);
    }

//To get modifiers in current class
    private String getModifier(Class currentClass) {

        StringBuilder sb = new StringBuilder();
        int modifier = currentClass.getModifiers();
        String modifierDet = parseModifier(modifier);

        sb.append(" ")
        .append(modifierDet)
        .append(" ")
        .append("class ")
        .append(currentClass.getName());

        return sb.toString();

    }

//To get methods in class
    private String getMethods(Class currentClass) {

        StringBuilder sb = new StringBuilder();
        Method[] methodArray = currentClass.getDeclaredMethods();

        for (Method method : methodArray) {

            int modifier = method.getModifiers();
            String methodName = method.getReturnType().getSimpleName();
            String modifierDet = parseModifier(modifier);

            sb.append(modifierDet)
              .append(" ")
              .append(methodName)
              .append(" ")
              .append(method.getName())
              .append("(")
              .append(countParameter(method))
              .append(")")
              .append("\n");
        }
        return sb.toString();
    }


//To get modifiers of a method
    private String parseModifier(int modifier) {

        StringBuilder sb = new StringBuilder();

             if (Modifier.isPublic(modifier))       { sb.append("public "); }
        else if (Modifier.isPrivate(modifier))      { sb.append("private "); }
        else if (Modifier.isProtected(modifier))    { sb.append("protected "); }

             if (Modifier.isStatic(modifier))       { sb.append("static "); }
        else if (Modifier.isAbstract(modifier))     { sb.append("abstract "); }
        else if (Modifier.isFinal(modifier))        { sb.append("final "); }
        else if (Modifier.isSynchronized(modifier)) { sb.append("synchronized "); }
        else if (Modifier.isTransient(modifier))    { sb.append("transient "); }
        else if (Modifier.isNative(modifier))       { sb.append("native "); }
        else if (Modifier.isVolatile(modifier))     { sb.append("volatile "); }
        else if (Modifier.isInterface(modifier))    { sb.append("interface "); }

        return sb.toString();

    }

//To get paramenters of a method
    private <T> String  countParameter(T method) {

        StringBuilder sb = new StringBuilder();
        Class[] parameter =  ((Executable) method).getParameterTypes();

        if (parameter.length > 0) {

            for(Class params : parameter) {

                    sb.append(params.getSimpleName())
                      .append(", " );
            }

            sb.replace(sb.length() - 2, sb.length(), "");
            return sb.toString();
        }
        return " ";
    }

    private String getConstructor(Class currentClass ) {

        Constructor[] constructor = currentClass.getConstructors();
        StringBuilder sb = new StringBuilder();

        for(Constructor construct : constructor) {

            int modifier = construct.getModifiers();
            String modifierDet = parseModifier(modifier);
            String consName = construct.getName();
            String consParam = countParameter(construct);

            sb.append(modifierDet)
              .append(consName)
              .append(" ")
              .append(consParam)
              .append("\n");
        }
        return sb.toString();
    }


//To get interface in the current class
    private String getInterface(Class currentClass) {

        Class[] interfaceDet = currentClass.getInterfaces();
        StringBuilder sb = new StringBuilder();

        if (interfaceDet.length > 1) { sb.append(" implements");

            for (Class interfaceVar : interfaceDet) {

                sb.append(" ")
                  .append(interfaceVar.getName())
                  .append(",");
            }
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    //void execute
    public static void main(String[] args) throws Exception {

        MyJavap executeOf = new MyJavap();

        //Class cc = getClass();
        executeOf.getClassName(args);

        //Class classDetails = cc.getClassDetails();
        //Console console = getConsole();
        executeOf.classDetails(args);

    }
}
