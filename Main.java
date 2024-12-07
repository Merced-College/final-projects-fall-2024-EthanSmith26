// Ethan Smith
// 11/15/24
// Professor Kanemoto, CPSC-39-12111
// "Who Want's To Be A Millionaire?" inspired trivia game

package millionaireTriviaGame;

import javax.swing.*;
import java.util.*;

public class Main {
    
    public static void main(String[] args) {
        
        // Load the questions from the CSV file into a LinkedList
        LinkedList<Question> questions = QuestionLoader.loadQuestions("Questions.csv");

        // Check if questions are loaded correctly
        if (questions.isEmpty()) {
            System.out.println("No questions loaded.");
            return;  // Exit if no questions are loaded
        }

        // Convert the LinkedList to an ArrayList for easier shuffling
        List<Question> shuffledQuestions = new ArrayList<>(questions);
        
        // Shuffle the ArrayList to randomize the order of questions
        Collections.shuffle(shuffledQuestions);  

        // Convert the shuffled ArrayList back to a LinkedList 
        // so we can easily remove questions during the game
        LinkedList<Question> finalQuestions = new LinkedList<>(shuffledQuestions);

        // Use SwingUtilities.invokeLater to ensure that the GUI is updated on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create a new GameLayout instance and pass the shuffled LinkedList to the GUI
                new GameLayout(finalQuestions);  // Pass shuffled LinkedList to the GUI for display
            }
        });
    }
}
