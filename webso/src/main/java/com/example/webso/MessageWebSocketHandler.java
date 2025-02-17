package com.example.webso;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageWebSocketHandler extends TextWebSocketHandler {

    private static final String CORRECT_PASSWORD = "123ABC";
    private static List<WebSocketSession> sessions = new ArrayList<>();
    private static List<String> messages = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Agregar la nueva sesión a la lista
        sessions.add(session);
        // Enviar los mensajes previos al cliente recién conectado
        sendPreviousMessages(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        String[] parts = payload.split(":");

        if (parts.length == 2) {
            String email = parts[0].trim();
            String password = parts[1].trim();
            String loginMessage;

            if (CORRECT_PASSWORD.equals(password)) {
                loginMessage = email + " se conectó CORRECTAMENTE";
            } else {
                loginMessage = email + " se conectó INCORRECTAMENTE";
            }

            // Agregar el mensaje a la lista global
            messages.add(loginMessage);

            // Enviar el mensaje a todos los clientes conectados
            sendToAllClients(loginMessage);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Eliminar la sesión de la lista cuando se cierra la conexión
        sessions.remove(session);
    }

    private void sendToAllClients(String message) {
        for (WebSocketSession client : sessions) {
            if (client.isOpen()) {
                try {
                    client.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendPreviousMessages(WebSocketSession session) {
        try {
            for (String message : messages) {
                session.sendMessage(new TextMessage(message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
