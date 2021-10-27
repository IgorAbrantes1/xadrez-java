package boardgame;

public abstract class Piece {
    private final Board board;
    protected Position position;

    public Piece(Board board) {
        this.board = board;
        this.position = null;
    }

    protected Board getBoard() {
        return board;
    }

    public abstract boolean[][] possibleMoves();

    public boolean possibleMove(Position position) {
        return this.possibleMoves()[position.getRow()][position.getColumn()];
    }

    public boolean isThereAnyPossibleMove() {
        boolean[][] mat = this.possibleMoves();

        for (boolean[] booleans : mat) {
            for (int i = 0; i < mat.length; i++) {
                if (booleans[i]) {
                    return true;
                }
            }
        }

        return false;
    }
}
