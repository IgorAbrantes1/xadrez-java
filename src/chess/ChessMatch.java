package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {
    private final Board board;
    private final List<Piece> piecesOnTheBoard = new ArrayList<>();
    private final List<Piece> capturedPieces = new ArrayList<>();
    private int turn;
    private Color currentPlayer;
    private boolean check;
    private boolean checkMate;

    public ChessMatch() {
        this.board = new Board(8, 8);
        this.turn = 1;
        this.currentPlayer = Color.WHITE;
        this.check = false;
        this.initialSetup();
    }

    public boolean getCheckMate() {
        return this.checkMate;
    }

    public int getTurn() {
        return this.turn;
    }

    public Color getCurrentPlayer() {
        return this.currentPlayer;
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[this.board.getRows()][this.board.getColumns()];

        for (int i = 0; i < this.board.getRows(); i++) {
            for (int j = 0; j < this.board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) this.board.piece(i, j);
            }
        }

        return mat;
    }

    public boolean getCheck() {
        return this.check;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        this.board.placePiece(piece, new ChessPosition(column, row).toPosition());
        this.piecesOnTheBoard.add(piece);
    }

    private void initialSetup() {
        // WHITE
        this.placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        this.placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        this.placeNewPiece('e', 1, new King(board, Color.WHITE));
        this.placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        this.placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        this.placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        this.placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        this.placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        this.placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        this.placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        this.placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        this.placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        this.placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        // BLACK
        this.placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        this.placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        this.placeNewPiece('e', 8, new King(board, Color.BLACK));
        this.placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        this.placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        this.placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        this.placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        this.placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        this.placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        this.placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        this.placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        this.placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        this.placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        this.validateSourcePosition(position);
        return this.board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();

        this.validateSourcePosition(source);
        this.validateTargetPosition(source, target);
        Piece capturedPiece = this.makeMove(source, target);

        if (this.testCheck(currentPlayer)) {
            this.undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check.");
        }

        this.check = this.testCheck(opponent(currentPlayer));

        if (this.testCheckMate(opponent(currentPlayer))) {
            this.checkMate = true;
        } else {
            this.nextTurn();
        }

        return (ChessPiece) capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!this.board.thereIsAPiece(position))
            throw new ChessException("There is no piece on source position.");
        if (this.currentPlayer != ((ChessPiece) this.board.piece(position)).getColor()) {
            throw new ChessException("The chosen piece is not yours.    ");
        }
        if (!this.board.piece(position).isThereAnyPossibleMove())
            throw new ChessException("There is no possible moves for the chosen piece.");
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!this.board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can't move to target position.");
        }
    }

    private void nextTurn() {
        this.turn++;
        this.currentPlayer = (this.currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) this.board.removePiece(source);
        p.increaseMoveCount();

        Piece capturedPiece = this.board.removePiece(target);
        this.board.placePiece(p, target);

        if (capturedPiece != null) {
            this.piecesOnTheBoard.remove(capturedPiece);
            this.capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece) this.board.removePiece(target);
        p.decreaseMoveCount();
        this.board.placePiece(p, source);

        if (capturedPiece != null) {
            this.board.placePiece(capturedPiece, target);
            this.capturedPieces.remove(capturedPiece);
            this.piecesOnTheBoard.add(capturedPiece);
        }
    }

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        List<Piece> list = this.piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());

        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board.");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = this.king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = this.piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());

        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }

        List<Piece> list = this.piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());

        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < this.board.getRows(); i++) {
                for (int j = 0; j < this.board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = this.makeMove(source, target);
                        boolean testCheck = this.testCheck(color);
                        this.undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
