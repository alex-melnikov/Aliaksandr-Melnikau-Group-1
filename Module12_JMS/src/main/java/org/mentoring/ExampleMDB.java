package org.mentoring;

import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig =
        {
        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Topic"),
        @ActivationConfigProperty(propertyName="destination", propertyValue="topic/testTopic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class ExampleMDB implements MessageListener
{
   public void onMessage(Message recvMsg)
   {
      System.out.println("----------------");
      System.out.println("Received message");
      System.out.println("----------------");
   }
}