package sample;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LearnWindow {

    public static void displayCountingAlgo() {
        Stage window = new Stage();

        final String data = "Friezes \n"+
                "Counting Algorithm\n"+
                "The starting vertex is marked with a red colour\n"+
                "For each vertex count the number of triangles and mark that vertex with the number of triangles it makes\n"+
                "From the starting vertex go clockwise making the 2nd row of the frieze\n"+
                "The first and last row will be of 1's only\n"+
                "Use the formula a*d = b*c + 1 for calculating all the other rows";

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("LEARN MORE");
        Label label = new Label(data);
        label.setWrapText(true);
        label.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 20; -fx-text-fill: darkred;");
        StackPane layout = new StackPane();
        layout.getChildren().add(label);
        window.setScene(new Scene(layout,900,700));
        window.showAndWait();
    }

    public static void displayFlowerAlgo() throws FileNotFoundException {
        Stage window = new Stage();

        final String data ="Friezes \n"+
                "Flower Pattern\n"+
                "The starting vertex is marked with a red colour in the left canvas\n"+
                "The flower pattern is made using the polygon made in the left canvas.\n"+
                "The first 2 polygons on the inside are already marked as 1's and the second row generated by using the counting method.\n"+
                "The user has to enter the values of the next polygon vertexes using the formula:  c = (a*d - 1)/b \n"+
                "The generic formula is a*d = b*c + 1 for calculating all the other rows\n"+
                "Use the following figure for reference\n"+
                "Image Courtesy: Prof. Dr. Sandra Hedetneimi - Clemson University";


        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("LEARN MORE");
        // window.setMaxWidth(800);
        //  window.setMaxHeight(800);

        Label label = new Label(data);
        label.setWrapText(true);
        label.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 20; -fx-text-fill: darkred;");
        Image image = new Image(new FileInputStream(new File(Main.class.getResource("Examples_flower.png").getFile())));
        ImageView img = new ImageView(image);
        BorderPane layout = new BorderPane();
        layout.setTop(label);
        layout.setBottom(img);
        window.setScene(new Scene(layout,900,700));
        window.showAndWait();
    }

    public static void displayAlgorithmic(){
        Stage window = new Stage();

        final String data = "Friezes \n"+
                "Algorithmic Technique\n"+
                "1. Pick an arbitrary vertex with which to start and associate label = 0.\n"+
                "2. Label any vertices directly connected to that vertex with label = 1.\n"+
                "3. Then choose any unlabeled  vertex in a triangle such that it’s other two vertices\n"+
                "have already been associated with label i and label j and apply the label i+j.\n"+
                "4. Now begin the frieze pattern by placing a border row of 1’s on the top and a row of 1’s on the bottom\n"+
                "5. Begin with the vertex labeled 0 and proceed clockwise around the polygon placing all non-zero\n"+
                "labels down the “diagonal”, using the 1 in the top row as the 1 adjacent to the 0 in clockwise order\n"+
                "and the 1 in the bottom row as the 1 adjacent to the 0 in counterclockwise order.\n"+
                "6. Using this diagonal of numbers, the inﬁnite frieze is determined.\n"+
                "-Prof. Dr. Sandra Hedetneimi, Clemson University";

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
