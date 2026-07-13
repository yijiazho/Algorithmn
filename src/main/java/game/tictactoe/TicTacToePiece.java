package game.tictactoe;

public class TicTacToePiece {

    private final char symbol; // 'X' or 'O'

    public TicTacToePiece(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
