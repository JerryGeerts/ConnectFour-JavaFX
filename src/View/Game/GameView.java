package View.Game;

import Controller.Game.GameController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class GameView {
    private Label namePlayerOne;
    private Label namePlayerTwo;

    private Circle circlePlayerOne;
    private Circle circlePlayerTwo;

    private Text letterPlayerOne;
    private Text letterPlayerTwo;

    private Text gameInfo;
    private Text gameInfoDescr;

    private Button surrenderPlayerOne;
    private Button surrenderPlayerTwo;

    private GameController gameController;

    public GameView(GameController gameController) {
        this.gameController = gameController;
    }

    public HBox createRoot() {
        HBox game = new HBox(40);
        game.setPrefSize(850, 500);

        namePlayerOne = new Label();
        namePlayerTwo = new Label();
        namePlayerOne.getStyleClass().add("gameNamePlayer");
        namePlayerTwo.getStyleClass().add("gameNamePlayer");

        circlePlayerOne = new Circle(50);
        circlePlayerTwo = new Circle(50);

        letterPlayerOne = new Text();
        letterPlayerTwo = new Text();

        surrenderPlayerOne = new Button("Surrender");
        surrenderPlayerTwo = new Button("Surrender");

        game.getChildren().addAll(generatePlayerVbox(namePlayerOne, circlePlayerOne, letterPlayerOne, surrenderPlayerOne),
                generateGameVbox(),
                generatePlayerVbox(namePlayerTwo, circlePlayerTwo, letterPlayerTwo, surrenderPlayerTwo));

        setupController();

        return game;
    }

    private VBox generatePlayerVbox(Label namePlayer, Circle circlePlayer, Text letter, Button surrenderButton) {
        VBox player = new VBox(100);
        player.setPrefSize(250, 500);
        player.getStyleClass().add("gamePlayer");
        player.setAlignment(Pos.CENTER);

        StackPane playerCircle = new StackPane();

        letter.setBoundsType(TextBoundsType.VISUAL);
        letter.relocate(50 - letter.getBoundsInLocal().getWidth() / 2, 50 - letter.getBoundsInLocal().getHeight() / 2);
        letter.getStyleClass().add("playerLetter");

        surrenderButton.getStyleClass().add("surrenderButton");

        circlePlayer.getStyleClass().add("gameCirclePlayer");

        playerCircle.getChildren().addAll(circlePlayer, letter);
        player.getChildren().addAll(playerCircle, namePlayer, surrenderButton);

        return player;
    }

    private VBox generateGameVbox() {
        VBox gameboard = new VBox(10);
        gameboard.setPrefSize(350, 500);
        gameboard.setAlignment(Pos.CENTER);
        gameboard.getStyleClass().add("gameBoard");

        GridPane gameGrid = new GridPane();

        for (int i = 0; i < gameController.getBoardWidth(); i++) {
            Button insertButton = new Button();
            insertButton.getStyleClass().add("gameButton");
            insertButton.setRotate(90);

            gameController.setInsertButton(insertButton, i);

            gameGrid.add(insertButton, i, 0);

            for (int x = 0; x < gameController.getBoardHeight(); x++) {
                Circle cell = new Circle(20);
                cell.getStyleClass().add("gameCell");

                gameGrid.add(cell, i, x + 1);

                gameController.getBoardCell(i, x).setCircle(cell);
            }
        }

        gameGrid.setVgap(10);
        gameGrid.setHgap(10);
        gameGrid.getStyleClass().add("gameGrid");
        gameGrid.setAlignment(Pos.BASELINE_CENTER);

        gameInfo = new Text();
        gameInfo.getStyleClass().add("gameInfo");

        gameInfoDescr = new Text();
        gameInfoDescr.getStyleClass().add("gameInfoDescr");

        gameboard.getChildren().addAll(gameInfo, gameInfoDescr, gameGrid);

        return gameboard;
    }

    private void setupController() {
        gameController.setNamePlayerOne(namePlayerOne);
        gameController.setNamePlayerTwo(namePlayerTwo);

        gameController.setCirclePlayerOne(circlePlayerOne);
        gameController.setCirclePlayerTwo(circlePlayerTwo);

        gameController.setLetterPlayerOne(letterPlayerOne);
        gameController.setLetterPlayerTwo(letterPlayerTwo);

        gameController.setGameInfo(gameInfo);
        gameController.setGameInfoDescr(gameInfoDescr);

        gameController.setSurrenderPlayerOne(surrenderPlayerOne);
        gameController.setSurrenderPlayerTwo(surrenderPlayerTwo);

        gameController.initialize();
    }

}
