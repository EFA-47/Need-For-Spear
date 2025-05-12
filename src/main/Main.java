package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    public static final int WIDTH   = 1000;
    public static final int HEIGHT = 600;
    public static Stage stage;
    private Group root;
    private Button start;
    private Button load;
    private Button help;
    private VBox buttons= new VBox(10);
    private Button simple,firm,explosive,gift;
    private javafx.scene.control.TextField numberOf = new TextField();
    public static javafx.scene.control.Label tip = new javafx.scene.control.Label("Ask for help if you don't know how to play\n");
    private int numbers=0;
    public static User currentUser;
    private int type=0;
    @Override
    public void start(Stage stage) throws Exception {

        Main.stage = stage;
        stage.setTitle("Need for Spear");
        stage.setScene(new Scene(getLogin(),WIDTH,HEIGHT));
        stage.show();
        start.setOnAction(event -> {
            Game game = new Game(null);
            stage.setScene(new Scene(game,WIDTH /*+ 200*/,HEIGHT));
            game.setListener();
            tip.setVisible(false);
        });

        help.setOnAction(event ->{

            if(tip.isVisible()){
                tip.setVisible(false);
            }else {
                tip.setVisible(true);
            }
        });
        load.setOnAction(event -> {
            tip.setVisible(false);
            try {
                List<Data> dataList = new ArrayList<>();
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("NFS Save","*.nfs"));
                File file = fileChooser.showOpenDialog(stage);
                if (file == null) return;
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                Object read = null;
                while ((read = ois.readObject()) != null){
                    if (read instanceof User){
                        User user = (User) read;
                        if (!user.getName().equals(currentUser.getName())){
                            return;
                        }else{
                            currentUser = user;
                        }
                    }else {
                        Data data = (Data) read;
                        dataList.add(data);
                    }
                }
                ois.close();
                Game game = new Game(dataList);
                stage.setScene(new Scene(game,WIDTH,HEIGHT));
                game.setListener();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        simple.setOnAction(event -> {type=0;
            numbers = Integer.parseInt(numberOf.getText());
            if(numbers>74)
                Game.setSimpleObstacles(numbers);
        });
        firm.setOnAction(event -> {type=1;
            numbers = Integer.parseInt(numberOf.getText());
            if(numbers>9)
                Game.setFIRM(numbers);
        });
        explosive.setOnAction(event -> {type=2;
            numbers = Integer.parseInt(numberOf.getText());
            if(numbers>4)
                Game.setEXPLOSIVE(numbers);
        });
        gift.setOnAction(event ->{ type=3;
            numbers = Integer.parseInt(numberOf.getText());
            if(numbers>9)
                Game.setGIFT(numbers);
        });



    }

    private VBox getLogin(){
        List<User> users = readUsers();
        TextField user = new TextField();
        user.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        VBox root = new VBox(5);
        Label user_lbl = new Label("Username:");
        Label password_lbl = new Label("Password:");
        user.setFont(new Font(15));
        passwordField.setFont(new Font(15));
        user_lbl.setFont(new Font(15));
        password_lbl.setFont(new Font(15));
        root.getChildren().addAll(user_lbl,user,password_lbl,passwordField);
        Button login,signup;
        login = new Button("Login");
        signup = new Button("Sign up");
        login.setFont(new Font(15));
        signup.setFont(new Font(15));
        HBox btns = new HBox(5);
        HBox.setHgrow(login, Priority.ALWAYS);
        HBox.setHgrow(signup,Priority.ALWAYS);
        btns.getChildren().addAll(login,signup);
        root.getChildren().add(btns);
        root.setPrefSize(300,500);
        root.setPadding(new Insets(200));
        login.setOnAction(event -> {
            for (User user1:users){
                if (user1.getName().equals(user.getText()) & passwordField.getText().equals(user1.getPassword())){
                    currentUser = user1;
                    stage.setScene(new Scene(this.root,WIDTH,HEIGHT));
                    return;
                }
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login");
            alert.setHeaderText("Login error");
            alert.setContentText("Username or password does not match");
            alert.showAndWait();
        });
        signup.setOnAction(event -> {
            for (User user1:users){
                if (user1.getName().equals(user.getText()) & passwordField.getText().equals(user1.getPassword())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Sign up");
                    alert.setHeaderText("Sign up error");
                    alert.setContentText("Username taken please select another");
                    alert.showAndWait();
                    return;
                }
            }
            User newUser = new User(user.getText(),passwordField.getText());
            newUser.setLives(3);
            newUser.setScore(0);
            users.add(newUser);
            writeUserData(users);
            currentUser = newUser;
            stage.setScene(new Scene(this.root,WIDTH,HEIGHT));
        });
        return root;
    }
    private List<User> readUsers(){
        List<User> users = new ArrayList<>();
        File file = new File("user.txt");
        if (!file.exists()) return users;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Object read = null;
            while ((read = ois.readObject()) != null){
                User user = (User) read;
                users.add(user);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }
    private void writeUserData(List<User> users){
        File file = new File("user.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            for (User user:users){
                oos.writeObject(user);
            }
            oos.writeObject(null);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws Exception {
        root = new Group();
        start = new Button("Start Game");
        load = new Button("Load and Start");
        help=new Button("Help");
        VBox vBox = new VBox(start,load,help);
        vBox.setSpacing(20);
        root.getChildren().addAll(vBox);
        vBox.setLayoutX(WIDTH/2.0);
        vBox.setLayoutY(HEIGHT/2.0);
        buttons.setAlignment(Pos.CENTER);
        buttons.setLayoutX(Main.WIDTH + 10);
        simple = new Button("Simple");
        firm = new Button("Firm");
        explosive = new Button("Explosive");
        gift = new Button("Gift");

        buttons.getChildren().addAll(new Label("Obstacles"),numberOf,simple,firm,explosive,gift);
        buttons.setSpacing(20);
        buttons.setLayoutX(WIDTH/3.0);
        buttons.setLayoutY(HEIGHT/3.0);
        root.getChildren().add(buttons);

        root.getChildren().add(tip);
        tip.setFont(new Font(10));
        tip.setLayoutX(10);
        tip.setLayoutY(10);
        tip.setVisible(false);


    }





    public Button getSimple() {
        return simple;
    }

    public Button getFirm() {
        return firm;
    }

    public Button getExplosive() {
        return explosive;
    }

    public Button getGift() {
        return gift;
    }

    public TextField getNumberOf() {
        return numberOf;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
