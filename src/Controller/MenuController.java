package Controller;

import Model.Repository;
import View.ScoreboardView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class MenuController {
    private HashMap<String, Pane> screenMap = new HashMap<>();

    private Scene main;
    private Repository playerRepo;

    private Button playButton;
    private Button competitionButton;
    private Button scoreBoardButton;

    private Button exitButton;

    public MenuController(Scene main, Repository playerRepo) {
        this.main = main;
        this.playerRepo = playerRepo;
        this.main.getStylesheets().add("Style/style.css");
    }

    public void addScreen(String name, Pane pane) {
        screenMap.put(name, pane);
    }

    public void activate(String name) {
        GridPane gridPane = new GridPane();

        gridPane.add(menuItems(), 1, 0);
        gridPane.add(screenMap.get(name), 2, 0);
        main.setRoot(gridPane);
    }

    private VBox menuItems() {
        final int MENU_WIDTH = 250;

        competitionButton = new Button("Competition play");
        playButton = new Button("Free play");
        scoreBoardButton = new Button("Scoreboard");
        exitButton = new Button("Exit");

        VBox menu = new VBox();

        VBox menuItems = new VBox();
        VBox exitMenu = new VBox();

        menuItems.getChildren().addAll(playButton, competitionButton, scoreBoardButton);

        exitMenu.getChildren().add(exitButton);

        menu.getChildren().addAll(menuItems, exitMenu);

        menuItems.setPrefSize(MENU_WIDTH, 450);
        exitMenu.setPrefSize(MENU_WIDTH, 50);

        playButton.setPrefSize(MENU_WIDTH, 50);
        competitionButton.setPrefSize(MENU_WIDTH, 50);
        exitButton.setPrefSize(MENU_WIDTH, 50);
        scoreBoardButton.setPrefSize(MENU_WIDTH, 50);

        menu.getStyleClass().add("menuBar");

        playButton.getStyleClass().add("playButton");
        competitionButton.getStyleClass().add("competitionButton");
        exitButton.getStyleClass().add("exitButton");
        scoreBoardButton.getStyleClass().add("scoreBoardButton");

        initButtons();

        return menu;
    }

    private void initButtons() {
        exitButton.setOnAction(event -> {
            System.exit(0);
        });

        playButton.setOnAction(event -> {
            activate("gameCreate");
        });

        competitionButton.setOnAction(event -> {
            activate("competitionView");
        });

        scoreBoardButton.setOnAction(event -> {
            ScoreBoardController scoreBoardController = new ScoreBoardController(playerRepo);
            ScoreboardView scoreboardView = new ScoreboardView(scoreBoardController);
            addScreen("scoreBoardView", scoreboardView.getScoreBoardView());
            activate("scoreBoardView");
        });
    }
}
