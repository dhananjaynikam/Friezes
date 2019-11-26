package sample;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class Main extends Application {
    private Stage window = new Stage();
    private int height = 650;
    private int width = 900;
    public int canvasHeight = 300;
    public int canvasWidth = 300;
    public boolean firstTime = true;
    public boolean diagonalSelected=false;
    private int numberOfSides=0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setScene(getStartPageScene());
        window.setTitle("Assignment-3-Frieze");
        window.show();
    }

    public Scene getStartPageScene() {
        Scene startPageScene;

        BackgroundFill backgroundFill = new BackgroundFill(Color.TURQUOISE,new CornerRadii(0),new Insets(0,0,0,0));
        Background bck = new Background(backgroundFill);
        MenuBar menuBar = new MenuBar();

        Menu startMenu = new Menu("START");
        MenuItem algorithmicMenu = new MenuItem("ALGORITHMIC");
        MenuItem countingMethodMenu = new MenuItem("COUNTING METHOD");
        startMenu.getItems().addAll(algorithmicMenu,countingMethodMenu);
        Menu moreAbout = new Menu("More about project");
        MenuItem learn = new MenuItem("LEARN");
        moreAbout.getItems().addAll(learn);
        menuBar.getMenus().addAll(startMenu,moreAbout);
        menuBar.setStyle("-fx-font-size: 1.2em; -fx-background-color: #ffffff;-fx-border-style: solid inside;-fx-border-width: 2;-fx-border-insets: 5;-fx-border-radius: 5; -fx-border-color: blue; ");

       Scene algorithmicScene = getAlgorithmicScene();
//        Scene countingMethodScene;  //getCountingMethodScene();
//
        algorithmicMenu.setOnAction(e -> {
            window.setScene(algorithmicScene);
        });
//
//        countingMethodMenu.setOnAction(e -> {
//            window.setScene(countingMethodScene);
//        });

        learn.setOnAction(e -> {
            LearnWindow.display();
        });

        //Creating label for the centre pane
        Label introLabel = new Label("Friezes");
        introLabel.setStyle("-fx-font-size: 4em; -fx-background-color: #ffffff; ");
        VBox centreStartScene = new VBox();
        centreStartScene.setAlignment(Pos.CENTER);
        centreStartScene.setPadding(new Insets(20,10,10,10));
        introLabel.setWrapText(true);
        centreStartScene.getChildren().add(introLabel);

        //BorderPane layout to get all the layouts together
        BorderPane startPageLayout = new BorderPane();
        startPageLayout.setTop(menuBar);
        startPageLayout.setCenter(centreStartScene);
        startPageLayout.setBackground(bck);
        startPageScene = new Scene(startPageLayout,width,height);

        return startPageScene;
    }

    public Scene getAlgorithmicScene(){
        Scene algorithmicScene;
        HashMap<Integer, Color> colorHashMap = new HashMap<>();
        colorHashMap.put(1,Color.RED);
        colorHashMap.put(2,Color.GREEN);
        colorHashMap.put(3,Color.BLUE);
        AtomicInteger diagonalCounter = new AtomicInteger(0);
        ArrayList<Point> vertexList = new ArrayList<>();
        ArrayList<ArrayList<Point>> edgeList = new ArrayList<>();
        ArrayList<ArrayList<Point>> diagonalList = new ArrayList<>();
        ArrayList<Point> startingPoints = new ArrayList<>();
        ArrayList<GraphEdgeList> graph = new ArrayList<>();

        Insets insets = new Insets(10);
        String label = "1 2 3 4 5 6 7 8 9 10 1 2 3 4 5 6 7 8 9 10\n"+
                "1 2 3 4 5 6 7 8 9 10 1 2 3 4 5 6 7 8 9 10\n"+
                "1 2 3 4 5 6 7 8 9 10 1 2 3 4 5 6 7 8 9 10\n"+
                "1 2 3 4 5 6 7 8 9 10 1 2 3 4 5 6 7 8 9 10\n"+
                "1 2 3 4 5 6 7 8 9 10 1 2 3 4 5 6 7 8 9 10\n"+
                "1 2 3 4 5 6 7 8 9 10 1 2 3 4 5 6 7 8 9 10\n"+
                "1 2 3 4 5 6 7 8 9 10 1 2 3 4 5 6 7 8 9 10\n"+
                "1 2 3 4 5 6 7 8 9 10 1 2 3 4 5 6 7 8 9 10\n"+
                "1 2 3 4 5 6 7 8 9 10 1 2 3 4 5 6 7 8 9 10\n";

        BorderPane borderPane = new BorderPane();
        Pane wrapperPane = new Pane();
        wrapperPane.setMaxHeight(canvasHeight);
        wrapperPane.setMaxWidth(canvasWidth);
        wrapperPane.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY)));
        VBox labelVBox = new VBox(10);
        Label label1 = new Label(label);
        Label label2 = new Label(label);
        Label label3 = new Label(label);
        TextField numberOfSidesTextField = new TextField();
        Label numberOfSidesLabel = new Label("Enter number of Sides of Polygon:");
        numberOfSidesTextField.setPromptText("3-10");
        Button generateFrieze = new Button("GENERATE FRIEZE");
        HBox topHBox = new HBox(10);
        topHBox.getChildren().addAll(numberOfSidesLabel,numberOfSidesTextField,generateFrieze);

        labelVBox.getChildren().addAll(label1,label2,label3);

        Canvas canvas = new Canvas(canvasWidth,canvasHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        draw(gc);
        wrapperPane.getChildren().add(canvas);
        borderPane.setPadding(new Insets(50,10,10,20));
        borderPane.setLeft(wrapperPane);
        borderPane.setCenter(labelVBox);
        borderPane.setTop(topHBox);
        BorderPane.setMargin(borderPane.getTop(),insets);
        BorderPane.setMargin(borderPane.getLeft(),insets);
        BorderPane.setMargin(borderPane.getCenter(),insets);
        BorderPane.setMargin(borderPane.getLeft(),insets);
        numberOfSidesTextField.focusedProperty().addListener(e -> {
            if(firstTime){
                topHBox.requestFocus();
                firstTime = false;
            }
        });

        numberOfSidesTextField.setOnAction(e -> {
            if(validateInput(numberOfSidesTextField.getText())){
                numberOfSides = Integer.parseInt(numberOfSidesTextField.getText());
                drawPolygon(numberOfSides,gc,vertexList,edgeList);
            }else{
                SelectionBox.display("ERROR", "Enter a valid number between 3 and 10", "OK", null);
            }
        });
        algorithmicScene = new Scene(borderPane, width,height);
        Point2D[] diagonalPoints = new Point2D[2];

        canvas.setOnMouseClicked(e -> {
            Point2D temp = new Point2D(e.getX(),e.getY());
            if(!diagonalSelected){
                for(Point item: vertexList) {
                    if (temp.distance(item.getCenter()) <= 15) {
                        if(diagonalPoints[0] == null){
                            diagonalPoints[0] = item.getCenter();
                            break;
                        }else {
                            diagonalPoints[1] = item.getCenter();
                            if(validateDiagonal(diagonalPoints,diagonalList)){
                                Point temp1 = new Point();
                                Point temp2 = new Point();
                                for(int i = 0;i< vertexList.size();i++){
                                    if(vertexList.get(i).getCenter().equals(diagonalPoints[0])) {
                                        temp1 = vertexList.get(i);
                                        break;
                                    }
                                }
                                for(int i = 0;i< vertexList.size();i++){
                                    if(vertexList.get(i).getCenter().equals(diagonalPoints[1])) {
                                        temp2 = vertexList.get(i);
                                        break;
                                    }
                                }
                                ArrayList<Point> tempDiagList = new ArrayList<>();
                                tempDiagList.add(temp1);
                                tempDiagList.add(temp2);
                                edgeList.add(tempDiagList);
                                diagonalList.add(tempDiagList);
                                gc.strokeLine(temp1.getCenter().getX(),temp1.getCenter().getY(),temp2.getCenter().getX(),temp2.getCenter().getY());
                                diagonalCounter.getAndIncrement();
                            }
                        }
                        diagonalPoints[0] = null;
                        diagonalPoints[1] = null;
                    }
                }
            }
            if(startingPoints.size() < 3 && diagonalSelected){
                for(Point item: vertexList){
                    if(temp.distance(item.getCenter()) <= 15){
                        if(startingPoints.contains(item)){
                            break;
                        }else {
                            startingPoints.add(item);
                            gc.setFill(colorHashMap.get(startingPoints.size()));
                            gc.fillOval(item.getCenter().getX() - 15, item.getCenter().getY() - 15, 30, 30);
                            gc.setFill(Color.BLACK);
                            break;
                        }
                    }
                }
            }
            if(diagonalCounter.get() == vertexList.size()-3){
                diagonalSelected = true;
            }
        });

        generateFrieze.setOnAction(e -> {
            if(!diagonalSelected){
                SelectionBox.display("ERROR","PLease complete all the steps to generate frieze", "OK",null);
            }else {
                initialiseGraph(edgeList,graph);
                int[] diagFrieze  = calculateFriezeAlgorithmic(graph,startingPoints);
                label1.setText(Arrays.toString(diagFrieze));
            }
        });

        return algorithmicScene;
    }

