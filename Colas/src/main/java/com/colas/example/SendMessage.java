package com.colas.example;

import java.util.List;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * This class send messages to MessageQueue
 * @author ALDO
 *
 */
public class SendMessage {
	
	private static final String URL = "tcp://localhost:61616";
	private static final String USER = ActiveMQConnection.DEFAULT_USER;
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final boolean TRANSACTED_SESSION = true;
	
	/**
	 * This method send a message to the queue
	 * @param destinationQueue, this is queue
	 * @param message, this the message that is going to send
	 * @throws JMSException, this is the exception
	 */
	public void sendMessage(String destinationQueue, String message) throws JMSException {

		final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USER, PASSWORD, URL);
		connectionFactory.setTrustAllPackages(true);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		final Session session = connection.createSession(TRANSACTED_SESSION, Session.AUTO_ACKNOWLEDGE);
		final Destination destination = session.createQueue(destinationQueue);

		final MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);

		createMessage(session, producer, message);
		session.commit();

		session.close();
		connection.close();	
		
	}
	
	/**
	 * This method send a object to the queue
	 * @param destinationQueue, this is queue
	 * @param object, this the object that is going to send
	 * @throws JMSException, this is the exception
	 */
	public void sendObject(String destinationQueue, Alumno object) throws JMSException {

		final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USER, PASSWORD, URL);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		final Session session = connection.createSession(TRANSACTED_SESSION, Session.AUTO_ACKNOWLEDGE);
		final Destination destination = session.createQueue(destinationQueue);

		final MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);

		createObject(session, producer, object);
		session.commit();

		session.close();
		connection.close();	
		
	}
	
	/**
	 * This method send a messages to the queue
	 * @param destinationQueue, this is the queue
	 * @param messages, this is message list
	 * @throws JMSException, this is the exception
	 */
	public void sendMessages(String destinationQueue, List<String> messages) throws JMSException {

		final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USER, PASSWORD, URL);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		final Session session = connection.createSession(TRANSACTED_SESSION, Session.AUTO_ACKNOWLEDGE);
		final Destination destination = session.createQueue(destinationQueue);

		final MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);

		createMessages(session, producer, messages);
		session.commit();

		session.close();
		connection.close();	
		
	}
	
	/**
	 * This method create messages that will send to the queue
	 * @param session, a session opened with the activeMQ
	 * @param producer, the object that  send the messages to the queue
	 * @param messages, a list of messages
	 * @throws JMSException
	 */
	private void createMessages(Session session, MessageProducer producer, List<String> messages) throws JMSException {
		for(String row: messages){
			createMessage(session, producer, row);
		}
	}
	
	/**
	 * This method create a message that will send to the queue
	 * @param session, a session opened with the activeMQ
	 * @param producer, the object that  send the message to the queue
	 * @param message, a message
	 * @throws JMSException
	 */
	private void createMessage(Session session, MessageProducer producer, String message) throws JMSException {
		final TextMessage textMessage = session.createTextMessage(message);
		producer.send(textMessage);
	}
		
	/**
	 * This method create a object and send to the queue
	 * @param session,  a session opened with the activeMQ 
	 * @param producer, the object that  send the object to the queue
	 * @param object, this is the object to send
	 * @throws JMSException
	 */
	private void createObject(Session session, MessageProducer producer, Alumno object) throws JMSException {
		 ObjectMessage msg = session.createObjectMessage(); 
         msg.setObject(object);         
         producer.send(msg);
	}
	

}
