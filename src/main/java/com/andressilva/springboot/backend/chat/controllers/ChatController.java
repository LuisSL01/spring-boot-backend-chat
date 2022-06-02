package com.andressilva.springboot.backend.chat.controllers;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.andressilva.springboot.backend.chat.models.documents.Mensaje;
import com.andressilva.springboot.backend.chat.models.service.ChatService;

@Controller
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private SimpMessagingTemplate webSocket;
	
	
	private String[] colores = {"red","blue","green","purple", "magenta" ,"orange"};
	
	
	@MessageMapping("/mensaje")
	@SendTo("/chat/mensaje")
	public Mensaje recibeMensaje(Mensaje mensaje) {
		mensaje.setFecha(new Date().getTime());
		if(mensaje.getTipo().equals("NUEVO_USUARIO")) {
			mensaje.setColor(colores[new Random().nextInt(colores.length)]);
			mensaje.setTexto("nuevo usuario");	
		}else {
			chatService.guardarMensaje(mensaje);
		}
		return mensaje;
	}
	
	@MessageMapping("/escribiendo")
	@SendTo("/chat/escribiendo")
	public String estaEscribiendo(String username) {
		return username.concat(" está escribiendo...");
	}
	
	@MessageMapping("/historial")
	public void historial(String clienteId){
		System.out.println("entrando a solciitar historial");
		List<Mensaje> sms = chatService.obtenerUltimos10Mensajes();
		
		System.out.println(sms);
		
		
		webSocket.convertAndSend("/chat/historial/"+clienteId,chatService.obtenerUltimos10Mensajes());
	}
}
