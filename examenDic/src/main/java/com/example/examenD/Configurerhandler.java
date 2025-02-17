package com.example.examenD;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;

import org.springframework.web.socket.config.annotation.WebSocketConfigurer;

import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;



import com.example.examenD.Servicehandler;

@Configuration

@EnableWebSocket



public class Configurerhandler implements WebSocketConfigurer {

	@Autowired

	private Servicehandler confighandler;

	

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

		

		registry.addHandler(confighandler, "/cuenta");

		

		

	}

	

	



}