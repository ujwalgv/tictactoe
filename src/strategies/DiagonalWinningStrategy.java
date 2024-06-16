package strategies;

import models.Board;
import models.Move;

import java.util.HashMap;
import java.util.Map;

public class DiagonalWinningStrategy implements WinningStrategy{
    Map<Character, Integer> leftDiagonal = new HashMap<>();
    Map<Character, Integer> rightDiagonal = new HashMap<>();


    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        Character symbol = move.getPlayer().getSymbol();

        //Check if the cell is a part of the left diagonal
        if(row==col){
          if(!leftDiagonal.containsKey(symbol)){
              leftDiagonal.put(symbol,0);
          }
          leftDiagonal.put(symbol, leftDiagonal.get(symbol)+1);

          //Check if the left diagonal is completely filled, then it will be a win
            if(leftDiagonal.get(symbol).equals(board.getDimension())){
                return true;
            }
        }

        //Check if the cell is a part of the right diagonal
        if (row+col == board.getDimension()-1){
            if (!rightDiagonal.containsKey(symbol)){
                rightDiagonal.put(symbol,0);
            }
            rightDiagonal.put(symbol,rightDiagonal.get(symbol)+1);

            if(rightDiagonal.get(symbol).equals(board.getDimension())){
                return true;
            }
        }

        return false;
    }

    public void handleUndo(Board board, Move lastMove) {
        int row = lastMove.getCell().getRow();
        int col = lastMove.getCell().getCol();
        Character symbol = lastMove.getPlayer().getSymbol();

        if (row==col){
            if (leftDiagonal.containsKey(symbol)){
                leftDiagonal.put(symbol,leftDiagonal.get(symbol)-1);
            }
        }

        if (row+col==board.getDimension()-1){
            if (rightDiagonal.containsKey(symbol)){
                rightDiagonal.put(symbol,rightDiagonal.get(symbol)-1);
            }
        }

    }
}
