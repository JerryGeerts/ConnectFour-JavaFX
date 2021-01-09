package Test;

import Model.Competition.CompetitionMatchModel;
import Model.Competition.CompetitionModel;
import Model.Game.PlayerModel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CompetitionModelTest {
    private ArrayList<CompetitionMatchModel> testMatches = new ArrayList<>();

    @Test
    void getCompetitionMatchModels() {
        testMatches.add(new CompetitionMatchModel(new PlayerModel("Jerry"), new PlayerModel("Sanne")));
        testMatches.add(new CompetitionMatchModel(new PlayerModel("Jerry"), new PlayerModel("Sanne")));
        testMatches.add(new CompetitionMatchModel(new PlayerModel("Jerry"), new PlayerModel("Sanne")));

        CompetitionModel competitionModel = new CompetitionModel("Test competitie", testMatches);

        ArrayList<CompetitionMatchModel> expResult = testMatches;
        ArrayList<CompetitionMatchModel> result = competitionModel.getCompetitionMatchModels();

        Assert.assertEquals(expResult, result);
    }

    @Test
    void getCompetitionName() {
        CompetitionModel competitionModel = new CompetitionModel("Test competitie", testMatches);

        String expResult = "Test competitie";
        String result = competitionModel.getCompetitionName();

        Assert.assertEquals(expResult, result);
    }

    @Test
    void getWinner() {
        PlayerModel testPlayer = new PlayerModel("Jerry");

        CompetitionModel competitionModel = new CompetitionModel("Test competitie", testMatches);
        competitionModel.setWinner(testPlayer);

        PlayerModel expResult = testPlayer;
        PlayerModel result = competitionModel.getWinner();

        Assert.assertEquals(expResult, result);
    }

    @Test
    void setWinner() {
        PlayerModel testPlayer = new PlayerModel("Sanne");

        CompetitionModel competitionModel = new CompetitionModel("Test competitie", testMatches);
        competitionModel.setWinner(testPlayer);

        PlayerModel expResult = testPlayer;
        PlayerModel result = competitionModel.getWinner();

        Assert.assertEquals(expResult, result);
    }
}