import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @author Alexander Hernandez
 * Date July 13th, 2023
 * Memory Puzzle Game
 * Class ViewClass
 */
public class ViewClass extends Application {

    // TODO: Instance Variables for View Components and Model
    private TextField tf;
    private Button stpBtn;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn10;
    private Button checkButton;
    private Button rstButton;
    private Label watchLb;
    private GraphicsContext gc;
    private Square sq;
    private Model md = new Model();
    private int score, selColor, count = 0;
    private final int[] checkGrid = new int[9];

    // TODO: Private Event Handlers and Helper Methods

    /**
     * Reset method restarts the scenario, resets score, relocates elements
     * cleans label and FieldText.
     * @param e Event
     */
    private void resetHandler(ActionEvent e){
        md.setScore(0);
        watchLb.setText("");
        tf.setText("[Your Name]");
        rstButton.setVisible(false);
        cleanCanvas();
    }

    /**
     * This method stops the timer in the Model Class. Actually "the clock"
     * is only a method that pulls the time from the system (in ms)
     * Label prints the time elapsed between set the grid and stop the timer
     * cleans the canvas by re-drawing rects and grids
     * Hides and shows the specific buttons
     * @param e Event
     */
    private void stopWatch(ActionEvent e){
        md.stop();
        watchLb.setText(tf.getText() + ", Elapsed time is: " + md.getElapsedTime()/1000 + " sec");
        cleanCanvas();
        stpBtn.setVisible(false);
        checkButton.setVisible(true);
        showColorButtons(true);
    }

    /**
     * This method firstly checks if all the colors have been selected
     * in the check grid. If so, re-arrange the elements (show and hide)
     * on the GUI and set the score. If not, set the counter (count) at 0, to start
     * the check grid filling over, clean the canvas and re-arrange the elements
     * @param e Event
     */
    private void checkHandler(ActionEvent e){
        if(count < 9){
            watchLb.setTextFill(Color.RED);
            watchLb.setText("You must Select all the grid!");
            cleanCanvas();
            count = 0;
            showColorButtons(false);
            checkButton.setVisible(false);
        } else {
            checkButton.setVisible(false);
            rstButton.setVisible(true);
            System.out.println(sq.printValues(checkGrid));  //Testing purposes
            score = md.matchValidation(sq.getSampleGrid(), checkGrid) * 10 / (int)(md.getElapsedTime()/1000);
            watchLb.setTextFill(Color.GREEN);
            watchLb.setText("Your score: " + score);
        }

    }

    /**
     * This method sets the visibility of certain buttons as true or
     * false depending on the required layout. Main purpose i to reduce
     * lines of code in other methods.
     * @param hide Boolean visibility
     */
    private void showColorButtons(boolean hide){
        btn2.setVisible(hide);
        btn3.setVisible(hide);
        btn4.setVisible(hide);
        btn5.setVisible(hide);
        btn6.setVisible(hide);
        btn7.setVisible(hide);
        btn8.setVisible(hide);
        btn9.setVisible(hide);
        btn10.setVisible(hide);
    }

    /**
     * This method responses to the "Set the Grid / Play Again" button
     * hides and shows the set of buttons required, sets count at 0 to
     * start drawing the rectangles in the sample grid, the starts the Timer method
     * that actually only pulls the current time of the system in ms and siigns it to
     * a certain variable and most important, calls the method drawSquare to draw the
     * set of random Color squares on the sample grid.
     * @param e Event
     */
    private void myHandler(ActionEvent e) {
        showColorButtons(false);
        count = 0;
        stpBtn.setVisible(true);
        watchLb.setVisible(true);
        checkButton.setVisible(false);
        rstButton.setVisible(false);
        md.start();            //Starts the clock, by calling start() method in Model Class
        drawSquare();
        System.out.println(sq.printValues(sq.getSampleGrid()));    //Testing purposes
        tf.requestFocus(); //Cursor returns to this TextField once the circle is drawn
        watchLb.setTextFill(Color.BLACK);
        watchLb.setText("****** Ticking... ******");
    }

