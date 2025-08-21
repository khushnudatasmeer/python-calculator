import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MemoryGame extends JFrame implements ActionListener {
    JLabel wordLabel, timerLabel;
    JTextField[] inputs;
    JButton startButton, submitButton;
    JPanel inputPanel, buttonPanel;
    String[] wordBank = {"apple", "river", "stone", "cloud", "dream", "pencil", "tiger", "dance", "train", "light"};
    String[] selectedWords;
    javax.swing.Timer countdownTimer;
    int timeLeft;

    public MemoryGame() {
        setTitle("Brain Boost - Memory Game");
        setSize(600, 380);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(230, 240, 255));

        wordLabel = new JLabel("Click 'Start' to begin!", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        wordLabel.setForeground(new Color(25, 25, 112));
        wordLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(wordLabel, BorderLayout.NORTH);

        timerLabel = new JLabel();                              // Updated
        timerLabel.setVisible(false);
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        timerLabel.setForeground(new Color(220, 20, 60));  // crimson red
        timerLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
        add(timerLabel, BorderLayout.CENTER);

        inputPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        inputs = new JTextField[5];
        for (int i = 0; i < 5; i++) {
            inputs[i] = new JTextField();
            inputs[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            inputs[i].setBorder(BorderFactory.createLineBorder(new Color(65, 105, 225), 2));
            inputs[i].setVisible(false);
            inputPanel.add(inputs[i]);
        }
        add(inputPanel, BorderLayout.SOUTH);

        buttonPanel = new JPanel();
        startButton = new JButton("Start Game");
        submitButton = new JButton("Submit Answers");
        submitButton.setVisible(false);

        Font btnFont = new Font("Tahoma", Font.BOLD, 16);
        Color btnColor = new Color(65, 105, 225);
        startButton.setFont(btnFont);
        startButton.setBackground(btnColor);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);

       

        startButton.addActionListener(this);
        submitButton.addActionListener(this);

        buttonPanel.add(startButton);
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.NORTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startGame();
        } else if (e.getSource() == submitButton) {
            checkAnswers();
        }
    }

    private void startGame() {
        // Select random words
        List<String> list = Arrays.asList(wordBank);
        Collections.shuffle(list);
        selectedWords = list.subList(0, 5).toArray(new String[0]);

        // Show words
        StringBuilder display = new StringBuilder("<html>");
        for (String word : selectedWords) {
            display.append(word).append("<br>");
        }
        display.append("</html>");
        wordLabel.setText(display.toString());

        // Reset timer label
        timeLeft = 5;
        timerLabel.setVisible(true);
        timerLabel.setText("Time left: 5 seconds");

        // Reset input fields
        for (JTextField tf : inputs) {
            tf.setText("");
            tf.setVisible(false);
        }
        submitButton.setEnabled(false);

        // Start countdown timer (ticks every 1 second)
        countdownTimer = new javax.swing.Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft + " seconds");
                if (timeLeft <= 0) {
                    countdownTimer.stop();
                    timerLabel.setText("");
                    wordLabel.setText("Enter the words you remember:");
                    for (JTextField tf : inputs) tf.setVisible(true);
                    submitButton.setEnabled(true);
                    repaint();
                }
            }
        });
        countdownTimer.start();
    }

    private void checkAnswers() {
        int score = 0;
        for (int i = 0; i < 5; i++) {
            String answer = inputs[i].getText().trim().toLowerCase();
            for (String word : selectedWords) {
                if (word.equals(answer)) {
                    score++;
                    break;
                }
            }
        }
        JOptionPane.showMessageDialog(this, "You remembered " + score + " out of 5 words!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MemoryGame().setVisible(true);
        });
    }
}
