package hu.dpc.osgi;

import org.apache.camel.Exchange;

/**
 * Created by elek on 4/24/14.
 */
public class Setter {

    public void demodata(Exchange ex) {
        ex.getOut().setBody(new DemoData());
    }

    public String helloAddres(String test) {
        return "Address of " + test;
    }
}
