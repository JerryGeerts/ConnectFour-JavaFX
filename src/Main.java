import Controller.Competition.CompetitionController;
import Controller.Competition.CompetitionCreateController;
import Controller.Game.GameCreateController;
import Controller.MenuController;
import Controller.ScoreBoardController;
import Model.Repository;
import View.Competition.CompetitionCreateView;
import View.Competition.CompetitionView;
import View.Game.GameCreateView;
import View.ScoreboardView;
import View.StartScreenView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class Main extends Application {
    private MenuController menuController;
    //init competitionRepo
    private Repository competitionRepo = new Repository(new File("src/Data/Competitions.ser"));
    //init playerRepo
    private Repository playerRepo = new Repository(new File("src/Data/Players.ser"));

    private double xOffset = 0;
    private double yOffset = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Setup primary stage
        screenHandler(primaryStage);

        //Add all screens to a hashmap
        addScreens();

        //Change screen to start screen
        changeScreen("startScreen");
    }

    /**
     * Setup primaryStage
     *
     * @param primaryStage Primary stage of the application
     */
    private void screenHandler(Stage primaryStage) {
        //Init main scene
        Scene mainScene = new Scene(new Pane(), 1100, 500);
        //init Menu controller
        menuController = new MenuController(mainScene, playerRepo);

        //Setup primary stage
        primaryStage.setTitle("Vier op een rij");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);

        //When clicked get X, Y of mouse click
        mainScene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        //Change primary stage X, Y when dragged
        mainScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });


        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * Add all static screens to hashmap
     */
    private void addScreens() {
        GameCreateController gameCreateController = new GameCreateController(menuController, playerRepo);
        GameCreateView gameCreateView = new GameCreateView(gameCreateController);
        menuController.addScreen("gameCreate", gameCreateView.getRoot());

        StartScreenView startScreen = new StartScreenView();
        menuController.addScreen("startScreen", startScreen.getStartScreen());

        CompetitionController competitionController = new CompetitionController(menuController, competitionRepo, playerRepo);
        CompetitionView competitionView = new CompetitionView(competitionController);
        menuController.addScreen("competitionView", competitionView.getCompetitionView());

        CompetitionCreateController competitionCreateController = new CompetitionCreateController(menuController, competitionRepo, playerRepo);
        CompetitionCreateView competitionCreateView = new CompetitionCreateView(competitionCreateController);
        menuController.addScreen("competitionCreateView", competitionCreateView.getCompetitionCreateView());
    }

    /**
     * Change current screen
     *
     * @param name name of the screen that you would like to switch too
     */
    private void changeScreen(String name) {
        menuController.activate(name);
    }
}
