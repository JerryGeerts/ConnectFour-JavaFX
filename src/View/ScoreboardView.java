package View;

import Controller.ScoreBoardController;
import Model.Game.PlayerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ScoreboardView {
    private ScoreBoardController scoreBoardController;
    private HBox scoreBoardView;
    private ListView<PlayerModel> scoreBoard;
    private ComboBox sortingFilter;
    private Button saveScoreboard;

    public ScoreboardView(ScoreBoardController scoreBoardController) {
        this.scoreBoardController = scoreBoardController;

        getView();
        initView();
        initController();
    }

    private void getView() {
        scoreBoardView = new HBox(50);
        scoreBoardView.setMinSize(850, 500);
        scoreBoardView.setAlignment(Pos.CENTER);

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);

        //Information text
        Text scoreboardHeader = new Text("Scoreboard");
        scoreboardHeader.getStyleClass().add("scoreboardHeader");

        //Combobox filter
        ObservableList<String> options = FXCollections.observableArrayList(
                "Name A -> Z",
                "Name Z -> A",
                "Match wins High -> Low",
                "Match wins Low -> High",
                "Competition wins High -> Low",
                "Competition wins Low -> High"
        );

        sortingFilter = new ComboBox<>(options);
        sortingFilter.setPrefSize(600, 30);
        sortingFilter.setPromptText("Click here to change filter");
        sortingFilter.getStyleClass().add("sortingFilter");

        sortingFilter.setButtonCell(new ListCell() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    getStyleClass().add("emptySortingFilterCell");
                } else {
                    getStyleClass().add("sortingFilterCell");
                    setText(item.toString());
                }
            }

        });

        //Scoreboard listview
        scoreBoard = new ListView<>();
        scoreBoard.setMaxSize(600, 250);
        scoreBoard.getStyleClass().add("listView");
        scoreBoard.setCellFactory(new Callback<>() {
            @Override
            public ListCell<PlayerModel> call(ListView<PlayerModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(PlayerModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setPrefHeight(50);

                            setText(String.format("Name: %-20s %40s Match wins: %-4s Competition wins: %-4s", item.getName(), "", item.getMatchWins(), item.getCompetitionWins()));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        saveScoreboard = new Button("Save scoreboard");
        saveScoreboard.getStyleClass().add("saveScoreboard");

        content.getChildren().addAll(scoreboardHeader, sortingFilter, scoreBoard, saveScoreboard);
        scoreBoardView.getChildren().add(content);
    }

    private void addItems() {

    }

    private void initView() {

    }

    private void initController() {
        scoreBoardController.setScoreBoard(scoreBoard);
        scoreBoardController.setSortingFilter(sortingFilter);
        scoreBoardController.setSaveScoreboard(saveScoreboard);

        scoreBoardController.initController();
    }

    public HBox getScoreBoardView() {
        return scoreBoardView;
    }
}
