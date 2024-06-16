package strategies;

import models.Board;
import models.Cell;
import models.CellState;

import java.util.List;

public class EasyBotStrategy implements BotStrategy{

    @Override
    public Cell makeMove(Board board) {
        for(List<Cell> row: board.getBoard())
            for (Cell cell : row) {
                if (cell.getCellState().equals(CellState.EMPTY)){
                    return cell;
                }
            }
        return null;
    }

}
