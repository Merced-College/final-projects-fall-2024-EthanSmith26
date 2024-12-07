package millionaireTriviaGame;

import java.io.*;
import java.util.LinkedList;

public class QuestionLoader {

    // Method to load questions from a CSV file into a LinkedList
    public static LinkedList<Question> loadQuestions(String fileName) {
        
        // Create a new LinkedList to store the questions
        LinkedList<Question> questions = new LinkedList<>();

        // Use a BufferedReader to read the file line by line
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            
            String line;
            boolean isFirstLine = true; // Flag to skip the header line in the CSV

            // Read each line of the CSV file
            while ((line = bufferedReader.readLine()) != null) {
                
                // Skip the first line (header) that contains column names
                if (isFirstLine) {
                    isFirstLine = false; // Mark that the first line has been skipped
                    continue; // Skip to the next line
                }
                
                // Split the current line into fields using commas as the delimiter
                String[] fields = line.split(",");
                
                // Ensure the line has exactly 6 fields (valid question format)
                if (fields.length == 6) {
                    String questionText = fields[0];  // The question text
                    String optionA = fields[1];       // Option A
                    String optionB = fields[2];       // Option B
                    String optionC = fields[3];       // Option C
                    String optionD = fields[4];       // Option D
                    String correctAnswer = fields[5]; // The correct answer

                    // Create a new Question object and add it to the list
                    Question question = new Question(questionText, optionA, optionB, optionC, optionD, correctAnswer);
                    questions.add(question);
                }
            }
        } catch (IOException e) {
            // Handle any IOExceptions that may occur when reading the file
            e.printStackTrace();
        }
        
        // Return the populated list of questions
        return questions;
    }
}
