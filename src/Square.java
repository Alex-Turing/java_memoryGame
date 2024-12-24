/**
 * @author Alexander Hernandez
 * Date July 13th, 2023
 * Memory Puzzle Game
 * Class Square
 */
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square {
    private ViewClass vc = new ViewClass();
    private int[] sampleGrid = new int[9];

    /**
     * Square Constructor
     */
    public Square() {
    }

    /**
     * Draws the square on a GraphicsContext object.
     * Uses a set of variables to build the grid color, such as:
     * 1. sqSide that is the rectangle (actually square) side to draw.
     * 2. intColor is the variable that comes from random number method in View Class
     * then injects that variable as an argument to the randomColor method to select
     * a specific color. At the same time uses that value to build the sampleGrid array.
     * 3. s is a variable that attaches the variables coming from the nested loops (i and j)
     * to the array index in order to push the intColor value in the specific sampleGrid array index
     * @param gc The GraphicsContext to draw on.
     */
    public void draw(GraphicsContext gc) {
        int sqSide = 100;
        int intColor = 0;
        int s = 0;
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                intColor = vc.getSelColor();
                sampleGrid[s+i+j] = intColor;
                Color color = vc.randomColor(intColor);
                gc.setStroke(color);
                gc.setFill(color);
                gc.fillRect(j*sqSide+45, i*sqSide+25, sqSide, sqSide);
            }
            s += 2 ;
        }
    }

    /**
     * This method only re-draw the grids on canvas.
     * @param gc
     */
    public void drawGrids(GraphicsContext gc){
        int x = 0;
        for(int i = 0; i < 4; i++){
            gc.setStroke(Color.BLACK);
            gc.strokeRect(45+x,25,2,300);
            x+=100;
        }
        int y = 0;
        for(int i = 0; i < 4; i++){
            gc.setStroke(Color.BLACK);
            gc.strokeRect(45,25 + y,300,2);
            y += 100;
        }
        int x1 = 400;
        for(int i = 0; i < 4; i++){
            gc.setStroke(Color.BLACK);
            gc.strokeRect(45 + x1,25,2,300);
            x1 += 100;
        }
        int y1 = 0;
        for(int i = 0; i < 4; i++){
            gc.setStroke(Color.BLACK);
            gc.strokeRect(445,25 + y1,300,2);
            y1 += 100;
        }
    }

    /**
     * This method draws the rectangles in the checkGrid. This method requires 4 arguments
     * to draw the rectangles; the first is count to set the position of the rectangle on
     * the grid. Second yPos is the y position. Third color is the color of the rectangle.
     * The last one is the GraphicsContext
     * @param count Sets the position of the rectangle on the grid.
     * @param yPos is the y position.
     * @param color is the color of the rectangle.
     * @param gc is the GraphicsContext.
     */
    public void drawGuesses(int count, double yPos, Color color, GraphicsContext gc){
        double xPos = 0;
        if(count == 1 || count == 4 || count == 7) xPos = 445;
        else if (count == 2 || count == 5 || count == 8) xPos = 545;
        else if (count == 3 || count == 6 || count == 9) xPos = 645;
        gc.setFill(color);
        gc.fillRect(xPos, yPos, 100, 100);
        drawGrids(gc);
    }

    /**
     * Getter method to get the sample grid and pushes it in the matchValidation
     * method in the Model Class.
     * @return
     */
    public int[] getSampleGrid() {
        return sampleGrid;
    }

    /**
     * This method prints the arrays, mainly for testing purposes in the console.
     * @param array to print.
     * @return
     */
    public String printValues(int[] array){
        String element = "";
        for(int a:array){
            element += String.valueOf(a) + " ";
        }
        return "[ " + element + "]";
    }
}
