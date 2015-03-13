package ru.programpark.tests.dcevm;

/**
 * Created by kozyr on 19.01.15.
 */
public class TestA {
    int f=0;

    public void test2(int i, String test){
        System.out.println("true2 = " + i + " static: " + TestB.TEMP + " arg: " +test + " f: " + f) ;
    }
}
