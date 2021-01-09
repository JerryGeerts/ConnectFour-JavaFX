package Test;

import Model.Competition.CompetitionMatchModel;
import Model.Game.PlayerModel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompetitionMatchModelTest {
    private PlayerModel playerOne = new PlayerModel("Jerry");
    private PlayerModel playerTwo = new PlayerModel("Sanne");
    private CompetitionMatchModel competitionMatchModel = new CompetitionMatchModel(playerOne, playerTwo);

    @Test
    void getPlayerOne() {
        PlayerModel expResult = playerOne;
        PlayerModel result = competitionMatchModel.getPlayerOne();

        Assert.assertEquals(expResult, result);
    }

    @Test
    void getPlayerTwo() {
        PlayerModel expResult = playerTwo;
        PlayerModel result = competitionMatchModel.getPlayerTwo();

        Assert.assertEquals(expResult, result);
    }

    @Test
    void getWinningPlayer() {
        competitionMatchModel.setWinningPlayer(playerTwo);

        PlayerModel expResult = playerTwo;
        PlayerModel result = competitionMatchModel.getWinningPlayer();

        Assert.assertEquals(expResult, result);
    }

    @Test
    void setWinningPlayer() {
        competitionMatchModel.setWinningPlayer(playerOne);

        PlayerModel expResult = playerOne;
        PlayerModel result = competitionMatchModel.getWinningPlayer();

        Assert.assertEquals(expResult, result);
    }

    @Test
    void getGameWon() {
        competitionMatchModel.setWinningPlayer(playerOne);

        boolean expResult = true;
        boolean result = competitionMatchModel.getGameWon();

        Assert.assertEquals(expResult, result);
    }
}