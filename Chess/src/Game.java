package chess.take3.game;

public class Game {

	private Piece board[][] = new Piece[8][8];
	int turn;
	private Piece[] undo;
	private int[] undo2;
	public static boolean undoPRBool;
	public static Piece[] undoPR;
	public static boolean enableChange;
	public static int[] undoPRLoc;

	public Game() {

		turn = 0;
		undo = new Piece[2];
		undo2 = new int[4];
		undoPR = new Piece[2];
		undoPRLoc = new int[4];
		undoPRBool = false;

	}

	public boolean validStart(int x, int y) {

		if (board[x][y] != null) {
			if (turn == board[x][y].color) {
				return true;
			}
		}

		return false;

	}

	public boolean validEnd(int startX, int startY, int endX, int endY) {

		return board[startX][startY].checkValid(endX, endY, board);

	}

	public void move(int startX, int startY, int endX, int endY) {

		UI.undo.setEnabled(true);
		undo2[0] = startX;
		undo2[1] = startY;
		undo2[2] = endX;
		undo2[3] = endY;
		try {
			if (board[startX][startY] != null)
				undo[0] = (Piece) board[startX][startY].clone();
			else
				undo[0] = null;
			if (board[endX][endY] != null)
				undo[1] = (Piece) board[endX][endY].clone();
			else
				undo[1] = null;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		H.puts(board[startX][startY].toString());
		board[startX][startY].moveTo(endX, endY);
		board[endX][endY] = board[startX][startY];
		board[startX][startY] = null;
		turn = turn == 0 ? 1 : 0;

	}

	public void loadGame() {
		boolean exist = false;
		boolean cancel = false;
		while (!exist && !cancel) {
			Load load = new Load();
			load.loadGame();
			exist = load.getExists();
			cancel = load.getCancel();
			if (exist && !load.getCancel()) {
				board = load.getBoard();
				turn = load.getTurn();
			}
		}
	}

	public void undoMove() {
		turn = turn == 0 ? 1 : 0;
		Piece[] undoPR = Game.undoPR;
		int[] undoPRLoc = Game.undoPRLoc;
		boolean undoPRBool = Game.undoPRBool;
		try {
			if (undo[0] != null)
				board[undo2[0]][undo2[1]] = (Piece) undo[0].clone();
			else
				board[undo2[0]][undo2[1]] = null;
			if (undo[1] != null)
				board[undo2[2]][undo2[3]] = (Piece) undo[1].clone();
			else
				board[undo2[2]][undo2[3]] = null;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		UI.refreshBoard();
		if (undoPRBool) {
			try {
				if (undoPR[0] != null)
					board[undoPRLoc[0]][undoPRLoc[1]] = (Piece) undoPR[0]
							.clone();
				else
					board[undoPRLoc[0]][undoPRLoc[1]] = null;
				if (undoPR[1] != null)
					board[undoPRLoc[2]][undoPRLoc[3]] = (Piece) undoPR[1]
							.clone();
				else
					board[undoPRLoc[2]][undoPRLoc[3]] = null;
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		undoPRBool = false;
		UI.refreshBoard();
	}

	public boolean checkStillCheck(int sX, int sY, int eX, int eY) {
		Check check = new Check();
		Piece board2[][];
		board2 = check.movePiece(board, sX, sY, eX, eY);
		return check.check(board2, turn == 0 ? 1 : 0);
	}

	public boolean checkCheck() {
		Check check = new Check();
		return check.check(board, turn);
	}

	public boolean checkMate() {
		Check check = new Check();
		boolean mate = check.checkMate(board, turn);
		return mate;
	}

	public void newGame() {
		Load load = new Load();
		load.newGame();
		board = load.getBoard();
		turn = load.getTurn();
	}

	public Piece[][] getBoard() {
		return board;
	}

	public int getTurn() {
		return turn;
	}

}
