import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread {
	static int portNumber;
	//Stores socket that client uses
	private Socket socketClient;
	//Streams used for communicating
	private ObjectOutputStream out;
	private ObjectInputStream in;

	private Consumer<Serializable> callback;
	
	Client(Consumer<Serializable> call){
		callback = call;
	}

	//Called once socket starts
	public void run() {
		//Creates socket and streams needed for communication
		try {
			socketClient = new Socket("127.0.0.1",Client.portNumber);
			out = new ObjectOutputStream(socketClient.getOutputStream());
			in = new ObjectInputStream(socketClient.getInputStream());
			socketClient.setTcpNoDelay(true);
		} catch(Exception e) {
		}

		//Connects to server
		while(true) {
			//Reads messages from Server
			try {
				String message = in.readObject().toString();
				if (message.contains("Word length: ")) {
					PreGame.wordLength = Integer.parseInt(message.substring("Word length: ".length()));
				} else if (message.contains("Theme1: ")) {
					Themes.themes[0] = message.substring("Theme1: ".length());
				} else if (message.contains("Theme2: ")) {
					Themes.themes[1] = message.substring("Theme2: ".length());
				} else if (message.contains("Theme3: ")) {
					Themes.themes[2] = message.substring("Theme3: ".length());
				} else if (message.contains("Attempts1: ")) {
					Themes.attempts[0] = Integer.parseInt(message.substring("Attempts1: ".length()));
				} else if (message.contains("Attempts2: ")) {
					Themes.attempts[1] = Integer.parseInt(message.substring("Attempts2: ".length()));
				} else if (message.contains("Attempts3: ")) {
					Themes.attempts[2] = Integer.parseInt(message.substring("Attempts3: ".length()));
				} else if (message.contains("Theme1 won: ")) {
					Themes.won[0] = Boolean.parseBoolean(message.substring("Theme1 won: ".length()));
				} else if (message.contains("Theme2 won: ")) {
					Themes.won[1] = Boolean.parseBoolean(message.substring("Theme2 won: ".length()));
				} else if (message.contains("Theme3 won: ")) {
					Themes.won[2] = Boolean.parseBoolean(message.substring("Theme3 won: ".length()));
				} else if (message.equals("already made") || message.contains("found at index ") || message.contains("not in word")
						|| message.equals("resulted in him/her/them losing the attempt") || message.equals("resulted in user winning! :)")
						|| message.equals("successful in him/her/them losing the attempt") || message.equals("successful in him/her/them winning! :)")
						|| message.equals("successful in him/her/them losing the game") || message.equals("successful in him/her/them winning the game! :)")) {
					if(message.contains("found at index ")) {
						System.out.println("Client got this message: " + message);
					}
					Game.gameMessages.add(message);
				} else if (message.contains("NumGuesses: ")) {
					Game.currentGuesses = Integer.parseInt(message.substring("NumGuesses: ".length()));
				} else {
					System.out.println("This message is not an option: " + message);
				}
				callback.accept(message);
			}
			catch(Exception e) {}
		}
    }

	//Allows user to send data to server
	public void send(String data) {
		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		if (socketClient != null) {
			return socketClient.isConnected();
		}
		return false;
	}

	public void disconnectClient() {
		try {
			socketClient.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error disconnecting client form Server");
		}

	}
}
