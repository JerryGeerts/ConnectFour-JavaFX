package Model.Competition;

import Model.Game.PlayerModel;

import java.io.Serializable;
import java.util.ArrayList;

public class CompetitionModel implements Serializable {
    private String competitionName;

    private ArrayList<CompetitionMatchModel> competitionMatchModels;
    private ArrayList<CompetitionMatchModel> secondRound;
    private ArrayList<CompetitionMatchModel> thirdRound;

    private PlayerModel winner;

    public CompetitionModel(String competitionName, ArrayList<CompetitionMatchModel> competitionMatchModels) {
        this.competitionName = competitionName;
        this.competitionMatchModels = competitionMatchModels;
        this.secondRound = new ArrayList<>();
        this.thirdRound = new ArrayList<>();
    }

    public ArrayList<CompetitionMatchModel> getCompetitionMatchModels() {
        return competitionMatchModels;
    }

    public ArrayList<CompetitionMatchModel> getSecondRound() {
        return secondRound;
    }

    public ArrayList<CompetitionMatchModel> getThirdRound() {
        return thirdRound;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public PlayerModel getWinner() {
        return winner;
    }

    public void setWinner(PlayerModel winner) {
        this.winner = winner;
    }
}
