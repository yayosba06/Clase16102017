package com.colas.example;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

public class LeerMensaje {

	public static void main(String[] args) {

		ReadMessage readMessage = new ReadMessage();
		String queue = "ALUMNOS_OBJ";

		try {

//			List<String> cola = readMessage.readMessages(queue);
//			cola.forEach(System.out::println);
			
			List<ObjectMessage> lista = readMessage.readObjectMessages(queue);
			
			for(ObjectMessage obj: lista) {
				Alumno alumno = (Alumno)obj.getObject();
				System.out.println(alumno.getIdAlumno());
				System.out.println(alumno.getApellidos());
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
