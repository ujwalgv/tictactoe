package factories;

import models.DifficultyLevel;
import strategies.BotStrategy;
import strategies.EasyBotStrategy;

public class BotStrategyFactory {
    public static BotStrategy getBotStrategy(DifficultyLevel difficultyLevel){
        return new EasyBotStrategy();
    }
}
