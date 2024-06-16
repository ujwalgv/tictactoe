package controllers;

import exceptions.BotCountMismatch;
import exceptions.DimensionAndPlayerCountMismatch;
import exceptions.UniqueSymbolMismatch;
import models.Game;
import models.Player;
import strategies.WinningStrategy;

import java.util.List;

public class GameController {

    public Game createGame(int dimension, List<Player> players, List<WinningStrategy> winningStrategies) throws UniqueSymbolMismatch, DimensionAndPlayerCountMismatch, BotCountMismatch {
        return Game.getBuilder().setDimension(dimension).setPlayers(players).setWinningStrategies(winningStrategies).build();
    }

    public void GamePrinter(Game game){
        game.printBoard();
    }

    public void makeMove(Game game){
        game.makeMove();
    }


    public void makeUndo(Game game) {
        game.makeUndo();
    }


}
