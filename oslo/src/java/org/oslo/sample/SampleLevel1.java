package org.oslo.sample;

/**
 * Created by IntelliJ IDEA.
 * User: ac08683
 * Date: 30.apr.2003
 * Time: 14:46:58
 * To change this template use Options | File Templates.
 */
public class SampleLevel1 {
  SampleLevel2 sampleLevel2 = new SampleLevel2();

  public SampleLevel1() {
  }

  public boolean doSomethingLevel1() {
    int number = sampleLevel2.doSomethingLevel2nr1();
    Object object = sampleLevel2.doSomethingLevel2nr2();
    return true;
  }
}
