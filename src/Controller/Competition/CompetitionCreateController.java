package Controller.Competition;

import Controller.MenuController;
import Model.Competition.CompetitionMatchModel;
import Model.Competition.CompetitionModel;
import Model.Game.PlayerModel;
import Model.Repository;
import View.Competition.CompetitionView;
import javafx.scene.control.*;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class CompetitionCreateController {
    private MenuController menuController;

    private double xOffset = 0;
    private double yOffset = 0;

    private Repository competitionRepo;
    private Repository playerRepo;

    private TextField competitionName;
    private TextField competitionPlayerName;

    private Button addPlayer;
    private Button editPlayer;
    private Button deletePlayer;
    private Button saveCompetition;

    private ListView<PlayerModel> competitionPlayers;

    private ArrayList<PlayerModel> competitionPlayerList;

    public CompetitionCreateController(MenuController menuController, Repository competitionRepo, Repository playerRepo) {
        this.competitionRepo = competitionRepo;
        this.menuController = menuController;
        this.playerRepo = playerRepo;
    }

    public void initController() {
        competitionPlayerList = new ArrayList<>();
        competitionRepo.getObjects();

        initButtons();
    }

    /**
     * Init's all the buttons on the create screen
     */
    private void initButtons() {
        //Add player button
        addPlayer.setOnAction(event -> {
            if (!competitionPlayerName.getText().equals("")) {
                if (competitionPlayerList.size() <= 7) {
                    //If player textbox contains name add it too the competition and clear the textbox
                    competitionPlayers.getItems().add(new PlayerModel(competitionPlayerName.getText()));
                    competitionPlayerList.add(new PlayerModel(competitionPlayerName.getText()));
                    competitionPlayerName.clear();
                } else {
                    errorAlert("Add error", "The maximum players for a competition is 8!");
                }
            } else {
                //If player textbox is empty tell user too give a player name
                errorAlert("Add error", "Please enter a player name!");
            }
        });

        //Edit player button
        editPlayer.setOnAction(event -> {
            //Get selected player
            PlayerModel selectedPlayer = competitionPlayers.getSelectionModel().getSelectedItem();

            if (selectedPlayer != null) {
                //If player isn't null remove the player and add the name too the playerName textbox
                competitionPlayerName.setText(selectedPlayer.getName());
                competitionPlayers.getItems().remove(selectedPlayer);
                competitionPlayerList.remove(selectedPlayer);
                competitionPlayerList.trimToSize();
            } else {
                //If player is null show error that user has to select a player
                errorAlert("Edit error", "Please select a player by clicking on one!");
            }
        });

        //Delete player button
        deletePlayer.setOnAction(event -> {
            //Get selected player
            PlayerModel selectedPlayer = competitionPlayers.getSelectionModel().getSelectedItem();

            if (selectedPlayer != null) {
                //If player isn't null show alert box for conformation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete confirmation");
                alert.setContentText("Would you like to delete this player?");

                setupAlert(alert);

                Optional<ButtonType> result = alert.showAndWait();

                //If alert box conformation is true delete player
                if (result.get() == ButtonType.OK) {
                    competitionPlayers.getItems().remove(selectedPlayer);
                    competitionPlayerList.remove(selectedPlayer);
                    competitionPlayerList.trimToSize();
                }
            } else {
                //If player is null show error that user has to select a player
                errorAlert("Delete error", "Please select a player by clicking on one!");
            }
        });

        //Save competition
        saveCompetition.setOnAction(event -> {
            if (competitionPlayerList.size() == 8) {
                //Make competition
                CompetitionModel saveModel = new CompetitionModel(competitionName.getText(), createMatches());

                //Add competition to file
                competitionRepo.addObject(saveModel);

                //Clear all fields
                competitionPlayers.getItems().clear();
                competitionName.clear();
                competitionPlayerName.clear();

                //Switch screen back to competitionView
                CompetitionController competitionController = new CompetitionController(menuController, competitionRepo, playerRepo);
                CompetitionView competitionView = new CompetitionView(competitionController);
                menuController.addScreen("competitionView", competitionView.getCompetitionView());
                menuController.activate("competitionView");
            } else {
                errorAlert("Save error", "A competition has too have 8 players!");
            }

        });
    }

    private ArrayList<CompetitionMatchModel> createMatches() {
        ArrayList<CompetitionMatchModel> groups = new ArrayList<>();

        Collections.shuffle(competitionPlayerList);

        for (int j = 0; j < competitionPlayerList.size(); j += 2) {
            groups.add(new CompetitionMatchModel(competitionPlayerList.get(j), competitionPlayerList.get(j + 1)));
        }

        return groups;
    }

    private void errorAlert(String title, String context) {
        Alert alert = new Alert(Alert.AlertType.WARNING);

        setupAlert(alert);

        alert.setTitle(title);
        alert.setContentText(context);
        alert.show();
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

    public void setAddPlayer(Button addPlayer) {
        this.addPlayer = addPlayer;
    }

    public void setCompetitionName(TextField competitionName) {
        this.competitionName = competitionName;
    }

    public void setCompetitionPlayerName(TextField competitionPlayerName) {
        this.competitionPlayerName = competitionPlayerName;
    }

    public void setEditPlayer(Button editPlayer) {
        this.editPlayer = editPlayer;
    }

    public void setDeletePlayer(Button deletePlayer) {
        this.deletePlayer = deletePlayer;
    }

    public void setCompetitionPlayers(ListView<PlayerModel> competitionPlayers) {
        this.competitionPlayers = competitionPlayers;
    }

    public void setSaveCompetition(Button saveCompetition) {
        this.saveCompetition = saveCompetition;
    }
}
