package strategies;

import models.Board;
import models.Move;

import java.util.HashMap;
import java.util.Map;

public class ColWinningStrategy implements WinningStrategy{

    Map<Integer, Map<Character, Integer>> allColMaps = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        //Get the corresonding row and symbol of the respective player
        int col = move.getCell().getCol();
        Character symbol = move.getPlayer().getSymbol();

        //Check if that col is present, if not create the row and initialise with empty map.
        if (!allColMaps.containsKey(col)){
            allColMaps.put(col,new HashMap<>());
        }
        Map<Character, Integer> colMap = allColMaps.get(col);

        //check if the row contains that symbol, else initialise that row with 0 count of that symbol
        if(!colMap.containsKey(symbol)){
            colMap.put(symbol,0);
        }
        colMap.put(symbol,colMap.get(symbol)+1); //increment that symbol count by 1

        //Check the winner if symbol count in the row matches with the board dimension.
        if(colMap.get(symbol).equals(board.getDimension())){
            return true;
        }
        return false;

    }

    public void handleUndo(Board board, Move lastMove) {
        int col = lastMove.getCell().getCol();
        Map<Character,Integer> colMap = allColMaps.get(col);

        Character symbol = lastMove.getPlayer().getSymbol();
        colMap.put(symbol,colMap.get(symbol)-1);

    }
}
