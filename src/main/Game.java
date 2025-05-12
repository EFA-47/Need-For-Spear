package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import main.ymir.Ymir;

import javax.swing.Timer;

import java.io.*;
import java.util.*;

public class Game extends Pane {

    private static final int OBSTACLE_HEIGHT = 20;
    private static final int OBSTACLE_WIDTH = Main.WIDTH/20;
    private static final int GAP = 15;
    private static int SIMPLE_OBSTACLES = 75;
    private static int FIRM = 10;
    private static int GIFT = 10;
    private static int EXPLOSIVE = 5;
    private static final int OFFSET = 12;
    public static final int PADDLE_WIDTH = Main.WIDTH/10;
    public static final int PADDLE_HEIGHT = 20;
    public static int score = 0;
    public static int remainingLives = 3;
    public static boolean isPlaying = false;
    private static Label score_lbl = new Label("Score: "+score);
    private static Label life_lbl = new Label("Remaining Lives: "+remainingLives);
    private Button play_pause = new Button("Play");
    private Button save = new Button("Save");
    private int time = 0;
    private EnchantedSphere enchantedSphere= EnchantedSphere.getInstance();
    public static Label gameOver = new Label("Game Over");
    private List<Rectangle> borders = new ArrayList<>();
    private VBox buttons = new VBox(10);
    private Button simple,firm,explosive,gift,remove;
    private TextField numberOf = new TextField();
    public static Obstacle selected = null;
    private int selectedType = -1;
    private Timer timer;
    public int second;
    public static final List<Obstacle> obstacles = new ArrayList<>();
    private int x,y;

    public int getSecond() {
        return second;
    }
    public void simpleTimer(){
        timer= new Timer(1000, e -> second++);
    }

