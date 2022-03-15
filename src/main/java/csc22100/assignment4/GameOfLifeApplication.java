package csc22100.assignment4;

import com.google.common.base.Ticker;
import csc22100.assignment4.gameoflife.life.Life;
import csc22100.assignment4.gameoflife.life.LifeService;
import csc22100.assignment4.ui.GameOfLifeController;
import csc22100.assignment4.ui.GameOfLifeViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOfLifeApplication extends Application {

    private static final String FXML_NAME = "/game_of_life.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(FXML_NAME));

        Life life = new Life(10 ,10);
        LifeService lifeService = new LifeService(life, Ticker.systemTicker());
        GameOfLifeViewModel viewModel = new GameOfLifeViewModel(lifeService);

        Parent root = loader.load();
        GameOfLifeController controller = loader.getController();
        controller.init(viewModel);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
