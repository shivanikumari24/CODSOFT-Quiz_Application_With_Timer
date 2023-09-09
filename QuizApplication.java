import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApplication {
    private JFrame frame;
    private JPanel panel;
    private JLabel questionLabel;
    private ButtonGroup optionsGroup;
    private JRadioButton[] optionButtons;
    private JButton nextButton;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;
    private int timeRemaining;

    private String[] questions = {
        "Question 1: What is the National Animal of India?",
        "Question 2: Who founded the Indian National Army?",
        // Add more questions here...
    };

    private String[][] options = {
        {"Tiger", "Lion", "Elephant", "Deer"},
        {"Sarojini Naidu", "Subhas Chandra Bose", "Bal Gangadhar Tilak", "Mohandas Karamchand Gandhi"},
        // Add options for each question here...
    };

    private int[] correctAnswers = {0, 1, /* Add correct answers for each question here... */};

    public QuizApplication() {
        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 300);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        questionLabel = new JLabel();
        panel.add(questionLabel);

        optionsGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionsGroup.add(optionButtons[i]);
            panel.add(optionButtons[i]);
        }

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                showNextQuestion();
            }
        });
        panel.add(nextButton);

        frame.add(panel);
        currentQuestionIndex = 0;
        score = 0;

        startTimer();
        showNextQuestion();
        frame.setVisible(true);
    }

    private void showNextQuestion() {
        if (currentQuestionIndex < questions.length) {
            questionLabel.setText(questions[currentQuestionIndex]);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(options[currentQuestionIndex][i]);
                optionButtons[i].setSelected(false);
            }
            currentQuestionIndex++;
            timeRemaining = 10; // Set the time for each question (in seconds)
            updateTimerLabel();
            timer.restart();
        } else {
            showResult();
        }
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                updateTimerLabel();
                if (timeRemaining == 0) {
                    timer.stop();
                    checkAnswer();
                    showNextQuestion();
                }
            }
        });
    }

    private void updateTimerLabel() {
        questionLabel.setText(questions[currentQuestionIndex - 1] + " Time Remaining: " + timeRemaining + "s");
    }

    private void checkAnswer() {
        int selectedOption = -1;
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected()) {
                selectedOption = i;
                break;
            }
        }

        if (selectedOption == correctAnswers[currentQuestionIndex - 1]) {
            score++;
        }
    }

    private void showResult() {
        panel.removeAll();
        questionLabel.setText("Quiz Completed!");
        JLabel scoreLabel = new JLabel("Your Score: " + score + "/" + questions.length);
        panel.add(scoreLabel);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizApplication();
            }
        });
    }
}

