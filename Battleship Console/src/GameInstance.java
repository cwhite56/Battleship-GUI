import java.util.ArrayList;
import java.util.Scanner;

public class GameInstance {

	private int gridSize;
	private ArrayList<Battleship> shipList = new ArrayList<>();
	private Scanner scnr = new Scanner(System.in);

	public GameInstance(int gridSize) {
		this.gridSize = gridSize;
		initShips(this.gridSize);
		gameplayLoop();
		System.out.println("You won!");
	}

	public void initShips(int gridSize) {

		Battleship s1 = new Battleship(3, gridSize);
		shipList.add(s1);

		Battleship s2 = new Battleship(3, gridSize);

		while (collisionCheck(s2)) {

			s2 = new Battleship(3, gridSize);
		}
		shipList.add(s2);

		Battleship s3 = new Battleship(3, gridSize);

		while (collisionCheck(s3)) {
			s3 = new Battleship(3, gridSize);
		}
		shipList.add(s3);
	}

	public boolean collisionCheck(Battleship other) {
		for (Battleship check : shipList) {

			for (int i = 0; i < check.getSize(); i++) {

				for (int j = 0; j < check.getSize(); j++) {

					if (check.get(i).getX() == other.get(j).getX() && check.get(i).getY() == other.get(j).getY()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void gameplayLoop() {

		System.out.println("Welcome to Battleship!");
		System.out.println(
				"Each move must be entered as two numbers separated by a space. These are your x and y components");
		System.out.println("The grid size is a " + gridSize + "x" + gridSize);
		System.out.println("Your guess can range from 0 to " + (gridSize - 1));

		while (!shipList.isEmpty()) {
			System.out.println("Please enter your move: ");
			int x = scnr.nextInt();
			int y = scnr.nextInt();

			for (Battleship check : shipList) {

				for (int i = 0; i < check.size(); i++) {

					if (check.get(i).getX() == x && check.get(i).getY() == y) {
						System.out.println("Hit!");
						check.remove(i);
						break;
					} else {
						System.out.println("Miss!");
						break;
					}
				}
				if (check.isEmpty()) {
					shipList.remove(check);
					System.out.println("You sunk my battleship!");
					break;
				}
				break;
			}

		}
		return;
	}
}