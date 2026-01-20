import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class BattleshipServer {
    public static final String STOP_STRING = "##";
    private static int totalPlayers;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private volatile ArrayList<ArrayList<Boolean>> bothPlayerShipLists = new ArrayList<>();
    public static void main(String args[]) throws IOException, ClassNotFoundException {

        BattleshipServer server = new BattleshipServer();
        server.setupNetworkingServer();
        
    }
    /**
     * Method that initializes all necessary streams to communicate with the client
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void setupNetworkingServer()throws IOException, ClassNotFoundException{
        serverSocket = new ServerSocket(5000);
        System.out.println("Server is running and waiting for client connection...");

        while (true) {
        clientSocket = serverSocket.accept();
        totalPlayers++;
        BattleshipThread battleshipThread = new BattleshipThread(clientSocket, totalPlayers, this);
        Thread thread = new Thread(battleshipThread);
        thread.start();
        }
    }
    /**
     * Method that takes the player number and returns the opposing player's ship list
     * @param playerNumber of calling thread
     * @return opposing player's ship list
     */
    public ArrayList<Boolean> getOpponentShipList(BattleshipThread thread) {

        if (thread.getPlayerID() == 1) {
            
            while(bothPlayerShipLists.size() < 2) {
            }

            return bothPlayerShipLists.get(1);
        } 

        else if (thread.getPlayerID() == 2) {
            return bothPlayerShipLists.get(0);
        }

        else {
            return null;
        }
    }

    public void addPlayerShipList(Player player, BattleshipThread thread) {
        bothPlayerShipLists.add(thread.getPlayerID() - 1, player.getShipList());
    }   
}