    /**
     * This method executes several functions:
     * 1. First takes 2 arguments: Color and an integer value that is
     * the corresponding value of a certain color.
     * 2. Builds the checkGrid array that is intended to compare the 2 grids.
     * 3. Increments the count that actually is the index position in the checkGrid array
     * if count variable is between 1 and 9 (inclusive), set the y position in the canvas
     * to draw the rectangles. Then draws the rectangles in the check Grid. When the count is 9
     * set the layout of the elements on the GUI.
     * @param color Color
     * @param value Color value from random color method.
     */
    private void colorButtonHandler(Color color, int value){
        checkGrid[count] = value;
        count += 1;
        if(count <= 9){
            if(count == 1 || count == 2 || count == 3) sq.drawGuesses(count, 25, color, gc);
            else if (count == 4 || count == 5 || count == 6) sq.drawGuesses(count, 125, color, gc);
            else {
                sq.drawGuesses(count, 225, color, gc);
                if(count == 9) showColorButtons(false);
            }
        }
    }

    /**
     * These following methods set the color and the value associated to that color
     * as arguments in the colorButtonHandler method to draw the rectangles in the checkGrid.
     * @param e Event
     */
    private void redHandler(ActionEvent e) {colorButtonHandler(Color.RED, 0);}
    private void blueHandler(ActionEvent e) {colorButtonHandler(Color.BLUE, 1);}
    private void greenHandler(ActionEvent e) {colorButtonHandler(Color.GREEN,2);}
    private void yellowHandler(ActionEvent e) {colorButtonHandler(Color.YELLOW,3);}
    private void coralHandler(ActionEvent e) {colorButtonHandler(Color.CORAL,4);}
    private void greyHandler(ActionEvent e) {colorButtonHandler(Color.GREY,5);}
    private void blueVioletHandler(ActionEvent e) {colorButtonHandler(Color.BLUEVIOLET,6);}
    private void cornFlowerHandler(ActionEvent e) {colorButtonHandler(Color.CORNFLOWERBLUE,7);}
    private void fuchsiaHandler(ActionEvent e) {colorButtonHandler(Color.FUCHSIA,8);}

    /**
     * This method randomly assigns a value between 0 and 8 to the variable
     * selColor and returns the value the method randomColor to select a
     * certain color to fill the rectangles. This same integer value is used
     * to fill the checkGrid array used to compare the colors.
     * @return integer number to push in the randomColor method.
     */
    public int getSelColor() {
        selColor = (int) Math.floor(Math.random() * 9);
        return selColor;
    }

    /**
     * Randomly assigns a color to the variable picked and returns it to the
     * sample and check grids.
     * @param color Rectangle color.
     * @return picked color.
     */
    public Color randomColor(int color){
        return switch (color) {
            case 0 -> Color.RED;
            case 1 -> Color.BLUE;
            case 2 -> Color.GREEN;
            case 3 -> Color.YELLOW;
            case 4 -> Color.CORAL;
            case 5 -> Color.GREY;
            case 6 -> Color.BLUEVIOLET;
            case 7 -> Color.CORNFLOWERBLUE;
            case 8 -> Color.FUCHSIA;
            default -> Color.BLACK;
        };
    }

    /**
     * This method draws the background lightblue rectangles,
     * the grids and the color on the sample grid.
     */
    private void drawSquare(){
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(5, 5, 390, 370);
        gc.fillRect(400, 5, 790, 370);
        sq.draw(gc);
        sq.drawGrids(gc);
    }

    /**
     * This method cleans the canvas by re-drawing the blueligth rectangles
     * and grids.
     */
    public void cleanCanvas(){
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(5, 5, 390, 370);
        gc.fillRect(400, 5, 790, 370);
        sq.drawGrids(gc);
    }

