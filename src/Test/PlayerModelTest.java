package Test;

import Model.Competition.CompetitionMatchModel;
import Model.Competition.CompetitionModel;
import Model.Game.PlayerModel;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerModelTest {
    private Paint testColor = Color.gray(1);
    private PlayerModel testPlayerOne = new PlayerModel("Jerry");
    private PlayerModel testPlayerTwo = new PlayerModel("Jerry", testColor);

    @Test
    void getColor() {
        Paint expResult = testColor;
        Paint result = testPlayerTwo.getColor();

        Assert.assertEquals(expResult, result);
    }

    @Test
    void addWin() {
        testPlayerOne.addWin();

        int expResult = 1;
        int result = testPlayerOne.getMatchWins();

        Assert.assertEquals(expResult, result);
    }

    @Test
    void getMatchWins() {
        int expResult = 0;
        int result = testPlayerTwo.getMatchWins();

        Assert.assertEquals(expResult, result);
    }

    @Test
    void getCompetitionWins() {
        int expResult = 0;
        int result = testPlayerOne.getCompetitionWins();

        Assert.assertEquals(expResult, result);
    }

    @Test
    void addCompeitionWin() {
        testPlayerTwo.addCompeitionWin();

        int expResult = 1;
        int result = testPlayerTwo.getCompetitionWins();

        Assert.assertEquals(expResult, result);
    }

    @Test
    void getName() {
        String expResult = "Jerry";
        String result = testPlayerOne.getName();

        Assert.assertEquals(expResult, result);
    }
}
