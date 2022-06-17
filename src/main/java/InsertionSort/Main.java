package InsertionSort;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application
{

    /**
     * Starts the application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Starts the application window
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage){
        Visualization.generateWindow(primaryStage);
    }
}
