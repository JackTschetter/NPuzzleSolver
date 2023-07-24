/* Node class containing member variables state, parent, action, and path_cost, and f
 * The Node class contains a constructor and a single method child_node(char a), that returns another Node
 * We will utilize the child_node method extensively in searching
 * We will also exploit the path cost variable when performing depth_limited_search
 * Created by Jack Tschetter x500 : tsch043
 */

package logic;

// import logic.State;

public class Node implements Comparable <Node> {
    State state;
    Node parent;
    char action; //l, u, r, d
    int path_cost;
    int f;

    public Node(State state, Node parent, char action, int path_cost) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.path_cost = path_cost;
        this.f = 0;
    }

    public Node child_node(char a) {
        int zero_index_i = -1;
        int zero_index_j = -1;
        int[][] new_state = new int[3][3];
        for (int i = 2; i >= 0; i--) {
            for (int j = 2; j >= 0; j--) {
                new_state[i][j] = state.final_state[i][j];
            }
        }

        for (int i = 2; i >= 0; i--) {
            for (int j = 2; j >= 0; j--) {
                if (new_state[i][j] == 0) {
                    zero_index_i = i;
                    zero_index_j = j;
                }
            }
        }

        if (a == 'r') { //right

            if (zero_index_j < 1) {
                return null;
            }

            int right = new_state[zero_index_i][zero_index_j-1]; //right is j+1
            new_state[zero_index_i][zero_index_j] = right;
            new_state[zero_index_i][zero_index_j - 1] = 0;

        } else if (a == 'l') { //left

            if (zero_index_j >= 2) {
                return null;
            }

            int left = new_state[zero_index_i][zero_index_j+1];
            new_state[zero_index_i][zero_index_j] = left;
            new_state[zero_index_i][zero_index_j + 1] = 0;

        } else if (a == 'd') { //down

            if (zero_index_i < 1) {
                return null;
            }

            int down = new_state[zero_index_i-1][zero_index_j]; //right is j+1
            new_state[zero_index_i][zero_index_j] = down;
            new_state[zero_index_i -1][zero_index_j] = 0;

        } else if (a == 'u') { //up
            if (zero_index_i >= 2) {
                return null;
            }

            int up = new_state[zero_index_i+1][zero_index_j];
            new_state[zero_index_i][zero_index_j] = up;
            new_state[zero_index_i+1][zero_index_j] = 0;
        }
        State s = new State(new_state);
        Node temp = new Node(s, this, a, path_cost+1);
        return temp;
    }

    public int compareTo(Node newNode) {
        return Integer.compare(this.f, newNode.f);
    }

    public boolean equals(Node n2) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.state.final_state[i][j] != n2.state.final_state[i][j]) {
                    return false;
                }
            } //closes inner for loop
        } //closes outer for loop
        return true;
    }
}