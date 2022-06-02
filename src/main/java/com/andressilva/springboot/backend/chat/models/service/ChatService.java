package com.andressilva.springboot.backend.chat.models.service;

import java.util.List;

import com.andressilva.springboot.backend.chat.models.documents.Mensaje;

public interface ChatService {

	public List<Mensaje> obtenerUltimos10Mensajes();
	public Mensaje guardarMensaje(Mensaje mensaje);
}
