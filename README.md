# Tic-Tac-Toe Java Swing Game

## Overview

This project is a simple Tic-Tac-Toe game implemented in Java using Swing for the graphical user interface (GUI). You can play against an AI opponent with adjustable difficulty levels. The game features a clean and intuitive interface with score tracking and customizable first player options.

## Features

*   **Interactive GUI:**  A user-friendly graphical interface built with Java Swing.
*   **AI Opponent:** Play against a computer AI with three difficulty levels:
    *   **Easy:** AI makes random moves.
    *   **Medium:** AI uses a simplified Minimax algorithm (limited depth).
    *   **Hard:** AI uses the Minimax algorithm with alpha-beta pruning for optimal play.
*   **Difficulty Selection:** Choose the AI difficulty level via a dropdown menu in the game interface.
*   **First Player Choice:** Decide who goes first: Player, AI, or Randomly selected at the start of each game.
*   **Score Tracking:** Keeps track of your wins, AI wins, and draws across multiple games.
*   **Game Status Messages:**  Clear status messages inform you about whose turn it is and the game outcome.
*   **Visual Feedback:**  Highlights winning lines and provides subtle button hover effects for better user experience.
*   **Customizable Look and Feel:**  The game uses a dark theme with customizable colors for the background, buttons, and text, enhancing visual appeal.
*   **New Game Option:** Easily start a new game with the click of a button.
*   **Game Over Dialog:**  A dialog box appears at the end of each game, prompting you to play again.

## How to Run

1.  **Prerequisites:**
    *   **Java Development Kit (JDK):** Ensure you have Java JDK installed on your system. You can download it from [Oracle's website](https://www.oracle.com/java/technologies/javase-jdk-downloads.html) or use an open-source distribution like [OpenJDK](https://openjdk.java.net/).
    *   **IDE (Optional but Recommended):** While not strictly necessary, using an Integrated Development Environment (IDE) like IntelliJ IDEA, Eclipse, or NetBeans is highly recommended for easier project management, compilation, and execution.

2.  **Download or Clone the Project:**
    *   If you have the code as individual files, ensure they are placed in a directory structure that reflects the package structure (`org/example/`).
    *   If you are using Git, you can clone the repository containing this code.

3.  **Compile the Code:**
    *   **Using an IDE:** Open the project in your IDE. Most IDEs will automatically detect the Java project and handle compilation. You can usually build or compile the project from the IDE's menu (e.g., "Build Project" in IntelliJ IDEA).
    *   **Using the Command Line:**
        *   Navigate to the project's root directory (the directory containing the `org` folder) in your terminal or command prompt.
        *   Compile the `Main.java` file using the `javac` command:
            ```bash
            javac org/example/Main.java
            ```
            This will create `Main.class` file in the `org/example/` directory.

4.  **Run the Game:**
    *   **Using an IDE:**  In your IDE, locate the `Main.java` file (or the `Main` class). You should be able to right-click on it and select "Run 'Main.main()'" or a similar option to execute the program.
    *   **Using the Command Line:**
        *   From the project's root directory (where you ran `javac`), execute the compiled class using the `java` command, making sure to include the package name:
            ```bash
            java org.example.Main
            ```

5.  **Play the Game:**
    *   Once the program runs, the Tic-Tac-Toe game window will appear.
    *   Click on the empty squares on the game board to make your moves (represented by 'O').
    *   The AI's moves are represented by 'X'.
    *   Use the dropdown menus at the top to change the difficulty and first player.
    *   Click the "New Game" button to start a fresh game.

## Controls

*   **Mouse Clicks:** Click on the empty squares on the Tic-Tac-Toe grid to place your 'O' mark.
*   **Dropdown Menus:** Use the "Difficulty" and "First Player" dropdowns to adjust game settings.
*   **"New Game" Button:** Click this button to reset the board and start a new game with the current settings.

## Dependencies

*   **Java Swing:**  The game is built using Java Swing, which is part of the standard Java libraries. No external libraries are required.

## Customization

*   **Colors:** You can easily customize the game's colors by modifying the `Color` variables in the `Main.java` file:
    *   `bgColor` (Background color)
    *   `boardColor` (Board grid color)
    *   `buttonColor` (Button color)
    *   `buttonHoverColor` (Button hover color)
    *   `textColor` (Text color)
    *   `humanColor` (Human player 'O' color)
    *   `aiColor` (AI player 'X' color)
*   **Font:**  Change the fonts used in the game by modifying the `Font` variables:
    *   `gameFont` (Font for 'X' and 'O' marks)
    *   `statusFont` (Font for status messages)
*   **Board Size:** While the current code is set to a 3x3 board (`BOARD_SIZE = 3`), you could potentially modify this constant and adapt the UI layout for different board sizes, although further adjustments might be needed for optimal display.


## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.