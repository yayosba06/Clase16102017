package com.colas.example;

import java.util.Arrays;

import javax.jms.JMSException;

public class Messages {
	
	public static void main(String[] args) {
		
		SendMessage sendMessage = new SendMessage();
		String queue = "ALUMNOS";
		try {
			
			sendMessage.sendMessages(queue, Arrays.asList("Javier", "Vilialdo", "Erick", "Luis", "Clara", "Ricardo"));
//			Alumno alumno = new Alumno();
//			alumno.setIdAlumno(1);
//			alumno.setNombres("Clara");
//			alumno.setApellidos("Justino");
//			alumno.setEstadoCivil("Soltero");
//			sendMessage.sendObject(queue, alumno);
//			
			
		} catch (JMSException e) {			
			e.printStackTrace();
		}
	}

}
