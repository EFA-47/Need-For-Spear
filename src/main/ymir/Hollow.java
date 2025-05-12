package main.ymir;

import javafx.scene.paint.Color;
import main.Game;
import main.Obstacle;
import main.SimpleObstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hollow implements YmirStrategy{
    @Override
    public void activateAbility(Ymir ymir, Game parent) {
        List<Obstacle> obstacles = new ArrayList<>(Game.obstacles);
        int size=8;
        if(obstacles.size()<size)size=obstacles.size();
        for (int i=0;i<size;i++){
            Random random = new Random();
            if (obstacles.size() <= 0) break;
            int index = random.nextInt(obstacles.size());
            Obstacle obstacle = obstacles.get(index);
            while(!(obstacle instanceof SimpleObstacle)){
                index = random.nextInt(obstacles.size());
                obstacle = obstacles.get(index);
            }
            obstacle.hallow = true;
            obstacle.setHealth(1);
            obstacle.getShape().setFill(Color.rgb(191, 64, 191));
            obstacles.remove(index);
        }
    }
}
