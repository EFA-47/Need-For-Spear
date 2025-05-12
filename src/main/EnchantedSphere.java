package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.List;

public class EnchantedSphere {
    private static EnchantedSphere enchantedSphere=null;
    public Shape sphere;
    private double x;
    private double y;
    private int speedX, speedY;
    private int angle;
    private double startX=Main.WIDTH/2;
    private double startY=Main.HEIGHT-100;
    static protected Direction direction;
    private double acceleration = 1;




    private EnchantedSphere(){sphere = new Circle(15);
        sphere.setFill(Color.TEAL);
        x = startX;
        y = startY;
        this.speedX = 0;
        this.speedY = -5;
        this.angle = 90;
    }
    public static EnchantedSphere getInstance(){
        if(enchantedSphere==null){enchantedSphere=new EnchantedSphere();
        enchantedSphere.setStartX(Main.WIDTH/2);
        enchantedSphere.setStartY(Main.HEIGHT-100);
        }
        return enchantedSphere;
    }

    public Shape getSphere(){
        return sphere;
    }
/*
    public EnchantedSphere(double x, double y, List<Obstacle> obstacles, NoblePhantasm noblePhantasm, List<Rectangle> borders) {
        shape = new Circle(15);
        shape.setFill(Color.TEAL);
        this.x = startX = x;
        this.y = startY = y;
        this.speedX = 0;
        this.speedY = -5;
        this.angle = 90;
        this.paddle = noblePhantasm;
        this.borders = borders;
        shape.setLayoutX(x);
        shape.setLayoutY(y);
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(40), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (!Game.isPlaying) return;
                if (getAcceleration() != 1){
                    time += 40;
                }
                if (time >= 15000){
                    setAcceleration(1);
                }
                setX(getX() + (speedX * acceleration));
                setY(getY() + (speedY * acceleration));
                shape.setLayoutX(getX());
                shape.setLayoutY(getY());
                if (shape.getBoundsInParent().intersects(noblePhantasm.getPaddle().getBoundsInParent())) {
                         speedY = -getSpeedY();
                         if ( noblePhantasm.getAngle() >= 15){
                             speedX = 5;
                             direction=Direction.RIGHT;
                         }
                         if ( noblePhantasm.getAngle() <= -15){
                             speedX = -5;
                             direction=Direction.LEFT;
                         }
                    /*if((noblePhantasm.getAngle()==15) &&(direction.equals(Direction.LEFT)||direction.equals(Direction.UP))){
                        speedY=-3;
                        speedX=-4;
                        direction=Direction.LEFT;
                    }else if(noblePhantasm.getAngle()==15 &&(direction.equals(Direction.RIGHT)||direction.equals(Direction.UP))){
                        speedY=-4;
                        speedX=3;
                        direction=Direction.RIGHT;
                    }else if(noblePhantasm.getAngle()==30 &&(direction.equals(Direction.LEFT)||direction.equals(Direction.UP))){
                        speedY=-4;
                        speedX=-3;
                        direction=Direction.LEFT;
                    }else if(noblePhantasm.getAngle()==30 &&(direction.equals(Direction.RIGHT)||direction.equals(Direction.UP))){
                        speedY=-3;
                        speedX=4;
                        direction=Direction.RIGHT;
                    }else if(noblePhantasm.getAngle()==45 &&(direction.equals(Direction.LEFT)||direction.equals(Direction.UP))){
                        speedY=-5;
                        speedX=0;
                        direction=Direction.UP;
                    }else if(noblePhantasm.getAngle()==45 &&(direction.equals(Direction.RIGHT)||direction.equals(Direction.UP))){
                        speedY=0;
                        speedX=5;
                        direction=Direction.RIGHT;
                    }else if(noblePhantasm.getAngle()==-15 &&(direction.equals(Direction.LEFT)||direction.equals(Direction.UP))){
                        speedY=-4;
                        speedX=3;
                        direction=Direction.RIGHT;
                    }else if(noblePhantasm.getAngle()==-15 &&(direction.equals(Direction.RIGHT)||direction.equals(Direction.UP))){
                        speedY=-3;
                        speedX=4;
                        direction=Direction.RIGHT;
                    }else if(noblePhantasm.getAngle()==-30 &&(direction.equals(Direction.LEFT)||direction.equals(Direction.UP))){
                        speedY=-3;
                        speedX=-4;
                        direction=Direction.LEFT;
                    }else if(noblePhantasm.getAngle()==-30 &&(direction.equals(Direction.RIGHT)||direction.equals(Direction.UP))){
                        speedY=-4;
                        speedX=-3;
                        direction=Direction.LEFT;
                    }else if(noblePhantasm.getAngle()==-45 &&(direction.equals(Direction.LEFT)||direction.equals(Direction.UP))){
                        speedY=0;
                        speedX=-5;
                        direction=Direction.LEFT;
                    }else if(noblePhantasm.getAngle()==-45 &&(direction.equals(Direction.RIGHT)||direction.equals(Direction.UP))){
                        speedY=-5;
                        speedX=0;
                        direction=Direction.UP;
                    }else{
                        speedY=-getSpeedY();
                    }

                }
                if (shape.getBoundsInParent().intersects(borders.get(1).getBoundsInParent())) {
                    //border 1 is right border
                    speedX = -getSpeedX();

                    direction=Direction.LEFT;
                }
                if (shape.getBoundsInParent().intersects(borders.get(0).getBoundsInParent())) {
                    //border 0 is left border
                   speedX = -getSpeedX();

                    direction=Direction.RIGHT;
                }
                if (shape.getBoundsInParent().intersects(borders.get(3).getBoundsInParent())) {
                    //border 3 is bottom border
                    Game.remainingLives--;
                    Game.updateUI();
                    setX(startX);
                    setY(startY);
                    speedX = 0;
                    speedY = -5;
                }
                if (shape.getBoundsInParent().intersects(borders.get(2).getBoundsInParent())) {
                    //border 2 is top border
                    speedY = -getSpeedY();
                    if (speedX == 0 & speedY == -5) {
                        //speedX = 5;
                        speedY = 5;
                    }
                }
                for (int i = 0; i < obstacles.size(); i++) {
                    if (shape.getBoundsInParent().intersects(obstacles.get(i).shape.getBoundsInParent())) {
                        if (speedX == 0 & speedY == -5 & obstacles.get(i).canCollide) {
                            if (obstacles.get(i).getWidth()!=15) {
                                speedX = 5;
                            }
                            direction=Direction.RIGHT;

                        }
                        Point point = obstacles.get(i).getSpeedAndDirection(shape, speedX, speedY);
                        speedX = point.getX();
                        speedY = point.getY();
                    }
                    if (i >= obstacles.size()) continue;
                    if (noblePhantasm.getPaddle().getBoundsInParent().intersects(obstacles.get(i).shape.getBoundsInParent())) {
                        if (obstacles.get(i) instanceof ExplosiveObstacle) {
                            obstacles.get(i).falling = false;
                            Game.remainingLives--;
                            Game.updateUI();
                        } else if (obstacles.get(i) instanceof GiftObstacle) {
                            obstacles.get(i).falling = false;
                            Game.remainingLives += 1;
                            Game.updateUI();
                        }
                    }
                }
                if (Game.remainingLives == 0) {
                    Game.isPlaying = false;
                    Game.gameOver.setVisible(true);
                    Game.gameOver.setText("Game Over\nScore: " + Game.score);

                }
                if(Game.obstacles.size()==0){
                    Game.isPlaying=false;
                    Game.gameOver.setVisible(true);
                    Game.gameOver.setText("You Won!\nScore: " + Game.score);
                }
            }

        }));

        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }*/

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }
}
