package test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Game;
import main.Main;
import org.junit.Test;
import static org.junit.Assert.*;


public class BallTest {
    @Test
    public void ballStartingDirection() throws InterruptedException {
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
        thread.start();
        Thread.sleep(2000);

        try {

            Button play_pause = game[0].getPlay_pause();
            Game.isPlaying = true;
            game[0].getTimer().start();
            assertEquals(0,game[0].getEnchantedSphere().getSpeedX());
            assertEquals(-5,game[0].getEnchantedSphere().getSpeedY());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(1000);
    }

    @Test
    public void ballBounceDirectionFromObstacle() throws InterruptedException {
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
        thread.start();
        Thread.sleep(2000);
        try {
            Game.isPlaying = true;
            game[0].getTimer().start();
            assertEquals(0,game[0].getEnchantedSphere().getSpeedX());
            assertEquals(-5,game[0].getEnchantedSphere().getSpeedY());
            Thread.sleep(3000);
            assertEquals(0,game[0].getEnchantedSphere().getSpeedX());
            assertEquals(5,game[0].getEnchantedSphere().getSpeedY());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(1000);
    }

    @Test
    public void ballBounceDirectionFromPaddle() throws InterruptedException {
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
        thread.start();
        Thread.sleep(2000);
        try {
            Game.isPlaying = true;
            game[0].getTimer().start();
            assertEquals(0,game[0].getEnchantedSphere().getSpeedX());
            assertEquals(-5,game[0].getEnchantedSphere().getSpeedY());
            Thread.sleep(3000);
            //game[0].getPaddle().setLayoutX(Main.WIDTH - 250);
            assertEquals(0,game[0].getEnchantedSphere().getSpeedX());
            assertEquals(5,game[0].getEnchantedSphere().getSpeedY());
            Thread.sleep(1500);
            assertEquals(0,game[0].getEnchantedSphere().getSpeedX());
            assertEquals(-5,game[0].getEnchantedSphere().getSpeedY());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(1000);
    }

    @Test
    public void ballBounceDirectionFromBorder() throws InterruptedException {
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
        thread.start();
        Thread.sleep(2000);
        try {
            Game.isPlaying = true;
            game[0].getTimer().start();
            assertEquals(0,game[0].getEnchantedSphere().getSpeedX());
            assertEquals(-5,game[0].getEnchantedSphere().getSpeedY());
            Thread.sleep(3000);
            //game[0].getPaddle().setLayoutX(Main.WIDTH - 250);
            assertEquals(0,game[0].getEnchantedSphere().getSpeedX());
            assertEquals(5,game[0].getEnchantedSphere().getSpeedY());
            Thread.sleep(1500);
            assertEquals(5,game[0].getEnchantedSphere().getSpeedX());
            assertEquals(-5,game[0].getEnchantedSphere().getSpeedY());
            Thread.sleep(4000);
            assertEquals(-5,game[0].getEnchantedSphere().getSpeedX());
            assertEquals(5,game[0].getEnchantedSphere().getSpeedY());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(1000);
    }

    @Test
    public void ballBottomBorder() throws InterruptedException {
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
        thread.start();
        Thread.sleep(2000);
        try {
            Game.isPlaying = true;
            game[0].getTimer().start();
            int remainingLives = Game.remainingLives;
            assertEquals(0,game[0].getEnchantedSphere().getSpeedX());
            assertEquals(-5,game[0].getEnchantedSphere().getSpeedY());
            Thread.sleep(5000);
            assertNotEquals(remainingLives,Game.remainingLives);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(1000);
    }
}
