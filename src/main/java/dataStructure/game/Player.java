package dataStructure.game;

public class Player {

    private final int id;
    private final String name;
    private char symbol;

    public Player(String name) {
        this.id = generateId();
        this.name = name;
        this.symbol = generateSymbol();
    }

    public Player(String name, char symbol) {
        this.id = generateId();
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
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
