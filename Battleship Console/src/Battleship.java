import java.util.ArrayList;
import java.util.Random;

public class Battleship extends ArrayList<ShipNode> {
	
	private ShipNode head = new ShipNode();
	private int shipSize;
	private Random rng = new Random();
	
	public Battleship(int shipSize, int gridSize) {
		setHeadX(gridSize);
		setHeadY(gridSize);
		this.add(head);
		this.shipSize = shipSize;
		
		int temp = rng.nextInt(2);
		
		if (temp == 1) {
			buildBodyHorizontally(gridSize);
		}
		else {
			buildBodyVertically(gridSize);
		}
	}
	
	public void setHeadX(int gridSize) {
		int x = rng.nextInt(gridSize);
		head.setX(x);
	}
	
	public void setHeadY(int gridSize) {
		int y = rng.nextInt(gridSize);
		head.setY(y);
	}
	
	public int getSize() {
		return shipSize;
	}
	
	
	public void buildBodyHorizontally(int gridSize) {
		for (int i = 0; i < shipSize - 1; i++) {
			
			ShipNode temp = new ShipNode(head);
			// When ship size is 4 and x pr y = 4, both if statements run
			
			if (head.getX() + shipSize >= gridSize) {
				temp.setX(head.getX() - i - 1);
				
			}
			
			else if (head.getX() - shipSize < 1) {
				temp.setX(head.getX() + i + 1);
			}

			else {
				temp.setX(head.getX() + 1);
			}
			
			this.add(temp);
		}
		
		
	}
	public void buildBodyVertically(int gridSize) {
		for (int i = 0; i < shipSize - 1; i++) {
			
			ShipNode temp = new ShipNode(head);
			
			if (head.getY() + shipSize >= gridSize) {
				temp.setY(head.getY() - i - 1);
			}
			
			else if (head.getY() - shipSize < 1) {
				temp.setY(head.getY() + i + 1);
			}
			else {
				temp.setY((head.getY() + 1));
			}
			this.add(temp);
		}
		
		
	}
}
		
	