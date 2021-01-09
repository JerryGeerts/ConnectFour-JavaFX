package Controller.Game;

import Controller.Competition.CompetitionShowController;
import Controller.MenuController;
import Model.Competition.CompetitionMatchModel;
import Model.Competition.CompetitionModel;
import Model.Game.Board.BoardCellModel;
import Model.Game.Board.BoardModel;
import Model.Game.Board.BoardRowModel;
import Model.Game.GameModel;
import Model.Game.PlayerModel;
import Model.Repository;
import View.Competition.CompetitionShowView;
import View.Game.GameCreateView;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GameController {
    private MenuController menuController;
    private Repository competitionRepo;
    private Repository playerRepo;
    private CompetitionModel competitionModel;
    private CompetitionMatchModel match;
    private ArrayList<BoardRowModel> boardRowModels;

    private int boardHeight;
    private int boardWidth;

    private Label labelPlayerOne;
    private Label labelPlayerTwo;

    private String namePlayerOne;
    private String namePlayerTwo;

    private Circle circlePlayerOne;
    private Circle circlePlayerTwo;

    private Text letterPlayerOne;
    private Text letterPlayerTwo;

    private Paint colorPlayerOne;
    private Paint colorPlayerTwo;

    private Button surrenderPlayerOne;
    private Button surrenderPlayerTwo;

    private PlayerModel playerOne;
    private PlayerModel playerTwo;

    private GameModel game;

    private boolean gameWon = false;

    GameController(String namePlayerOne,
                   String namePlayerTwo,
                   Paint colorPlayerOne,
                   Paint colorPlayerTwo,
                   int boardWidth,
                   int boardHeight,
                   CompetitionMatchModel match,
                   MenuController menuController,
                   Repository competitionRepo,
                   Repository playerRepo,
                   CompetitionModel competitionModel) {
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;

        this.namePlayerOne = namePlayerOne;
        this.namePlayerTwo = namePlayerTwo;

        this.colorPlayerOne = colorPlayerOne;
        this.colorPlayerTwo = colorPlayerTwo;

        this.playerOne = new PlayerModel(namePlayerOne, colorPlayerOne);
        this.playerTwo = new PlayerModel(namePlayerTwo, colorPlayerTwo);

        this.match = match;
        this.menuController = menuController;
        this.competitionRepo = competitionRepo;
        this.playerRepo = playerRepo;
        this.competitionModel = competitionModel;

        ArrayList<PlayerModel> players = new ArrayList<>();
        BoardModel board = new BoardModel(this.boardWidth, this.boardHeight);

        players.add(this.playerOne);
        players.add(this.playerTwo);

        game = new GameModel(board, players);
    }

    public void initialize() {
        for (int i = 0; i < boardWidth; i++) {
            BoardRowModel boardRow = game.getBoardRow(i);

            boardRow.getInsertButton().setOnAction(event -> {
                if (!gameWon) {
                    PlayerModel currentPlayer = game.getTurn();

                    if (boardRow.insertCoin(currentPlayer)) {
                        if (checkWinner(currentPlayer, 4)) {
                            gameWon = true;

                            game.getGameInfo().setText("Four in a row!");
                            game.getGameInfoDescr().setText(currentPlayer.getName() + " is the winner!");

                            countDown counter = new countDown(currentPlayer, currentPlayer == playerOne ? playerTwo : playerOne);
                            counter.start();

                        } else {
                            game.nextTurn();
                        }
                    }
                }
            });
        }

        labelPlayerOne.setText("Player one: " + namePlayerOne);
        labelPlayerTwo.setText("Player two: " + namePlayerTwo);

        circlePlayerOne.setFill(colorPlayerOne);
        circlePlayerTwo.setFill(colorPlayerTwo);

        if (!namePlayerOne.isEmpty() && !namePlayerTwo.isEmpty()) {
            letterPlayerOne.setText((namePlayerOne.charAt(0) + "").toUpperCase());
            letterPlayerTwo.setText((namePlayerTwo.charAt(0) + "").toUpperCase());
        }

        initButtons();
    }

    private void initButtons() {
        surrender(surrenderPlayerOne, playerOne, playerTwo);
        surrender(surrenderPlayerTwo, playerTwo, playerOne);
    }

    private void surrender(Button surrenderButton, PlayerModel surrPlayer, PlayerModel winPlayer) {
        surrenderButton.setOnAction(event -> {
            gameWon = true;
            game.getGameInfo().setText(surrPlayer.getName() + " has surrendered!");
            game.getGameInfoDescr().setText(winPlayer.getName() + " is the winner!");

            countDown counter = new countDown(winPlayer, surrPlayer);
            counter.start();

        });
    }

    private boolean checkWinner(PlayerModel player, int amount) {
        boardRowModels = game.getBoard().getBoardRowModels();

        return (checkVertical(player, amount)
                || checkHorizontal(player, amount)
                || checkDiagonalForward(player, amount)
                || checkDiagonalBackwards(player, amount)
        );
    }

    private boolean checkVertical(PlayerModel player, int amount) {
        ArrayList<BoardCellModel> winningCells = new ArrayList<>();

        for (BoardRowModel boardRowModel : boardRowModels) {
            int counter = 0;

            for (int j = 0; j < boardRowModel.getBoardCellModels().size(); j++) {
                BoardCellModel cell = boardRowModel.getBoardCellModels().get(j);

                counter = getCounter(player, winningCells, counter, cell);

                if (counter == amount) {
                    setCellBorder(winningCells);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkHorizontal(PlayerModel player, int amount) {
        ArrayList<BoardCellModel> winningCells = new ArrayList<>();

        for (int j = 0; j < boardRowModels.get(0).getBoardCellModels().size(); j++) {
            int counter = 0;

            for (BoardRowModel row : boardRowModels) {
                BoardCellModel cell = row.getCell(j);

                counter = getCounter(player, winningCells, counter, cell);

                if (counter == amount) {
                    setCellBorder(winningCells);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkDiagonalForward(PlayerModel player, int amount) {
        int counter = 0;
        int currentHight = boardHeight - amount;
        ArrayList<BoardCellModel> winningCells = new ArrayList<>();

        for (int i = 0; i < (boardWidth - (boardWidth - amount)); i++) {
            do {
                if (currentHight == -1) {
                    currentHight++;
                }

                for (int j = 0; j < (boardHeight - currentHight); j++) {
                    int x = j + i;
                    int y = currentHight + j;
                    if (x < boardWidth) {
                        BoardRowModel row = boardRowModels.get(x);
                        BoardCellModel cell = row.getBoardCellModels().get(y);

                        counter = getCounter(player, winningCells, counter, cell);

                        if (counter == amount) {
                            setCellBorder(winningCells);
                            return true;
                        }
                    }
                }

                currentHight--;
            } while (currentHight != -1);
        }

        return false;
    }

    private boolean checkDiagonalBackwards(PlayerModel player, int amount) {
        int counter = 0;
        int currentHight = boardHeight - amount + 1;
        ArrayList<BoardCellModel> winningCells = new ArrayList<>();

        for (int i = 0; i < (boardWidth - (boardWidth - amount)); i++) {
            do {
                if (currentHight == 6) {
                    currentHight--;
                }

                for (int j = 0; j < (boardHeight + currentHight); j++) {
                    int x = j + i;
                    int y = currentHight - j;
                    if (x < boardWidth && y >= 0 && y < boardHeight) {
                        BoardRowModel row = boardRowModels.get(x);
                        BoardCellModel cell = row.getBoardCellModels().get(y);

                        counter = getCounter(player, winningCells, counter, cell);

                        if (counter == amount) {
                            setCellBorder(winningCells);
                            return true;
                        }
                    }
                }

                currentHight++;
            } while (currentHight != 6);
        }

        return false;
    }

    private int getCounter(PlayerModel player, ArrayList<BoardCellModel> winningCells, int counter, BoardCellModel cell) {
        if (cell.getUsed() && cell.getPlayer() == player) {
            counter++;
            winningCells.add(cell);
        } else {
            winningCells.clear();
            counter = 0;
        }
        return counter;
    }

    private void setCellBorder(ArrayList<BoardCellModel> winningCells) {
        for (BoardCellModel winningCell : winningCells) {
            winningCell.getCircle().setStrokeType(StrokeType.INSIDE);
            winningCell.getCircle().setStroke(Color.BLACK);
            winningCell.getCircle().setStrokeWidth(3);
        }
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public BoardCellModel getBoardCell(int x, int y) {
        return game.getCell(x, y);
    }

    public void setInsertButton(Button insertButton, int row) {
        game.setInsertButton(insertButton, row);
    }

    public void setNamePlayerTwo(Label namePlayerTwo) {
        labelPlayerTwo = namePlayerTwo;
    }

    public void setNamePlayerOne(Label namePlayerOne) {
        labelPlayerOne = namePlayerOne;
    }

    public void setCirclePlayerOne(Circle circlePlayerOne) {
        this.circlePlayerOne = circlePlayerOne;
    }

    public void setCirclePlayerTwo(Circle circlePlayerTwo) {
        this.circlePlayerTwo = circlePlayerTwo;
    }

    public void setLetterPlayerOne(Text letterPlayerOne) {
        this.letterPlayerOne = letterPlayerOne;
    }

    public void setLetterPlayerTwo(Text letterPlayerTwo) {
        this.letterPlayerTwo = letterPlayerTwo;
    }

    public void setGameInfo(Text gameInfo) {
        game.setGameInfo(gameInfo);
    }

    public void setGameInfoDescr(Text gameInfoDescr) {
        game.setGameInfoDescr(gameInfoDescr);
    }

    public void setSurrenderPlayerOne(Button surrenderPlayerOne) {
        this.surrenderPlayerOne = surrenderPlayerOne;
    }

    public void setSurrenderPlayerTwo(Button surrenderPlayerTwo) {
        this.surrenderPlayerTwo = surrenderPlayerTwo;
    }

    public class countDown extends Thread {
        private PlayerModel winningPlayer;
        private PlayerModel losingPlayer;

        countDown(PlayerModel winningPlayer, PlayerModel losingPlayer) {
            this.winningPlayer = winningPlayer;
            this.losingPlayer = losingPlayer;
        }

        public void run() {
            try {
                int waitTime = 5;
                Thread.sleep(1000);

                for (int i = 0; i <= 5; i++) {
                    game.getGameInfo().setText("Game is ending in: " + (waitTime - i));
                    Thread.sleep(1000);
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<Object> allPlayers = playerRepo.getObjects();
            boolean winningPlayerExited = false;
            boolean losingPlayerExited = false;


            for (Object player : allPlayers){
                PlayerModel currentPlayer = (PlayerModel) player;

                if(winningPlayer.getName().equals(currentPlayer.getName())){
                    winningPlayer = currentPlayer;
                    winningPlayerExited = true;
                }

                if(losingPlayer.getName().equals(currentPlayer.getName())){
                    losingPlayer = currentPlayer;
                    losingPlayerExited = true;
                }
            }

            winningPlayer.addWin();

            if(!winningPlayerExited){
                playerRepo.addObject(winningPlayer);
            }

            if(!losingPlayerExited){
                playerRepo.addObject(losingPlayer);
            }

            playerRepo.saveObject();

            if (match != null) {
                Platform.runLater(() -> {
                    match.setWinningPlayer(winningPlayer);

                    competitionRepo.saveObject();

                    CompetitionShowController competitionShowController = new CompetitionShowController(menuController, competitionModel, competitionRepo, playerRepo);
                    CompetitionShowView competitionShowView = new CompetitionShowView(competitionShowController);
                    menuController.addScreen("competitionShowView", competitionShowView.getCompetitionShowView());
                    menuController.activate("competitionShowView");
                });
            }
            else{
                GameCreateController gameCreateController = new GameCreateController(menuController, playerRepo);
                GameCreateView gameCreateView = new GameCreateView(gameCreateController);
                menuController.addScreen("gameCreate", gameCreateView.getRoot());
                menuController.activate("gameCreate");
            }
        }
    }
}
