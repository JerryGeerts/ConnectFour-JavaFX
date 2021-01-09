package View;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class StartScreenView {
    private VBox startScreen;

    /**
     * Constructor
     */
    public StartScreenView() {
        //Init text
        Text title = new Text("Welcome by, four in a row!");
        Text description = new Text("A game made by: Jerry Geerts");

        //Style text
        title.getStyleClass().add("mainTitle");
        description.getStyleClass().add("mainDescr");

        //init VBox
        startScreen = new VBox();

        //Setup startScreen
        startScreen.setPrefSize(850, 500);
        startScreen.setAlignment(Pos.CENTER);
        startScreen.getChildren().addAll(title, description);
    }

    /**
     * return startScreen
     *
     * @return Root of this view
     */
    public VBox getStartScreen() {
        return startScreen;
    }
}
