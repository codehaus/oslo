package org.oslo.sample;

/**
 * Created by IntelliJ IDEA.
 * User: ac08683
 * Date: 30.apr.2003
 * Time: 14:46:32
 * To change this template use Options | File Templates.
 */
public class SampleMain {
    SampleLevel1 sampleLevel1 = new SampleLevel1();

    public SampleMain() {
    }

    public static void main(String[] args) {
        SampleMain sampleMain = new SampleMain();
        sampleMain.doSomething();
    }

    public void doSomething() {
        boolean result = sampleLevel1.doSomethingLevel1();
        sampleLevel1.doSomethingLevel2_nr2("Hello", 10, new SampleLevel2());
    }
}
