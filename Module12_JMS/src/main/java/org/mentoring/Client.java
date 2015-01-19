package org.mentoring;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;

public class Client {

	public static void main(String[] args) throws Exception {
		Connection conn = null;
		Session session = null;
		Topic topic = null;
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jboss.naming.remote.client.InitialContextFactory");
		p.put(Context.PROVIDER_URL, "remote://localhost:1099");
		p.put(Context.SECURITY_PRINCIPAL, "guest");
		p.put(Context.SECURITY_CREDENTIALS, "guest");
		InitialContext ctx = new InitialContext(p);
		ConnectionFactory cf = (ConnectionFactory) ctx.lookup("jms/RemoteConnectionFactory");
		conn = cf.createConnection();
		topic = (Topic) ctx.lookup("topic/testTopic");
		session = conn.createSession(false, TopicSession.AUTO_ACKNOWLEDGE);
		conn.start();
		TextMessage tm = session.createTextMessage("JavaBeat Test Message");
		MessageProducer  	producer=session.createProducer(topic);
		producer.send(tm);
		conn.close();
		System.out.println("Message sent successfully");
	}
}
