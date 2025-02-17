package com.example.examenD;

import java.util.ArrayList;

import java.util.List;

import java.util.concurrent.CopyOnWriteArrayList;



import org.springframework.stereotype.Service;

import org.springframework.web.socket.CloseStatus;

import org.springframework.web.socket.TextMessage;

import org.springframework.web.socket.WebSocketSession;

import org.springframework.web.socket.handler.TextWebSocketHandler;



@Service

public class Servicehandler extends TextWebSocketHandler {



	private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

	private List<String> mensaje = new ArrayList<String>();



	@Override

	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		sessions.add(session);



	}



	@Override

	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		sessions.remove(session);



	}



	@Override

	public void handleTextMessage(WebSocketSession session, TextMessage mesage) throws Exception {

int total=0;

		for(int a=0;a<mensaje.size();a++) {

			String total2=mensaje.get(a);

			int total3=Integer.parseInt(total2);

		total=total+total3;

			

			

			

			

			

		}

		for (WebSocketSession websocketsession : sessions) {

			String nose = mesage.getPayload();

			mensaje.add(nose);

			int numEntero = Integer.parseInt(nose);

			

			//websocketsession.sendMessage(mesage);			



			total += numEntero;



			String total2 = String.valueOf(total);



			websocketsession.sendMessage(new TextMessage(total2));

		}



	}

}