package com.sgs.waarp;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CreateTables {
	
	public void init() {
		try {
			File inputFile = new File(this.getClass().getClassLoader().getResource("config-serverA.xml").getFile());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("db");
			String DBdriver=null;
			String DBserver=null;
			String DBuser=null;
			String DBpassword=null;
			Connection conn = null;
			Statement stmt = null;
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					 DBdriver = eElement.getElementsByTagName("dbdriver").item(0).getTextContent();
					 DBserver = eElement.getElementsByTagName("dbserver").item(0).getTextContent();
					 DBuser = eElement.getElementsByTagName("dbuser").item(0).getTextContent();
					 DBpassword = eElement.getElementsByTagName("dbpasswd").item(0).getTextContent();
				}
			}
			
			Class.forName(DBdriver);
			conn = DriverManager.getConnection(DBserver, DBuser, DBpassword);
			stmt = conn.createStatement();
		      
		      String s3mappingtab = "CREATE TABLE s3bucketmapping" +
		    		  "(id int(11) NOT NULL AUTO_INCREMENT,"+
		    		  "filename varchar(450) NOT NULL,"+
		    		  "specialKey varchar(450) NOT NULL,"+
		    		  "s3fileurl varchar(450) NOT NULL,"+
		    		  "processedOn datetime DEFAULT CURRENT_TIMESTAMP,"+
		    		  "PRIMARY KEY (id))";
		      
		      String fileNametab= "CREATE TABLE filenamehandler"+ 
		    		  "(id int(11) NOT NULL AUTO_INCREMENT,"+
		    		  "filename varchar(500) NOT NULL,"+
		    		  "uuid varchar(500) NOT NULL,"+
		    		  "processedOn datetime DEFAULT CURRENT_TIMESTAMP,"+
		    		  "PRIMARY KEY (id))";
		      stmt.executeUpdate(s3mappingtab);
		      stmt.executeUpdate(fileNametab);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}	

