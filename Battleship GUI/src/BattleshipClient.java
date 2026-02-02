import java.io.*;
import java.net.*;
import java.util.ArrayList;
/**
 * Class that represents the Battleship client that interacts with the server
 */
public class BattleshipClient {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private ObjectOutputStream objectOutputStream;
	private Player player;
	private BattleshipGUI gui;
	private static volatile boolean gameFinished = false;

	public BattleshipClient() throws IOException{
		player = new Player();
		gui = new BattleshipGUI(player, this);
		
	}
// left to do: Minor bug fixes // visual track of turn order
	public static void main(String[] args) throws IOException {
		BattleshipClient client = new BattleshipClient();
		while (!gameFinished) {
		
		}
		client.closeStreams();
	}
	/**
	 * Method to initialize connection to server
	 * @throws IOException
	 */
	public void setupNetworking() throws IOException{
	socket = new Socket("localhost", 5000);
	out = new PrintWriter(socket.getOutputStream(), true);
	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	objectOutputStream = new ObjectOutputStream(socket.getOutputStream()); 
	objectOutputStream.writeObject(player);
	}
	/**
	 * Method to send a player guess through the TCP connection
	 * @return whether that guess was a miss / hit
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public boolean sendData() throws IOException{
		out.println(player.getPlayerGuess());
		String result = in.readLine();

		if (result.equalsIgnoreCase("true and win")) {
			finishGame();
			return true;
		}

		return Boolean.parseBoolean(result);
	}
	/**
	 * Method that signals a player has lost all of their ships to the server
	 */
	public void finishGame() {
		gameFinished = true;
		out.println(BattleshipServer.STOP_STRING);
	}
	/**
	 * Method that closes all streams
	 * @throws IOException
	 */
	public void closeStreams() throws IOException {
		this.socket.close();
		System.out.println("Client closed successfully.");
	}
	
}
