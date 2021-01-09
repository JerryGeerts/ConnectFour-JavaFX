package Model.Game.Board;

import Model.Game.PlayerModel;
import javafx.scene.shape.Circle;

public class BoardCellModel {
    private boolean used;
    private PlayerModel player;
    private Circle circle;

    public BoardCellModel() {
        used = false;
        player = null;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(PlayerModel player) {
        this.used = true;
        this.player = player;
        circle.setFill(player.getColor());

    }

    public PlayerModel getPlayer() {
        return player;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
