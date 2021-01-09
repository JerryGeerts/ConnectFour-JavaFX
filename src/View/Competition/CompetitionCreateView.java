package View.Competition;

import Controller.Competition.CompetitionCreateController;
import Model.Game.PlayerModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class CompetitionCreateView {
    private CompetitionCreateController controller;

    private VBox competitionCreateView;

    private Text competitionCreateTitle;
    private Text competitionCreateName;
    private Text competitionCreatePlayer;

    private TextField competitionName;
    private TextField competitionPlayerName;

    private Button addPlayer;
    private Button editPlayer;
    private Button deletePlayer;
    private Button saveCompetition;

    private ListView<PlayerModel> competitionPlayers;

    public CompetitionCreateView(CompetitionCreateController controller) {
        //Set controller
        this.controller = controller;

        //Set root
        competitionCreateView = getRoot();

        //Init the controller
        initContoller();

        //Init the view
        initView();
    }

    public VBox getRoot() {
        //Create Hbox
        VBox competitionCreate = new VBox(10);
        competitionCreate.setAlignment(Pos.CENTER);
        competitionCreate.setPrefSize(850, 500);

        //Screen title
        competitionCreateTitle = new Text("Create a competition");

        //Competition name
        competitionCreateName = new Text("Competition name:");
        competitionName = new TextField();
        competitionName.setMaxWidth(600);

        //Add players
        competitionCreatePlayer = new Text("Add player:");

        HBox addPlayers = new HBox(10);

        competitionPlayerName = new TextField();
        addPlayer = new Button("Add");

        competitionPlayerName.setMinWidth(470);

        addPlayers.setMaxWidth(600);
        addPlayers.setAlignment(Pos.CENTER);
        addPlayers.getChildren().addAll(competitionPlayerName, addPlayer);

        //ListView
        competitionPlayers = new ListView<>();
        competitionPlayers.setMaxSize(600, 200);
        initListView(competitionPlayers);

        //Button Bar
        HBox buttonBar = new HBox(10);

        editPlayer = new Button("Edit");
        deletePlayer = new Button("Delete");

        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.getChildren().addAll(editPlayer, deletePlayer);

        //Save competition
        saveCompetition = new Button("Save competition");

        //Add to HBox
        competitionCreate.getChildren().addAll(competitionCreateTitle, competitionCreateName, competitionName, competitionCreatePlayer, addPlayers, competitionPlayers, buttonBar, saveCompetition);

        return competitionCreate;
    }

    private void initListView(ListView<PlayerModel> lv) {
        lv.setCellFactory(new Callback<>() {
            @Override
            public ListCell<PlayerModel> call(ListView<PlayerModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(PlayerModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                        } else {
                            setPrefHeight(50);
                            setText(item.getName());
                        }
                    }
                };
            }
        });
    }

    private void initContoller() {
        controller.setAddPlayer(addPlayer);
        controller.setDeletePlayer(deletePlayer);
        controller.setEditPlayer(editPlayer);
        controller.setSaveCompetition(saveCompetition);

        controller.setCompetitionName(competitionName);
        controller.setCompetitionPlayerName(competitionPlayerName);

        controller.setCompetitionPlayers(competitionPlayers);

        controller.initController();
    }

    private void initView() {
        competitionCreateTitle.getStyleClass().add("competitionCreateTitle");

        competitionCreateName.getStyleClass().add("competitionCreateDescr");
        competitionCreatePlayer.getStyleClass().add("competitionCreateDescr");

        competitionName.getStyleClass().add("competitionCreateTextbox");
        competitionPlayerName.getStyleClass().add("competitionCreateTextbox");

        addPlayer.getStyleClass().add("competitionCreateButton");
        editPlayer.getStyleClass().add("competitionCreateButton");
        deletePlayer.getStyleClass().add("competitionCreateButton");
        saveCompetition.getStyleClass().add("competitionCreateButton");

        competitionPlayers.getStyleClass().add("listView");
    }

    public VBox getCompetitionCreateView() {
        return competitionCreateView;
    }
}
