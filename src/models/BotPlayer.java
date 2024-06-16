package models;

import factories.BotStrategyFactory;
import strategies.BotStrategy;

public class BotPlayer extends Player{

    private DifficultyLevel difficultyLevel;

    private BotStrategy botStrategy;

    public BotPlayer(String name, int id, char symbol, PlayerType playerType, DifficultyLevel difficultyLevel) {
        super(name, id, symbol, playerType);
        this.difficultyLevel = difficultyLevel;
        this.botStrategy = BotStrategyFactory.getBotStrategy(difficultyLevel);
    }


    @Override
    public Cell makeMove(Board board) {
        System.out.println("Bot is making the move");
        Cell cell = botStrategy.makeMove(board);
        cell.setCellState(CellState.FILLED);
        cell.setPlayer(this);
        return cell;
    }
}
