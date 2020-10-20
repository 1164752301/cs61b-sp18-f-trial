package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String s = "";
        for(int i = 0; i < n; i++) {
            char picked = CHARACTERS[rand.nextInt(CHARACTERS.length)];
            s += Character.toString(picked);
        }
        return s;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        StdDraw.clear();
        StdDraw.enableDoubleBuffering();
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.show();
        //TODO: If game is not over, display relevant game information at the top of the screen
        if (!gameOver) {
            Font smallFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(smallFont);
            StdDraw.textLeft(1, height - 1, "Round: " + round);
            StdDraw.text(width / 2, height - 1, playerTurn ? "Type!" : "Watch!");
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.line(0, height - 2, width, height - 2);
            StdDraw.show();
        }
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        playerTurn = false;
        for(int i = 0; i < letters.length(); i++) {
            String s = Character.toString(letters.charAt(i));
            drawFrame(s);
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        playerTurn = true;
        String result = "";
        drawFrame("");
        while (result.length() < n){
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            } else {
                result += Character.toString(StdDraw.nextKeyTyped());
                drawFrame(result);
            }
        }
        return result;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        this.round = 1;
        this.gameOver = false;
        //TODO: Establish Game loop
        while(!gameOver){
            drawFrame("Round: " + round);
            StdDraw.pause(500);
            String question = generateRandomString(round);
            flashSequence(question);
            String answer = solicitNCharsInput(round);
            if (question.equals(answer) == false) {
                gameOver = true;
            }
            round += 1;
        }
        drawFrame("Game Over! You made it to round: " + (round - 1));
    }

}
