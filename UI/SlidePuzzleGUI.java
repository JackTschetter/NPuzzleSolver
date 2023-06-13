/* Class to create a GUI for the N-puzzle
 * This class creates a panel containing subpanels
 * One subpanel contains the controls, the other contains the puzzle board
 * Created entirely by Jack R. Tschetter for the class CSCI 5511 at umn.
 */

package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
// import java.io.*;
// import java.lang.Thread;
// import javax.swing.event.*;
// import java.util.concurrent.TimeUnit;

import logic.*;

public class SlidePuzzleGUI extends JPanel implements ItemListener {

    private GraphicsPanel puzzleGraphics; 
    private SlidePuzzleModel _puzzleModel;

    static JCheckBox c1, c2;

    static JButton startButton;

    String solutionSequence;

    static String initString = "";

    boolean c1true = false, c2true = false;

    /** Constructor */
    public SlidePuzzleGUI(String initState) {

        _puzzleModel = new SlidePuzzleModel(3, 3, initState);

        /**Create a button. */
        nextMoveButton = new JButton("Next Move");

        c2 = new JCheckBox("Num Wrong Moves");
        c1 = new JCheckBox("Manhattan Distance");

        /** Create control panel. */
        JPanel controlPanel = new JPanel();
        c1.addItemListener(this);
        c2.addItemListener(this);
        //TODO : read up on how FlowLayout() works.
        controlPanel.setLayout(new FlowLayout(15, 15, 15));
        controlPanel.add(c1);
        controlPanel.add(c2);
        controlPanel.add(nextMoveButton);

        /** Create graphics panel */
        puzzleGraphics = new GraphicsPanel();

        /** Set the layout and add the components */
        this.setLayout(new BorderLayout()); //TODO : Read up on how BorderLayout() works.
        this.add(controlPanel, BorderLayout.NORTH);
        this.add(puzzleGraphics, BorderLayout.CENTER);
    } //closes constructor

