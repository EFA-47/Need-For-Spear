package main.ymir;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import main.Game;
import main.Obstacle;

import java.util.Random;

public class Ymir extends Obstacle {
    private Timeline loop;
    public Ymir(int x, int y, int width, int height, Game parent) {
        super(x, y, width, height, parent);
        shape = new Rectangle(width,height);
        shape.setFill(Color.rgb(112, 41, 99));
        setHealth(2);
        setListener();
        shape.setLayoutX(x);
        shape.setLayoutY(y);
        loop = new Timeline(new KeyFrame(Duration.millis(30000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Game.isPlaying){
                    if (destroyed){
                        loop.stop();
                    }
                    Random random = new Random();
                    int rand = random.nextInt(4);
                    YmirStrategy ability = null;
                    if (rand == 1){
                        ability = new InfiniteVoid();
                    }else if (rand == 2){
                        ability = new Hollow();
                    }else if (rand == 3){
                        ability = new DoubleAccel();
                    }
                    if ((ability != null)&(!destroyed)){
                        ability.activateAbility(Ymir.this,parent);
                    }
                }
            }
        }));
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }
}
