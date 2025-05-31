package dataStructure.game.chess;

import dataStructure.game.Player;
import dataStructure.game.RuleSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChessRuleSet implements RuleSet<ChessMove, ChessBoard, Player> {

    @Override
    public boolean isMoveValid(ChessMove move, ChessBoard board, Player player) {
        Piece piece = board.getCell(move.fromRow, move.fromCol);
        if (piece == null || piece.color != player.getSymbol()) return false;

        Piece target = board.getCell(move.toRow, move.toCol);
        if (target != null && target.color == piece.color) return false;

        int dr = move.toRow - move.fromRow;
        int dc = move.toCol - move.fromCol;

        switch (piece.type) {
            case PAWN:
                return validatePawnMove(piece, move, board, dr, dc);
            case KNIGHT:
                return validateKnightMove(move, board, dr, dc);
            case BISHOP:
                return validateBishopMove(move, board, dr, dc);
            case ROOK:
                return validateRookMove(move, board, dr, dc);
            case QUEEN:
                return validateQueenMove(move, board, dr, dc);
            case KING:
                return validateKingMove(move, board, dr, dc);
        }
        return false;
    }

    private boolean validatePawnMove(Piece pawn, ChessMove move, ChessBoard board, int dr, int dc) {
        int direction = (pawn.color == 'W') ? -1 : 1;
        if (dc == 0) {
            // Move single step forward
            if (dr == direction && board.getCell(move.toRow, move.toCol) == null) {
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

    private boolean validateRookMove(ChessMove move, ChessBoard board, int dr, int dc) {
        return dr != 0 && dc != 0;
    }

    private boolean validateBishopMove(ChessMove move, ChessBoard board, int dr, int dc) {
        return Math.abs(dr) == Math.abs(dc);
    }

    private boolean validateQueenMove(ChessMove move, ChessBoard board, int dr, int dc) {
        return validateBishopMove(move, board, dr, dc) || validateRookMove(move, board, dr, dc);
    }

    private boolean validateKingMove(ChessMove move, ChessBoard board, int dr, int dc) {
        return Math.abs(dr) <= 1 && Math.abs(dc) <= 1;
    }

    private boolean validateKnightMove(ChessMove move, ChessBoard board, int dr, int dc) {
        return Math.abs(dr) * Math.abs(dc) == 2;
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
}
