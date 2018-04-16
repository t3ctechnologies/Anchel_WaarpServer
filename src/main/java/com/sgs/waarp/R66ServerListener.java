package com.sgs.waarp;

import java.io.File;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.waarp.openr66.protocol.exception.OpenR66ProtocolPacketException;
import org.waarp.openr66.server.R66Server;

public class R66ServerListener extends ContextLoaderListener {

	public void contextInitialized(ServletContextEvent arg0) {
		new R66ServerDBInitializer().initdb();
		try {
			File configFile = new File(this.getClass().getClassLoader().getResource("config-serverA.xml").getFile());
			String path = null;
			if (configFile.exists()) {
				path = configFile.toString();
			}
			String[] waarpconfig = { path };
			R66Server.initR66Server(waarpconfig);
		} catch (OpenR66ProtocolPacketException e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("R66Server terminated");
	}

}
