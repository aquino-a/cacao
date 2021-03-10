package com.cacao.server;

import com.cacao.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@SpringBootApplication
public class ServerApplication {

	@Autowired
	private MessageService messageService;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@EventListener
	public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
		var message = (GenericMessage) event.getMessage();
		var simpDestination = (String) message.getHeaders().get("simpDestination");
		if (simpDestination.startsWith("/user/api/topic/message")) {
			var userId = event.getUser().getName();
			messageService.sendUnreadMessages(userId);
		}
	}
}