    public Game(List<Data> loadData){
        simpleTimer();
        if (loadData == null) {
            create();
            draw();
        }else{
            load(loadData);
            updateUI();
        }
        gameOver.setFont(new Font(50));
        gameOver.setVisible(false);
        gameOver.setLayoutX(Main.WIDTH/2.0);
        gameOver.setLayoutY(Main.HEIGHT/2.0);
        Rectangle left = new Rectangle(10, Main.HEIGHT);
        Rectangle top = new Rectangle(Main.WIDTH, 10);
        getChildren().add(left);
        getChildren().add(top);
        Rectangle right = new Rectangle(10, Main.HEIGHT);
        Rectangle bottom = new Rectangle(Main.WIDTH,10);
        right.setLayoutX(Main.WIDTH - 10);
        bottom.setLayoutY(Main.HEIGHT - 10);
        getChildren().addAll(right,bottom);
        noblePhantasm.getPaddle().setLayoutX(Main.WIDTH/2.0 - PADDLE_WIDTH/2.0 );
        noblePhantasm.getPaddle().setLayoutY(Main.HEIGHT - 60 - PADDLE_HEIGHT/2.0);
        getChildren().add(noblePhantasm.getPaddle());
        borders.add(left);
        borders.add(right);
        borders.add(top);
        borders.add(bottom);
        enchantedSphere.setSpeedX(0);
        enchantedSphere.setSpeedY(-5);
        enchantedSphere.setStartX(Main.WIDTH/2.0);
        enchantedSphere.setStartY(Main.HEIGHT - 100);
        enchantedSphere.getSphere().setLayoutX(Main.WIDTH/2.0);
        enchantedSphere.getSphere().setLayoutY(Main.HEIGHT - 100);
        EnchantedSphere.direction =Direction.UP;
        getChildren().add(enchantedSphere.getSphere());
        getChildren().addAll(score_lbl,life_lbl,play_pause,save);
        play_pause.setLayoutX(10);
        play_pause.setLayoutY(Main.HEIGHT-40);
        save.setLayoutX(Main.WIDTH - 50);
        save.setLayoutY(Main.HEIGHT-40);
        score_lbl.setLayoutX(60);
        score_lbl.setLayoutY( Main.HEIGHT - 30);
        life_lbl.setLayoutX(120);
        life_lbl.setLayoutY( Main.HEIGHT - 30);

        play_pause.setOnAction(e->{
            if (isPlaying){
                play_pause.setText("Play");
                isPlaying = false;
                timer.stop();
            }else{
                play_pause.setText("Pause");
                isPlaying = true;
                Main.stage.setWidth(Main.WIDTH /*+ 18*/);
                timer.start();
            }
        });

        save.setOnAction(e->{
            List<Data> dataList = new ArrayList<>();
            for (Obstacle obstacle:obstacles){
                Data data = null;
                if (obstacle instanceof SimpleObstacle)
                    data = new Data(obstacle.shape.getLayoutX(),obstacle.shape.getLayoutY(),0);
                else if(obstacle instanceof FirmObstacle)
                    data = new Data(obstacle.shape.getLayoutX(),obstacle.shape.getLayoutY(),1);
                else if(obstacle instanceof ExplosiveObstacle)
                    data = new Data(((Circle) obstacle.shape).getCenterX(),((Circle) obstacle.shape).getCenterY(),2);
                else if(obstacle instanceof GiftObstacle)
                    data = new Data(obstacle.shape.getLayoutX(),obstacle.shape.getLayoutY(),3);
                else if (obstacle instanceof Ymir)
                    data = new Data(obstacle.shape.getLayoutX(),obstacle.shape.getLayoutY(),4);
                dataList.add(data);
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("NFS Save","*.nfs"));
            File file = fileChooser.showSaveDialog(Main.stage);
            if (file == null) return;
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                Main.currentUser.setLives(remainingLives);
                Main.currentUser.setScore(score);
                oos.writeObject(Main.currentUser);
                for (Data data:dataList){
                    oos.writeObject(data);
                }
                oos.writeObject(null);
                oos.flush();
                oos.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Save File");
                alert.setHeaderText("File Save");
                alert.setContentText("Game saved");
                alert.showAndWait();
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        getChildren().add(gameOver);

        buttons.setAlignment(Pos.CENTER);
        buttons.setLayoutX(Main.WIDTH + 10);
        simple = new Button("Simple");
        firm = new Button("Firm");
        explosive = new Button("Explosive");
        gift = new Button("Gift");
        remove = new Button("Remove");
        buttons.getChildren().addAll(new Label("Obstacles"),numberOf,simple,firm,explosive,gift,remove);
        getChildren().add(buttons);
        simple.setOnAction(event -> add(0));
        firm.setOnAction(event -> add(1));
        explosive.setOnAction(event -> add(2));
        gift.setOnAction(event -> add(3));
        remove.setOnAction(event -> {
            if (selected == null) return;
            obstacles.remove(selected);
            getChildren().remove(selected.shape);
            getChildren().remove(selected.tag);
        });
    }

    public void add(int type){
        int numbers;
        if (y > Main.HEIGHT - 50) return;
        try {
            numbers = Integer.parseInt(numberOf.getText());
        }catch (NumberFormatException nfe){
            return;
        }
        if (type == 0){
            for (int j=0;j<numbers;j++){
                for (int i=0;i<16;i++) {
                    if (j >= numbers) break;
                    SimpleObstacle simpleObstacle = new SimpleObstacle(x,y,OBSTACLE_WIDTH,OBSTACLE_HEIGHT,this);
                    obstacles.add(simpleObstacle);
                    getChildren().add(simpleObstacle.shape);
                    getChildren().add(simpleObstacle.tag);
                    x += simpleObstacle.getWidth() + GAP;
                    j++;
                    if (x > Main.WIDTH - 50){
                        x = OFFSET;
                        y += OBSTACLE_HEIGHT + GAP;
                    }
                }

            }

        }
        if (type == 1){
            for (int j=0;j<numbers;j++){
                for (int i=0;i<16;i++) {
                    if (j >= numbers) break;
                    FirmObstacle firmObstacle = new FirmObstacle(x,y,OBSTACLE_WIDTH,OBSTACLE_HEIGHT,this);
                    obstacles.add(firmObstacle);
                    getChildren().add(firmObstacle.shape);
                    getChildren().add(firmObstacle.tag);
                    x += firmObstacle.getWidth() + GAP;
                    j++;
                    if (x > Main.WIDTH - 50){
                        x = OFFSET;
                        y += OBSTACLE_HEIGHT + GAP;
                    }
                }

            }

        }
        if (type == 2){
            for (int j=0;j<numbers;j++){
                for (int i=0;i<16;i++) {
                    if (j >= numbers) break;
                    ExplosiveObstacle explosiveObstacle = new ExplosiveObstacle(x,y,10,OBSTACLE_HEIGHT,this);
                    obstacles.add(explosiveObstacle);
                    getChildren().add(explosiveObstacle.shape);
                    getChildren().add(explosiveObstacle.tag);
                    x += explosiveObstacle.getWidth() + GAP;
                    j++;
                    if (x > Main.WIDTH - 50){
                        x = OFFSET;
                        y += OBSTACLE_HEIGHT + GAP;
                    }
                }

            }

        }
        if (type == 3){
            for (int j=0;j<numbers;j++){
                for (int i=0;i<16;i++) {
                    if (j >= numbers) break;
                    GiftObstacle giftObstacle = new GiftObstacle(x,y,OBSTACLE_WIDTH,OBSTACLE_HEIGHT,this);
                    obstacles.add(giftObstacle);
                    getChildren().add(giftObstacle.shape);
                    getChildren().add(giftObstacle.tag);
                    x += giftObstacle.getWidth() + GAP;
                    j++;
                    if (x > Main.WIDTH - 50){
                        x = OFFSET;
                        y += OBSTACLE_HEIGHT + GAP;
                    }
                }

            }

        }
    }

    private void load(List<Data> data){
        for (Data d:data){
            if (d.getType() == 0){
                SimpleObstacle simpleObstacle = new SimpleObstacle((int) d.getX(),(int) d.getY(),OBSTACLE_WIDTH,OBSTACLE_HEIGHT,this);
                simpleObstacle.tag.setLayoutX(d.getX());
                simpleObstacle.tag.setLayoutY(d.getY());
                obstacles.add(simpleObstacle);
            }
            if (d.getType() == 1){
                FirmObstacle firmObstacle = new FirmObstacle((int) d.getX(),(int) d.getY(),OBSTACLE_WIDTH,OBSTACLE_HEIGHT,this);
                firmObstacle.tag.setLayoutX(d.getX());
                firmObstacle.tag.setLayoutY(d.getY());
                obstacles.add(firmObstacle);
            }
            if (d.getType() == 2){
                ExplosiveObstacle explosiveObstacle = new ExplosiveObstacle((int) d.getX(),(int) d.getY(),15,OBSTACLE_HEIGHT,this);
                explosiveObstacle.tag.setVisible(false);
                obstacles.add(explosiveObstacle);
            }
            if (d.getType() == 3){
                GiftObstacle giftObstacle = new GiftObstacle((int) d.getX(),(int) d.getY(),OBSTACLE_WIDTH,OBSTACLE_HEIGHT,this);
                giftObstacle.tag.setLayoutX(d.getX());
                giftObstacle.tag.setLayoutY(d.getY());
                obstacles.add(giftObstacle);
            }
            if (d.getType() == 4){
                Ymir ymir = new Ymir((int) d.getX(),(int) d.getY(),OBSTACLE_WIDTH,OBSTACLE_HEIGHT,this);

                ymir.tag.setLayoutX(d.getX());
                ymir.tag.setLayoutY(d.getY());
                obstacles.add(ymir);
            }
        }
        for (Obstacle obstacle:obstacles){
            getChildren().add(obstacle.getShape());
            getChildren().add(obstacle.tag);
            obstacle.tag.toFront();
        }
        remainingLives=Main.currentUser.getLives();
        score=Main.currentUser.getScore();
    }

    public static void updateUI(){
        score_lbl.setText("Score: "+score);
        life_lbl.setText("Remaining Lives: "+remainingLives);
    }

    public void setListener(){
        Main.stage.getScene().setOnKeyPressed(event -> {
            Shape paddle = noblePhantasm.getPaddle();
            KeyCode code = event.getCode();
            if (code == KeyCode.LEFT){
                paddle.setLayoutX(paddle.getLayoutX() - 10);
                boolean collision = false;
                for (Rectangle rectangle:borders){
                    if (paddle.getBoundsInParent().intersects(rectangle.getBoundsInParent())){
                        collision = true;
                    }
                }
                if (collision) {
                    paddle.setLayoutX(paddle.getLayoutX() + 10);
                }
            }else if (code == KeyCode.RIGHT){
                paddle.setLayoutX(paddle.getLayoutX() + 10);
                boolean collision = false;
                for (Rectangle rectangle:borders){
                    if (paddle.getBoundsInParent().intersects(rectangle.getBoundsInParent())){
                        collision = true;
                    }
                }
                if (collision) {
                    paddle.setLayoutX(paddle.getLayoutX() - 10);
                }
            }
            if (code == KeyCode.A){
                noblePhantasm.rotateLeft();
            }else if (code == KeyCode.D){
                noblePhantasm.rotateRight();
            }
        });
        Main.stage.getScene().setOnMouseClicked(event -> {
            int sceneX = (int) event.getSceneX();
            int sceneY = (int) event.getSceneY();
            if (selectedType == -1) return;
            int numbers;
            try {
                 numbers = Integer.parseInt(numberOf.getText());
            }catch (NumberFormatException nfe){
                return;
            }
            if (selectedType == 0){
                for (int j=0;j<numbers;j++){
                    for (int i=0;i<16;i++) {
                        SimpleObstacle simpleObstacle = new SimpleObstacle(sceneX,sceneY,OBSTACLE_WIDTH,OBSTACLE_HEIGHT,this);
                        obstacles.add(simpleObstacle);
                        getChildren().add(simpleObstacle.shape);
                        getChildren().add(simpleObstacle.tag);
                        x += simpleObstacle.getWidth() + GAP;
                        j++;
                        if (j >= obstacles.size()) break;
                    }
                    x = OFFSET;
                    y += OBSTACLE_HEIGHT + GAP;
                }

            }
            if (selectedType == 1){
                FirmObstacle firmObstacle = new FirmObstacle(sceneX,sceneY,OBSTACLE_WIDTH,OBSTACLE_HEIGHT,this);
                obstacles.add(firmObstacle);
                getChildren().add(firmObstacle.shape);
                getChildren().add(firmObstacle.tag);
            }
            if (selectedType == 2){
                ExplosiveObstacle explosiveObstacle = new ExplosiveObstacle(sceneX,sceneY,10,OBSTACLE_HEIGHT,this);
                obstacles.add(explosiveObstacle);
                getChildren().add(explosiveObstacle.shape);
            }
            if (selectedType == 3){
                GiftObstacle giftObstacle = new GiftObstacle(sceneX,sceneY,OBSTACLE_WIDTH,OBSTACLE_HEIGHT,this);
                obstacles.add(giftObstacle);
                getChildren().add(giftObstacle.shape);
                getChildren().add(giftObstacle.tag);
            }
            selectedType = -1;
        });




        Timeline loop = new Timeline(new KeyFrame(Duration.millis(40), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (!Game.isPlaying) return;
                if (enchantedSphere.getAcceleration() != 1){
                    time += 40;
                }
                if (time >= 15000){
                    enchantedSphere.setAcceleration(1);
                }
                enchantedSphere.setX(enchantedSphere.getX() + (enchantedSphere.getSpeedX() * enchantedSphere.getAcceleration()));
                enchantedSphere.setY(enchantedSphere.getY() + (enchantedSphere.getSpeedY() * enchantedSphere.getAcceleration()));
                enchantedSphere.getSphere().setLayoutX(enchantedSphere.getX());
                enchantedSphere.getSphere().setLayoutY(enchantedSphere.getY());
                if (enchantedSphere.getSphere().getBoundsInParent().intersects(noblePhantasm.getPaddle().getBoundsInParent())) {
                    enchantedSphere.setSpeedY( -enchantedSphere.getSpeedY());
                    if ( noblePhantasm.getAngle() >= 15){
                        enchantedSphere.setSpeedX(5);
                        enchantedSphere.direction=Direction.RIGHT;
                    }
                    if ( noblePhantasm.getAngle() <= -15){
                        enchantedSphere.setSpeedX(-5);
                        enchantedSphere.direction=Direction.LEFT;
                    }
/*              if((noblePhantasm.getAngle()==15) && ((enchantedSphere.direction.equals(Direction.LEFT)))){
                    enchantedSphere.setSpeedY(-3);
                    enchantedSphere.setSpeedX(-4);
                    enchantedSphere.direction=Direction.LEFT;
                }else if(noblePhantasm.getAngle()==15 &&(!(enchantedSphere.direction.equals(Direction.LEFT)))){
                    enchantedSphere.setSpeedY(-4);
                    enchantedSphere.setSpeedX(3);
                    enchantedSphere.direction=Direction.RIGHT;
                }else if(noblePhantasm.getAngle()==30 &&((enchantedSphere.direction.equals(Direction.LEFT)))){
                    enchantedSphere.setSpeedY(-4);
                    enchantedSphere.setSpeedX(-3);
                    enchantedSphere.direction=Direction.LEFT;
                }else if(noblePhantasm.getAngle()==30 &&(!(enchantedSphere.direction.equals(Direction.LEFT)))){
                    enchantedSphere.setSpeedY(-3);
                    enchantedSphere.setSpeedX(4);
                    enchantedSphere.direction=Direction.RIGHT;
                }else if(noblePhantasm.getAngle()==45 &&((enchantedSphere.direction.equals(Direction.LEFT)))){
                    enchantedSphere.setSpeedY(-5);
                    enchantedSphere.setSpeedX(0);
                    enchantedSphere.direction=Direction.UP;
                }else if(noblePhantasm.getAngle()==45 &&(!(enchantedSphere.direction.equals(Direction.LEFT)))){
                    enchantedSphere.setSpeedY(0);
                    enchantedSphere.setSpeedX(5);
                    enchantedSphere.direction=Direction.RIGHT;
                }else if(noblePhantasm.getAngle()==-15 &&(!(enchantedSphere.direction.equals(Direction.RIGHT)))){
                    enchantedSphere.setSpeedY(-4);
                    enchantedSphere.setSpeedX(-3);
                    enchantedSphere.direction=Direction.LEFT;
                }else if(noblePhantasm.getAngle()==-15 &&((enchantedSphere.direction.equals(Direction.RIGHT)))){
                    enchantedSphere.setSpeedY(-3);
                    enchantedSphere.setSpeedX(4);
                    enchantedSphere.direction=Direction.RIGHT;
                }else if(noblePhantasm.getAngle()==-30 &&(!(enchantedSphere.direction.equals(Direction.RIGHT)))){
                    enchantedSphere.setSpeedY(-3);
                    enchantedSphere.setSpeedX(-4);
                    enchantedSphere.direction=Direction.LEFT;
                }else if(noblePhantasm.getAngle()==-30 &&((enchantedSphere.direction.equals(Direction.RIGHT)))){
                    enchantedSphere.setSpeedY(-4);
                    enchantedSphere.setSpeedX(-3);
                    enchantedSphere.direction=Direction.RIGHT;
                }else if(noblePhantasm.getAngle()==-45 &&(!(enchantedSphere.direction.equals(Direction.RIGHT)))){
                    enchantedSphere.setSpeedY(0);
                    enchantedSphere.setSpeedX(-5);
                    enchantedSphere.direction=Direction.LEFT;
                }else if(noblePhantasm.getAngle()==-45 &&((enchantedSphere.direction.equals(Direction.RIGHT)))){
                    enchantedSphere.setSpeedY(-5);
                    enchantedSphere.setSpeedX(0);
                    enchantedSphere.direction=Direction.UP;
                }else{
                    enchantedSphere.setSpeedY(-enchantedSphere.getSpeedY());
                }*/
                }
                if (enchantedSphere.getSphere().getBoundsInParent().intersects(borders.get(1).getBoundsInParent())) {
                    //border 1 is right border
                    enchantedSphere.setSpeedX(-enchantedSphere.getSpeedX());

                    enchantedSphere.direction=Direction.LEFT;
                }
                if (enchantedSphere.getSphere().getBoundsInParent().intersects(borders.get(0).getBoundsInParent())) {
                    //border 0 is left border
                    enchantedSphere.setSpeedX(-enchantedSphere.getSpeedX());

                    enchantedSphere.direction=Direction.RIGHT;
                }
                if (enchantedSphere.getSphere().getBoundsInParent().intersects(borders.get(3).getBoundsInParent())) {
                    //border 3 is bottom border
                    remainingLives--;
                    updateUI();
                    enchantedSphere.setX(enchantedSphere.getStartX());
                    enchantedSphere.setY(enchantedSphere.getStartY());
                    enchantedSphere.setSpeedX(0);
                    enchantedSphere.setSpeedY(-5);
                }
                if (enchantedSphere.getSphere().getBoundsInParent().intersects(borders.get(2).getBoundsInParent())) {
                    //border 2 is top border
                    enchantedSphere.setSpeedY(-enchantedSphere.getSpeedY());
                    if (enchantedSphere.getSpeedX() == 0 & enchantedSphere.getSpeedY() == -5) {
                        //speedX = 5;
                        enchantedSphere.setSpeedY(5);
                    }
                }
                for (int i = 0; i < obstacles.size(); i++) {
                    if (enchantedSphere.getSphere().getBoundsInParent().intersects(obstacles.get(i).shape.getBoundsInParent())) {
                        Point point = obstacles.get(i).getSpeedAndDirection(enchantedSphere.getSphere(), enchantedSphere.getSpeedX(), enchantedSphere.getSpeedY());
                        enchantedSphere.setSpeedX( point.getX());
                        enchantedSphere.setSpeedY( point.getY());
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
                    if(borders.get(3).getBoundsInParent().intersects(obstacles.get(i).shape.getBoundsInParent())){
                        obstacles.get(i).falling = false;
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
    }

    private void create(){
        for (int i=0;i<SIMPLE_OBSTACLES;i++){
            SimpleObstacle simpleObstacle = new SimpleObstacle(0,0,OBSTACLE_WIDTH,OBSTACLE_HEIGHT,this);
            obstacles.add(simpleObstacle);

        }
        int limit = FIRM ;
        for (int i=0;i<limit;i++){
            FirmObstacle firmObstacle = new FirmObstacle(0,0,OBSTACLE_WIDTH,OBSTACLE_HEIGHT,this);
            obstacles.add(firmObstacle);
        }
        limit = GIFT ;
        for (int i=0;i<limit;i++){
            GiftObstacle giftObstacle = new GiftObstacle(0,0,OBSTACLE_WIDTH,OBSTACLE_HEIGHT,this);
            obstacles.add(giftObstacle);
        }

        limit = EXPLOSIVE;
        for (int i=0;i<limit;i++){
            ExplosiveObstacle explosiveObstacle = new ExplosiveObstacle(0,0,15,OBSTACLE_HEIGHT,this);
            obstacles.add(explosiveObstacle);
        }
        Ymir ymir = new Ymir(0,0,OBSTACLE_WIDTH,OBSTACLE_HEIGHT,this);
        obstacles.add(ymir);
        Collections.shuffle(obstacles);
    }
    private void draw(){
        x = OFFSET;
        y = OFFSET;
        int a=0;
        while(a<obstacles.size()){

            Obstacle obstacle  = obstacles.get(a);
            getChildren().add(obstacle.shape);
            getChildren().add(obstacle.tag);
            obstacle.setX(x);
            obstacle.setY(y);
            if(obstacle.getWidth()==15){
                x += obstacle.getWidth() + GAP + 5;
            }
            x += obstacle.getWidth() + GAP;
            if(x>Main.WIDTH-obstacle.getWidth()){
                x=OFFSET;
                y += OBSTACLE_HEIGHT + GAP;
            }a++;
        }
    }

    public static void setSimpleObstacles(int simpleObstacles) {
        SIMPLE_OBSTACLES = simpleObstacles;
    }

    public static void setFIRM(int FIRM) {
        Game.FIRM = FIRM;
    }

    public static void setGIFT(int GIFT) {
        Game.GIFT = GIFT;
    }


    public static void setEXPLOSIVE(int EXPLOSIVE) {
        Game.EXPLOSIVE = EXPLOSIVE;
    }

    public Button getPlay_pause() {
        return play_pause;
    }

    public Timer getTimer() {
        return timer;
    }

    /*public EnchantedSphere getSphere() {
        return sphere;
    }*/

    public Shape getPaddle() {
        return noblePhantasm.getPaddle();
    }

    public TextField getNumberOf() {
        return numberOf;
    }
    public static NoblePhantasm getNoblePhantasm() {
        return noblePhantasm;
    }

    public static void setNoblePhantasm(NoblePhantasm noblePhantasm) {
        Game.noblePhantasm = noblePhantasm;
    }

    private static NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();

    public EnchantedSphere getEnchantedSphere() {
        return enchantedSphere;
    }

    public void setEnchantedSphere(EnchantedSphere enchantedSphere) {
        this.enchantedSphere = enchantedSphere;
    }
}
