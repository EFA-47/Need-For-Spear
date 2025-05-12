package test;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.*;
import org.junit.Test;
import static org.junit.Assert.*;
public class AddTest {

    @Test
    public void addSimple(){
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

                            int size = Game.obstacles.size();
                            game[0].getNumberOf().setText("1");
                            game[0].add(0);
                            assertEquals(size+1,Game.obstacles.size());
                            assertTrue(Game.obstacles.get(Game.obstacles.size()-1) instanceof SimpleObstacle);
                            game[0].getNumberOf().setText("3");
                            game[0].add(0);
                            assertEquals(size+4,Game.obstacles.size());
                            assertTrue(Game.obstacles.get(Game.obstacles.size()-1) instanceof SimpleObstacle);
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
    public void addFirm(){
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

                            int size = Game.obstacles.size();
                            game[0].getNumberOf().setText("1");
                            game[0].add(1);
                            assertEquals(size+1,Game.obstacles.size());
                            assertTrue(Game.obstacles.get(Game.obstacles.size()-1) instanceof FirmObstacle);
                            game[0].getNumberOf().setText("3");
                            game[0].add(1);
                            assertEquals(size+4,Game.obstacles.size());
                            assertTrue(Game.obstacles.get(Game.obstacles.size()-1) instanceof FirmObstacle);
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
    public void addExplosive(){
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

                            int size = Game.obstacles.size();
                            game[0].getNumberOf().setText("1");
                            game[0].add(2);
                            assertEquals(size+1,Game.obstacles.size());
                            assertTrue(Game.obstacles.get(Game.obstacles.size()-1) instanceof ExplosiveObstacle);
                            game[0].getNumberOf().setText("3");
                            game[0].add(2);
                            assertEquals(size+4,Game.obstacles.size());
                            assertTrue(Game.obstacles.get(Game.obstacles.size()-1) instanceof ExplosiveObstacle);
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
    public void addGift(){
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

                            int size = Game.obstacles.size();
                            game[0].getNumberOf().setText("1");
                            game[0].add(3);
                            assertEquals(size+1,Game.obstacles.size());
                            assertTrue(Game.obstacles.get(Game.obstacles.size()-1) instanceof GiftObstacle);
                            game[0].getNumberOf().setText("3");
                            game[0].add(3);
                            assertEquals(size+4,Game.obstacles.size());
                            assertTrue(Game.obstacles.get(Game.obstacles.size()-1) instanceof GiftObstacle);
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
    public void doNotAddMoreThanLimit(){
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


                            game[0].getNumberOf().setText("1");
                            for (int i = 0; i< 140;i++) {
                                game[0].add(0);
                            }
                            int size = Game.obstacles.size();
                            game[0].add(0);
                            assertEquals(size,Game.obstacles.size());
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
