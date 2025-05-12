package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.sql.Time;

public class ExplosiveObstacle extends Obstacle{
    private Timeline loop;
    public ExplosiveObstacle(int x, int y, int width, int height, Game parent) {
        super(x, y, width, height,parent);
        shape = new Circle(width);
        shape.setFill(Color.DARKRED);
        setHealth(1);
        setListener();
        ((Circle) shape).setCenterX(x);
        ((Circle) shape).setCenterY(y);
    }

    @Override
    protected void destroy() {
        canCollide = false;
        loop = new Timeline(new KeyFrame(Duration.millis(40), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Game.isPlaying){
                    shape.setLayoutY(shape.getLayoutY()+1);
                    if (!falling){
                        ExplosiveObstacle.super.destroy();
                        loop.stop();
                    }
                }
            }
        }));
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }
}
