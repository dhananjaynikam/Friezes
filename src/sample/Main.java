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

import java.io.FileNotFoundException;
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
    int[] flowerFrieze;

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
        Menu countingMethodMenu = new Menu("COUNTING METHOD");
        MenuItem flower = new MenuItem("FLOWER TECHNIQUE ");
        MenuItem linearFrieze = new MenuItem("LINEAR FRIEZE");
        countingMethodMenu.getItems().addAll(flower,linearFrieze);

        startMenu.getItems().addAll(algorithmicMenu,countingMethodMenu);
        Menu moreAbout = new Menu("More about project");
        Menu learn = new Menu("LEARN");
        MenuItem learnFlower = new MenuItem("Flower Pattern");
        MenuItem learnLinearCounting = new MenuItem("Counting Method");
        MenuItem learnAlgorithmic = new MenuItem("Algorithmic");
        moreAbout.getItems().addAll(learn);
        menuBar.getMenus().addAll(startMenu,moreAbout);
        menuBar.setStyle("-fx-font-size: 1.2em; -fx-background-color: #ffffff;-fx-border-style: solid inside;-fx-border-width: 2;-fx-border-insets: 5;-fx-border-radius: 5; -fx-border-color: blue; ");
        learn.getItems().addAll(learnAlgorithmic,learnLinearCounting,learnFlower);
        Scene algorithmicScene = getAlgorithmicScene();
        Scene countingMethodScene = getCountingMethodScene();
        Scene flowerPatternScene = getFlowerPatternScene();