    public void itemStateChanged(ItemEvent e) {
        /** Instantiate a new Search object. */
        Search a = new Search();

        /* Get the command line input
         * When compiling the program, the command line expects a single argument in the form of an 8 digit integer
         * If no integer is passed in, an array index of bounds error will be thrown at compile time
         * Convert the int value to a State (See State.java)
         */

        State initState = new State(Integer.parseInt(initString));

        /** if the state of checkbox1 is changed */
        if (e.getSource() == c1) {
            if (e.getStateChange() == 1 && (c2true==false && c1true==false)) {
                System.out.println("c1 true");
                c1true = true;
                c2true = false;
                c2.setEnabled(false);

                /* Solve the 8 puzzle using informed search with manhattan_distance. */
                solutionSequence = a.astar("manhattan_distance", initState);
                System.out.println(solutionSequence);
                startButton.addActionListener(new solveAction(solutionSequence));
                // nextMoveButton.doClick(); //Dont want to start when heuristic chosen. Only after startButton clicked.
            }
        } else if (e.getSource() == c2) {
            if (e.getStateChange() == 1 && (c2true==false && c1true==false)) {
                System.out.println("c2 true");
                c2true = true;
                c1true = false;
                c1.setEnabled(false);
                /* Solve the 8 puzzle using informed search with num_wrong_tiles. */
                solutionSequence = a.astar("num_wrong_tiles", initState);
                /* Possible loop/recursion structure here. */
                System.out.println(solutionSequence);
                startButton.addActionListener(new solveAction(solutionSequence));
                // nextMoveButton.doClick(); //Dont want to start when heuristic chosen. Only after startButton clicked.
            }
        }
    }
        public static void main (String[] args) {
        initString = args[0];
        JFrame window = new JFrame("Slide Puzzle");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new SlidePuzzleGUI(args[0]));
        window.pack();
        window.setVisible(true); //As of JDK version 1.5 this replaces window.show();
        window.setResizable(false);
    }

    /* This GraphicsPanel is class is defined inside the outer class, so it can use the outer class instance variables. */
    class GraphicsPanel extends JPanel implements MouseListener {
        private static final int ROWS = 3;
        private static final int COLS = 3;

        private static final int CELL_SIZE = 175; //Pixels. Might need to adjust the value again.
        private Font _biggerFont;


        /** Constructor */
        public GraphicsPanel() {
            _biggerFont = new Font("SansSerif", Font.BOLD, CELL_SIZE/3); //TODO : Center the text in the cell.
            this.setPreferredSize(new Dimension(CELL_SIZE * COLS, CELL_SIZE * ROWS));
            this.setBackground(Color.red); //Might want to play around with this.
            this.addMouseListener(this);  // Listen own mouse events.
        }//end constructor


        /** x method paintComponent. */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    int x = c * CELL_SIZE;
                    int y = r * CELL_SIZE;
                    String text = _puzzleModel.getFace(r, c);
                    // System.out.println("Text:" + text);
                    if (text != null) {
                        if (text.equals("0")) {
                            g.setColor(this.getBackground());
                            g.fillRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
                            // g.fillRect(x+6, y+6, CELL_SIZE-3, CELL_SIZE-3);
                            g.setColor(Color.black); //Set the text color.
                            g.setFont(_biggerFont);
                            // g.drawString(text, x + 20, y + (3 * CELL_SIZE) / 4);
                        }
                        else {
                            g.setColor(Color.gray);
                            g.fillRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
                            // g.fillRect(x+6, y+6, CELL_SIZE-3, CELL_SIZE-3);
                            g.setColor(Color.black); //Set the text color.
                            g.setFont(_biggerFont);
                            g.drawString(text, x + 20, y + (3 * CELL_SIZE) / 4);
                        }
                    }
                }
            }
        }//end paintComponent

        /** Leave this as empty. */
        public void mousePressed(MouseEvent e) {

        }//end mousePressed


        /** Ignore these events. */
        public void mouseClicked (MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered (MouseEvent e) {}
        public void mouseExited  (MouseEvent e) {}
    }//end class GraphicsPanel

    public class solveAction implements ActionListener {
        String solutionSequence = "";
        private int current_position = 0;

        public solveAction(String solutionSequence) {
            this.solutionSequence = solutionSequence;
        }

        public void actionPerformed(ActionEvent e) {
                // puzzleGraphics.repaint();
                // try {
                //     Thread.sleep(500);
                // } catch (Exception eee) {
                //     eee.printStackTrace();
                // }
                /* The below print statement executes every time the next move button is clicked. Even after game is solved .*/
                System.out.println("actionPerformed");
                // System.out.println("Solved?" + _puzzleModel.isGameOver());

                while (!_puzzleModel.isGameOver()) {
                    // puzzleGraphics.repaint();
                    if(current_position >= solutionSequence.length()) {
                        return;
                    }
    
                    int zero_index_i = -1;
                    int zero_index_j = -1;
    
                    for (int i = 2; i >= 0; i--) {
                        for (int j = 2; j >= 0; j--) {
                            if (_puzzleModel._contents[i][j].tileFace.equals("0")) {
                                zero_index_i = i;
                                zero_index_j = j;
                            }
                        }
                    }

                    if(solutionSequence.charAt(current_position) == 'U') {
                        // _puzzleModel.moveTile(zero_index_i+1, zero_index_j); /* This returns the boolean false. */
                        System.out.println(_puzzleModel.isGameOver());
                        try {
                            _puzzleModel.moveTile(zero_index_i+1, zero_index_j);
                            // nextMoveButton.doClick();
                        } catch (Exception ee) {
                            System.out.println(ee);
                        }
                        // System.out.println(_puzzleModel.moveTile(zero_index_i+1, zero_index_j));
                        // nextMoveButton.addActionListener(new solveAction(solutionSequence));
                    } else if (solutionSequence.charAt(current_position) == 'D') {
                        // _puzzleModel.moveTile(zero_index_i-1, zero_index_j); /* This returns the boolean false. */
                        System.out.println(_puzzleModel.isGameOver());
                        try {
                            _puzzleModel.moveTile(zero_index_i-1, zero_index_j);
                            // puzzleGraphics.repaint(); //Move or repeat this part?
                            // Thread.kill();
                            // Thread.sleep(1000);
                            // nextMoveButton.doClick();
                        } catch (Exception ee) {
                            System.out.println("Error : Down");
                            // ee.printStackTrace();
                        }
                        // System.out.println(_puzzleModel.moveTile(zero_index_i+1, zero_index_j));
                        // nextMoveButton.addActionListener(new solveAction(solutionSequence));
                    } else if (solutionSequence.charAt(current_position) == 'R') {
                        // _puzzleModel.moveTile(zero_index_i, zero_index_j-1); /* This returns the boolean false. */
                        // System.out.println("Current thread" + Thread.currentThread().getId());
                        System.out.println("Is game over: " +_puzzleModel.isGameOver());
                        try {
                            _puzzleModel.moveTile(zero_index_i, zero_index_j-1);
                            // Thread.sleep(1000);
                            // nextMoveButton.doClick();
                        } catch (Exception ee) {
                            System.out.println("Error : Right");
                            System.out.println(ee);
                        }
                        // System.out.println(_puzzleModel.moveTile(zero_index_i+1, zero_index_j));
                        // nextMoveButton.addActionListener(new solveAction(solutionSequence));
                    } else if (solutionSequence.charAt(current_position) == 'L') {
                        // _puzzleModel.moveTile(zero_index_i, zero_index_j+1); /* This returns the boolean false. */
                        System.out.println(_puzzleModel.isGameOver());
                        // System.out.println("Current thread" + Thread.currentThread().getId());
                        // System.out.println("Sleeping on 1000");
                        try {
                            _puzzleModel.moveTile(zero_index_i, zero_index_j+1);
                            System.out.println("Current thread" + Thread.currentThread().getId());
                            if (Thread.currentThread().getId() == 22) {
                                try {
                                    System.out.println("Thread 22");
                                }
                                catch (Exception ee) {
                                    ee.printStackTrace();
                                }
                            }
                            // Thread.sleep(1000);
                            // puzzleGraphics.repaint();
                            // Thread.sleep(1000);
                            // nextMoveButton.doClick();
                        } catch (Exception ee) {
                            System.out.println("Error : Left");
                            System.out.println(ee);
                        }
                        // System.out.println(_puzzleModel.moveTile(zero_index_i+1, zero_index_j));
                        // nextMoveButton.addActionListener(new solveAction(solutionSequence));
                    }
                    // Thread.sleep(1000);
                    // puzzleGraphics.repaint(); //Move or repeat this part?
                    current_position++;
                    System.out.println("Current Position Is : " + current_position);
                }
                puzzleGraphics.repaint();
            } //Close actionPerformed
        }//Close inner class
    }//Close class SlidePuzzleGUI
