package models;

import exceptions.BotCountMismatch;
import exceptions.DimensionAndPlayerCountMismatch;
import exceptions.UniqueSymbolMismatch;
import strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Game {
    private Board board;
    private List<Player> players;

    public List<Move> getMoves() {
        return moves;
    }

    private List<Move> moves;
    private GameState gameState;
    private List<WinningStrategy> winningStrategies;

    public String getWinner() {
        return winner;
    }

    private String winner;
    private int nextPlaterIndex;

    public Game(List<Player> players, int dimension, List<WinningStrategy> winningStrategies) {
        this.players = players;
        this.winningStrategies = winningStrategies;
        this.board = new Board(dimension);
        this.moves = new ArrayList<>();
        this.gameState = GameState.IN_PROG;
        this.nextPlaterIndex = 0;

    }

    public static GameBuilder getBuilder(){
        return new GameBuilder();
    }

    public void printBoard() {
        this.board.printBoard();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void makeMove() {
        Player player = players.get(nextPlaterIndex);
        Cell cell = player.makeMove(board);
        Move move = new Move(cell,player);
        moves.add(move);

        //Check winner after every move
        if (checkWinner(move, board)) {
            gameState = GameState.CONCLUDED;
            winner = player.getName();
            return;
        }

        //check if the board is completely filled
        if(moves.size()== board.getDimension()* board.getDimension()){
            gameState = GameState.DRAW;
            return;
        }

        //If the move was successful and there was no winner, then update the next player index
        nextPlaterIndex++;
        nextPlaterIndex = nextPlaterIndex % players.size();
    }

    private boolean checkWinner(Move move, Board board) {
        for(WinningStrategy winningStrategy: winningStrategies){
            if (winningStrategy.checkWinner(board, move)) {
                return true;
            }
        }
        return false;
    }

    public void makeUndo() {
        if (moves.size()==0){
            System.out.println("There are no moves to undo");
            return;
        }

        Move lastMove = moves.get(moves.size()-1);
        moves.remove(lastMove);

        Cell cell = lastMove.getCell();
        cell.setPlayer(null);
        cell.setCellState(CellState.EMPTY);

        for (WinningStrategy winningStrategy: winningStrategies){
            winningStrategy.handleUndo(board,lastMove);
        }

        if (nextPlaterIndex!=0){
            nextPlaterIndex--;
        } else {
            nextPlaterIndex = players.size()-1;
        }
        System.out.println("Undo successful, Its " + players.get(nextPlaterIndex).getName() + "'s turn again.");

    }
    public static class GameBuilder {
        private List<Player> players;
        private int dimension;

        public GameBuilder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        private List<WinningStrategy> winningStrategies;

        public GameBuilder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public GameBuilder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        private GameBuilder(){
            this.dimension = 0;
            this.players = new ArrayList<>();
        }

        public Game build() throws DimensionAndPlayerCountMismatch, UniqueSymbolMismatch, BotCountMismatch {
            validateBotCount();
            validateUniqueSymbols();
            validatateDimensionAndPlayerCount();
            return new Game(players,dimension, winningStrategies);
        }

        private void validatateDimensionAndPlayerCount() throws DimensionAndPlayerCountMismatch {
            if (this.players.size() != this.dimension-1){
                throw new DimensionAndPlayerCountMismatch("Check the player count and board dimnesion");
            }
        }

        private void validateUniqueSymbols() throws UniqueSymbolMismatch {
            HashSet<Character> symbols = new HashSet<>();
            for (Player p : players){
                if (symbols.contains(p.getSymbol())){
                    throw new UniqueSymbolMismatch("Please provide unique sumbols for the players!");
                }
            }
        }

        private void validateBotCount() throws BotCountMismatch {
            int botCount = 0;
            for (Player p: players){
                if (p.getPlayerType().equals(PlayerType.BOT)){
                    botCount++;
                    if (botCount > 1) {
                        throw new BotCountMismatch("There cannot be more than 1 bot!");
                    }
                }
            }
        }
    }
}
