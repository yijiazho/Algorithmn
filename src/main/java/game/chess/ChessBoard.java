package game.chess;

import java.util.Optional;

import game.Board;

public class ChessBoard implements Board<ChessPiece> {
    private final ChessPiece[][] grid = new ChessPiece[8][8];

    public ChessBoard() {
        setupInitialPosition();
    }

    public ChessPiece getCell(int row, int col) {
        return grid[row][col];
    }

    public void setCell(int row, int col, ChessPiece piece) {
        grid[row][col] = piece;
    }

    public Optional<ChessPiece> hasPiece(int row, int col) {
        if (grid[row][col] != null) {
            return Optional.of(grid[row][col]);
        } else {
            return Optional.empty();
        }
    }

    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        grid[toRow][toCol] = grid[fromRow][fromCol];
        grid[fromRow][fromCol] = null;
    }

    public void setupInitialPosition() {
        // Simple initial setup for pawns and major pieces
        for (int i = 0; i < 8; i++) {
            grid[1][i] = new ChessPiece(ChessPiece.Type.PAWN, 'B');
            grid[6][i] = new ChessPiece(ChessPiece.Type.PAWN, 'W');
        }

        grid[0][0] = grid[0][7] = new ChessPiece(ChessPiece.Type.ROOK, 'B');
        grid[0][1] = grid[0][6] = new ChessPiece(ChessPiece.Type.KNIGHT, 'B');
        grid[0][2] = grid[0][5] = new ChessPiece(ChessPiece.Type.BISHOP, 'B');
        grid[0][3] = new ChessPiece(ChessPiece.Type.QUEEN, 'B');
        grid[0][4] = new ChessPiece(ChessPiece.Type.KING, 'B');

        grid[7][0] = grid[7][7] = new ChessPiece(ChessPiece.Type.ROOK, 'W');
        grid[7][1] = grid[7][6] = new ChessPiece(ChessPiece.Type.KNIGHT, 'W');
        grid[7][2] = grid[7][5] = new ChessPiece(ChessPiece.Type.BISHOP, 'W');
        grid[7][3] = new ChessPiece(ChessPiece.Type.QUEEN, 'W');
        grid[7][4] = new ChessPiece(ChessPiece.Type.KING, 'W');
    }

    @Override
    public Board<ChessPiece> copy() {
        ChessBoard copy = new ChessBoard();
        for (int r = 0; r < 8; r++) {
            System.arraycopy(this.grid[r], 0, copy.grid[r], 0, 8);
        }
        return copy;
    }

    public void clear() {
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                grid[r][c] = null;
    }
}
