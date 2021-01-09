package Controller.Competition;

import Controller.MenuController;
import Model.Competition.CompetitionModel;
import Model.Repository;
import View.Competition.CompetitionShowView;
import javafx.scene.control.*;
import javafx.stage.StageStyle;

import java.util.Optional;

public class CompetitionController {
    private ListView<CompetitionModel> competitionList;

    private Button createCompetition;
    private Button readCompetition;
    private Button deleteCompetition;

    private double xOffset = 0;
    private double yOffset = 0;

    private MenuController menuController;

    private Repository competitionRepo;
    private Repository playerRepo;

    public CompetitionController(MenuController menuController, Repository competitionRepo, Repository playerRepo) {
        this.menuController = menuController;
        this.competitionRepo = competitionRepo;
        this.playerRepo = playerRepo;
    }

    public void initController() {
        initButtons();

        initCompetitions();
    }

    private void initCompetitions() {
        competitionList.getItems().clear();

        for (Object object : competitionRepo.getObjects()) {
            competitionList.getItems().add((CompetitionModel) object);
        }
    }

    private void initButtons() {
        createCompetition.setOnAction(event -> {
            menuController.activate("competitionCreateView");
        });

        readCompetition.setOnAction(event -> {
            CompetitionModel currentSelected = competitionList.getSelectionModel().getSelectedItem();

            if (currentSelected != null) {
                CompetitionShowController competitionShowController = new CompetitionShowController(menuController, currentSelected, competitionRepo, playerRepo);
                CompetitionShowView competitionShowView = new CompetitionShowView(competitionShowController);
                menuController.addScreen("competitionShowView", competitionShowView.getCompetitionShowView());
                menuController.activate("competitionShowView");
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);

                setupAlert(alert);

                alert.setTitle("Show error");
                alert.setContentText("Please select a competition by clicking on one!");
                alert.show();
            }
        });

        deleteCompetition.setOnAction(event -> {
            if (competitionList.getSelectionModel().getSelectedItem() != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete confirmation");
                alert.setContentText("Would you like to delete this competition?");

                setupAlert(alert);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    competitionRepo.deleteObject(competitionList.getSelectionModel().getSelectedItem());
                    competitionList.getItems().remove(competitionList.getSelectionModel().getSelectedItem());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);

                setupAlert(alert);

                alert.setTitle("Delete error");
                alert.setContentText("Please select a competition by clicking on one!");
                alert.show();
            }
        });
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

    public void setCompetitionList(ListView<CompetitionModel> competitionList) {
        this.competitionList = competitionList;
    }

    public void setCreateCompetition(Button createCompetition) {
        this.createCompetition = createCompetition;
    }

    public void setReadCompetition(Button readCompetition) {
        this.readCompetition = readCompetition;
    }

    public void setDeleteCompetition(Button deleteCompetition) {
        this.deleteCompetition = deleteCompetition;
    }
}
