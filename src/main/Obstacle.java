package main;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.sql.Time;


public class Obstacle {
    private int x;
    private int y;
    private int width;
    private int height;
    private final Game parent;
    protected Shape shape;
    private int ability;

    private int Health = 1;
    protected boolean canCollide = true;
    public boolean falling = true;
    public Label tag = new Label(getHealth() + "");
    public boolean destroyed = false;
    public boolean frozen = false;
    public boolean hallow = false;
    private Color color;

    public Obstacle(int x, int y, int width, int height, Game parent) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.parent = parent;

    }

    public void setListener() {
        tag.setLayoutX(shape.getLayoutX() + width / 2.0);
        tag.setLayoutY(shape.getLayoutY());
        tag.setText(getHealth() + "");
        tag.setTextFill(Color.WHITE);
        color = (Color) shape.getFill();
        shape.setOnMouseDragged(event -> {
            if (Game.isPlaying) return;
            double sceneX = event.getSceneX();
            double sceneY = event.getSceneY();

            if (shape instanceof Rectangle) {
                double layoutX = shape.getLayoutX();
                double layoutY = shape.getLayoutY();
                shape.setLayoutX(sceneX);
                shape.setLayoutY(sceneY);
                tag.setLayoutX(sceneX + width / 2.0);
                tag.setLayoutY(sceneY);
                tag.toFront();
                boolean canMove = true;
                for (Obstacle obstacle : Game.obstacles) {
                    if (obstacle.shape.getBoundsInParent().intersects(shape.getBoundsInParent())) {
                        if (!shape.equals(obstacle.shape))
                            canMove = false;
                    }
                }
                if (!canMove) {
                    shape.setLayoutX(layoutX);
                    shape.setLayoutY(layoutY);
                    tag.setLayoutX(layoutX + width / 2.0);
                    tag.setLayoutY(layoutY);
                    tag.toFront();
                }
            }
            if (shape instanceof Circle) {
                double centerX = ((Circle) shape).getCenterX();
                double centerY = ((Circle) shape).getCenterY();
                ((Circle) shape).setCenterX(sceneX);
                ((Circle) shape).setCenterY(sceneY);
                boolean canMove = true;
                for (Obstacle obstacle : Game.obstacles) {
                    if (obstacle.shape.getBoundsInParent().intersects(shape.getBoundsInParent())) {
                        if (!shape.equals(obstacle.shape))
                            canMove = false;
                    }
                }
                if (!canMove) {
                    ((Circle) shape).setCenterX(centerX);
                    ((Circle) shape).setCenterY(centerY);
                }
            }

        });
        shape.setOnMouseClicked(event -> Game.selected = this);
    }

    public void setX(int x) {
        this.x = x;
        if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            circle.setCenterX(x + width);
            return;
        }
        this.shape.setLayoutX(x);
        tag.setLayoutX(x);
    }

    public void setY(int y) {
        this.y = y;
        if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            circle.setCenterY(y + width);
            return;
        }
        this.shape.setLayoutY(y);
        tag.setLayoutY(y);
    }

    public Point getSpeedAndDirection(Shape other, int x, int y) {
        Point point = new Point(x, y);
        if (!canCollide) return point;
        Rectangle top = new Rectangle(4 * width / 5, 0);
        Rectangle bottom = new Rectangle(4 * width / 5, 0);
        Rectangle left = new Rectangle(0, height);
        Rectangle right = new Rectangle(0, height);
        top.setLayoutX(shape.getLayoutX() + width / 10);
        top.setLayoutY(shape.getLayoutY());
        bottom.setLayoutX(shape.getLayoutX() + width / 10);
        bottom.setLayoutY(shape.getLayoutY() + height);
        left.setLayoutY(shape.getLayoutY());
        left.setLayoutX(shape.getLayoutX());
        right.setLayoutY(shape.getLayoutY());
        right.setLayoutX(shape.getLayoutX() + width);
        if (shape.getBoundsInParent().intersects(other.getBoundsInParent())) {
            if (getHealth() > 0 & !frozen)
                setHealth(getHealth() - 1);
            tag.setText(getHealth() + "");
            if (getHealth() == 0) {
                tag.setText("");
                destroy();
            }
            if (top.getBoundsInParent().intersects(other.getBoundsInParent())) {
                point.setY(-point.getY());
            }
            if (right.getBoundsInParent().intersects(other.getBoundsInParent())) {
                if (EnchantedSphere.direction.equals(Direction.LEFT)) {
                    point.setX(-point.getX());
                    EnchantedSphere.direction = Direction.RIGHT;
                }
            }
            if (left.getBoundsInParent().intersects(other.getBoundsInParent())) {
                if (EnchantedSphere.direction.equals(Direction.RIGHT)) {
                    point.setX(-point.getX());
                    EnchantedSphere.direction = Direction.LEFT;
                }
            }
            if (bottom.getBoundsInParent().intersects(other.getBoundsInParent())) {
                point.setY(-point.getY());
            }
        }
        return point;
    }

    public void melt() {
        Timeline timeline = new Timeline(new KeyFrame(new Duration(15000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                frozen = false;
                shape.setFill(color);
            }
        }));
        timeline.playFromStart();
    }

    protected void destroy() {
        destroyed = true;
        parent.getChildren().remove(shape);
        Game.obstacles.remove(this);
        if (!hallow){
            if(!(this instanceof ExplosiveObstacle))
            Game.score +=  (300 / parent.getSecond());
        }
        Game.updateUI();
    }

    public int getWidth() {
        return width;
    }

    public int getAbility() {
        return ability;
    }

    public void setAbility(int ability) {
        this.ability = ability;
    }

    public Shape getShape() {
        return shape;
    }

    public int getHealth() {
        return Health;
    }

    public void setHealth(int health) {
        Health = health;
    }
}
