import java.util.ArrayList;
/**
 * Class that represents the player and its fields
 */
public class Player {
    private ArrayList<Boolean> shipList = new ArrayList<>();
    private int currentGuess;
    private final int MAX_CAPACITY = 100;

    public Player () {
        for (int i = 0; i < MAX_CAPACITY; i++) {
            shipList.add(false);
        }
    }

    public void setShipNode (int index, boolean value) {
        this.shipList.set(index, value);
    }

    public boolean getShipNode(int index) {
        return this.shipList.get(index);
    }

    public ArrayList<Boolean> getShipList () {
        return shipList;
    }

    public void setPlayerGuess(int index) {
        currentGuess = index;
    }

    public int getPlayerGuess() {
        return currentGuess;
    }
    /**
     * Method that checks if any ships or ship nodes remain
     * @return whether any ships / nodes remain
     */
    public boolean shipsLeft() {
        for (int i = 0; i < shipList.size(); i++) {
            if (shipList.get(i)) {
                return true;
            }
        }
        return false;
    }
}
