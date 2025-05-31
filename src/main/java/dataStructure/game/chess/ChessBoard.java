package dataStructure.game.chess;


import dataStructure.game.Board;

public class ChessBoard implements Board {
    private final Piece[][] grid = new Piece[8][8];

    public ChessBoard() {
        setupInitialPosition();
    }

    public Piece getCell(int row, int col) {
        return grid[row][col];
    }

    public void setCell(int row, int col, Piece piece) {
        grid[row][col] = piece;
    }

    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        grid[toRow][toCol] = grid[fromRow][fromCol];
        grid[fromRow][fromCol] = null;
    }

    private void setupInitialPosition() {
        // Simple initial setup for pawns and major pieces
        for (int i = 0; i < 8; i++) {
            grid[1][i] = new Piece(Piece.Type.PAWN, 'B');
            grid[6][i] = new Piece(Piece.Type.PAWN, 'W');
        }

        grid[0][0] = grid[0][7] = new Piece(Piece.Type.ROOK, 'B');
        grid[0][1] = grid[0][6] = new Piece(Piece.Type.KNIGHT, 'B');
        grid[0][2] = grid[0][5] = new Piece(Piece.Type.BISHOP, 'B');
        grid[0][3] = new Piece(Piece.Type.QUEEN, 'B');
        grid[0][4] = new Piece(Piece.Type.KING, 'B');

        grid[7][0] = grid[7][7] = new Piece(Piece.Type.ROOK, 'W');
        grid[7][1] = grid[7][6] = new Piece(Piece.Type.KNIGHT, 'W');
        grid[7][2] = grid[7][5] = new Piece(Piece.Type.BISHOP, 'W');
        grid[7][3] = new Piece(Piece.Type.QUEEN, 'W');
        grid[7][4] = new Piece(Piece.Type.KING, 'W');
    }

    @Override
    public Board copy() {
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
