package App;

import controllers.GameController;
import exceptions.BotCountMismatch;
import exceptions.DimensionAndPlayerCountMismatch;
import exceptions.UniqueSymbolMismatch;
import models.*;
import strategies.ColWinningStrategy;
import strategies.DiagonalWinningStrategy;
import strategies.RowWinningStrategy;
import strategies.WinningStrategy;

import java.security.DigestInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws UniqueSymbolMismatch, DimensionAndPlayerCountMismatch, BotCountMismatch {
        GameController gameController = new GameController();
        Scanner scanner = new Scanner(System.in);

        List<Player> players = new ArrayList<>();
        System.out.println("---Welcome to Tic Tac Toe Game---");
        System.out.println("---------------------------------");
        System.out.println("Enter the board dimension - ");
        int dimension = scanner.nextInt();

        System.out.println("---Board of size -"+dimension+" is created!---");
        System.out.println("Enter the number of players -");
        int playersCount = scanner.nextInt();
        while(playersCount > dimension-1){
            System.out.println("Sorry, your size of board does not allow "+playersCount+" of players.");
            System.out.println("Please enter the count less than "+dimension+" again now -");
            playersCount = scanner.nextInt();
        }

        System.out.println("---Alright! Please enter the details of the players ---");
        System.out.println("-------------------------------------------------------");
        for (int i=1;i<=playersCount;i++){
            System.out.println("Please enter the name of player"+i+":");
            String name = scanner.next();
            System.out.println("Please enter the symbol of player"+i+":");
            Character symbol = scanner.next().charAt(0);
            System.out.println("Please enter the type of player (Human/Bot): ");
            PlayerType playerType = PlayerType.valueOf(scanner.next().toUpperCase());

            if (playerType.equals(PlayerType.BOT)){
                System.out.println("Please enter the difficulty level of the Bot (Easy/Medium/Hard)");
                DifficultyLevel difficultyLevel = DifficultyLevel.valueOf(scanner.next().toUpperCase());
                players.add(new BotPlayer(name, i, symbol,playerType,difficultyLevel));
            } else if (playerType.equals(PlayerType.HUMAN)){
                players.add(new HumanPlayer(name, i, symbol,playerType,scanner));
            }


        }
//        players.add(new HumanPlayer("Ujwal",1,'X', PlayerType.HUMAN, scanner));
//        players.add(new BotPlayer("GPT",2,'O',PlayerType.BOT, DifficultyLevel.EASY));

        List<WinningStrategy> winningStrategies = new ArrayList<>();

        winningStrategies.add(new RowWinningStrategy());
        winningStrategies.add(new ColWinningStrategy());
        winningStrategies.add(new DiagonalWinningStrategy());

        System.out.println("Initialising the board and game.. GET READY..!");
        System.out.println("-----------------------------------------------");

        Game game = gameController.createGame(dimension, players,winningStrategies);

        while (GameState.IN_PROG.equals(game.getGameState())){
            gameController.GamePrinter(game);
            String wantUndo = "n";
            if (game.getMoves().size()>0){
                System.out.println("Do you want to undo? (y/n) - ");
                wantUndo = scanner.next();
            }



            if (wantUndo.equalsIgnoreCase("y")){
                gameController.makeUndo(game);
            }
            gameController.makeMove(game);
        }

        if (GameState.CONCLUDED.equals(game.getGameState())){
            System.out.println(game.getWinner() + ", is the winner of the game");
        }
        else if (GameState.DRAW.equals(game.getGameState())){
            System.out.println("The game was a draw!");
        }

    }
}
