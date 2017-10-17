package com.colas.example;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * This class read message from Message Queue
 * @author ALDO
 *
 */
public class ReadMessage {

	private static final String URL = "tcp://localhost:61616";
	private static final String USER = ActiveMQConnection.DEFAULT_USER;
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;	
	private static final boolean TRANSACTED_SESSION = false;
	private static final int TIMEOUT = 1000;
	
	/**
	 * This method read messages from queue
	 * @param destinationQueue, this is queue where the method will read message
	 * @return a list of messages
	 * @throws JMSException, this the exception
	 */
	public List<String> readMessages(String destinationQueue) throws JMSException {
		 
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USER, PASSWORD, URL);
        connectionFactory.setTrustAllPackages(true);
        final Connection connection = connectionFactory.createConnection();
 
        connection.start();
 
        final Session session = connection.createSession(TRANSACTED_SESSION, Session.AUTO_ACKNOWLEDGE);
        final Destination destination = session.createQueue(destinationQueue);
        final MessageConsumer consumer = session.createConsumer(destination);
 
        List<String> messageList = processAllMessagesInQueue(consumer);
 
        consumer.close();
        session.close();
        connection.close(); 
        
        return messageList;
        
    }
	
	/**
	 * This method read objects from queue
	 * @param destinationQueue, this is queue where the method will read objects
	 * @return a list of objects
	 * @throws JMSException, this the exception
	 */
	public List<ObjectMessage> readObjectMessages(String destinationQueue) throws JMSException {
		 
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USER, PASSWORD, URL);
        final Connection connection = connectionFactory.createConnection();
 
        connection.start();
 
        final Session session = connection.createSession(TRANSACTED_SESSION, Session.AUTO_ACKNOWLEDGE);
        final Destination destination = session.createQueue(destinationQueue);
        final MessageConsumer consumer = session.createConsumer(destination);
 
        List<ObjectMessage> messageObjectList = processAllMessagesObjectInQueue(consumer);
 
        consumer.close();
        session.close();
        connection.close(); 
        
        return messageObjectList;
        
    }
	
	/**
	 * This method process all the objects from queue
	 * @param consumer, this is the class that is going to read the objects
	 * @return a list of objects
	 * @throws JMSException, this the exception
	 */
	private List<ObjectMessage> processAllMessagesObjectInQueue(MessageConsumer consumer) throws JMSException {
		 List<ObjectMessage> messageList = new ArrayList<>();
			Message message;
	        while ((message = consumer.receive(TIMEOUT)) != null) {
	        	messageList.add(proccessObject(message));
	        }
	        
	        return messageList;
	}
	
	/**
	 * This method process an object using a Object Message
	 * @param message, this the Object Message
	 * @return an Object Message
	 * @throws JMSException, this the exception
	 */
	private ObjectMessage proccessObject(Message message) throws JMSException {
        if (message instanceof ObjectMessage) {
            return (ObjectMessage) message;                   
            
        }
        
        return null;
    }	
	
	/**
	 * This method process all the messages from queue
	 * @param consumer, this is the class that is going to read the messages
	 * @return a list of messages
	 * @throws JMSException, this the exception
	 */
	private List<String> processAllMessagesInQueue(MessageConsumer consumer) throws JMSException {
        List<String> messageList = new ArrayList<>();
		Message message;
        while ((message = consumer.receive(TIMEOUT)) != null) {
        	messageList.add(proccessMessage(message));
        }
        
        return messageList;
    }
	
	/**
	 * This method process an object using a Message
	 * @param message, this the Message
	 * @return an Message
	 * @throws JMSException, this the exception
	 */
	private String proccessMessage(Message message) throws JMSException {
        if (message instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage) message;
            return textMessage.getText();            
            
        }
        
        return "";
	}

}
