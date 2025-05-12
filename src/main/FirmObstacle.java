package main;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FirmObstacle extends Obstacle{

    public FirmObstacle(int x, int y, int width, int height, Game parent) {
        super(x, y, width, height,parent);
        shape = new Rectangle(width,height);
        shape.setFill(Color.BLACK);
        setHealth(5);
        setListener();
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }
}
