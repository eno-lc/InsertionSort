package InsertionSort;

import javafx.animation.SequentialTransition;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;
import static InsertionSort.Visualization.*;

public class Algorithm {


    /**
     * This method represents the Insertion Sort Algorithm which will be visualized by using the SequentialTransition JavaFx Animation Class
     * Accepts an array with integer values that represents the node array
     * Accepts a list of StackPane objects that represent the visual part of the node array
     * @param generatedNodesArray
     * @param list
     * @return
     */
    public static SequentialTransition insertionSort(int[] generatedNodesArray, ArrayList<StackPane> list) {

        SequentialTransition transition = new SequentialTransition();

        int temporaryValue;

        for (int i = 1; i < generatedNodesArray.length; i++) {
            for (int j = i; j > 0; j--) {
                if (generatedNodesArray[j] < generatedNodesArray[j - 1]) {
                    temporaryValue = generatedNodesArray[j];
                    generatedNodesArray[j] = generatedNodesArray[j - 1];
                    generatedNodesArray[j - 1] = temporaryValue;
                    transition.getChildren().add(nodeSwapping(list.get(j - 1), list.get(j), list, sortingSpeed));
                }
            }
        }
        return transition;
    }
}
