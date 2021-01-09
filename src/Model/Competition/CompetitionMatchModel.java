package Model.Competition;

import Model.Game.PlayerModel;

import java.io.Serializable;
import java.time.LocalDate;

public class CompetitionMatchModel implements Serializable {
    private PlayerModel playerOne;
    private PlayerModel playerTwo;

    private PlayerModel winningPlayer;

    public CompetitionMatchModel(PlayerModel playerOne, PlayerModel playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public PlayerModel getPlayerOne() {
        return playerOne;
    }

    public PlayerModel getPlayerTwo() {
        return playerTwo;
    }

    public PlayerModel getWinningPlayer() {
        return winningPlayer;
    }

    public void setWinningPlayer(PlayerModel winningPlayer) {
        this.winningPlayer = winningPlayer;
    }

    public boolean getGameWon() {
        return winningPlayer != null;
    }
}