//    public Scene getCountingMethodScene(){
//        Scene countingMethodScene;
//
//        return countingMethodScene;
//    }

    private void draw(GraphicsContext gc){
        gc.strokeRect(0,0,canvasWidth,canvasHeight);
    }

    private boolean validateInput(String text){
        if(text.isEmpty()) return false;
        int number = Integer.parseInt(text);
        if(number >= 3 && number <=10) return true;

        return false;
    }

    private boolean validateDiagonal(Point2D[] points, ArrayList<ArrayList<Point>> diagonals){
        Point2D A = points[0];
        Point2D B = points[1];

        for(ArrayList<Point> item : diagonals){
            Point2D C = item.get(0).getCenter();
            Point2D D = item.get(1).getCenter();
            Point2D intersection = calculateIntersection(A,B,C,D);
            if(intersection.distance(new Point2D(150,150)) < 120)
                return false;
        }
        return true;
    }

    private Point2D calculateIntersection(Point2D A,Point2D B,Point2D C,Point2D D){
        double a1 = B.getY() - A.getY();
        double b1 = A.getX() - B.getX();
        double c1 = a1*(A.getX()) + b1*(A.getY());

        double a2 = D.getY() - C.getY();
        double b2 = C.getX() - D.getX();
        double c2 = a2*(C.getX())+ b2*(C.getY());

        double determinant = a1*b2 - a2*b1;

        double x = (b2*c1 - b1*c2)/determinant;
        double y = (a1*c2 - a2*c1)/determinant;
        return new Point2D(x, y);
    }

    private void initialiseGraph(ArrayList<ArrayList<Point>> edgeList, ArrayList<GraphEdgeList> graph) {
        //Storing only the vertex id since it is unique and we do not need any other reference to that vertex.
        //Can always get the vertex id from the graph and use vertexList or the edgeList for other purposes.
        for(int i =0; i <= numberOfSides; i++){
            graph.add(i,new GraphEdgeList());
        }
        for(int i =0; i < edgeList.size(); i++){
            GraphEdgeList.add(graph,edgeList.get(i).get(0).getId(),edgeList.get(i).get(1).getId());
        }
    }

    private int[] calculateFriezeAlgorithmic(ArrayList<GraphEdgeList> graph, ArrayList<Point> startingPoints) {
        int[] diagFrieze = new int[numberOfSides+1];
        ArrayList<Integer> marked = new ArrayList<>();
        Point start = startingPoints.get(0);
        GraphEdgeNode startListNode = graph.get(start.getId()).start;
        while(startListNode != null){
            startListNode.getDirectConnection().setData(0);
            startListNode = startListNode.getNextConnection();
        }
        marked.add(start.getId());
        diagFrieze[start.getId()] = 0;
        startListNode = graph.get(start.getId()).start;
        for(int i =1; i <=2; i++){
            GraphEdgeNode temp = graph.get(startListNode.getId()).start;
            marked.add(startListNode.getId());
            diagFrieze[startListNode.getId()] = 1;
            while(temp != null){
                temp.getDirectConnection().setData(1);
                temp = temp.getNextConnection();
            }
            startListNode = startListNode.getNextConnection();
        }
        while(marked.size() < numberOfSides) {
            for (int i = 1; i < graph.size(); i++) {
                if (marked.contains(i)) {
                    continue;
                } else {
                    int numberOfMarkedNodes = 0;
                    GraphEdgeNode temp = graph.get(i).start;
                    while (temp != null) {
                        if (temp.getData() == -1) {
                            temp = temp.getNextConnection();
                            continue;
                        } else {
                            numberOfMarkedNodes++;
                            temp = temp.getNextConnection();
                        }
                    }
                    if (numberOfMarkedNodes >= 2) {
                        GraphEdgeNode tempStart = graph.get(i).start;
                        int tempCount = 0;
                        int ithNodeData = 0;
                        while (tempStart != null) {
                            if (tempStart.getData() != -1 && tempCount < 2) {
                                tempCount++;
                                ithNodeData = ithNodeData + tempStart.getData();
                                tempStart = tempStart.getNextConnection();
                            } else {
                                tempStart = tempStart.getNextConnection();
                            }
                        }
                        marked.add(i);
                        diagFrieze[i] = ithNodeData;
                        tempStart = graph.get(i).start;
                        while (tempStart != null) {
                            tempStart.getDirectConnection().setData(ithNodeData);
                            tempStart = tempStart.getNextConnection();
                        }
                    }
                }
            }
        }

        return diagFrieze;
    }

    private void drawPolygon(int sides, GraphicsContext gc, ArrayList<Point> vertexList, ArrayList<ArrayList<Point>> edgeList){
        gc.clearRect(0,0,canvasWidth,canvasHeight);
        draw(gc);
        double centerX = 150;
        double centerY = 150;
        double radius = 125;
        double[] XPoints = new double[sides];
        double[] YPoints = new double[sides];
        final double angleStep = Math.PI*2 / sides;
        double angle = 0;
        for (int i = 0; i < sides; i++, angle -= angleStep) {
            XPoints[i] = Math.sin(angle) * radius + centerX; // x coordinate of the corner
            YPoints[i] = Math.cos(angle) * radius + centerY; // y coordinate of the corner
            vertexList.add(new Point(new Point2D(XPoints[i],YPoints[i]),i+1));
        }
        drawPoints(XPoints,YPoints,gc);
        gc.strokePolygon(XPoints,YPoints,sides);
        //make the edge list
        for(int i =0; i< vertexList.size();i++){
            ArrayList<Point> temp = new ArrayList<>();
            if(i == vertexList.size()-1){
                temp.add(vertexList.get(i));
                temp.add(vertexList.get(0));
            }else {
                temp.add(vertexList.get(i));
                temp.add(vertexList.get(i + 1));
            }
            edgeList.add(temp);
        }
    }

    private void drawPoints(double[] XPoints, double[] YPoints, GraphicsContext gc){
        for(int i =0; i<XPoints.length;i++){
            gc.fillOval(XPoints[i]-15,YPoints[i]-15,30,30);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
