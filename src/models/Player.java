package models;

public abstract class Player {
    private String name;
    private int id;
    private PlayerType playerType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    private char symbol;


    public Player(String name, int id, char symbol, PlayerType playerType) {
        this.name = name;
        this.id = id;
        this.symbol = symbol;
        this.playerType = playerType;
    }
    
    public PlayerType getPlayerType(){
        return this.playerType;
    }

    public abstract Cell makeMove(Board board);
}
