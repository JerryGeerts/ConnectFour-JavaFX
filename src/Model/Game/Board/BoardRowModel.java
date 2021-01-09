package Model.Game.Board;

import Model.Game.PlayerModel;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class BoardRowModel {
    private ArrayList<BoardCellModel> boardCellModels = new ArrayList<>();
    private Button insertButton;

    public BoardRowModel(int heigth) {
        for (int i = 0; i < heigth; i++) {
            boardCellModels.add(new BoardCellModel());
        }
    }

    public boolean insertCoin(PlayerModel player) {
        for (int i = boardCellModels.size() - 1; i >= 0; i--) {
            BoardCellModel boardCell = boardCellModels.get(i);
            if (!boardCell.getUsed()) {
                boardCell.setUsed(player);
                return true;
            }
        }
        return false;
    }

    public BoardCellModel getCell(int y) {
        return boardCellModels.get(y);
    }

    public ArrayList<BoardCellModel> getBoardCellModels() {
        return boardCellModels;
    }

    public void setInsert(Button insert) {
        this.insertButton = insert;
    }

    public Button getInsertButton() {
        return insertButton;
    }
}
