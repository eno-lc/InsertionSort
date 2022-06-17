package InsertionSort;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

import static InsertionSort.Algorithm.insertionSort;

public class Visualization {


    /**
     * JavaFx Components that are used to create the Stage/Window for the algorithm visualizing
     * 1. The default sorting speed
     * 2. Horizontal Boxes are used to store different visual components in the main Stage/Window - kind of a HTML DIV
     * 3. Buttons are used to start the sorting of nodes and generate a new array of random nodes
     * 4. Texts are used for slider labels
     * 5. Panes are used for the positioning of the elements inside the main Stage/Window
     * 6. Slider is used to change the sorting speed visualized
     * 7. Random is a Java Class which generates a random integer value
     * 8. Scene is the main window
     * 9. An array list with StackPane Objects where each of the elements represents a node
     */
    static double sortingSpeed = 400;
    final static HBox horizontalBox = new HBox(20);
    final static HBox horizontalBox2 = new HBox(20);
    final static HBox slideBox = new HBox(5);
    final static HBox topBox = new HBox(40);

    final static Button sortButton = new Button("Sort Nodes");
    final static Button generateButton = new Button("Generate Nodes");

    final static Text slower = new Text("Slower");
    final static Text faster = new Text("Faster");

    final static BorderPane borderPane = new BorderPane();
    final static AnchorPane bottomPane = new AnchorPane();
    final static Slider slider = new Slider(100, 4000, 400);
    final static Random random = new Random(5);
    final static Scene scene = new Scene(borderPane, 800, 400);
    final static ArrayList<StackPane> nodeList = new ArrayList<>();


    /**
     * 1. Generate the window where the Insertion Sort will be visualized
     * 2. Accepts a Stage parameter which is sent from the start method which is an embedded method in JavaFx to start the application - the visual part
     * 3. Calls the generateSingleNodes method, which will generate an array with random value nodes
     * 4. Sets up the default values for the generated window
     * 5. Gives access to the Sort Action Event and Generate New Node Array Action Event
     * @param mainStage
     */
    public static void generateWindow(Stage mainStage)
    {
        generateSingleNodes();

        horizontalBox.setAlignment(Pos.CENTER);
        horizontalBox.getChildren().addAll(nodeList);
        borderPane.setCenter(horizontalBox);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> sortingSpeed = (double) newValue);
        slideBox.getChildren().addAll(faster, slider, slower);
        horizontalBox2.getChildren().addAll(generateButton, sortButton, slideBox);
        horizontalBox2.setPadding(new Insets(20, 20, 20, 20));
        bottomPane.getChildren().addAll(horizontalBox2);
        borderPane.setBottom(bottomPane);
        topBox.setAlignment(Pos.BASELINE_CENTER);

        mainStage.setTitle("Insertion Sorter");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();

        sortActionButton();
        generateNodeActionButton();
    }


    /**
     * This method helps with the visual part of the node swapping that is done from the Insertion Sort Algorithm
     * Accepts 2 node parameters of type StackPane which will be swapped if needed
     * Accepts a list of StackPane Objects where each element represents a single node that is up to be swapped
     * Accepts the speed value of how fast the visualization will be shown
     *
     * Uses the TranslateTransion and ParallelTransition classes, those classes come from JavaFx Animation that are used
     * to display the animation when the nodes are swapping between each other
     *
     * @param firstNode
     * @param secondNode
     * @param list
     * @param speed
     * @return
     */
    public static ParallelTransition nodeSwapping(StackPane firstNode, StackPane secondNode, ArrayList<StackPane> list, double speed) {

        TranslateTransition forwardTransition = new TranslateTransition();
        TranslateTransition backTransition = new TranslateTransition();

        forwardTransition.setDuration(Duration.millis(speed));
        backTransition.setDuration(Duration.millis(speed));

        ParallelTransition parallelSwapping = new ParallelTransition();

        forwardTransition.setNode(firstNode);
        backTransition.setNode(secondNode);
        forwardTransition.setByX(60);
        backTransition.setByX(-60);
        parallelSwapping.getChildren().addAll(forwardTransition, backTransition);

        Collections.swap(list, list.indexOf(firstNode), list.indexOf(secondNode));

        return parallelSwapping;
    }

    /**
     * This method generates a fixed size array with random values
     * @return
     */
    private static int[] arrayGenerator() {

        int[] array = new int[((List<StackPane>) Visualization.nodeList).size()];

        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.parseInt(((List<StackPane>) Visualization.nodeList).get(i).getId());
        }

        return array;
    }

    /**
     * This method generates the rectangle nodes that will be sorted by the algorithm in the visualization format
     * The node value will be assigned randomly with a value up to 10 using the Random Java Class
     */
    public static void generateSingleNodes() {

        for (int i = 0; i < 10; i++) {

            int nodeValue = random.nextInt(10);

            Rectangle node = new Rectangle(40, (nodeValue * 10) + 50);
            node.setFill(Color.valueOf("#64ae64"));

            Text text = new Text(String.valueOf(nodeValue));
            StackPane stackPane = new StackPane();

            stackPane.setPrefSize(node.getWidth(), node.getHeight());
            stackPane.setId(String.valueOf(nodeValue));
            stackPane.getChildren().addAll(node, text);

            nodeList.add(stackPane);
        }

    }

    /**
     * This method is used to start the sorting visualization on button cilck - Action Event Trigger
     */
    public static void sortActionButton(){

        sortButton.setOnAction(event -> {
            SequentialTransition transition;
            int[] array = arrayGenerator();
            transition = insertionSort(array, nodeList);

            sortButton.setDisable(true);
            slider.setDisable(true);
            transition.play();
            transition.setOnFinished(event1 -> sortButton.setDisable(false));
            sortButton.setDisable(false);
            slider.setDisable(false);
        });

    }

    /**
     * This method is used to trigger the action event to when generating a new node array - Action Event Trigger
     */
    public static void generateNodeActionButton(){

        generateButton.setOnAction(event -> {
            horizontalBox.getChildren().clear();
            nodeList.clear();
            generateSingleNodes();
            horizontalBox.getChildren().addAll(nodeList);
        });

    }
}
