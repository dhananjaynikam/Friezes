package sample;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LearnWindow {

    public static void display() {
        Stage window = new Stage();

        final String data = "Pixar In a Box \n" +
                "- By Khan Academy & Pixar\n" +
                "-Environment modelling module of the tutorial has been implemented in the program in 2D for character modelling\n"+
                "-Pixar in a Box is a behind-the-scenes look at how Pixar artists do their jobs\n"+
                "-The program here extracts a few concepts from the tutorial and applies them in the real world, using Advanced Data Structures\n" +
                "-The areas of animation displayed here are String art, Parabolic Curve and Polygon modeling\n"+
                "-String Art is used to create a rendition of curves using the midpoints of two connected lines joined together in a logical manner\n"+
                "-Use the Parabolic Curve option in the start menu of the program.\n"+
                "-Parabolic curve is a rendition of a curve which can be made look like real curved objects like a grass using different widths and colors and moving the control points can help animating the scene.\n"+
                "-Use the Parabolic Curve option in the start menu of the program.\n"+
                "-The polygon modelling is a example of making shapes look like characters in animated movies, using the split and average logic creates a curved convex shape which can be later converted to new shapes using control points\n"+
                "-Use the Polygon option in the start menu of the program to explore the character modelling\n"+
                "Learn More at https://www.khanacademy.org/partner-content/pixar";

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("LEARN MORE");
       // window.setMaxWidth(800);
      //  window.setMaxHeight(800);

        Label label = new Label(data);
        label.setWrapText(true);
        label.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 20; -fx-text-fill: darkred;");
        StackPane layout = new StackPane();
        layout.getChildren().add(label);

        window.setScene(new Scene(layout,900,700));
        window.showAndWait();



    }
}
