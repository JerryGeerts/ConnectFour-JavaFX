package Controller.Competition;

import Controller.Game.GameCreateController;
import Controller.MenuController;
import Model.Competition.CompetitionMatchModel;
import Model.Competition.CompetitionModel;
import Model.Game.PlayerModel;
import Model.Repository;
import View.Game.GameCreateView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class CompetitionShowController {
    private final Repository competitionRepo;
    private CompetitionModel competitionModel;

    private ArrayList<CompetitionMatchModel> firstBracket;
    private ArrayList<CompetitionMatchModel> secondBracket;
    private ArrayList<CompetitionMatchModel> thirdBracket;

    private MenuController menuController;

    private VBox firstRound;
    private VBox secondRound;
    private VBox thirdRound;


    private Button startButton;
    private Repository playerRepo;

    public CompetitionShowController(MenuController menuController, CompetitionModel competitionModel, Repository competitionRepo, Repository playerRepo) {
        this.menuController = menuController;
        this.competitionModel = competitionModel;
        this.competitionRepo = competitionRepo;
        this.playerRepo = playerRepo;
    }

    public void initController() {
        initGame();
        initButtons();
    }

    private void initButtons() {
        startButton.setOnAction(event -> {
            startNextGame();
        });
    }

    private void initGame() {
        firstBracket = competitionModel.getCompetitionMatchModels();
        secondBracket = competitionModel.getSecondRound();
        thirdBracket = competitionModel.getThirdRound();

        //First round
        initFirstRound();

        //Second round
        initSecondRound();

        //Third round
        initThirdRound();
    }

    private void initFirstRound() {
        PlayerModel winnerFirstRound = null;

        //Loop through all firstRound HBox children
        for (int i = 0; i < firstRound.getChildren().size(); i++) {
            //Get match VBox
            VBox match = (VBox) firstRound.getChildren().get(i);
            //get Competition match model
            CompetitionMatchModel currentCompMatch = firstBracket.get(i);

            //Set player name labels
            Label playerOne = new Label(currentCompMatch.getPlayerOne().getName());
            Label playerTwo = new Label(currentCompMatch.getPlayerTwo().getName());

            //Set player name labels styling
            playerOne.getStyleClass().add("competitionPlayer");
            playerTwo.getStyleClass().add("competitionPlayer");

            //Add playernames too match
            match.getChildren().addAll(playerOne, playerTwo);

            //If match is won
            if (currentCompMatch.getGameWon()) {
                //Give match match styling
                giveMatchStyle(match, playerOne, playerTwo, currentCompMatch);

                if (secondBracket.size() > 0) {
                    if (secondBracket.get(0).getPlayerOne() != currentCompMatch.getWinningPlayer() &&
                            secondBracket.get(0).getPlayerTwo() != currentCompMatch.getWinningPlayer()) {
                        if (secondBracket.size() == 2) {
                            if (secondBracket.get(1).getPlayerOne() != currentCompMatch.getWinningPlayer() &&
                                    secondBracket.get(1).getPlayerTwo() != currentCompMatch.getWinningPlayer()) {
                                //If match 1 & 2 of second round doesn't have this player add too second round
                                winnerFirstRound = addWinner(winnerFirstRound, currentCompMatch, secondBracket);
                            }
                        } else {
                            //If match 1 of second round doesn't have this player add too second round
                            winnerFirstRound = addWinner(winnerFirstRound, currentCompMatch, secondBracket);
                        }
                    }
                } else {
                    //If no matches are in second round add one
                    winnerFirstRound = addWinner(winnerFirstRound, currentCompMatch, secondBracket);
                }
            }
        }
    }

    private void initSecondRound() {
        PlayerModel winnerSecondRound = null;

        //Loop through all matches
        for (int i = 0; i < secondBracket.size(); i++) {
            if (i < secondRound.getChildren().size()) {
                //Get match VBox
                VBox match = (VBox) secondRound.getChildren().get(i);
                //Get Competition match model
                CompetitionMatchModel currentCompMatch = secondBracket.get(i);

                //Get playername label
                Label playerOne = (Label) match.getChildren().get(0);
                Label playerTwo = (Label) match.getChildren().get(1);

                //Set playername label text
                playerOne.setText(currentCompMatch.getPlayerOne().getName());
                playerTwo.setText(currentCompMatch.getPlayerTwo().getName());

                //If this game is won
                if (currentCompMatch.getGameWon()) {
                    //Give match styling
                    giveMatchStyle(match, playerOne, playerTwo, currentCompMatch);

                    //Add player too third round
                    winnerSecondRound = addWinner(winnerSecondRound, currentCompMatch, thirdBracket);
                }
            }
        }
    }

    private void initThirdRound() {
        //Loop through all matches
        for (int i = 0; i < thirdBracket.size(); i++) {
            if (i < thirdRound.getChildren().size()) {
                //Get match VBox
                VBox match = (VBox) thirdRound.getChildren().get(i);
                //Get Competition match model
                CompetitionMatchModel currentCompMatch = thirdBracket.get(i);

                //Get playername label
                Label playerOne = (Label) match.getChildren().get(0);
                Label playerTwo = (Label) match.getChildren().get(1);

                //Set playername label text
                playerOne.setText(currentCompMatch.getPlayerOne().getName());
                playerTwo.setText(currentCompMatch.getPlayerTwo().getName());

                //If this game is won
                if (currentCompMatch.getGameWon()) {
                    //Give match styling
                    giveMatchStyle(match, playerOne, playerTwo, currentCompMatch);

                    competitionModel.setWinner(currentCompMatch.getWinningPlayer());
                    //Add competition win to player
                    currentCompMatch.getWinningPlayer().addCompeitionWin();
                    playerRepo.saveObject();
                }
            }
        }
    }

    private PlayerModel addWinner(PlayerModel winnerFirstRound, CompetitionMatchModel currentCompMatch, ArrayList<CompetitionMatchModel> secondBracket) {
        if (winnerFirstRound == null) {
            winnerFirstRound = currentCompMatch.getWinningPlayer();
        } else {
            CompetitionMatchModel newComp = new CompetitionMatchModel(winnerFirstRound, currentCompMatch.getWinningPlayer());
            secondBracket.add(newComp);

            winnerFirstRound = null;
        }
        return winnerFirstRound;
    }

    private void giveMatchStyle(VBox match, Label playerOne, Label playerTwo, CompetitionMatchModel currentCompMatch) {
        match.getStyleClass().add("competitionMatchFinished");

        if (currentCompMatch.getPlayerOne().getName().equals(currentCompMatch.getWinningPlayer().getName())) {
            playerOne.getStyleClass().add("competitionMatchWinner");
            playerTwo.getStyleClass().add("competitionMatchloser");
        } else if (currentCompMatch.getPlayerTwo().getName().equals(currentCompMatch.getWinningPlayer().getName())) {
            playerTwo.getStyleClass().add("competitionMatchWinner");
            playerOne.getStyleClass().add("competitionMatchloser");
        }
    }

    private void startNextGame() {
        if (!firstBracket.get(firstBracket.size() - 1).getGameWon()) {
            getGame(firstBracket);
        } else if (!secondBracket.get(secondBracket.size() - 1).getGameWon()) {
            getGame(secondBracket);
        } else if (competitionModel.getWinner() == null) {
            getGame(thirdBracket);
        }
    }

    private void getGame(ArrayList<CompetitionMatchModel> bracket) {
        for (CompetitionMatchModel match : bracket) {
            if (!match.getGameWon()) {
                GameCreateController currentMatchController = new GameCreateController(menuController, match, competitionRepo, playerRepo, competitionModel);
                GameCreateView currentMatchView = new GameCreateView(currentMatchController);
                menuController.addScreen("currentMatch", currentMatchView.getRoot());
                menuController.activate("currentMatch");
                break;
            }
        }
    }

    public CompetitionModel getCompetitionModel() {
        return competitionModel;
    }

    public void setFirstRound(VBox firstRound) {
        this.firstRound = firstRound;
    }

    public void setSecondRound(VBox secondRound) {
        this.secondRound = secondRound;
    }

    public void setThirdRound(VBox thirdRound) {
        this.thirdRound = thirdRound;
    }

    public void setStartButton(Button startButton) {
        this.startButton = startButton;
    }
}
