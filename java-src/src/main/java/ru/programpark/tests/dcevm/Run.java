package ru.programpark.tests.dcevm;

/**
 * Created by kozyr on 19.01.15.
 */
public class Run {
    public static void main(String[] args) throws InterruptedException {
        int count =0;
        while(true){
            Test.test(count++);
            Thread.sleep(10000);
        }
    }
}
