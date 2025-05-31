package dataStructure.game;

public abstract class Player {

    private final int id;
    private final String name;
    private char symbol;

    public Player(String name) {
        this.id = generateId();
        this.name = name;
        this.symbol = generateSymbol();
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    private int generateId() {
        // TODO: UUID
        return 0;
    }

    private char generateSymbol() {
        // TODO: Generate a unique symbol
        return 'X';
    }
}
