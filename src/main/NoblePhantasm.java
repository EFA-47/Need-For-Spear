package main;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class NoblePhantasm {
    private static NoblePhantasm noblePhantasm = null;
    private final Shape paddle;
    private int angle = 0;
    private NoblePhantasm(){
        paddle = new Rectangle(Game.PADDLE_WIDTH,Game.PADDLE_HEIGHT);
    }

    public static NoblePhantasm getInstance(){
        if (noblePhantasm == null) noblePhantasm = new NoblePhantasm();
        return noblePhantasm;
    }

    public void rotateLeft(){
        angle -= 15;
        if (angle < -45) {
            angle = -45;
            return;
        }
        paddle.setRotate(angle);
    }

    public void rotateRight(){
        angle += 15;
        if (angle > 45) {
            angle = 45;
            return;
        }
        paddle.setRotate(angle);
    }

    public int getAngle() {
        return angle;
    }

    public Shape getPaddle() {
        return paddle;
    }
}
