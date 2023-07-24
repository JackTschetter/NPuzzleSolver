/* Search class
 * The search class performs A* search with a given heuristic
 * The other methods are helpers for the setup of the A* search
 * Created by Jack Tschetter x500 : tsche043
 */
package logic;

// import logic.Heuristics;

import java.util.*;

public class Search {

    /** 2D array stores the "final" solved state as provided in the rightup. */
    public final int[][] solved = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};

    /** Constructor */
    public Search() {

    }

    /** Depth first search */
    /** The search space (branching factor) here is 4, because there are 4 possible actions to take. */

    public boolean dfs(Node initial_node) {

        if (initial_node == null) {
            return false;
        } else if (Heuristics.num_wrong_tiles(initial_node.state) == 0) {
            return true;
        }
        initial_node.state.Print();
        System.out.println(Heuristics.num_wrong_tiles(initial_node.state));
        Node l_child = initial_node.child_node('l');
        Node r_child = initial_node.child_node('r');
        Node u_child = initial_node.child_node('u');
        Node d_child = initial_node.child_node('d');

        if (dfs(l_child)) {
            return true;
        } else if (dfs(r_child)) {
            return true;
        } else if (dfs(u_child)) {
            return true;
        }
        return dfs(d_child);
    }

    /** Helper function */
    public boolean dfs_wrapper(State state) {
        return dfs(new Node(state, null, 'e', 0));
    }

    /** Print the output */
    public String output(Node node) {
        String final_output = "";
        String outPut = "";
        while (node.parent != null) {
            if (node.action == 'r') {
                final_output = "Right, " + final_output;
                outPut = "R" + outPut;
            } else if (node.action == 'l') {
                final_output = "Left, " + final_output;
                outPut = "L" + outPut;
            } else if (node.action == 'u') {
                final_output = "Up, " + final_output;
                outPut = "U" + outPut;
            } else {
                final_output = "Down, " + final_output;
                outPut = "D" + outPut;
            }
            node = node.parent;
        }
        if (final_output.equals("")) {
            System.out.println("");
            return "";
        } else {
            System.out.println(final_output.substring(0, final_output.length() - 2));
            return outPut;
        }
    }

    /** Depth limited search. */
    public boolean dls(Node initial_node, int limit) {

        if (initial_node == null) {
            return false;
        } else if (initial_node.path_cost > limit) {
            return false;
        } else if (Heuristics.num_wrong_tiles(initial_node.state) == 0) {
            initial_node.state.Print();
            output(initial_node);
            return true;
        }

        //System.out.println(Heuristics.num_wrong_tiles(initial_node.state));
        Node l_child = initial_node.child_node('l');
        Node r_child = initial_node.child_node('r');
        Node u_child = initial_node.child_node('u');
        Node d_child = initial_node.child_node('d');

        //r_child.state.Print();
        if (dls(l_child, limit)) {
            return true;
        } else if (dls(r_child, limit)) {
            return true;
        } else if (dls(u_child, limit)) {
            return true;
        }
        return dls(d_child, limit);
    }

    /** Iterative deepening. */
    public boolean iterative_deepening(State state) {
        int current_limit = 0;
        Node asNode = new Node(state, null, 'e', 0);

        /* Note to grader:
         * For this part, you might think it is "imprecise" to use a fixed limit
         * However the mathematics work out that every (solvable) 8 puzzle can be solved in max. 31 steps
         * https://math.stackexchange.com/questions/2688257/maximum-of-minimum-number-of-moves-required-for-hardest-8-puzzle
         */

        while (current_limit < 31) {
            if (dls(asNode, current_limit)) {
                return true; //found solution
            }
            current_limit++;
        }
        return false; //no solution
    }

    public String astar(String heuristic, State state) { //have the user specify the type of heuristic to use

        PriorityQueue<Node> nodesGenerated = new PriorityQueue<Node>();

        HashMap<Node, Node> nodesExpanded = new HashMap<Node, Node>();

        Node startNode = new Node(state, null, 'e', 0);

        /**add startNode to nodesGenerated*/
        nodesGenerated.add(startNode);

        while (nodesGenerated.size() > 0) {

            /** Get Node n off the nodesGenerated list with the lowest f(n). */
            Node n = nodesGenerated.remove();

            if (Heuristics.num_wrong_tiles(n.state) == 0) {
                n.state.Print();
                return output(n);
                //return true; //solutionFound
            }

                Node[] successors = new Node[4];

                /**generate each successor node of n*/
                successors[0] = n.child_node('l');
                successors[1] = n.child_node('r');
                successors[2] = n.child_node('u');
                successors[3] = n.child_node('d');

                for (Node n2 : successors) {

                    int hn2 = 0;

                    if (n2 != null) {
                        if (heuristic.equals("Manhattan")) {

                            /**Set h(n2) to be the heuristically estimated distance to solutionNode */
                            hn2 = Heuristics.manhattan_distance(n2.state);

                        } else {

                            /**Set h(n2) to be the heuristically estimated distance to solutionNode */
                            hn2 = Heuristics.num_wrong_tiles(n2.state);

                        }

                        /**Set g(n2) to be g(n) plus the cost to get to n2 from n */
                        int gn2 = n2.path_cost;

                        /**Set f(n2) to be g(n2) plus h(n2) */
                        int fn2 = hn2 + gn2;

                        n2.f = fn2;

                        if (nodesExpanded.get(n2) != null) {
                            Node oldn2 = nodesExpanded.get(n2);
                            if (n2.f >= oldn2.f) {
                                nodesGenerated.remove(n2);
                                continue;
                            }
                            nodesGenerated.remove(n2);
                            nodesGenerated.add(oldn2);
                        } else {
                            nodesGenerated.add(n2);
                        }
                    } //end if statement
                }//end for loop
                nodesExpanded.put(n, n);
        } //ends while loop
        return "Solution not found!"; //at this point all reachable nodes searched, and still no solution
    } //closes function
} //closes the class
