package strategies;

import models.Board;
import models.Cell;

public interface BotStrategy {
    Cell makeMove(Board board);
}
