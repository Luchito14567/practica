package com.example.socket_lucio;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.*;

public class ChatHandler extends TextWebSocketHandler {
    private Map<WebSocketSession, String> usuarios = new HashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String mensaje = message.getPayload(); //Obtengo el mensaje que llega

        // Si es nuevo usuario
        if (!usuarios.containsKey(session)) {
            usuarios.put(session, mensaje); //Guardo el nombre de la persona que se conecta por primera vez
            broadcast("Bienvenido " + mensaje + "!");
            return;
        }

        // Si escribe "salir"
        if (mensaje.equalsIgnoreCase("salir")) {
            String nombre = usuarios.get(session);
            broadcast("Chau " + nombre + "!"); 
            usuarios.remove(session);
            session.close();
            return;
        }

        // Mensaje normal
        String nombre = usuarios.get(session);
        broadcast(nombre + ": " + mensaje);
    }

    private void broadcast(String mensaje) throws Exception {
        for (WebSocketSession sesion : usuarios.keySet()) {
            sesion.sendMessage(new TextMessage(mensaje));
        }
    }
}