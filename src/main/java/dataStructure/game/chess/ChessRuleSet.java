package dataStructure.game.chess;

import dataStructure.game.Player;
import dataStructure.game.RuleSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChessRuleSet implements RuleSet<ChessBoard, ChessMove, Player> {

    @Override
    public boolean isMoveValid(ChessMove move, ChessBoard board, Player player) {
        Piece piece = board.getCell(move.fromRow, move.fromCol);
        if (piece == null || piece.color != player.getSymbol()) {
            return false;
        }

        Piece target = board.getCell(move.toRow, move.toCol);
        // cannot move to the same color piece
        if (target != null && target.color == piece.color) {
            return false;
        }

        switch (piece.type) {
            case PAWN:
                return validatePawnMove(piece, move, board);
            case KNIGHT:
                return validateKnightMove(move, board);
            case BISHOP:
                return validateBishopMove(move, board);
            case ROOK:
                return validateRookMove(move, board);
            case QUEEN:
                return validateQueenMove(move, board);
            case KING:
                return validateKingMove(move, board);
        }
        return false;
    }

    private boolean validatePawnMove(Piece pawn, ChessMove move, ChessBoard board) {
        int direction = (pawn.color == 'W') ? -1 : 1;
        int dr = move.toRow - move.fromRow;
        int dc = move.toCol - move.fromCol;
        if (dc == 0) {
            // Move single step forward
            if (dr == direction && board.hasPiece(move.toRow, move.toCol).isEmpty()) {
                return true;
            }
            // Move two steps forward from starting position
            if ((move.fromRow == 1 && pawn.color == 'B') || (move.fromRow == 6 && pawn.color == 'W')) {
                return dr == 2 * direction &&
                        board.getCell(move.toRow, move.toCol) == null &&
                        board.getCell(move.fromRow + direction, move.fromCol) == null;
            }
        } else if (Math.abs(dc) == 1 && dr == direction) {
            // Capture
            Piece target = board.getCell(move.toRow, move.toCol);
            return target != null && target.color != pawn.color;
        }
        return false;
    }

    private boolean validateRookMove(ChessMove move, ChessBoard board) {
        // make sure no piece in the path
        if (move.fromRow == move.toRow) {
            int minCol = Math.min(move.fromCol, move.toCol);
            int maxCol = Math.max(move.fromCol, move.toCol);
            for (int col = minCol + 1; col < maxCol; col++) {
                Optional<Piece> piece = board.hasPiece(move.fromRow, col);
                if (piece.isPresent()) {
                    return false;
                }
            }
            return true;
        } else if (move.fromCol == move.toCol) {
            int minRow = Math.min(move.fromRow, move.toRow);
            int maxRow = Math.max(move.fromRow, move.toRow);
            for (int row = minRow + 1; row < maxRow; row++) {
                Optional<Piece> piece = board.hasPiece(row, move.fromCol);
                if (piece.isPresent()) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean validateBishopMove(ChessMove move, ChessBoard board) {
        int dr = move.toRow - move.fromRow;
        int dc = move.toCol - move.fromCol;
        if (dr == dc) {
            int minRow = Math.min(move.fromRow, move.toRow);
            int minCol = Math.min(move.fromCol, move.toCol);
            for (int i = 1; i < Math.abs(dr); i++) {
                Optional<Piece> piece = board.hasPiece(minRow + i, minCol + i);
                if (piece.isPresent()) {
                    return false;
                }
            }
            return true;
        } else if (dr + dc == 0) {
            int minRow = Math.min(move.fromRow, move.toRow);
            int maxCol = Math.max(move.fromCol, move.toCol);
            for (int i = 1; i < Math.abs(dr); i++) {
                Optional<Piece> piece = board.hasPiece(minRow + i, maxCol - i);
                if (piece.isPresent()) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean validateQueenMove(ChessMove move, ChessBoard board) {
        return validateBishopMove(move, board) || validateRookMove(move, board);
    }

    private boolean validateKingMove(ChessMove move, ChessBoard board) {
        return Math.abs(move.toRow - move.fromRow) <= 1 && Math.abs(move.toCol - move.fromCol) <= 1;
    }

    private boolean validateKnightMove(ChessMove move, ChessBoard board) {
        return Math.abs(move.toRow - move.fromRow) * Math.abs(move.toCol - move.fromCol) == 2;
    }

    @Override
    public void applyMove(ChessMove move, ChessBoard board, Player player) {
        ChessBoard newBoard = (ChessBoard) board.copy();
        newBoard.movePiece(move.fromRow, move.fromCol, move.toRow, move.toCol);
    }

    @Override
    public Optional<Player> checkWinner(ChessBoard board, List<Player> players) {
        // Simplified: game ends when a king is captured
        for (Player player : players) {
            boolean hasKing = false;
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    Piece piece = board.getCell(r, c);
                    if (piece != null && piece.type == Piece.Type.KING && piece.color == player.getSymbol()) {
                        hasKing = true;
                    }
                }
            }
            if (!hasKing) {
                for (Player opponent : players) {
                    if (opponent.getSymbol() != player.getSymbol()) return Optional.of(opponent);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean isGameOver(ChessBoard board, List<Player> players) {
        return checkWinner(board, players).isPresent();
    }

    @Override
    public List<ChessMove> generateAvailableMoves(ChessBoard board, Player player) {
        List<ChessMove> moves = new ArrayList<>();
        for (int r1 = 0; r1 < 8; r1++) {
            for (int c1 = 0; c1 < 8; c1++) {
                Piece piece = board.getCell(r1, c1);
                if (piece != null && piece.color == player.getSymbol()) {
                    for (int r2 = 0; r2 < 8; r2++) {
                        for (int c2 = 0; c2 < 8; c2++) {
                            ChessMove move = new ChessMove(r1, c1, r2, c2, player);
                            if (isMoveValid(move, board, player)) {
                                moves.add(move);
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }

    @Override
    public boolean supportsDraw() {
        return true;
    }

    @Override
    public boolean isDraw(ChessBoard board, List<Player> players) {
        return false;
    }
}
