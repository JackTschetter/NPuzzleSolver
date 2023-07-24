/* Helper class for the GUI.
 * Creates a Tile object with methods getFace(), isEmpty(), and isInFinalPosition()
 * Created by Jack Tschetter x500 : tsche043
 */

package ui;

public class Tile {

    /** This string MUST be static */
    static String[][] solved = { {"1", "2", "3"}, {"8","0","4"}, {"7", "6", "5"} };

    int Row, Column;
    String tileFace;

    public Tile(int Row, int Column, String tileFace) {
        this.Row = Row;
        this.Column = Column;
        this.tileFace = tileFace;
    }

    public String getFace() {
        return tileFace;
    }

    public boolean isEmpty() {
        return(this.tileFace.equals("0"));
    }

    public boolean isInFinalPosition(int r, int c) {
        return solved[r][c].equals(this.tileFace);
    }
}