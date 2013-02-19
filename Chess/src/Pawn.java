package chess.take3.game;

public class Pawn extends Piece implements Cloneable {

	private boolean special;

	public Pawn(int _locationX, int _locationY, int _color) {
		super(_locationX, _locationY, _color);
		special = false;
	}

	public String toString() {
		String pColor = color == 1 ? "black" : "white";
		return pColor + " Pawn at coords (" + locationX + "," + locationY + ")";
	}

	public boolean checkValid(int eX, int eY, Piece board[][]) {
		int x = locationX;
		int y = locationY;
		int n = color == 0 ? -1 : 1;
		if (!(x == eX && y == eY))
			if (board[eX][eY] == null || color != board[eX][eY].color) {
				if (moves == 0 && x == eX && eY == y + n * 2
						&& board[x][y + n] == null
						&& board[x][y + n * 2] == null) {
					special = true;
					return true;
				} else if (x == eX && eY == y + n && board[x][y + n] == null)
					return true;
				else if (eX == x + 1 && eY == y + n) {
					if (board[eX][eY - n] != null
							&& board[eX][eY - n].getType().equals("Pawn")
							&& board[eX][eY - n].getSpecial()
							&& board[eX][eY] == null
							&& board[eX][eY - n].getColor() != super.getColor()) {
						if (Game.enableChange) {
							Game.undoPRBool = true;
							Game.undoPRLoc[0] = x;
							Game.undoPRLoc[1] = y;
							Game.undoPRLoc[2] = eX;
							Game.undoPRLoc[3] = eY;
							try {
								if (board[eX][eY] != null)
									Game.undoPR[0] = (Piece) board[eX][eY]
											.clone();
								else
									Game.undoPR[0] = null;
								if (board[eX][eY - n] != null)
									Game.undoPR[1] = (Piece) board[eX][eY - n]
											.clone();
								else
									Game.undoPR[1] = null;
							} catch (CloneNotSupportedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							board[eX][eY - n] = null;
						}
						return true;
					} else if (board[eX][eY] != null
							&& board[eX][eY].color != color) {
						return true;
					}
				} else if (eX == x - 1 && eY == y + n) {
					if (board[eX][eY - n] != null
							&& board[eX][eY - n].getType().equals("Pawn")
							&& board[eX][eY - n].getSpecial()
							&& board[eX][eY] == null
							&& board[eX][eY - n].getColor() != super.getColor()) {
						if (Game.enableChange) {
							Game.undoPRBool = true;
							Game.undoPRLoc[0] = x;
							Game.undoPRLoc[1] = y;
							Game.undoPRLoc[2] = eX;
							Game.undoPRLoc[3] = eY;
							try {
								if (board[eX][eY] != null)
									Game.undoPR[0] = (Piece) board[eX][eY]
											.clone();
								else
									Game.undoPR[0] = null;
								if (board[eX][eY - n] != null)
									Game.undoPR[1] = (Piece) board[eX][eY - n]
											.clone();
								else
									Game.undoPR[1] = null;
							} catch (CloneNotSupportedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							board[eX][eY - n] = null;
						}
						return true;
					} else if (board[eX][eY] != null
							&& board[eX][eY].color != color) {
						return true;
					}
				}
			}

		return false;
	}

	public String getType() {
		return "Pawn";
	}

	public int getValue() {
		return color == 0 ? 7 : 1;
	}

	public boolean getSpecial() {
		return special;
	}

	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
