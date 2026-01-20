import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/** Class that represents the Battleship GUI
 */
public class BattleshipGUI {
    
    private static final String[] LETTER_AXIS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    private ImageIcon shipSquare = ImageScaler.scaleImage("Battleship GUI\\square.png");
    private ImageIcon hitCircle = ImageScaler.scaleImage("Battleship GUI\\circle.png");
    private ImageIcon missX = ImageScaler.scaleImage("Battleship GUI\\x.png");
    private static boolean localSelected = false;
    private static boolean placementFinished = false;
    private ArrayList<CustomJButton> guessButtonList;
    private ArrayList<CustomJButton> placementButtonList;
    private Player player;
    private BattleshipClient client;

    public BattleshipGUI(Player player, BattleshipClient client) {
        this.player = player;
        this.client = client;
        initJFrame();
        
    }
    /** Method to initialize all JComponents
     * 
     */
    public void initJFrame() {

        JFrame frame = new JFrame("Battleship");
        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

        JPanel guessPanel = new JPanel();
        guessPanel.setLayout(new BorderLayout());
        frame.getContentPane().add(guessPanel);
    
        guessButtonList = createButtonGrid(guessPanel, new GuessButtonListener());
        guessPanel.add(createLetterAxis(), BorderLayout.WEST);
        guessPanel.add(createNumberAxis(), BorderLayout.SOUTH);

        JPanel submitArea = new JPanel();
        submitArea.setLayout(new BoxLayout(submitArea, BoxLayout.Y_AXIS));
        submitArea.setBackground(Color.LIGHT_GRAY);
        guessPanel.add(submitArea, BorderLayout.EAST);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());
        submitArea.add(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelButtonListener());
        submitArea.add(cancelButton);

        JPanel shipPlacementPanel = new JPanel();
        shipPlacementPanel.setLayout(new BorderLayout());
        frame.getContentPane().add(shipPlacementPanel);

        placementButtonList = createButtonGrid(shipPlacementPanel, new PlaceButtonGridListener());
        shipPlacementPanel.add(createLetterAxis(), BorderLayout.WEST);
        shipPlacementPanel.add(createNumberAxis(), BorderLayout.SOUTH);

        JPanel placeArea = new JPanel();
        placeArea.setLayout(new BoxLayout(placeArea, BoxLayout.Y_AXIS));
        placeArea.setBackground(Color.LIGHT_GRAY);
        shipPlacementPanel.add(placeArea, BorderLayout.EAST);

