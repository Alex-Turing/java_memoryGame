/**
 * @author Alexander Hernandez
 * Date July 13th, 2023
 * Memory Puzzle Game
 * Class Model
 */
public class Model {
    private long startTime, endTime = -1;
    private int score = 0;

    /**
     * This method takes the current time of the system in ms and
     * assigns it to the variable startTime
     */
    public void start() {
        startTime = System.currentTimeMillis();
        endTime = -1;
    }
    /**
     * THis method takes the current time of the system in ms and
     * assigns it to the variable endTime
     */
    public void stop() { endTime = System.currentTimeMillis();}

    /**
     * This method returns the time elapsed from clicking set radius button
     * and stop watch button by substracting endTime and startTime
     * @return
     */
    public long getElapsedTime() {
        if (endTime > 0) {
            return endTime - startTime;
        }
        return -1;
    }

    /**
     * Getter methods to startTime and endTime
     * @return
     */
    public long getStartTime() { return startTime;}
    public long getEndTime() { return endTime;}

    /**
     * Getter method to get the score depending on how many guesses
     * the user does. Then this score is computing with the time variable
     * to get a final score.
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * This method compares the two arrays in the sampleGrid and the checkGrid.
     * Everytime the loop for matches a value, increments the variable score by one
     * @param sampleArray is the array randomly generated.
     * @param checkArray is the array filled by the user.
     * @return the score.
     */
    public int matchValidation(int[] sampleArray, int[] checkArray){
        for(int i = 0; i < sampleArray.length; i++){
            if(sampleArray[i] == checkArray[i]) score+=1;
        }
        return score;
    }
}
