package info.hska.erstiduell.questions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author timroes
 */
public final class QuestionLibrary {

    private static QuestionLibrary instance;

    /**
     * Loads a question library from a file. This is needed to initialize the
     * singleton class.
     *
     * @param file Path to the question library
     */
    public static void loadQuestions(File file)
            throws IOException, QuestionLibraryException {

        List<Question> questions = new ArrayList<Question>();

        FileInputStream stream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));

        String questionLine;
        while ((questionLine = br.readLine()) != null) {
            if (!questionLine.startsWith("#")
                    && questionLine.trim().length() > 0) {
                questions.add(loadNextQuestion(questionLine));
            }
        }

        instance = new QuestionLibrary(questions);

    }

    public static synchronized QuestionLibrary getInstance() {
        return instance;
    }

    /**
     * Loads a question from a line read from a question library.
     *
     * @param questionString A line from a question library.
     * @return
     * @throws QuestionLibraryException
     */
    private static Question loadNextQuestion(String questionString)
            throws QuestionLibraryException {

        String[] split = questionString.split(";");

        // Must be at least question and two answers
        if (split.length < 3) {
            throw new QuestionLibraryException("Invalid question format at line \""
                    + ((questionString.length() > 50) ? questionString.substring(0, 30) + "[...]" : questionString)
                    + "\"");
        }

        ArrayList<Answer> answers = new ArrayList<Answer>();
        String[] answerSplit;
        for (int i = 1; i < split.length; i++) {
            answerSplit = split[i].split("#");
            if (answerSplit.length != 2) {
                throw new QuestionLibraryException("Invalid question format at line \""
                        + ((questionString.length() > 50) ? questionString.substring(0, 30) + "[...]" : questionString)
                        + "\"");
            }
            
            answers.add(new Answer(answerSplit[1],
                    Integer.valueOf(answerSplit[0])));
        }
        
        Question question = new Question(split[0], answers, false);

        return question;

    }

	// Class begin
    private List<Question> questions;

    private QuestionLibrary(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getAllQuestions() {
        return questions;
    }

    public int getQuestionAmount() {
        return questions.size();
    }

    public int getDoneQuestions() {

        int i = 0;

        for (Question q : questions) {
            if (q.getDone()) {
                i++;
            }
        }

        return i;

    }

}
