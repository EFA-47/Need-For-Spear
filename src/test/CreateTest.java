package test;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
public class CreateTest {
    @Test
    public void createSimple(){
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
                            assertEquals(75,count);
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
    public void createFirm(){
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

    @Test
    public void createExplosive(){
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
                            assertEquals(5,count);
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
    public void createGift(){
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
