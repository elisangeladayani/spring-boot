package br.com.appday.amqp;

import java.util.concurrent.CountDownLatch;

/**
 * Created by vbatista on 3/6/16.
 */
public class AMQPReceiver {


    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
