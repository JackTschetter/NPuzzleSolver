/* SlidePuzzleModel class
 * Contains the following methods initalize(), getNumberOfRows(), getNumberOfCols(), getFace(), moveTile()
 * checkEmpty(), isLegalRowCol(), exchangeTiles(), and isGameOver()
 * Created by Jack Tschetter x500 : tsche043
 */

package ui;

public class SlidePuzzleModel {

    private int rows1;
    private int _cols;
    private String _text;
    public Tile[][] _contents;

    /** Constructor */
    public SlidePuzzleModel(int rows, int cols, String text) {
        assert text.length() == rows * cols;
        rows1 = rows;
        _cols = cols;
        _text = text;
        _contents = new Tile[rows1][_cols];
        initialize();
    }

    public void initialize() {
        for (int r = 0; r < rows1; r++) {
            for (int c = 0; c < _cols; c++) {
                int pos = (r * _cols) + c;
                String tileFace = _text.substring(pos, pos+1);
                _contents[r][c] = new Tile(r, c, tileFace);
            }
        }
    }

    public int getNumberOfRows() {
        return rows1;
    }

    public int getNumberOfCols() {
        return _cols;
    }

    /** getFace returns the string to display at a given row, col. */
    public String getFace(int row, int col) {
        return _contents[row][col].getFace();
    }

    /** Move a tile to the empty position beside it, when possible */
    public boolean moveTile(int r, int c) {

        /** Move is legal if empty cell is next to it */
        return checkEmpty(r, c, -1, 0) || checkEmpty(r, c, 1, 0)
                || checkEmpty(r, c, 0, -1) || checkEmpty(r, c, 0, 1);
    }

    /** Checks to see if there is an empty position beside tile. Return true, and do exchange when possible. */
    private boolean checkEmpty(int r, int c, int rdelta, int cdelta) {

        int rNeighbor = r + rdelta;
        int cNeighbor = c + cdelta;

        /** Check if neighbor is on board and it is empty. */
        if (isLegalRowCol(rNeighbor, cNeighbor)
                && _contents[rNeighbor][cNeighbor].isEmpty()) {
            exchangeTiles(r, c, rNeighbor, cNeighbor);
            return true;
        }
        return false; //Nothing to do, so return false
    }

    public boolean isLegalRowCol(int r, int c) {
        return r >= 0 && r < rows1 && c>=0 && c<_cols;
    }

    /** Exchanges 2 tiles. */
    private void exchangeTiles(int r1, int c1, int r2, int c2) {
        Tile temp = _contents[r1][c1];
        _contents[r1][c1] = _contents[r2][c2];
        _contents[r2][c2] = temp;
    }

    public boolean isGameOver() {
        for (int r = 0; r < rows1; r++) {
            for (int c = 0; c < rows1; c++) {
                Tile trc = _contents[r][c];
                if (!trc.isInFinalPosition(r, c)) {
                    return false;
                }
            }
        }
        return true; // At this point, nothing out of place
    }
}// Closes class SlidePuzzleModel
