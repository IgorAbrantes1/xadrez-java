package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];
        Position p = new Position(0, 0);

        if (this.getColor() == Color.WHITE) {
            // above
            p.setValues(this.position.getRow() - 1, this.position.getColumn());
            if (this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // above first move
            p.setValues(this.position.getRow() - 2, this.position.getColumn());
            Position p2 = new Position(this.position.getRow() - 1, this.position.getColumn());
            if (this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p) && this.getMoveCount() == 0
                    && this.getBoard().positionExists(p2) && !this.getBoard().thereIsAPiece(p2)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // top left diagonal
            p.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
            if (this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // top right diagonal
            p.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
            if (this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // #special move: en passant white
            if (this.position.getRow() == 3) {
                Position left = new Position(this.position.getRow(), this.position.getColumn() - 1);
                if (this.getBoard().positionExists(left) && this.isThereOpponentPiece(left)
                        && this.getBoard().piece(left) == this.chessMatch.getEnPassantVulnerable()) {
                    mat[left.getRow() - 1][left.getColumn()] = true;
                }

                Position right = new Position(this.position.getRow(), this.position.getColumn() + 1);
                if (this.getBoard().positionExists(right) && this.isThereOpponentPiece(right)
                        && this.getBoard().piece(right) == this.chessMatch.getEnPassantVulnerable()) {
                    mat[right.getRow() - 1][right.getColumn()] = true;
                }
            }
        } else if (this.getColor() == Color.BLACK) {
            // below
            p.setValues(this.position.getRow() + 1, this.position.getColumn());
            if (this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // below first move
            p.setValues(this.position.getRow() + 2, this.position.getColumn());
            Position p2 = new Position(this.position.getRow() + 1, this.position.getColumn());
            if (this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p) && this.getMoveCount() == 0
                    && this.getBoard().positionExists(p2) && !this.getBoard().thereIsAPiece(p2)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // bottom left diagonal
            p.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
            if (this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // bottom right diagonal
            p.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
            if (this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // #special move: en passant black
            if (this.position.getRow() == 4) {
                Position left = new Position(this.position.getRow(), this.position.getColumn() - 1);
                if (this.getBoard().positionExists(left) && this.isThereOpponentPiece(left)
                        && this.getBoard().piece(left) == this.chessMatch.getEnPassantVulnerable()) {
                    mat[left.getRow() + 1][left.getColumn()] = true;
                }

                Position right = new Position(this.position.getRow(), this.position.getColumn() + 1);
                if (this.getBoard().positionExists(right) && this.isThereOpponentPiece(right)
                        && this.getBoard().piece(right) == this.chessMatch.getEnPassantVulnerable()) {
                    mat[right.getRow() + 1][right.getColumn()] = true;
                }
            }
        }

        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
