package strategies;

import models.Board;
import models.Move;

import java.util.HashMap;
import java.util.Map;

public class RowWinningStrategy implements WinningStrategy{
    Map<Integer, Map<Character, Integer>> allRowMaps = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        //Get the corresonding row and symbol of the respective player
        int row = move.getCell().getRow();
        Character symbol = move.getPlayer().getSymbol();

        //Check if that row is present, if not create the row and initialise with empty map.
        if (!allRowMaps.containsKey(row)){
            allRowMaps.put(row,new HashMap<>());
        }
        Map<Character, Integer> rowMap = allRowMaps.get(row);

        //check if the row contains that symbol, else initialise that row with 0 count of that symbol
        if(!rowMap.containsKey(symbol)){
            rowMap.put(symbol,0);
        }
        rowMap.put(symbol,rowMap.get(symbol)+1); //increment that symbol count by 1

        //Check the winner if symbol count in the row matches with the board dimension.
        if(rowMap.get(symbol) == (board.getDimension())){
            return true;
        }
        return false;

    }

    @Override
    public void handleUndo(Board board, Move lastMove) {
        int row = lastMove.getCell().getRow();
        Map<Character,Integer> rowMap = allRowMaps.get(row);

        Character symbol = lastMove.getPlayer().getSymbol();
        rowMap.put(symbol,rowMap.get(symbol)-1);

    }
}
