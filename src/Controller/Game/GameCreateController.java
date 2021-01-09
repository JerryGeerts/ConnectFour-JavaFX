package Controller.Game;

import Controller.MenuController;
import Model.Competition.CompetitionMatchModel;
import Model.Competition.CompetitionModel;
import Model.Repository;
import View.Game.GameView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class GameCreateController {
    private Repository competitionRepo;
    private CompetitionModel competitionModel;
    private CompetitionMatchModel match;

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

    private Text letterPlayerOne;
    private Text letterPlayerTwo;

    private Circle circlePlayerOne;
    private Circle circlePlayerTwo;

    private Text error;

    private MenuController menuController;
    private Repository playerRepo;

    public GameCreateController(MenuController menuController, Repository playerRepo) {
        this.menuController = menuController;
        this.playerRepo = playerRepo;
    }

    public GameCreateController(MenuController menuController, CompetitionMatchModel match, Repository competitionRepo, Repository playerRepo ,Object competitionModel) {
        this.menuController = menuController;
        this.match = match;
        this.competitionRepo = competitionRepo;
        this.playerRepo = playerRepo;
        this.competitionModel = (CompetitionModel) competitionModel;
    }

    public void initialize() {
        if (match != null) {
            namePlayerOne.setText(match.getPlayerOne().getName());
            namePlayerOne.setEditable(false);
            letterPlayerOne.setText((namePlayerOne.getText().charAt(0) + "").toUpperCase());


            namePlayerTwo.setText(match.getPlayerTwo().getName());
            namePlayerTwo.setEditable(false);
            letterPlayerTwo.setText((namePlayerTwo.getText().charAt(0) + "").toUpperCase());
        }

        Button[] playerOne = {greenPlayerOne, redPlayerOne, bluePlayerOne, pinkPlayerOne, yellowPlayerOne};
        Button[] playerTwo = {greenPlayerTwo, redPlayerTwo, bluePlayerTwo, pinkPlayerTwo, yellowPlayerTwo};

        startGame.setOnAction(event -> {
            if (checkValues()) {
                GameController gameController = new GameController(namePlayerOne.getText(), namePlayerTwo.getText(),
                        circlePlayerOne.getFill(), circlePlayerTwo.getFill(), 7, 6, match, menuController, competitionRepo, playerRepo, competitionModel);

                GameView gameView = new GameView(gameController);
                menuController.addScreen("gameView", gameView.createRoot());
                menuController.activate("gameView");
            }
        });

        colorHandler(playerOne, circlePlayerOne);
        colorHandler(playerTwo, circlePlayerTwo);

        namePlayerOne.setOnKeyTyped(event -> {
            if (!namePlayerOne.getText().equals("")) {
                letterPlayerOne.setText((namePlayerOne.getText().charAt(0) + "").toUpperCase());
            } else {
                letterPlayerOne.setText("");
            }
        });

        namePlayerTwo.setOnKeyTyped(event -> {
            if (!namePlayerTwo.getText().equals("")) {
                letterPlayerTwo.setText((namePlayerTwo.getText().charAt(0) + "").toUpperCase());
            } else {
                letterPlayerTwo.setText("");
            }
        });
    }

    private boolean checkValues() {
        if (checkColor((Color) circlePlayerOne.getFill())) {
            if (checkColor((Color) circlePlayerTwo.getFill())) {
                if (!namePlayerOne.getText().equals("")) {
                    if (!namePlayerTwo.getText().equals("")) {
                        return true;
                    } else {
                        error.setText("Player two didn't enter a name!");
                    }
                } else {
                    error.setText("Player one didn't enter a name!");
                }
            } else {
                error.setText("Player two didn't select a color!");
            }
        } else {
            error.setText("Player one didn't select a color!");
        }

        return false;
    }

    private boolean checkColor(Color color) {
        Color defaultColor = Color.valueOf("0xffffffff");

        String currentColor = String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));

        String defaultColorHex = String.format("#%02X%02X%02X",
                (int) (defaultColor.getRed() * 255),
                (int) (defaultColor.getGreen() * 255),
                (int) (defaultColor.getBlue() * 255));

        return !currentColor.equals(defaultColorHex);
    }

    private void colorHandler(Button[] buttons, Circle circle) {
        for (Button button : buttons) {
            button.setOnAction(event -> {
                Paint buttonColor = button.getBackground().getFills().get(0).getFill();
                circle.setFill(buttonColor);
            });
        }
    }

    public void setBluePlayerOne(Button bluePlayerOne) {
        this.bluePlayerOne = bluePlayerOne;
    }

    public void setGreenPlayerOne(Button greenPlayerOne) {
        this.greenPlayerOne = greenPlayerOne;
    }

    public void setBluePlayerTwo(Button bluePlayerTwo) {
        this.bluePlayerTwo = bluePlayerTwo;
    }

    public void setGreenPlayerTwo(Button greenPlayerTwo) {
        this.greenPlayerTwo = greenPlayerTwo;
    }

    public void setNamePlayerOne(TextField namePlayerOne) {
        this.namePlayerOne = namePlayerOne;
    }

    public void setNamePlayerTwo(TextField namePlayerTwo) {
        this.namePlayerTwo = namePlayerTwo;
    }

    public void setPinkPlayerOne(Button pinkPlayerOne) {
        this.pinkPlayerOne = pinkPlayerOne;
    }

    public void setPinkPlayerTwo(Button pinkPlayerTwo) {
        this.pinkPlayerTwo = pinkPlayerTwo;
    }

    public void setRedPlayerOne(Button redPlayerOne) {
        this.redPlayerOne = redPlayerOne;
    }

    public void setRedPlayerTwo(Button redPlayerTwo) {
        this.redPlayerTwo = redPlayerTwo;
    }

    public void setYellowPlayerOne(Button yellowPlayerOne) {
        this.yellowPlayerOne = yellowPlayerOne;
    }

    public void setYellowPlayerTwo(Button yellowPlayerTwo) {
        this.yellowPlayerTwo = yellowPlayerTwo;
    }

    public void setStartGame(Button startGame) {
        this.startGame = startGame;
    }

    public void setCirclePlayerOne(Circle circlePlayerOne) {
        this.circlePlayerOne = circlePlayerOne;
    }

    public void setCirclePlayerTwo(Circle circlePlayerTwo) {
        this.circlePlayerTwo = circlePlayerTwo;
    }

    public void setLetterPlayerOne(Text letterPlayerOne) {
        this.letterPlayerOne = letterPlayerOne;
    }

    public void setLetterPlayerTwo(Text letterPlayerTwo) {
        this.letterPlayerTwo = letterPlayerTwo;
    }

    public void setError(Text error) {
        this.error = error;
    }
}
