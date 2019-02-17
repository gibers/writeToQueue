package com.example.demo;

import org.apache.activemq.ActiveMQSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.File;
import java.io.FileInputStream;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    JmsTemplate jmsTemplate;

//    @Autowired
//    JmsListenerContainerFactory jmsL ;

    @Test
    public void contextLoads() {
        jmsTemplate.convertAndSend("inputItemQueue", "je suis un message 22" );

//        jmsTemplate.convertAndSe

//        jmsTemplate.convertAndSend("inputItemQueue", "mess", new MessagePostProcessor() {
//            @Override
//            public Message postProcessMessage(Message message) throws JMSException {
//                return message;
//            }
//        });

//        File f = new File("src\\data\\a.txt");
        File f = new File("src//data//a.txt");
        String absolutePath = f.getAbsolutePath();
        System.out.println("f.isDirectory()  => " + f.isDirectory() );
//        int length = f.listFiles().length;
//        System.out.println( "length " + length);
//        System.out.println("absolutePath => " + absolutePath);

//        sendMessage("src//data//a.txt");

    }

    public void sendMessage(String fileName) {

        jmsTemplate.send( "inputItemQueue" ,new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                try {
                    BytesMessage bytesMessage = session.createBytesMessage();
                    FileInputStream fileInputStream = new FileInputStream(fileName);
                    final int BUFLEN = 64;
                    byte[] buf1 = new byte[BUFLEN];
                    int bytes_read = 0;
                    while ((bytes_read = fileInputStream.read(buf1)) != -1) {
                        bytesMessage.writeBytes(buf1, 0, bytes_read);
                    }
                    fileInputStream.close();
                    bytesMessage.setStringProperty("fileName", "toto.zip");


                    return bytesMessage;
                } catch (Exception e) {
                    return null;
                }

            }
        });
    }

}
