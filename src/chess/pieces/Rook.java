package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {
    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];

        Position p = new Position(0, 0);

        // above
        p.setValues(position.getRow() - 1, position.getColumn());

        while(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() - 1);
        }

//        ifPositionExistsAndIsThereOpponentPiece(p, mat);

        if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // below
        p.setValues(position.getRow() + 1, position.getColumn());

        while(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() + 1);
        }

//        ifPositionExistsAndIsThereOpponentPiece(p, mat);

        if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // left
        p.setValues(position.getRow(), position.getColumn() - 1);

        while(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() - 1);
        }

//        ifPositionExistsAndIsThereOpponentPiece(p, mat);

        if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // right
        p.setValues(position.getRow(), position.getColumn() + 1);

        while(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() + 1);
        }

//        ifPositionExistsAndIsThereOpponentPiece(p, mat);

        if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        return mat;
    }

    private void ifPositionExistsAndIsThereOpponentPiece(Position p, boolean[][] mat) {
        if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
    }
}
