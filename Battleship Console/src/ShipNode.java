class ShipNode {
	private int x;
	private int y;

	public ShipNode() {
		this.x = 0;
		this.y = 0;
	}

	public ShipNode(ShipNode newNode) {
		this.x = newNode.x;
		this.y = newNode.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}