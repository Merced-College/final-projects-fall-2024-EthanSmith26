package millionaireTriviaGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class GameLayout extends JFrame {

	private JTextArea questionTextArea;
    private JButton[] answerButtons;
    private LinkedList<Question> questions;
    private int score;
    private Question currentQuestion;

    // Additional fields for the scoring tower
    private JPanel scoringTowerPanel;
    private JLabel[] levelLabels;
    
    // Constructor
    public GameLayout(LinkedList<Question> questions) {
        
    	this.questions = questions;
        this.score = 0;

        // Set up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the window to fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Set up a split layout to manage scoring tower and central content
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(5); // Thin divider
        splitPane.setResizeWeight(0.8); // Prioritize space for the central content
        splitPane.setDividerLocation(0.8); // Initial divider location (80% for content, 20% for scoring tower)

        // Title label
        JLabel titleLabel = new JLabel("Millionaire Trivia Game", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(new Color(70, 130, 250));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Create a central panel for the question and buttons
        JPanel centralPanel = new JPanel(new BorderLayout(5, 5));

        // Question text area
        questionTextArea = new JTextArea();
        questionTextArea.setEditable(false);
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);
        questionTextArea.setFont(new Font("Arial", Font.BOLD, 40));
        questionTextArea.setBackground(Color.LIGHT_GRAY);
        questionTextArea.setMargin(new Insets(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(questionTextArea);
        scrollPane.setPreferredSize(new Dimension(947, 100));
        centralPanel.add(scrollPane, BorderLayout.CENTER); // Add the question area at the top

        // Create bottom panel for answer buttons
        JPanel bottomPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        answerButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = createAnswerButton(i);
            bottomPanel.add(answerButtons[i]);
        }
        centralPanel.add(bottomPanel, BorderLayout.SOUTH); // Add the answer buttons below

        // Add the scoring tower panel
        setupScoringTower();

        // Add the scoring tower panel to the EAST
        splitPane.setLeftComponent(centralPanel); // Left side: Question and buttons
        splitPane.setRightComponent(scoringTowerPanel); // Right side: Scoring tower

        // Add the split pane to the frame
        add(splitPane, BorderLayout.CENTER);

        // Start the game
        handleQuestionAndAnswer(-1);

        // Make the frame visible
        setVisible(true);
    }



    // Create an answer button with hover effect and action listener
    private JButton createAnswerButton(int index) {
        
    	JButton button = new JButton(); 
        button.setBackground(new Color(70, 130, 180));  // Set the button's background color to a steel blue shade
        button.setForeground(Color.WHITE);  // Set the button's text color to white
        button.setFont(new Font("Arial", Font.BOLD, 25));  // Set the font to Arial, bold, size 25 for readability
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));  // Add a black border around the button
        button.setFocusPainted(false);  // Disable the focus paint effect (no visual focus indication)
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Change the cursor to a hand when hovering over the button

        // Create a label inside the button to enable text wrapping
        JLabel label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 20));  // Set the font for the label inside the button
        label.setForeground(Color.WHITE);  // Set the label's text color to white for visibility
        label.setHorizontalAlignment(SwingConstants.CENTER);  // Center the text horizontally within the label
        label.setVerticalAlignment(SwingConstants.CENTER);  // Center the text vertically within the label
        label.setPreferredSize(new Dimension(150, 150));  // Set a preferred size for the label to accommodate wrapped text

        // Enable word wrapping for the label's text by formatting it with HTML
        label.setText("<html><p style='width: 130px;'>" + label.getText() + "</p></html>");

        // Add the label to the button (the label is now inside the button)
        button.add(label);

        // Add hover effect to change the button's background color on mouse enter/exit
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(100, 149, 237));  // Change background to a lighter blue when hovered over
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180));  // Revert to original background color when the mouse leaves
            }
        });

        // Add an action listener to handle the button click and pass the index of the answer
        button.addActionListener(e -> handleQuestionAndAnswer(index));

        // Return the fully created button with all custom settings
        return button;
    }

    private void setupScoringTower() {
        
    	// Define the money levels for the scoring tower
        String[] MONEY_LEVELS = {
            "$1,000,000", "$500,000", "$250,000", "$100,000", "$50,000",
            "$20,000", "$8,000", "$2,500", "$1,000", "$250"
        };

        // Create a JPanel to hold the content of the scoring tower, using a grid layout for even spacing
        JPanel scoringTowerContent = new JPanel();
        scoringTowerContent.setLayout(new GridLayout(MONEY_LEVELS.length, 1));  // GridLayout ensures each label is evenly spaced vertically
        scoringTowerContent.setBackground(new Color(176, 196, 222));  // Set background color to a light blue-gray
        scoringTowerContent.setPreferredSize(new Dimension(300, 5000));  // Set preferred size for the panel to span the height of the east side

        // Initialize the array to hold the level labels for each money tier
        levelLabels = new JLabel[MONEY_LEVELS.length];
        
        // Loop through each money level and create a label for it
        for (int i = 0; i < MONEY_LEVELS.length; i++) {
            levelLabels[i] = new JLabel(MONEY_LEVELS[i], JLabel.CENTER);  // Create a label with the money amount, centered text
            levelLabels[i].setFont(new Font("Arial", Font.BOLD, 35));  // Set font to Arial, bold, size 35
            levelLabels[i].setForeground(new Color(50, 50, 230));  // Set text color to blue
            levelLabels[i].setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));  // Add vertical padding to the label for spacing
            scoringTowerContent.add(levelLabels[i]);  // Add the label to the panel
        }

        // Create the final panel that holds the entire scoring tower content, which will be added to the east side of the layout
        scoringTowerPanel = new JPanel(new BorderLayout());  // Use BorderLayout for flexible positioning
        scoringTowerPanel.add(scoringTowerContent, BorderLayout.CENTER);  // Add the scoring tower content to the center of the panel
        scoringTowerPanel.setBackground(new Color(240, 240, 240));  // Set the background color of the panel to a light gray
        scoringTowerPanel.setPreferredSize(new Dimension(300, 10000));  // Set preferred size to ensure it fills the right side of the frame
    }

    // Handle displaying questions, checking answers, and managing game flow
    private void handleQuestionAndAnswer(int userAnswerIndex) {
        // Base case: If no more questions are available (e.g., CSV file loads incorrectly), end the game
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All questions have been asked.");
            System.exit(0);  // Terminate the game
            return;
        }

        // If the user has selected an answer, check whether it's correct
        if (userAnswerIndex != -1) {
            String correctAnswer = currentQuestion.getAnswer();  // Retrieve the correct answer for the current question
            String userAnswer = switch (userAnswerIndex) {
                case 0 -> "A";  // Map index 0 to answer choice "A"
                case 1 -> "B";  // Map index 1 to answer choice "B"
                case 2 -> "C";  // Map index 2 to answer choice "C"
                case 3 -> "D";  // Map index 3 to answer choice "D"
                default -> "";   // Default case if an invalid index is provided (shouldn't happen)
            };

            // Check if the user's selected answer matches the correct answer
            if (userAnswer.equals(correctAnswer)) {
                score++;  // Increment score for a correct answer
                JOptionPane.showMessageDialog(this, "Correct!");  // Notify the user of the correct answer
            } else {
                // Notify the user of the incorrect answer and display the correct answer
                JOptionPane.showMessageDialog(this, "Incorrect! The correct answer was: " + correctAnswer + "\nGame over!");
                System.exit(0);  // Terminate the game if the answer is wrong
                return;
            }
            
            // If the player has reached the final prize ($1,000,000), congratulate them and end the game
            if (score == levelLabels.length) {
                JOptionPane.showMessageDialog(this, "Congratulations! You've won $1,000,000!");
                System.exit(0);  // End the game if the player wins
                return;
            }

            // Update the scoring tower UI based on the player's score
            if (score < levelLabels.length) {
                // For each correct answer, set the corresponding scoring level label to green
                for (int i = levelLabels.length - 1; i >= levelLabels.length - score; i--) {
                    levelLabels[i].setForeground(new Color(0, 204, 0));  // Set the text color to green for correct answers
                }
            }
        }

        // Load the next question
        currentQuestion = questions.removeFirst();  // Remove and get the next question from the list
        questionTextArea.setText(currentQuestion.getQuestion());  // Display the current question in the text area
        // Set the answer options for the current question
        answerButtons[0].setText("A. " + currentQuestion.getOptionA());
        answerButtons[1].setText("B. " + currentQuestion.getOptionB());
        answerButtons[2].setText("C. " + currentQuestion.getOptionC());
        answerButtons[3].setText("D. " + currentQuestion.getOptionD());
    }
}