        JButton placeButton = new JButton("Place");
        placeButton.addActionListener(new PlaceButtonListener());
        placeArea.add(placeButton);
        frame.setVisible(true);
    }
    /**
     * Helper method to create the letter axis panel
     * @return letter axis panel
     */
    public static JPanel createLetterAxis() {
        JPanel letterAxisPanel = new JPanel();
        letterAxisPanel.setLayout(new BoxLayout(letterAxisPanel, BoxLayout.Y_AXIS));
        letterAxisPanel.setBackground(Color.LIGHT_GRAY);

        CompoundBorder letterAxisBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), BorderFactory.createEmptyBorder(12, 10, 12, 10));

        for (int i = 0; i < LETTER_AXIS.length; i++) {
            JLabel label = new JLabel(LETTER_AXIS[i]);
            label.setBorder(letterAxisBorder);
            letterAxisPanel.add(label);
        }
        return letterAxisPanel;
    }
    /**
     * Helper method to create the number axis panel
     * @return number axis panel
     */
    public static JPanel createNumberAxis() {
        JPanel numberAxisPanel = new JPanel();
        numberAxisPanel.setLayout(new BoxLayout(numberAxisPanel, BoxLayout.X_AXIS));
        numberAxisPanel.setBackground(Color.LIGHT_GRAY);

        CompoundBorder numberAxisBorder = new CompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1), BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel empty = new JLabel();
        empty.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        numberAxisPanel.add(empty);

        for (int i = 1; i <= LETTER_AXIS.length; i++) {
            JLabel label = new JLabel(Integer.toString(i));
            label.setPreferredSize(new Dimension(6, 40));
            label.setBorder(numberAxisBorder);
            numberAxisPanel.add(label);
        }
        return numberAxisPanel;
    }
    /**
     * Helper method to create button grid panel
     * @return button grid panel
     */
    public static ArrayList<CustomJButton> createButtonGrid(JPanel destination, ActionListener al) {
        JPanel buttonGrid = new JPanel();
        ArrayList<CustomJButton> buttonList = new ArrayList<>();
        buttonGrid.setLayout(new GridLayout(10, 10));

        for (int i = 0; i < 100; i++) {
            CustomJButton button = new CustomJButton();
            button.addActionListener(al);
            button.setPreferredSize(new Dimension(15, 15));
            buttonList.add(button);
            buttonGrid.add(button);
        }
        destination.add(buttonGrid, BorderLayout.CENTER);

        return buttonList;
    }

    public ArrayList<CustomJButton> getGuessButtonList() {
        return guessButtonList;
    }
    /**
     * Action Listener that sends player guess to the server through the client
     */
    class SubmitButtonListener implements ActionListener {
            /**
             * Method that takes the index of the selected guess button and sends it through the playerGuess method
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < guessButtonList.size(); i++) {

                    if (guessButtonList.get(i).getSelected()) {

                        guessButtonList.get(i).setBackground(new JButton().getBackground());
                        guessButtonList.get(i).setSelected(false);
                        
                        boolean result = playerGuess(i);

                        setButtonIcon(i, result);

                        break;
                    }
                }

                localSelected = false;

                if (!player.shipsLeft()) {
                    client.finishGame();
                }
            }
            /**
             * Method that sets the player guess and sends it as an index through the client to the server
             * @return whether the guess was a hit / miss
             */
            private boolean playerGuess(int index) {
                player.setPlayerGuess(index);
                
                try {
                    return client.sendData();

                } catch (IOException io) {
                    return false;
                }
            }
            /**
             * Method that sets the guess button to the approrpiate icon whether a hit / miss occurred 
             * @param index of the button guessed relative to the player's list of possible locations
             * @param result of whether a hit / miss
             */
            private void setButtonIcon (int index, boolean result) {

                if (result) {
                    guessButtonList.get(index).setIcon(hitCircle);
                    player.setShipNode(index, false);
                }

                else {
                    guessButtonList.get(index).setIcon(missX);
                }
            }

    }
    /**
     * Action Listener that cancels current player guess
     */
    class CancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < guessButtonList.size(); i++) {
                guessButtonList.get(i).setBackground(new JButton().getBackground());
                guessButtonList.get(i).setSelected(false);
            }
            localSelected = false;
        }
    }
    /**
     * Action Listener that locks in player's ship placement for the game
     */
    class PlaceButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < placementButtonList.size(); i ++) {

                if (placementButtonList.get(i).getSelected()) {
                    player.setShipNode(i, true);
                }
            }
            
            if (!placementFinished) {
                try {
                    client.setupNetworking();

                } catch (IOException io) {
                    System.out.println("Networking failed");
                }
            }   
            placementFinished = true;
        } 
    }
    /**
     * Action Listener that allows player to place thier ships on the board
     */
    class PlaceButtonGridListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (placementFinished) {
                    return;
                }

                CustomJButton source = (CustomJButton) e.getSource();

                if (!source.getSelected()) {
                    source.setSelected(true);
                    source.setIcon(shipSquare);
                }    
            }
    }
    /**
     * Action Listener that selects a location to be guessed
     */
    class GuessButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (localSelected) {
                return;
            }

            CustomJButton source = (CustomJButton) e.getSource();

            if (!source.getSelected()) {
                source.setSelected(true);
                source.setBackground(Color.RED);
                localSelected = true;
            }
        }
    }   
}
