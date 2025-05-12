package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SimpleObstacle extends Obstacle{
    public SimpleObstacle(int x, int y, int width, int height,Game parent) {
        super(x, y, width, height,parent);
        shape = new Rectangle(width,height);
        shape.setFill(Color.DARKGREEN);
        setListener();
        shape.setLayoutX(x);
        shape.setLayoutY(y);
//        Timeline loop = new Timeline(new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                if (Game.isPlaying & canCollide){
//                    for (int i=0;i<Game.obstacles.size();i++){
//                        if (shape.intersects(Game.obstacles.get(i).getShape().getBoundsInParent()) & !Game.obstacles.get(i).shape.equals(shape)){
//                            if (direction == Direction.LEFT) direction = Direction.RIGHT;
//                            else if (direction == Direction.RIGHT) direction = Direction.LEFT;
//
//                        }
//                    }
//                    if (direction == Direction.LEFT){
//                        shape.setLayoutX(shape.getLayoutX() - 1);
//                    }
//                    if (direction == Direction.RIGHT){
//                        shape.setLayoutX(shape.getLayoutX() + 1);
//                    }
//                }
//
//            }
//        }));
//        loop.setCycleCount(Timeline.INDEFINITE);
//        loop.play();
    }
}
