package com.ws.alpha.rabbit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Send.class)
public class SendTest {

    @Autowired
    private Send send;

    @Test
    public void sendTest() throws Exception {
        while(true){
            String msg = new Date().toString();
            send.send(msg);
            Thread.sleep(1000);
        }
    }

}