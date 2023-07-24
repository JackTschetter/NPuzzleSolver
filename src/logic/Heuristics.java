/* Heuristics class
 * This class contains two methods num_wrong_tiles(State state) and manhattan_distance(State state)
 * 
 *
 */

package logic;

// import logic.State;

public class Heuristics {

    static int[][] solved = { {1, 2, 3}, {8,0,4}, {7, 6, 5} };
    //static int[][] solved = { {1, 2, 3}, {4,5,6}, {7, 8, 0} };

    public static int num_wrong_tiles (State state) {
        
        /** Number of wrong tiles, default is 0 */
        int wrong_tiles = 0;

        /* Do comparrison with the solved state
         * Compare each value with the solved state
         * If the desired index and value are identical to the solved state, do nothing.
         * Else increment wrong_tiles by 1
         * For example in the solved that the index 0 is the array {1,2,0}
         * and the 0 index of the array is {1, 2, 0} is 1
         * So we would check if state[0][0] == 1
         * If yes do nothing, else wrong tile so increment the counter
         */

        for (int rows = 0; rows < state.final_state.length; rows++) {
            for (int cols = 0; cols < state.final_state[rows].length; cols++) {
                if (state.final_state[rows][cols] != solved[rows][cols]) {
                    wrong_tiles++;
                }
            }
        }
        return wrong_tiles;
    }

    public static int manhattan_distance (State state) {
        int manhattan_distance = 0;
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        for (int num = 0; num<=8; num++) {

            /** Get x1, y1 for the current state. */
            for (int i = 2; i >= 0; i--) {
                for (int j = 2; j >= 0; j--) {
                    if (state.final_state[i][j] == num) {
                        x1 = i;
                        y1 = j;
                    }
                }
            }

            /** Get x2, y2, for the expected state (from the provided writeup. */
            for (int i = 2; i >= 0; i--) {
                for (int j = 2; j >= 0; j--) {
                    if (solved[i][j] == num) {
                        x2 = i;
                        y2 = j;
                    }
                }
            }
            manhattan_distance += Math.abs(x1-x2) + Math.abs(y1-y2);
        }
        return manhattan_distance;
    }
}
