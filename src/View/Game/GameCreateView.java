package View.Game;

import Controller.Game.GameCreateController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class GameCreateView {
    private static final String TITEL = "Voorbeeld";

    private TextField namePlayerOne;
    private TextField namePlayerTwo;

    private Button greenPlayerOne;
    private Button bluePlayerOne;
    private Button redPlayerOne;
    private Button pinkPlayerOne;
    private Button yellowPlayerOne;

    private Button greenPlayerTwo;
    private Button bluePlayerTwo;
    private Button redPlayerTwo;
    private Button pinkPlayerTwo;
    private Button yellowPlayerTwo;

    private Button startGame;

    private GameCreateController gameCreateController;

    private HBox gameCreate;

    private Text letterPlayerOne;
    private Text letterPlayerTwo;

    private Circle circlePlayerOne;
    private Circle circlePlayerTwo;

    private Text error;

    public GameCreateView(GameCreateController gameCreateController) {
        this.gameCreateController = gameCreateController;

        gameCreate = new HBox();

        gameCreate.setPrefSize(850,500);

        VBox playerOne = createPlayerOptions();
        VBox playerTwo = createPlayerOptions();

        gameCreate.getChildren().addAll(playerOne, createInformation(), playerTwo);

        setupView();
        setupController();
    }

    public HBox getRoot() {
        return gameCreate;
    }

    private VBox createPlayerOptions() {
        VBox player = new VBox(40);
        HBox colors = new HBox(15);
        StackPane playerCircle = new StackPane();

        TextField namePlayer = new TextField();

        Button green = new Button();
        Button blue = new Button();
        Button red = new Button();
        Button pink = new Button();
        Button yellow = new Button();
        Circle circle = new Circle(50);
        Text letter = new Text();
        Text descr = new Text();

        if(namePlayerOne == null){
            namePlayerOne = namePlayer;
            greenPlayerOne = green;
            bluePlayerOne = blue;
            redPlayerOne = red;
            pinkPlayerOne = pink;
            yellowPlayerOne = yellow;
            circlePlayerOne = circle;
            letterPlayerOne = letter;
            descr.setText("Player one:");
        }
        else{
            namePlayerTwo = namePlayer;
            greenPlayerTwo = green;
            bluePlayerTwo = blue;
            redPlayerTwo = red;
            pinkPlayerTwo = pink;
            yellowPlayerTwo = yellow;
            circlePlayerTwo = circle;
            letterPlayerTwo = letter;
            descr.setText("Player two:");
        }


        colors.getChildren().addAll(green, blue, red, pink, yellow);

        circle.setFill(Color.gray(1));
        circle.getStyleClass().add("playerCircle");

        letter.setBoundsType(TextBoundsType.VISUAL);
        letter.relocate(50 - letter.getBoundsInLocal().getWidth() / 2, 50 - letter.getBoundsInLocal().getHeight() / 2);
        letter.getStyleClass().add("playerLetter");

        playerCircle.getChildren().addAll(circle, letter);

        player.getChildren().addAll(descr, namePlayer, playerCircle, colors);
        player.setPrefSize(250,500);
        player.setAlignment(Pos.CENTER);
        player.getStyleClass().add("playerPane");

        namePlayer.getStyleClass().add("playerName");
        descr.getStyleClass().add("playerDescr");

        return player;
    }

    private void setupController() {
        gameCreateController.setBluePlayerOne(bluePlayerOne);
        gameCreateController.setBluePlayerTwo(bluePlayerTwo);

        gameCreateController.setGreenPlayerOne(greenPlayerOne);
        gameCreateController.setGreenPlayerTwo(greenPlayerTwo);

        gameCreateController.setNamePlayerOne(namePlayerOne);
        gameCreateController.setNamePlayerTwo(namePlayerTwo);

        gameCreateController.setPinkPlayerOne(pinkPlayerOne);
        gameCreateController.setPinkPlayerTwo(pinkPlayerTwo);

        gameCreateController.setRedPlayerOne(redPlayerOne);
        gameCreateController.setRedPlayerTwo(redPlayerTwo);

        gameCreateController.setYellowPlayerOne(yellowPlayerOne);
        gameCreateController.setYellowPlayerTwo(yellowPlayerTwo);

        gameCreateController.setLetterPlayerOne(letterPlayerOne);
        gameCreateController.setLetterPlayerTwo(letterPlayerTwo);

        gameCreateController.setCirclePlayerOne(circlePlayerOne);
        gameCreateController.setCirclePlayerTwo(circlePlayerTwo);

        gameCreateController.setError(error);

        gameCreateController.setStartGame(startGame);

        gameCreateController.initialize();
    }

    private VBox createInformation(){
        VBox information = new VBox(50);

        Text title = new Text("Create your game");
        Text descr = new Text("Select a name and color here!");

        startGame = new Button("Start game");

        error = new Text();

        information.getChildren().addAll(title, descr, startGame, error);

        information.setPrefSize(350, 500);
        information.setAlignment(Pos.CENTER);

        title.getStyleClass().add("gameCreateTitle");
        descr.getStyleClass().add("gameCreateDescr");

        return information;
    }

    private void setupView(){
        greenPlayerOne.getStyleClass().addAll("green", "colorButton");
        greenPlayerTwo.getStyleClass().addAll("green", "colorButton");

        bluePlayerOne.getStyleClass().addAll("blue", "colorButton");
        bluePlayerTwo.getStyleClass().addAll("blue", "colorButton");

        redPlayerOne.getStyleClass().addAll("red", "colorButton");
        redPlayerTwo.getStyleClass().addAll("red", "colorButton");

        yellowPlayerOne.getStyleClass().addAll("yellow", "colorButton");
        yellowPlayerTwo.getStyleClass().addAll("yellow", "colorButton");

        pinkPlayerOne.getStyleClass().addAll("pink", "colorButton");
        pinkPlayerTwo.getStyleClass().addAll("pink", "colorButton");

        error.getStyleClass().add("error");

        startGame.getStyleClass().add("gameCreateButton");
    }
}
