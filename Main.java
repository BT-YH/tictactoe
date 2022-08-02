/**
 * TicTacToe Stage 4
 *
 * @author (Barry Tang)
 * @version (July 28, 2022)
 */

import java.util.Scanner;
import java.util.Arrays;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Game newGame = new Game();
        startGame(newGame);
    }
    
    public static void startGame(Game newGame) {
        newGame.printGrid();
        newGame.checkGame();
        System.out.println("Please enter your move in the format \"row column\". ");
        while (newGame.getResultIndex() == 2) {
            newGame.promptMove();
            newGame.printGrid();
            newGame.checkGame();
        }
        newGame.printResult();
    }
    
    public static void checkGame(Game newGame){
        newGame.initializexo();
        newGame.setField();
        newGame.printGrid();
        newGame.printResult();
        
        if(newGame.getResultIndex() == 2) {
            newGame.promptMove();
        }
        
        newGame.printGrid();
        newGame.printResult();
    }
    
    
}

class Game{
    private final String[] results = {
        "Impossible",
        "Draw",
        "Game not finished",
        "O wins",
        "X wins"};
        
    private char[] xo;
    private char[][] field = {{'_', '_', '_'},
                              {'_', '_', '_'},
                              {'_', '_', '_'}};
    
    private int xs = 0;
    private int os = 0;
    private Boolean xxx = false;
    private Boolean ooo = false;
    
    private int resultIndex;
    
    private int[] nextMove;
    
    private int currentPlayer = 0;
    
    public int getResultIndex() {
        return resultIndex;
    }
    
    public void initializexo() {
        Scanner in = new Scanner(System.in);
        
        System.out.print("> ");
        xo = in.nextLine().toCharArray();      

    }
    
    
    public void setField() {
        for (int i = 0; i < 9; i++) {
            field[i/3][i%3] = xo[i];
            
            if (xo[i] == 'X') {
                xs++;
            } else if (xo[i] == 'O') {
                os++;
            }
        }
    }    
    
    public void promptMove() {
        
        Scanner in = new Scanner(System.in);
        
        String[] nextMove;
        boolean correct = false;
        int row, col = 0;
    
        while (!correct) {
            System.out.println("> ");
            String move = in.nextLine();
            nextMove = move.split(" ");
            
            if (nextMove.length != 2) {
                System.out.println("Wrong format! Please re-enter: ");
                continue;
            }
            
            try {
                row = Integer.parseInt(nextMove[0]);
                col = Integer.parseInt(nextMove[1]);
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
                System.out.println("Please re-enter: ");
                continue; // continue the loop
            }
            
            if (row < 1 || row > 3 || col < 1 || col > 3){
                System.out.println("Coordinates should be from 1 to 3!");
                System.out.println("Please re-enter: ");
                continue;
            }
            
            if (field[row - 1][col - 1] != '_') {
                System.out.println("This cell is occupied! Choose another one!");
                System.out.println("Please re-enter: ");
                continue;
            }
            
            if (currentPlayer == 0) {
                moveX(row - 1, col - 1);
                currentPlayer++;
                xs++;
            } else if (currentPlayer == 1) {
                moveO(row - 1, col - 1);
                currentPlayer--;
                os++;
            }
            correct = true;
        }
 
    }
    
    private void moveX(int row, int col) {
        field[row][col] = 'X';
    }
    
    private void moveO(int row, int col) {
        field[row][col] = 'O';
    }
    
    public void printGrid() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.println(String.format("| %c %c %c |", field[i][0], field[i][1], field[i][2]));
        }
        System.out.println("---------");
    }
    
    public void printResult() {
        checkGame();
        System.out.println(results[resultIndex]);
    }
    
    public void checkGame() {
        checkWin();
        resultIndex = Math.abs(xs-os) > 1 || xxx && ooo ? 0
                      : xxx ? 4
                      : ooo ? 3
                      : xs + os == 9 ? 1
                      : 2;
    }
    
    private void checkWin() {
        for (int i = 0; i < 3; i++) {
            int row = 0;
            int clm = 0;
            int mDiag = 0;
            int aDiag = 0;

            for (int j = 0; j < 3; j++) { 
                row += field[i][j];          //smart
                clm += field[j][i];
                mDiag += field[j][j];
                aDiag += field[j][2-j];
            }

            // ASCII value for X is 88 (X+X+X is 264)
            // ASCII value for O is 79 (O+O+O is 237)
            xxx = xxx || row == 264 || clm == 264 || mDiag == 264 || aDiag == 264;
            ooo = ooo || row == 237 || clm == 237 || mDiag == 237 || aDiag == 237;
        }
    }
}










