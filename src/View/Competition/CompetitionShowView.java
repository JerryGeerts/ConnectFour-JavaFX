package View.Competition;

import Controller.Competition.CompetitionShowController;
import Model.Competition.CompetitionMatchModel;
import Model.Repository;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class CompetitionShowView {
    private CompetitionShowController controller;
    private Repository repository;

    private HBox competitionShowView;

    private VBox firstRound;
    private VBox secondRound;
    private VBox thirdRound;

    private Button startButton;

    public CompetitionShowView(CompetitionShowController controller) {
        this.controller = controller;

        getRoot(controller.getCompetitionModel().getCompetitionMatchModels());

        initView();
        initController();
    }

    private void getRoot(ArrayList<CompetitionMatchModel> matches) {
        //Main view
        HBox competitionView = new HBox(30);

        //Bracket
        HBox bracket = new HBox(10);
        bracket.setAlignment(Pos.CENTER);
        bracket.setMaxSize(650, 500);
        bracket.setPadding(new Insets(25, 0, 0, 0));
        getBracket(bracket);

        //Control
        VBox buttonRow = new VBox(30);
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setMaxSize(200, 500);
        buttonRow.setPadding(new Insets(0, 0, 0, 25));
        getButtons(buttonRow);

        competitionView.getChildren().addAll(buttonRow, bracket);

        competitionShowView = competitionView;
    }

    private void initController() {
        controller.setFirstRound(firstRound);
        controller.setSecondRound(secondRound);
        controller.setThirdRound(thirdRound);

        controller.setStartButton(startButton);

        controller.initController();
    }

    private void getButtons(VBox buttonRow) {
        startButton = new Button("Start next match");

        buttonRow.getChildren().add(startButton);
    }

    private void initView() {
        firstRound.getStyleClass().add("competitionBracket");
        secondRound.getStyleClass().add("competitionBracket");
        thirdRound.getStyleClass().add("competitionBracket");

        startButton.getStyleClass().add("competitionStartButton");
    }

    private void getBracket(HBox bracket) {
        firstRound = new VBox(58);
        firstRound.setPadding(new Insets(15, 0, 0, 0));
        getFirstRound(4);

        secondRound = new VBox(180);
        secondRound.setPadding(new Insets(75, 0, 0, 0));
        getSecondRound(2);

        thirdRound = new VBox();
        thirdRound.setPadding(new Insets(190, 0, 0, 0));
        getThirdRound(1);

        bracket.getChildren().addAll(firstRound, getFirstRoundDevider(), secondRound, getSecondRoundDevider(), thirdRound);
    }

    private HBox getFirstRoundDevider() {
        HBox firstRoundDevider = new HBox();

        VBox firstRoundLines = new VBox(118);

        for (int i = 0; i < 4; i++) {
            Line line = new Line(0, 0, 12, 0);
            line.getStyleClass().add("competitionRoundDevider");

            firstRoundLines.getChildren().add(line);
        }

        VBox firstLine = new VBox(118);

        for (int i = 0; i < 2; i++) {
            Line line = new Line(0, 0, 0, 120);
            line.getStyleClass().add("competitionRoundDevider");

            firstLine.getChildren().add(line);
        }


        VBox secondRoundLines = new VBox(239);

        for (int i = 0; i < 4; i++) {
            Line line = new Line(0, 0, 12, 0);
            line.getStyleClass().add("competitionRoundDevider");

            secondRoundLines.getChildren().add(line);
        }

        secondRoundLines.setPadding(new Insets(60, 0, 0, 0));

        firstRoundDevider.getChildren().addAll(firstRoundLines, firstLine, secondRoundLines);
        firstRoundDevider.setPadding(new Insets(45, 0, 0, 0));

        return firstRoundDevider;
    }

    private HBox getSecondRoundDevider() {
        HBox secondRoundDevider = new HBox();

        //Seond round h lines
        VBox secondRoundLines = new VBox(238);

        for (int i = 0; i < 2; i++) {
            Line line = new Line(0, 0, 12, 0);
            line.getStyleClass().add("competitionRoundDevider");

            secondRoundLines.getChildren().add(line);
        }

        //Second v line
        Line secondLine = new Line(0, 0, 0, 240);
        secondLine.getStyleClass().add("competitionRoundDevider");

        //Third round h lines
        VBox thirdRoundLines = new VBox();

        Line thirdRoundLine = new Line(0, 0, 12, 0);
        thirdRoundLine.getStyleClass().add("competitionRoundDevider");

        thirdRoundLines.setPadding(new Insets(115, 0, 0, 0));
        thirdRoundLines.getChildren().add(thirdRoundLine);

        //Add everything to Hbox
        secondRoundDevider.setPadding(new Insets(105, 0, 0, 0));
        secondRoundDevider.getChildren().addAll(secondRoundLines, secondLine, thirdRoundLines);

        return secondRoundDevider;
    }

    private void getFirstRound(int amount) {
        for (int i = 0; i < amount; i++) {
            VBox match = new VBox();
            match.getStyleClass().add("competitionMatch");
            match.setAlignment(Pos.BASELINE_CENTER);

            firstRound.getChildren().add(match);
        }
    }

    private void getSecondRound(int amount) {
        for (int i = 0; i < amount; i++) {
            VBox match = new VBox();
            match.getStyleClass().add("competitionMatch");
            match.setAlignment(Pos.BASELINE_CENTER);

            Label playerOne = new Label("Winner round 1 game " + ((i == 1) ? "1" : "3"));
            Label playerTwo = new Label("Winner round 1 game " + ((i == 1) ? "2" : "4"));

            playerOne.getStyleClass().add("competitionPlayer");
            playerTwo.getStyleClass().add("competitionPlayer");

            match.getChildren().addAll(playerOne, playerTwo);

            secondRound.getChildren().add(match);
        }
    }

    private void getThirdRound(int amount) {
        for (int i = 0; i < amount; i++) {
            VBox match = new VBox();
            match.getStyleClass().add("competitionMatch");
            match.setAlignment(Pos.BASELINE_CENTER);

            Label playerOne = new Label("Winner round 2 game 1");
            Label playerTwo = new Label("Winner round 2 game 2");

            playerOne.getStyleClass().add("competitionPlayer");
            playerTwo.getStyleClass().add("competitionPlayer");

            match.getChildren().addAll(playerOne, playerTwo);

            thirdRound.getChildren().add(match);
        }
    }

    public HBox getCompetitionShowView() {
        return competitionShowView;
    }
}
