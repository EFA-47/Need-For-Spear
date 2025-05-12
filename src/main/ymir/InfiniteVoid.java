package main.ymir;

import javafx.scene.paint.Color;
import main.Game;
import main.Obstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InfiniteVoid implements YmirStrategy{
    @Override
    public void activateAbility(Ymir ymir, Game parent) {
        List<Obstacle> obstacles = new ArrayList<>(Game.obstacles);
        for (int i=0;i<8;i++){
            Random random = new Random();
            if (obstacles.size() <= 0) break;
            int index = random.nextInt(obstacles.size());
            Obstacle obstacle = obstacles.get(index);
            obstacle.frozen = true;
            obstacle.getShape().setFill(Color.DARKGRAY);
            obstacle.melt();
            obstacles.remove(index);
        }
    }
}
