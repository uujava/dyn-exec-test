package ru.programpark.tests.dcevm;

/**
 * Created by kozyr on 19.01.15.
 */
public class Test {
    static TestA obj;
    public static void test(int i){
        if(obj==null){
            obj = new TestA();
        }
        obj.test2(i, "test");
    }
}
