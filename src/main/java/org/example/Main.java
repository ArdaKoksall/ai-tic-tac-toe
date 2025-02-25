package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends JFrame {
    private static final int BOARD_SIZE = 3;
    private static final char HUMAN = 'O';
    private static final char AI = 'X';
    private static final char EMPTY = ' ';
    private final JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
    private final JLabel statusLabel = new JLabel("Your turn! (O)");
    private final JPanel gamePanel = new JPanel();
    private final JPanel controlPanel = new JPanel();
    private final JLabel scoreLabelComponent = new JLabel("Score: You 0 - 0 AI");
    private final JComboBox<String> firstPlayerCombo = new JComboBox<>(new String[] {"Player", "AI", "Random"});
    private final char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
    private boolean gameActive = true;
    private int humanWins = 0;
    private int aiWins = 0;
    private int draws = 0;
    private enum Difficulty { EASY, MEDIUM, HARD }
    private Difficulty currentDifficulty = Difficulty.HARD;
    private enum FirstPlayer { PLAYER, AI, RANDOM }
    private FirstPlayer currentFirstPlayer = FirstPlayer.PLAYER;
    private final Color bgColor = new Color(30, 30, 30);
    private final Color boardColor = new Color(50, 50, 50);
    private final Color buttonColor = new Color(70, 70, 70);
    private final Color buttonHoverColor = new Color(90, 90, 90);
    private final Color textColor = new Color(220, 220, 220);
    private final Color humanColor = new Color(0, 173, 181);
    private final Color aiColor = new Color(255, 50, 50);
    private final Font gameFont = new Font("Arial", Font.BOLD, 60);
    private final Font statusFont = new Font("Arial", Font.BOLD, 16);

    public Main() {
        setTitle("Tic-Tac-Toe");
        setSize(600, 720);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(bgColor);
        initializeControlPanel();
        initializeGamePanel();
        initializeStatusBar();
        resetBoard();
        determineFirstMove();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeControlPanel() {
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        controlPanel.setBackground(bgColor);

        JButton newGameButton = createStyledButton();
        newGameButton.addActionListener(e -> resetGame());

        JComboBox<String> difficultyCombo = getStringJComboBox();

        JLabel difficultyLabel = createStyledLabel("Difficulty: ");
        JLabel firstPlayerLabel = createStyledLabel("First Player: ");
        scoreLabelComponent.setForeground(textColor);
        scoreLabelComponent.setFont(new Font("Arial", Font.BOLD, 14));
        updateScoreLabel();

        firstPlayerCombo.setForeground(textColor);
        firstPlayerCombo.setBackground(buttonColor);
        firstPlayerCombo.setFocusable(false);
        firstPlayerCombo.setSelectedIndex(0);
        firstPlayerCombo.addActionListener(e -> {
            switch (firstPlayerCombo.getSelectedIndex()) {
                case 1 -> currentFirstPlayer = FirstPlayer.AI;
                case 2 -> currentFirstPlayer = FirstPlayer.RANDOM;
                default -> currentFirstPlayer = FirstPlayer.PLAYER;
            }
        });


        controlPanel.add(newGameButton);
        controlPanel.add(difficultyLabel);
        controlPanel.add(difficultyCombo);
        controlPanel.add(firstPlayerLabel);
        controlPanel.add(firstPlayerCombo);
        controlPanel.add(scoreLabelComponent);

        add(controlPanel, BorderLayout.NORTH);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(textColor);
        return label;
    }

    private JComboBox<String> getStringJComboBox() {
        JComboBox<String> difficultyCombo = new JComboBox<>(new String[] {"Easy", "Medium", "Hard"});
        difficultyCombo.setForeground(textColor);
        difficultyCombo.setBackground(buttonColor);
        difficultyCombo.setFocusable(false);
        difficultyCombo.setSelectedIndex(2);
        difficultyCombo.addActionListener(e -> {
            switch (difficultyCombo.getSelectedIndex()) {
                case 0 -> currentDifficulty = Difficulty.EASY;
                case 1 -> currentDifficulty = Difficulty.MEDIUM;
                default -> currentDifficulty = Difficulty.HARD;
            }
        });
        return difficultyCombo;
    }

    private JButton createStyledButton() {
        JButton button = new JButton("New Game");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBackground(buttonColor);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(buttonHoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(buttonColor);
            }
        });
        return button;
    }

    private void initializeGamePanel() {
        gamePanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE, 10, 10));
        gamePanel.setBackground(bgColor);
        gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(gameFont);
                buttons[i][j].setForeground(textColor);
                buttons[i][j].setBackground(boardColor);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(bgColor, 0));

                buttons[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        JButton button = (JButton)evt.getSource();
                        if (button.getText().isEmpty() && gameActive) {
                            button.setBackground(boardColor.brighter());
                        }
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        JButton button = (JButton)evt.getSource();
                        if (button.getText().isEmpty()) {
                            button.setBackground(boardColor);
                        }
                    }
                });

                final int row = i, col = j;
                buttons[i][j].addActionListener(e -> humanMove(row, col));
                gamePanel.add(buttons[i][j]);
            }
        }
        add(gamePanel, BorderLayout.CENTER);
    }

    private void initializeStatusBar() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        statusPanel.setBackground(bgColor);

        statusLabel.setFont(statusFont);
        statusLabel.setForeground(textColor);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        statusPanel.add(statusLabel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private void determineFirstMove() {
        switch (currentFirstPlayer) {
            case AI -> makeAIFirstMove();
            case RANDOM -> {
                if (new Random().nextInt(2) == 0) {
                    makeAIFirstMove();
                } else {
                    statusLabel.setText("Your turn! (O)");
                }
            }
            case PLAYER -> statusLabel.setText("Your turn! (O)");
        }
    }

    private void makeAIFirstMove() {
        statusLabel.setText("AI's turn... thinking");
        Timer timer = new Timer(500, e -> {
            int[] firstMove;
            if (new Random().nextBoolean()) {
                firstMove = new int[]{1, 1};
            } else {
                int[] corners = {0, 2};
                firstMove = new int[]{
                        corners[new Random().nextInt(2)],
                        corners[new Random().nextInt(2)]
                };
            }
            makeMove(firstMove[0], firstMove[1], AI);
            statusLabel.setText("Your turn! (O)");
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void humanMove(int row, int col) {
        if (gameActive && board[row][col] == EMPTY) {
            makeMove(row, col, HUMAN);
            Character result = checkWinner();
            if (result != null) {
                endGame(result);
                return;
            }
            statusLabel.setText("AI's turn... thinking");
            Timer timer = new Timer(400, e -> {
                if (gameActive) {
                    aiMove();
                    Character result1 = checkWinner();
                    if (result1 != null) {
                        endGame(result1);
                    } else {
                        statusLabel.setText("Your turn! (O)");
                    }
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void makeMove(int row, int col, char player) {
        board[row][col] = player;
        buttons[row][col].setText(String.valueOf(player));
        if (player == HUMAN) {
            buttons[row][col].setForeground(humanColor);
        } else {
            buttons[row][col].setForeground(aiColor);
        }
    }

    private void aiMove() {
        int[] bestMove;
        switch (currentDifficulty) {
            case EASY -> bestMove = makeRandomMove();
            case MEDIUM -> bestMove = minimaxMove(4);
            default -> bestMove = minimaxMove(Integer.MAX_VALUE);
        }
        makeMove(bestMove[0], bestMove[1], AI);
    }

    private int[] makeRandomMove() {
        List<int[]> availableMoves = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    availableMoves.add(new int[]{i, j});
                }
            }
        }
        if (!availableMoves.isEmpty()) {
            return availableMoves.get(new Random().nextInt(availableMoves.size()));
        }
        return new int[]{-1, -1};
    }

    private int[] minimaxMove(int depth) {
        int bestScore = Integer.MIN_VALUE;
        int[] move = {-1, -1};
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = AI;
                    int score = minimax(depth, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = EMPTY;
                    if (score > bestScore) {
                        bestScore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }
        return move;
    }

    private int minimax(int maxDepth, int depth, boolean isMaximizing, int alpha, int beta) {
        Character result = checkWinner();
        if (result != null) {
            return switch (result) {
                case AI -> 10 - depth;
                case HUMAN -> depth - 10;
                default -> 0;
            };
        }
        if (depth >= maxDepth) {
            return evaluateBoard();
        }

        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = AI;
                        int score = minimax(maxDepth, depth + 1, false, alpha, beta);
                        board[i][j] = EMPTY;
                        bestScore = Math.max(score, bestScore);
                        alpha = Math.max(alpha, bestScore);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = HUMAN;
                        int score = minimax(maxDepth, depth + 1, true, alpha, beta);
                        board[i][j] = EMPTY;
                        bestScore = Math.min(score, bestScore);
                        beta = Math.min(beta, bestScore);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
        }
        return bestScore;
    }

    private int evaluateBoard() {
        int score = 0;
        score += evaluateLine(0, 0, 0, 1, 0, 2);
        score += evaluateLine(1, 0, 1, 1, 1, 2);
        score += evaluateLine(2, 0, 2, 1, 2, 2);
        score += evaluateLine(0, 0, 1, 0, 2, 0);
        score += evaluateLine(0, 1, 1, 1, 2, 1);
        score += evaluateLine(0, 2, 1, 2, 2, 2);
        score += evaluateLine(0, 0, 1, 1, 2, 2);
        score += evaluateLine(0, 2, 1, 1, 2, 0);
        return score;
    }

    private int evaluateLine(int r1, int c1, int r2, int c2, int r3, int c3) {
        int value = 0;
        int aiCount = 0;
        int humanCount = 0;
        if (board[r1][c1] == AI) aiCount++;
        if (board[r1][c1] == HUMAN) humanCount++;
        if (board[r2][c2] == AI) aiCount++;
        if (board[r2][c2] == HUMAN) humanCount++;
        if (board[r3][c3] == AI) aiCount++;
        if (board[r3][c3] == HUMAN) humanCount++;
        if (aiCount == 2 && humanCount == 0) value = 2;
        else if (aiCount == 1 && humanCount == 0) value = 1;
        else if (humanCount == 2 && aiCount == 0) value = -2;
        else if (humanCount == 1 && aiCount == 0) value = -1;
        return value;
    }

    private Character checkWinner() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] != EMPTY && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
            if (board[0][i] != EMPTY && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i];
            }
        }
        if (board[0][0] != EMPTY && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] != EMPTY && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }
        boolean isBoardFull = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    isBoardFull = false;
                    break;
                }
            }
        }
        return isBoardFull ? 'D' : null;
    }

    private void endGame(Character result) {
        gameActive = false;
        String message;
        if (result == 'D') {
            message = "It's a Draw!";
            draws++;
            statusLabel.setText("Game Over - Draw");
        } else if (result == HUMAN) {
            message = "You Win!";
            humanWins++;
            statusLabel.setText("Game Over - You Win!");
            highlightWinningCells();
        } else {
            message = "AI Wins!";
            aiWins++;
            statusLabel.setText("Game Over - AI Wins!");
            highlightWinningCells();
        }
        updateScoreLabel();
        Timer timer = getTimer(message);
        timer.start();
    }

    private Timer getTimer(String message) {
        Timer timer = new Timer(300, e -> {
            int choice = JOptionPane.showOptionDialog(
                    this,
                    message + "\nPlay again?",
                    "Game Over",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Yes", "No"},
                    "Yes"
            );
            if (choice == JOptionPane.YES_OPTION) {
                resetGame();
            }
        });
        timer.setRepeats(false);
        return timer;
    }

    private void updateScoreLabel() {
        scoreLabelComponent.setText("Score: You " + humanWins + " - " + aiWins + " AI (Draws: " + draws + ")");
    }

    private void highlightWinningCells() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] != EMPTY && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                highlightButtons(buttons[i][0], buttons[i][1], buttons[i][2]);
                return;
            }
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[0][i] != EMPTY && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                highlightButtons(buttons[0][i], buttons[1][i], buttons[2][i]);
                return;
            }
        }
        if (board[0][0] != EMPTY && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            highlightButtons(buttons[0][0], buttons[1][1], buttons[2][2]);
            return;
        }
        if (board[0][2] != EMPTY && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            highlightButtons(buttons[0][2], buttons[1][1], buttons[2][0]);
        }
    }

    private void highlightButtons(JButton... btns) {
        Color winColor = new Color(255, 204, 0, 150);
        for (JButton btn : btns) {
            btn.setBackground(winColor);
        }
    }

    private void resetGame() {
        resetBoard();
        gameActive = true;
        determineFirstMove();
    }

    private void resetBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY;
                buttons[i][j].setText("");
                buttons[i][j].setBackground(boardColor);
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Error setting look and feel: " + e.getMessage());
        }
        SwingUtilities.invokeLater(Main::new);
    }
}