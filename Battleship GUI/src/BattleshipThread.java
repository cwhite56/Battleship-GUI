import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class BattleshipThread implements Runnable{
    private BufferedReader in;
    private PrintWriter out;
    private ObjectInputStream objectInputStream;
    private Socket clientSocket;
    private int playerID;
    private Player player;
    private BattleshipServer server;
    public static final String STOP_STRING = "##";

    public BattleshipThread(Socket clientSocket, int playerID, BattleshipServer server) {
        this.playerID = playerID;
        this.clientSocket = clientSocket;
        this.server = server;
    }
    /**
     * Run override that sets up I/O streams to communicate with the server and obtains player ship lists
     */
    @Override
    public void run() {
        try {
            
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

            player = (Player)objectInputStream.readObject();
            server.addPlayerShipList(player, this);

            readMessages();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Method that reads messages from the client
     * @throws IOException
     */
    private void readMessages() throws IOException{
        
        String message;
        ArrayList<Boolean> opponentShipList = server.getOpponentShipList(this);
        
        while(!(message = in.readLine()).equals(STOP_STRING)) {
            
            if (opponentShipList.get(Integer.parseInt(message))) {
                out.println("true");
            }
            else if (!opponentShipList.get(Integer.parseInt(message))) {
                out.println("false");
            }
        }
        closeThreadStreams();
    }

    public int getPlayerID() {
        return playerID;
    }
    /**
     * Method that closes all streams
     * @throws IOException
     */
    private void closeThreadStreams() throws IOException {
        this.clientSocket.close();
        System.out.println("Thread closed successfully.");
    }
    
}
