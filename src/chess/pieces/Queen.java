package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {
    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "Q";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];

        Position p = new Position(0, 0);

        // above
        p.setValues(this.position.getRow() - 1, this.position.getColumn());
        while (this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() - 1);
        }
        this.ifPositionExistsAndIsThereOpponentPiece(p, mat);

        // below
        p.setValues(this.position.getRow() + 1, this.position.getColumn());
        while (this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() + 1);
        }
        this.ifPositionExistsAndIsThereOpponentPiece(p, mat);

        // left
        p.setValues(this.position.getRow(), this.position.getColumn() - 1);
        while (this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() - 1);
        }
        this.ifPositionExistsAndIsThereOpponentPiece(p, mat);

        // right
        p.setValues(this.position.getRow(), this.position.getColumn() + 1);
        while (this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() + 1);
        }
        this.ifPositionExistsAndIsThereOpponentPiece(p, mat);

        // nw
        p.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
        while (this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() - 1);
        }
        this.ifPositionExistsAndIsThereOpponentPiece(p, mat);

        // ne
        p.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
        while (this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() + 1);
        }
        this.ifPositionExistsAndIsThereOpponentPiece(p, mat);

        // se
        p.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
        while (this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() + 1);
        }
        this.ifPositionExistsAndIsThereOpponentPiece(p, mat);

        // sw
        p.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
        while (this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() - 1);
        }
        this.ifPositionExistsAndIsThereOpponentPiece(p, mat);

        return mat;
    }

    private void ifPositionExistsAndIsThereOpponentPiece(Position p, boolean[][] mat) {
        if (this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
    }
}
