package Model.Game;

import javafx.scene.paint.Paint;

import java.io.Serializable;

public class PlayerModel implements Serializable{
    private String name;
    private transient Paint color;
    private int matchWins;
    private int competitionWins;

    public PlayerModel(String name, Paint color) {
        this.name = name;
        this.color = color;
    }

    public PlayerModel(String name) {
        this.name = name;
    }

    public Paint getColor() {
        return color;
    }

    public void addWin() {
        this.matchWins++;
    }

    public int getMatchWins() {
        return matchWins;
    }

    public int getCompetitionWins() {
        return competitionWins;
    }

    public void addCompeitionWin(){
        this.competitionWins++;
    }

    public String getName() {
        return name;
    }
}
