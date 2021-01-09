package Controller;

import Model.Game.PlayerModel;
import Model.Repository;
import javafx.scene.control.*;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreBoardController {
    private ListView<PlayerModel> scoreBoard;
    private Repository playerRepo;
    private ComboBox<String> sortingFilter;
    private Button saveScoreboard;
    private double xOffset;
    private double yOffset;

    public ScoreBoardController(Repository playerRepo) {
        this.playerRepo = playerRepo;

    }

    public void initController(){
        getPlayers(playerRepo.getObjects());
        initButtons();
    }

    private void initButtons(){
        ArrayList players = playerRepo.getObjects();

        sortingFilter.setOnAction(event -> {
            switch (sortingFilter.getValue()){
                case "Name Z -> A":
                    players.sort(new AdjectiveNameFilter());
                    break;
                case "Name A -> Z":
                    players.sort(new AlphabeticNameFilter());
                    break;
                case "Match wins High -> Low":
                    players.sort(new HighestMatchFilter());
                    break;
                case "Match wins Low -> High":
                    players.sort(new LowestMatchFilter());
                    break;
                case "Competition wins High -> Low":
                    players.sort(new HighestCompetitionFilter());
                    break;
                case "Competition wins Low -> High":
                    players.sort(new LowestCompetitionFilter());
                    break;
            }

            getPlayers(players);
        });

        saveScoreboard.setOnAction(event -> {
            try{
                File file = new File("src/Data/Highscore.txt");

                FileWriter writer = new FileWriter(file);
                PrintWriter printLine = new PrintWriter(writer);

                for (Object p : players){
                    PlayerModel player = (PlayerModel) p;
                    printLine.printf("Name: %-20s Match wins: %-4s Competition wins: %-4s%n", player.getName(), player.getMatchWins(), player.getCompetitionWins());
                }

                printLine.close();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                setupAlert(alert);

                alert.setTitle("Saved scoreboard");
                alert.setContentText("Scoreboard saved at " + file.getAbsolutePath());
                alert.show();

            }catch(Exception e){
                System.out.println(e);
            }
        });
    }

    private void getPlayers(ArrayList players){
        scoreBoard.getItems().clear();

        for (PlayerModel player : (ArrayList<PlayerModel>) players){
            scoreBoard.getItems().add(player);
        }
    }

    public void setScoreBoard(ListView<PlayerModel> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public void setSortingFilter(ComboBox<String> sortingFilter) {
        this.sortingFilter = sortingFilter;
    }

    public void setSaveScoreboard(Button saveScoreboard) {
        this.saveScoreboard = saveScoreboard;
    }

    private void setupAlert(Alert alert) {
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.setHeaderText(null);
        alert.setGraphic(null);

        alert.getDialogPane().getScene().getStylesheets().add("Style/style.css");
        alert.getDialogPane().getStyleClass().add("deleteAlert");

        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().forEach(button -> {
            button.getStyleClass().add("deleteButton");
        });

        alert.getDialogPane().setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });

        buttonBar.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });

        alert.getDialogPane().setOnMouseDragged(mouseEvent -> {
            alert.setX(mouseEvent.getScreenX() - xOffset);
            alert.setY(mouseEvent.getScreenY() - yOffset);
        });

        buttonBar.setOnMouseDragged(mouseEvent -> {
            alert.setX(mouseEvent.getScreenX() - xOffset);
            alert.setY(mouseEvent.getScreenY() - yOffset);
        });
    }
}

class HighestMatchFilter implements Comparator<PlayerModel> {
    @Override
    public int compare(PlayerModel playerOne, PlayerModel playerTwo) {
        return Integer.compare(playerTwo.getMatchWins(), playerOne.getMatchWins());
    }
}

class LowestMatchFilter implements Comparator<PlayerModel> {
    @Override
    public int compare(PlayerModel playerOne, PlayerModel playerTwo) {
        return Integer.compare(playerOne.getMatchWins(), playerTwo.getMatchWins());
    }
}

class AlphabeticNameFilter implements Comparator<PlayerModel> {
    @Override
    public int compare(PlayerModel playerOne, PlayerModel playerTwo) {
        return playerOne.getName().compareTo(playerTwo.getName());
    }
}

class AdjectiveNameFilter implements Comparator<PlayerModel> {
    @Override
    public int compare(PlayerModel playerOne, PlayerModel playerTwo) {
        return playerTwo.getName().compareTo(playerOne.getName());
    }
}

class HighestCompetitionFilter implements Comparator<PlayerModel> {
    @Override
    public int compare(PlayerModel playerOne, PlayerModel playerTwo) {
        return playerTwo.getCompetitionWins() - playerOne.getCompetitionWins();
    }
}

class LowestCompetitionFilter implements Comparator<PlayerModel> {
    @Override
    public int compare(PlayerModel playerOne, PlayerModel playerTwo) {
        return playerOne.getCompetitionWins() - playerTwo.getCompetitionWins();
    }
}