//
        algorithmicMenu.setOnAction(e -> {
            window.setScene(algorithmicScene);
        });

        linearFrieze.setOnAction(e -> {
            window.setScene(countingMethodScene);
        });

        flower.setOnAction(e -> {
            window.setScene(flowerPatternScene);
        });

        learnAlgorithmic.setOnAction(e -> {
            LearnWindow.displayAlgorithmic();
        });
        learnFlower.setOnAction(e -> {
            try {
                LearnWindow.displayFlowerAlgo();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        learnLinearCounting.setOnAction(e -> {
            LearnWindow.displayCountingAlgo();
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

        BorderPane borderPane = new BorderPane();
        Pane wrapperPane = new Pane();
        wrapperPane.setMaxHeight(canvasHeight);
        wrapperPane.setMaxWidth(canvasWidth);
        wrapperPane.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY)));
        VBox labelVBox = new VBox(10);
        Label label1 = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
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
        draw(canvas);
        wrapperPane.getChildren().add(canvas);

        Button home = new Button("HOME");
        Button clear = new Button("CLEAR");
        topHBox.getChildren().addAll(clear,home);
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
                drawPolygon(numberOfSides,canvas,vertexList,edgeList);
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
                ArrayList<int[]> friezes = new ArrayList<>();
                for(Point item : startingPoints) {
                    int[] diagFrieze  = calculateFriezeAlgorithmicDiag(graph,item);
                    friezes.add(calculateFriezeAlgorithmic(diagFrieze, item.getId()));
                }
                ArrayList<String> friezesFinal = new ArrayList<>();
                for(int[] item : friezes){
                    friezesFinal.add(getFinalFriezeFormation(item));
                }
                label1.setText("Red Frieze=\n"+friezesFinal.get(0));
                label2.setText("Green Frieze=\n"+friezesFinal.get(1));
                label3.setText("Blue Frieze=\n"+friezesFinal.get(2));
            }
        });

        clear.setOnAction(e -> {
            label1.setText("");
            label2.setText("");
            label3.setText("");
            startingPoints.clear();
            graph.clear();
            edgeList.clear();
            diagonalList.clear();
            firstTime = true;
            diagonalSelected=false;
            numberOfSides=0;
            numberOfSidesTextField.clear();
            gc.clearRect(0,0,canvasWidth,canvasHeight);
        });

        home.setOnAction(e -> {
            label1.setText("");
            label2.setText("");
            label3.setText("");
            startingPoints.clear();
            graph.clear();
            edgeList.clear();
            diagonalList.clear();
            firstTime = true;
            diagonalSelected=false;
            numberOfSides=0;
            numberOfSidesTextField.clear();
            window.setScene(getStartPageScene());
            gc.clearRect(0,0,canvasWidth,canvasHeight);
        });

        return algorithmicScene;
    }

    public Scene getCountingMethodScene(){
        Scene countingMethodScene;
        AtomicInteger diagonalCounter = new AtomicInteger(0);
        ArrayList<Point> vertexList = new ArrayList<>();
        ArrayList<ArrayList<Point>> edgeList = new ArrayList<>();
        ArrayList<ArrayList<Point>> diagonalList = new ArrayList<>();
        ArrayList<GraphEdgeList> graph = new ArrayList<>();

        Insets insets = new Insets(10);

        BorderPane borderPane = new BorderPane();
        Pane wrapperPane = new Pane();
        wrapperPane.setMaxHeight(canvasHeight);
        wrapperPane.setMaxWidth(canvasWidth);
        wrapperPane.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY)));
        VBox labelVBox = new VBox(10);
        Label label1 = new Label();
        TextField numberOfSidesTextField = new TextField();
        Label numberOfSidesLabel = new Label("Enter number of Sides of Polygon:");
        numberOfSidesTextField.setPromptText("3-10");
        Button generateFrieze = new Button("GENERATE FRIEZE");
        HBox topHBox = new HBox(10);
        topHBox.getChildren().addAll(numberOfSidesLabel,numberOfSidesTextField,generateFrieze);
        Button home = new Button("HOME");
        Button clear = new Button("CLEAR");
        topHBox.getChildren().addAll(clear,home);
        labelVBox.getChildren().addAll(label1);

        Canvas canvas = new Canvas(canvasWidth,canvasHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        draw(canvas);
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
                drawPolygon(numberOfSides,canvas,vertexList,edgeList);
            }else{
                SelectionBox.display("ERROR", "Enter a valid number between 3 and 10", "OK", null);
            }
        });
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
            if(diagonalCounter.get() == vertexList.size()-3){
                diagonalSelected = true;
                gc.setFill(Color.RED);
                gc.fillOval(edgeList.get(0).get(0).getCenter().getX()-15,edgeList.get(0).get(0).getCenter().getY()-15,30,30);
                gc.setFill(Color.BLACK);
            }
        });

        generateFrieze.setOnAction(e -> {
            if(!diagonalSelected){
                SelectionBox.display("ERROR","PLease complete all the steps to generate frieze", "OK",null);
            }else {
                initialiseGraph(edgeList, graph);
                Point startingPoint = edgeList.get(0).get(0);
                int[] startFrieze  = calculateFriezeCountingStart(graph,startingPoint);
                int[] frieze = calculateFriezeCounting(startFrieze, startingPoint.getId());
                label1.setText("The Freize starting from RED point using counting method is:\n" + getFinalFriezeFormation(frieze));
            }
        });

        clear.setOnAction(e -> {
            label1.setText("");
            graph.clear();
            edgeList.clear();
            diagonalList.clear();
            firstTime = true;
            diagonalSelected=false;
            numberOfSides=0;
            numberOfSidesTextField.clear();
            gc.clearRect(0,0,canvasWidth,canvasHeight);
        });

        home.setOnAction(e -> {
            label1.setText("");
            graph.clear();
            edgeList.clear();
            diagonalList.clear();
            firstTime = true;
            diagonalSelected=false;
            numberOfSides=0;
            numberOfSidesTextField.clear();
            window.setScene(getStartPageScene());
            gc.clearRect(0,0,canvasWidth,canvasHeight);
        });

        countingMethodScene = new Scene(borderPane,width,height);
        return countingMethodScene;
    }

    private Scene getFlowerPatternScene() {
        Scene flowerPatternScene;
        AtomicInteger diagonalCounter = new AtomicInteger(0);
        ArrayList<Point> vertexList = new ArrayList<>();
        ArrayList<ArrayList<Point>> edgeList = new ArrayList<>();
        ArrayList<ArrayList<Point>> diagonalList = new ArrayList<>();
        ArrayList<GraphEdgeList> graph = new ArrayList<>();

        Insets insets = new Insets(10);

        BorderPane borderPane = new BorderPane();
        Pane wrapperPane = new Pane();
        wrapperPane.setMaxHeight(canvasHeight);
        wrapperPane.setMaxWidth(canvasWidth);
        wrapperPane.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY)));
        Pane wrapperPaneFlower = new Pane();
        wrapperPaneFlower.setMaxHeight(450);
        wrapperPaneFlower.setMaxWidth(450);
        wrapperPaneFlower.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY)));
        Canvas canvasFlower = new Canvas(450,450);
        TextField numberOfSidesTextField = new TextField();
        Label numberOfSidesLabel = new Label("Enter number of Sides of Polygon:");
        numberOfSidesTextField.setPromptText("3-10");
        Button generateFrieze = new Button("GENERATE FRIEZE");
        HBox topHBox = new HBox(10);
        topHBox.getChildren().addAll(numberOfSidesLabel,numberOfSidesTextField,generateFrieze);
        Button home = new Button("HOME");
        Button clear = new Button("CLEAR");
        topHBox.getChildren().addAll(clear,home);
        wrapperPaneFlower.getChildren().addAll(canvasFlower);
        GraphicsContext gc2 = canvasFlower.getGraphicsContext2D();
        draw(canvasFlower);
        Canvas canvas = new Canvas(canvasWidth,canvasHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        draw(canvas);
        wrapperPane.getChildren().add(canvas);
        borderPane.setPadding(new Insets(50,10,10,20));
        borderPane.setLeft(wrapperPane);
        borderPane.setRight(wrapperPaneFlower);
        borderPane.setTop(topHBox);
        BorderPane.setMargin(borderPane.getTop(),insets);
        BorderPane.setMargin(borderPane.getLeft(),insets);
        BorderPane.setMargin(borderPane.getRight(),insets);
        BorderPane.setMargin(borderPane.getLeft(),insets);

        numberOfSidesTextField.focusedProperty().addListener(e -> {
            if(firstTime){
                topHBox.requestFocus();
                firstTime = false;
            }
        });

        ArrayList<TextField> textFields = new ArrayList<>();
        ArrayList<Point2D> textFieldPoints = new ArrayList<>();

        numberOfSidesTextField.setOnAction(e -> {
            if(validateInput(numberOfSidesTextField.getText())){
                numberOfSides = Integer.parseInt(numberOfSidesTextField.getText());
                drawPolygon(numberOfSides,canvas,vertexList,edgeList);
                double centerX = 225;
                double centerY = 225;
                double radius = 80;
                double[] XPoints = new double[numberOfSides];
                double[] YPoints = new double[numberOfSides];
                final double angleStep = Math.PI*2 / numberOfSides;
                double angle = 0;
                for(int k = 1; k < numberOfSides; k++) {
                    for (int i = 0; i < numberOfSides; i = i + 1, angle += angleStep) {
                        XPoints[i] = Math.sin(angle) * radius + centerX; // x coordinate of the corner
                        YPoints[i] = Math.cos(angle) * radius + centerY; // y coordinate of the corner
                        textFieldPoints.add(new Point2D(XPoints[i],YPoints[i]));
                    }
                    gc2.strokePolygon(XPoints,YPoints,numberOfSides);
                    angle = angle + angleStep/2;
                    double asec = 1/Math.cos(Math.PI/numberOfSides);
                    radius = radius*asec;
                }
                for(int i = 0; i < textFieldPoints.size(); i++){
                    textFields.add(new TextField());
                    Point2D temp = textFieldPoints.get(i);
                    textFields.get(i).setLayoutX(temp.getX());
                    textFields.get(i).setLayoutY(temp.getY());
                    textFields.get(i).setPrefWidth(25);
                }
                wrapperPaneFlower.getChildren().addAll(textFields);
                textFields.forEach(tf -> {
                    tf.textProperty().addListener((obs, oldVal, newVal) -> {
                        int tempIndex = textFields.indexOf(tf);
                        if(Integer.parseInt(newVal) != flowerFrieze[tempIndex+1]){
                            SelectionBox.display("ERROR","Please follow the example in learning tab. (Formula:  a * d = b * c + 1)", "OK", null);
                            tf.clear();
                        }else{
                            tf.setEditable(false);
                        }
                    });
                });
            }else{
                SelectionBox.display("ERROR", "Enter a valid number between 3 and 10", "OK", null);
            }
        });

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
            if(diagonalCounter.get() == vertexList.size()-3){
                diagonalSelected = true;
                gc.setFill(Color.RED);
                gc.fillOval(edgeList.get(0).get(0).getCenter().getX()-15,edgeList.get(0).get(0).getCenter().getY()-15,30,30);
                gc.setFill(Color.BLACK);
            }
        });

        generateFrieze.setOnAction(e -> {
            if(!diagonalSelected){
                SelectionBox.display("ERROR","PLease complete all the steps to generate frieze", "OK",null);
            }else {
                initialiseGraph(edgeList, graph);
                Point startingPoint = edgeList.get(0).get(0);
                int[] startFrieze  = calculateFriezeCountingStart(graph,startingPoint);
                flowerFrieze = calculateFriezeCounting(startFrieze, startingPoint.getId());
                System.out.println(Arrays.toString(flowerFrieze));
                for(int i = 0; i< numberOfSides*2; i++){
                    textFields.get(i).setText(Integer.toString(flowerFrieze[i+1]));
                    textFields.get(i).setEditable(false);
                }
            }
        });

        clear.setOnAction(e -> {
            graph.clear();
            edgeList.clear();
            diagonalList.clear();
            firstTime = true;
            diagonalSelected=false;
            numberOfSides=0;
            numberOfSidesTextField.clear();
            gc.clearRect(0,0,canvasWidth,canvasHeight);
            gc2.clearRect(0,0,450,450);
        });

        home.setOnAction(e -> {
            graph.clear();
            edgeList.clear();
            diagonalList.clear();
            firstTime = true;
            diagonalSelected=false;
            numberOfSides=0;
            numberOfSidesTextField.clear();
            window.setScene(getStartPageScene());
            gc.clearRect(0,0,canvasWidth,canvasHeight);
            gc2.clearRect(0,0,450,450);
        });

        flowerPatternScene = new Scene(borderPane, width,height);
        return flowerPatternScene;
    }

    private void draw(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokeRect(0,0,canvas.getWidth(),canvas.getHeight());
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

    private int[] calculateFriezeAlgorithmicDiag(ArrayList<GraphEdgeList> graph, Point start) {
        int[] diagFrieze = new int[numberOfSides+1];
        ArrayList<Integer> marked = new ArrayList<>();
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

    private int[] calculateFriezeAlgorithmic(int[] diagFrieze, int startPoint ) {
        int[] frieze = new int[numberOfSides*(numberOfSides-1)+1];
        for(int k =0; k <= numberOfSides; k++){
            frieze[k] = 1;
        }
        for(int k =frieze.length-numberOfSides; k < frieze.length; k++){
            frieze[k] = 1;
        }
        int i =startPoint+1;
        int j =1;
        while(i != startPoint){
            if(i == numberOfSides){
                frieze[j] = diagFrieze[i];
                i = 1;
            }else {
                frieze[j] = diagFrieze[i];
                i++;
            }
            j = j+numberOfSides;
        }

        for(int k = numberOfSides+2; k <= numberOfSides*2;k++){
            for(int l = k; l < frieze.length-numberOfSides;l = l+numberOfSides){
                int a = frieze[l-1];
                int b = frieze[l-numberOfSides];
                int c = frieze[l+numberOfSides-1];
                int d = (b*c+1)/a;
                frieze[l] = d;
            }
        }
        return frieze;
    }

    private String getFinalFriezeFormation(int[] item) {
        StringBuilder frieze = new StringBuilder();
        int rowCounter = 0;
        for(int i = 0; i<=item.length-numberOfSides;i=i+numberOfSides){
            for(int l = rowCounter; l > 0; l--){
                frieze.append("-");
            }
            for(int k =i; k < i+numberOfSides;k++){
                frieze.append(item[k]);
                frieze.append(" ");
            }
            for(int k =i; k < i+numberOfSides;k++){
                frieze.append(item[k]);
                frieze.append(" ");
            }
            for(int l = numberOfSides-rowCounter-2; l > 0; l--){
                frieze.append("-");
            }
            frieze.append("\n");
            rowCounter++;
        }
        return frieze.toString();
    }

    private int[] calculateFriezeCountingStart(ArrayList<GraphEdgeList> graph, Point startingPoint) {
        //write the algorithm here
        //return the initial frieze in array
        int[] startFrieze = new int[numberOfSides+1];
        for(int i =1; i< graph.size(); i++){
            int size = graph.get(i).size;
            startFrieze[i] = size-1;
        }
        return startFrieze;
    }

    private int[] calculateFriezeCounting(int[] startFrieze, int startingPointId) {
        int[] frieze = new int[numberOfSides*(numberOfSides-1)+1];
        for(int k =1; k <= numberOfSides; k++){
            frieze[k] = 1;
        }
        for(int k =frieze.length-numberOfSides; k < frieze.length; k++){
            frieze[k] = 1;
        }
        for(int k = 1;k < startFrieze.length;k++){
            frieze[k+numberOfSides] = startFrieze[k];
        }
        int prev1 = frieze[1];
        int prev2 = startFrieze[1];
        for(int i =numberOfSides*2+1; i < frieze.length;i++){
            int a,b,c,d;
            if(i % numberOfSides == 0){
                b = prev1;
                d = prev2;
                a = frieze[i-numberOfSides];
                prev1 = prev2;
                prev2 = frieze[i-numberOfSides+1];
            }else{
                a = frieze[i - numberOfSides];
                d = frieze[i - numberOfSides+1];
                b = frieze[i - numberOfSides+1-numberOfSides];
            }
            c = (a*d-1)/b;
            frieze[i] = c;
        }

        return frieze;
    }

    private void drawPolygon(int sides, Canvas canvas, ArrayList<Point> vertexList, ArrayList<ArrayList<Point>> edgeList){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        draw(canvas);
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
