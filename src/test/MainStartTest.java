package test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MainStartTest {

    @Test
    public void setSimple(){
        final Game[] game = new Game[1];
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Main main = new Main();
                            main.init();
                            main.start(new Stage());
                            main.getNumberOf().setText("80");
                            main.getSimple().fire();
                            Thread.sleep(1000);
                            game[0] = new Game(null);
                            Main.stage.setScene(new Scene(game[0],Main.WIDTH /*+ 200*/,Main.HEIGHT));
                            game[0].setListener();

                            List<Obstacle> obstacles = Game.obstacles;
                            int count = 0;
                            for (Obstacle obstacle:obstacles){
                                if (obstacle instanceof SimpleObstacle){
                                    count++;
                                }
                            }
                            assertEquals(80,count);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setFirm(){
        final Game[] game = new Game[1];
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Main main = new Main();
                            main.init();
                            main.start(new Stage());
                            main.getNumberOf().setText("20");
                            main.getFirm().fire();
                            Thread.sleep(1000);
                            game[0] = new Game(null);
                            Main.stage.setScene(new Scene(game[0],Main.WIDTH /*+ 200*/,Main.HEIGHT));
                            game[0].setListener();

                            List<Obstacle> obstacles = Game.obstacles;
                            int count = 0;
                            for (Obstacle obstacle:obstacles){
                                if (obstacle instanceof FirmObstacle){
                                    count++;
                                }
                            }
                            assertEquals(20,count);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setExplosive(){
        final Game[] game = new Game[1];
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Main main = new Main();
                            main.init();
                            main.start(new Stage());
                            main.getNumberOf().setText("20");
                            main.getExplosive().fire();
                            Thread.sleep(1000);
                            game[0] = new Game(null);
                            Main.stage.setScene(new Scene(game[0],Main.WIDTH /*+ 200*/,Main.HEIGHT));
                            game[0].setListener();

                            List<Obstacle> obstacles = Game.obstacles;
                            int count = 0;
                            for (Obstacle obstacle:obstacles){
                                if (obstacle instanceof ExplosiveObstacle){
                                    count++;
                                }
                            }
                            assertEquals(20,count);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setGift(){
        final Game[] game = new Game[1];
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Main main = new Main();
                            main.init();
                            main.start(new Stage());
                            main.getNumberOf().setText("20");
                            main.getGift().fire();
                            Thread.sleep(1000);
                            game[0] = new Game(null);
                            Main.stage.setScene(new Scene(game[0],Main.WIDTH /*+ 200*/,Main.HEIGHT));
                            game[0].setListener();

                            List<Obstacle> obstacles = Game.obstacles;
                            int count = 0;
                            for (Obstacle obstacle:obstacles){
                                if (obstacle instanceof GiftObstacle){
                                    count++;
                                }
                            }
                            assertEquals(20,count);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setInvalid(){
        final Game[] game = new Game[1];
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                new JFXPanel();
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Main main = new Main();
                            main.init();
                            main.start(new Stage());
                            main.getNumberOf().setText("2");
                            main.getGift().fire();
                            Thread.sleep(1000);
                            game[0] = new Game(null);
                            Main.stage.setScene(new Scene(game[0],Main.WIDTH /*+ 200*/,Main.HEIGHT));
                            game[0].setListener();

                            List<Obstacle> obstacles = Game.obstacles;
                            int count = 0;
                            for (Obstacle obstacle:obstacles){
                                if (obstacle instanceof GiftObstacle){
                                    count++;
                                }
                            }
                            assertEquals(10,count);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
