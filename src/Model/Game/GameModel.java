package Model.Game;

import Model.Game.Board.BoardCellModel;
import Model.Game.Board.BoardModel;
import Model.Game.Board.BoardRowModel;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GameModel {
    private BoardModel board;
    private ArrayList<PlayerModel> players;
    private PlayerModel turn;
    private Text gameInfo;
    private Text gameInfoDescr;

    public GameModel(BoardModel board, ArrayList<PlayerModel> playerModels) {
        this.board = board;
        this.players = playerModels;

        turn = players.get(0);
    }

    public BoardModel getBoard() {
        return board;
    }

    public BoardCellModel getCell(int x, int y){
        return board.getCell(x,y);
    }

    public void setInsertButton(Button insertButton, int row){
        board.setInsertButton(insertButton, row);
    }

    public BoardRowModel getBoardRow(int x){
        return board.getBoardRow(x);
    }

    public void setGameInfo(Text gameInfo) {
        this.gameInfo = gameInfo;
        gameInfo.setText("It's " + players.get(0).getName() + "'s turn!");
    }

    public PlayerModel getTurn() {
        return turn;
    }

    public void nextTurn(){
        if(turn == players.get(0)){
            turn = players.get(1);
        }
        else{
            turn = players.get(0);
        }

        gameInfo.setText("It's " + turn.getName() + "'s turn!");
    }

    public Text getGameInfo() {
        return gameInfo;
    }

    public void setGameInfoDescr(Text gameInfoDescr) {
        this.gameInfoDescr = gameInfoDescr;
    }

    public Text getGameInfoDescr() {
        return gameInfoDescr;
    }
}

