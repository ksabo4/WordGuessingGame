import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server {

	//Has a specific port number for the server
	public static int portNumber = 1234;

	//# of clients in server
	private int count = 1;

	//Client threads that allows server to communicate with each client
	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();


	private TheServer server;
	private Consumer<Serializable> callback;
	
	
	public Server(Consumer<Serializable> call){
	
		callback = call;
		server = new TheServer();
		server.start();
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
			//Creates a new server socket
			try (ServerSocket mysocket = new ServerSocket(portNumber)){
				mysocket.setReuseAddress(true);
		    	System.out.println("Server is waiting for a client!");

				//Accepts client if it tries to connect
				while(true) {
					Socket client = mysocket.accept();
					ClientThread c = new ClientThread(client, count);
					callback.accept("client has connected to server: " + "client #" + count);
					clients.add(c);
					c.start();

					count++;
				
			    }
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
			}//end of while
		}

		//Allows server to communicate through client
		class ClientThread extends Thread{
			//Socket for connecting to client
			Socket connection;

			//Stores the index# for the client
			int count;

			//How the server receives and returns messages to client
			ObjectInputStream in;
			ObjectOutputStream out;

			//Game data
			GameData gameData;
			GuessWord guessWord;

			//Sets socket and #
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;
				gameData = new GameData();
			}

			//Starts once thread is created
			public void run(){
				try {
					//Creates streams
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
					
				 while(true) {
					    try {
							//Waits for message from client
							String data = in.readObject().toString();
							//Sends back message depending on message received
							if (data.contains("I need themes")) {
								callback.accept("Client: " + count + " needs themes");
								out.writeObject("Theme1: " + gameData.getCategory(1));
								out.writeObject("Theme2: " + gameData.getCategory(2));
								out.writeObject("Theme3: " + gameData.getCategory(3));
								out.writeObject("Attempts1: " + gameData.getAttempts(1));
								out.writeObject("Attempts2: " + gameData.getAttempts(2));
								out.writeObject("Attempts3: " + gameData.getAttempts(3));
								out.writeObject("Theme1 won: " + gameData.getCategoryWon(0));
								out.writeObject("Theme2 won: " + gameData.getCategoryWon(1));
								out.writeObject("Theme3 won: " + gameData.getCategoryWon(2));
							} else if (data.contains("I need word for theme ")) {
								gameData.setCurrentTheme(Integer.parseInt(data.substring("I need word for theme ".length())));
								callback.accept("Client: " + count + " needs a word for theme " + gameData.getCurrentTheme());
								if (guessWord == null) {
									guessWord = new GuessWord(gameData.getWord(gameData.getCurrentTheme()));
								} else {
									guessWord.setWord(gameData.getWord(gameData.getCurrentTheme()));
								}
								callback.accept("Client " + count + " got word " + guessWord.getWord());
								out.writeObject("Word length: " + guessWord.getWord().length());
								out.writeObject("NumGuesses: " + guessWord.getGuessesRemaining());
							} else if (data.contains("Guess: ")) {
								char guess = data.substring("Guess: ".length()).charAt(0);
								ArrayList<String> messages = guessWord.updateGuesses(guess);
								if (messages.contains("successful in him/her/them losing the attempt")) {
									gameData.decrementCategoryAttempt(gameData.getCurrentTheme());
									if (gameData.isGameOver()) {
										messages.remove("successful in him/her/them losing the attempt");
										messages.add("successful in him/her/them losing the game");
									}
								} else {
									if (messages.contains("successful in him/her/them winning! :)")) {
										gameData.setCategoryWon(gameData.getCurrentTheme() - 1);
										if (gameData.didUserWin()) {
											messages.remove("successful in him/her/them winning! :)");
											messages.add("successful in him/her/them winning the game! :)");
										}
									}
								}
								for (String message : messages) {
									synchronized (out) {
										synchronized (callback) {
											callback.accept("Client " + count + " " + "made a guess that was " + message);
											out.writeObject(message);
										}
									}
								}
							} else {
								callback.accept("Something is going wrong with client " + count);
							}
						}
						//If issue occurs,
					    catch(Exception e) {
							//Show that client is no longer in the server
					    	callback.accept("Client " + count + " left the server");
					    	clients.remove(this);
					    	break;
					    }
					}
				}//end of run
			
			
		}//end of client thread
}


	
	

	