    /**
     * This is where you create your components and the model and add event
     * handlers.
     *
     * @param stage The main stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 650); // set the size here
        stage.setTitle("Memory Puzzle"); // set the window title here
        stage.setScene(scene);

        // TODO: Add your GUI-building code here

        // 1. Create the model
        sq = new Square();      //Initializes the Object sq type Square which is the model of the project

        // 2. Create the GUI components
        tf = new TextField("[Your Name]");      //TextField comes from a library
        Button btn = new Button("Set the Grid / Play Again");
        stpBtn = new Button("Stop Clock");
        watchLb = new Label();
        Label instructionLabel = new Label("""
                Intructions:\s
                1. Enter Your name.
                2. Click on the "Set the Grid / Play Again" Button.
                3. Memorize the Color grid in the shortest possible time. Once you ready, click on "Stop timer" to freeze your time
                4. Click on the color button desired. The grid will be automatically filled in order starting at the top-left corner
                5. Once your grid is filled, click on "Check Button". To play Again, click on "Set the Grid / Play Again" Button.
                6. To quit, click on "Reset" Button.""");
        Canvas c = new Canvas(800, 350);
        gc = c.getGraphicsContext2D();
        btn2 = new Button();
        btn3 = new Button();
        btn4 = new Button();
        btn5 = new Button();
        btn6 = new Button();
        btn7 = new Button();
        btn8 = new Button();
        btn9 = new Button();
        btn10 = new Button();
        checkButton = new Button("Check Button");
        rstButton = new Button("Reset");

        // 3. Add components to the root
        root.getChildren().addAll(tf, btn, c, watchLb, stpBtn, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, checkButton, rstButton, instructionLabel); //add elements to the text field I just created in line 33

        // 4. Configure the components (colors, fonts, size, location)
        tf.relocate(10,430);
        tf.setPrefWidth(180);

        btn.relocate(70,370);
        btn.setPrefWidth(250);

        btn2.relocate(410,370);
        btn2.setPrefWidth(60);
        btn2.setBackground(Background.fill(Color.RED));
        btn2.setVisible(false);

        btn3.relocate(490,370);
        btn3.setPrefWidth(60);
        btn3.setBackground(Background.fill(Color.BLUE));
        btn3.setVisible(false);

        btn4.relocate(570,370);
        btn4.setPrefWidth(60);
        btn4.setBackground(Background.fill(Color.GREEN));
        btn4.setVisible(false);

        btn5.relocate(650,370);
        btn5.setPrefWidth(60);
        btn5.setBackground(Background.fill(Color.YELLOW));
        btn5.setVisible(false);

        btn6.relocate(730,370);
        btn6.setPrefWidth(60);
        btn6.setBackground(Background.fill(Color.CORAL));
        btn6.setVisible(false);

        btn7.relocate(450,430);
        btn7.setPrefWidth(60);
        btn7.setBackground(Background.fill(Color.GREY));
        btn7.setVisible(false);

        btn8.relocate(530,430);
        btn8.setPrefWidth(60);
        btn8.setBackground(Background.fill(Color.BLUEVIOLET));
        btn8.setVisible(false);

        btn9.relocate(610,430);
        btn9.setPrefWidth(60);
        btn9.setBackground(Background.fill(Color.CORNFLOWERBLUE));
        btn9.setVisible(false);

        btn10.relocate(690,430);
        btn10.setPrefWidth(60);
        btn10.setBackground(Background.fill(Color.FUCHSIA));
        btn10.setVisible(false);

        checkButton.relocate(550, 470);
        checkButton.setPrefWidth(100);
        checkButton.setVisible(false);

        stpBtn.relocate(200, 430);
        stpBtn.setVisible(false);

        watchLb.relocate(70, 470);
        watchLb.setFont(new Font("Calibri",20));
        watchLb.setVisible(false);

        rstButton.relocate(550, 400);
        rstButton.setPrefWidth(100);
        rstButton.setVisible(false);

        instructionLabel.relocate(10,500);

        // 5. Add Event Handlers and do final setup
        btn.setOnAction(this::myHandler);
        stpBtn.setOnAction(this::stopWatch);
        btn2.setOnAction(this::redHandler);
        btn3.setOnAction(this::blueHandler);
        btn4.setOnAction(this::greenHandler);
        btn5.setOnAction(this::yellowHandler);
        btn6.setOnAction(this::coralHandler);
        btn7.setOnAction(this::greyHandler);
        btn8.setOnAction(this::blueVioletHandler);
        btn9.setOnAction(this::cornFlowerHandler);
        btn10.setOnAction(this::fuchsiaHandler);
        checkButton.setOnAction(this::checkHandler);
        rstButton.setOnAction(this::resetHandler);

        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(5, 5, 390, 370);
        gc.fillRect(400, 5, 790, 370);
        sq.drawGrids(gc);

        // 6. Show the stage
        stage.show();
    }

    /**
     * Make no changes here.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }
}