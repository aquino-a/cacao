package com.cacao.server;

import com.cacao.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		var ac = SpringApplication.run(ServerApplication.class, args);
		var connectedEventApplicationListener = ac.getBean(SessionConnectedListener.class);
		ac.addApplicationListener(connectedEventApplicationListener);
	}

	@Component
	public static class SessionConnectedListener implements ApplicationListener<SessionConnectedEvent> {
		@Autowired
		private MessageService messageService;

		@Override
		public void onApplicationEvent(SessionConnectedEvent sessionConnectedEvent) {
			var userId = sessionConnectedEvent.getUser().getName();
			messageService.sendUnreadMessages(userId);
		}
	}






}
