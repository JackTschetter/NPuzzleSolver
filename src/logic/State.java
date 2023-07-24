/* State class
 * This class contains multiple constructors
 * The first constructor is useful for reading command line input
 * The second constructor takes in a 2D int array and will greatly simplify computations in our other classes/methods
 * 
 * Created by Jack Tschetter x500 : tsche043
 */

package logic;

public class State {
    int [][] final_state;

    /**Constructors*/

    /** Per the write up, the command line will be a single integer */
    public State (int initial_state) {
        this.final_state = new int[3][3];
        for (int i = 2; i >= 0; i--) {
            for (int j = 2; j >= 0; j--) {
                final_state[i][j] = initial_state % 10;
                initial_state = initial_state / 10;
            }
        }
    }

    /** Additionally constructor that takes in a 2D int array instead of a single integer */
    /** This greatly simplifies our program at a later point*/

    public State(int s[][]) {
        final_state = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final_state[i][j] = s[i][j];
            }
        }
    }

    /**Useful for debugging*/

    public void Print() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(final_state[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}