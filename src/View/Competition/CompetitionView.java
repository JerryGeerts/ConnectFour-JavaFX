package View.Competition;

import Controller.Competition.CompetitionController;
import Model.Competition.CompetitionModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class CompetitionView {
    private VBox competitionView;

    private CompetitionController competitionController;

    private ListView<CompetitionModel> competitionList;

    private Button createCompetition;
    private Button readCompetition;
    private Button deleteCompetition;

    public CompetitionView(CompetitionController competitionController) {
        this.competitionController = competitionController;

        competitionView = createRoot();

        initController();

        initView();
    }

    private VBox createRoot() {
        VBox competitionView = new VBox(20);
        competitionView.setPrefSize(850, 500);
        competitionView.setAlignment(Pos.CENTER);

        HBox competitionButtons = new HBox(36);
        competitionButtons.setMaxWidth(600);

        createCompetition = new Button("Create competition");
        readCompetition = new Button("Show competition");
        deleteCompetition = new Button("Delete competition");

        Text competitionInfo = new Text("All competitions");
        competitionInfo.getStyleClass().add("competitionInfo");

        competitionList = new ListView<>();
        competitionList.setMaxSize(600, 300);
        competitionList.getStyleClass().add("listView");

        competitionList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<CompetitionModel> call(ListView<CompetitionModel> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(CompetitionModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setPrefHeight(50);
                            setText(item.getCompetitionName());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        competitionButtons.getChildren().addAll(createCompetition, readCompetition, deleteCompetition);
        competitionView.getChildren().addAll(competitionInfo, competitionButtons, competitionList);

        return competitionView;
    }

    private void initController() {
        competitionController.setCompetitionList(competitionList);

        competitionController.setCreateCompetition(createCompetition);
        competitionController.setDeleteCompetition(deleteCompetition);
        competitionController.setReadCompetition(readCompetition);

        competitionController.initController();
    }

    private void initView() {
        createCompetition.getStyleClass().add("competitionButtons");
        readCompetition.getStyleClass().add("competitionButtons");
        deleteCompetition.getStyleClass().add("competitionButtons");
    }

    public VBox getCompetitionView() {
        return competitionView;
    }
}
