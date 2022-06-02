package com.andressilva.springboot.backend.chat.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andressilva.springboot.backend.chat.models.dao.ChatRepository;
import com.andressilva.springboot.backend.chat.models.documents.Mensaje;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepository;
	
	
	@Override
	public List<Mensaje> obtenerUltimos10Mensajes() {	
		return chatRepository.findFirst10ByOrderByFechaDesc();
	}

	@Override
	public Mensaje guardarMensaje(Mensaje mensaje) {
		return chatRepository.save(mensaje);
	}

}
