package Model.Game.Board;

import javafx.scene.control.Button;

import java.util.ArrayList;

public class BoardModel {
    private ArrayList<BoardRowModel> boardRowModels = new ArrayList<>();

    public BoardModel(int width, int heigth) {
        for (int i = 0; i < width; i++) {
            boardRowModels.add(new BoardRowModel(heigth));
        }
    }

    public BoardCellModel getCell(int x, int y) {
        return boardRowModels.get(x).getCell(y);
    }

    public void setInsertButton(Button insertButton, int row) {
        boardRowModels.get(row).setInsert(insertButton);
    }

    public BoardRowModel getBoardRow(int x) {
        return boardRowModels.get(x);
    }

    public ArrayList<BoardRowModel> getBoardRowModels() {
        return boardRowModels;
    }
}
