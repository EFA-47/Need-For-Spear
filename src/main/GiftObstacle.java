package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GiftObstacle extends Obstacle{
    private Timeline loop;
    public GiftObstacle(int x, int y, int width, int height, Game parent) {
        super(x, y, width, height,parent);
        shape = new Rectangle(width,height);
        shape.setFill(Color.DARKBLUE);
        setHealth(2);
        setListener();
        shape.setLayoutX(x);
        shape.setLayoutY(y);
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
                        GiftObstacle.super.destroy();
                        loop.stop();
                    }
                }
            }
        }));
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }
}
