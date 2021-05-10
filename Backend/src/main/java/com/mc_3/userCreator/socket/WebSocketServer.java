package com.mc_3.userCreator.socket;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;


import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Vamsi Krishna Calpakkam
 *
 */
@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocketServer {

	// Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();
    
    private String filename;
    private PrintWriter pr;
    private FileWriter fr;
    private BufferedWriter br;
    private File f;
    
    public WebSocketServer() throws IOException, FileNotFoundException
    {
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH_mm_ss");
    	LocalDateTime now = LocalDateTime.now();
    	  
    	  
    	//filename="WebsocketLog "+dtf.format(now)+".txt";//For offline testing
    	filename="/target/logs/"+"WebsocketLog "+dtf.format(now)+".txt";
		//pr=new PrintWriter(filename,"UTF-8");
		f = new File(filename);
		fr=new FileWriter(f,true);//true used for append mode
		br=new BufferedWriter(fr);
		pr=new PrintWriter(br);
		
		f.setWritable(true);
    	//setup();
    }

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    @OnOpen
    public void onOpen( Session session, @PathParam("username") String username)throws IOException
    {
        logger.info("Entered into Open");

        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        String message=username + " has joined the chat";
        
        broadcast(message);
        //br.write(message+"\n");
        pr.println(message+"\n");
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException
    {
        // Handle new messages
    	logger.info("Entered into Message: Got Message:"+message);
    	String username = sessionUsernameMap.get(session);

    	if (message.startsWith("@")) // Direct message to a user using the format "@username <message>"
    	{
    		String destUsername = message.split(" ")[0].substring(1); // don't do this in your code!
    		sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message);
    		sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);
    	}
    	else // Message to whole chat
    	{
	    	broadcast(username + ": " + message);
	    	//br.write(username + ": " + message+"\n");
	    	pr.println(username + ": " + message+"\n");
    	}
    }

    @OnClose
    public void onClose(Session session) throws IOException
    {
    	logger.info("Entered into Close");

    	String username = sessionUsernameMap.get(session);
    	sessionUsernameMap.remove(session);
    	usernameSessionMap.remove(username);

    	String message= username + " disconnected from the chat";
        broadcast(message);
        //br.write(message+"\n");
        pr.println(message+"\n");
        
        //if(sessionUsernameMap.size()==0 && usernameSessionMap.size()==0)
        //{
        	pr.close();
       // }
        
    }

    @OnError
    public void onError(Session session, Throwable throwable)
    {
        // Do error handling here
    	logger.info("Entered into Error");
    }

	private void sendMessageToPArticularUser(String username, String message)
    {
    	try 
    	{
    		usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } 
    	catch (IOException e) 
    	{
        	logger.info("Exception: " + e.getMessage().toString());
        	e.printStackTrace();
        }
    }

    private void broadcast(String message)
	{
		sessionUsernameMap.forEach((session, username)->{
		try 
		{
			session.getBasicRemote().sendText(message);
		} 
		catch (IOException e) 
		{
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();		
		}
		});
    }
    
}
