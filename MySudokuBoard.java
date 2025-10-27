/*
   This is started code for Crystal's Sudoku #2.
   The code is not pretty, but it works.
*/

import java.util.*;
import java.io.*;

public class MySudokuBoard {
   public final int SIZE = 9;
   protected int[][] myBoard;
   
   public MySudokuBoard(String theFile) {
      myBoard = new int[SIZE][SIZE];
      try {
         Scanner file = new Scanner(new File(theFile));
         for(int row = 0; row < SIZE; row++) {
            String theLine = file.nextLine();
            for(int col = 0; col < theLine.length(); col++) {
               myBoard[row][col] = theLine.charAt(col);
            }
         }
      } catch(Exception e) {
         System.out.println("Something went wrong :(");
         e.printStackTrace();
      }
   }
   
   public String toString() {
      String result = "My Board:\n\n";
      for(int row = 0; row < SIZE; row++) {
         for(int col = 0; col < SIZE; col++) {
            result += (myBoard[row][col]);
         }
         result += ("\n");
      }
      return result;
   }
   public boolean isValid() {
        for (int i = 0; i < SIZE; i++) {
            boolean[] rowSeen = new boolean[10];
            boolean[] colSeen = new boolean[10];
            boolean[] boxSeen = new boolean[10];

            for (int j = 0; j < SIZE; j++) {
                // ----- Row -----
                int rowChar = myBoard[i][j];
                if (rowChar != '.' && rowChar != '0') {
                    if (rowChar < '1' || rowChar > '9') return false;
                    int val = rowChar - '0';
                    if (rowSeen[val]) return false;
                    rowSeen[val] = true;
                }

                // ----- Column -----
                int colChar = myBoard[j][i];
                if (colChar != '.' && colChar != '0') {
                    if (colChar < '1' || colChar > '9') return false;
                    int val = colChar - '0';
                    if (colSeen[val]) return false;
                    colSeen[val] = true;
                }

                // ----- 3×3 Box -----
                int boxRow = 3 * (i / 3) + (j / 3);
                int boxCol = 3 * (i % 3) + (j % 3);
                int boxChar = myBoard[boxRow][boxCol];
                if (boxChar != '.' && boxChar != '0') {
                    if (boxChar < '1' || boxChar > '9') return false;
                    int val = boxChar - '0';
                    if (boxSeen[val]) return false;
                    boxSeen[val] = true;
                }
            }
        }
        return true;
   }
   private int[][] miniSquare(int spot) {
      int[][] mini = new int[3][3];
      for(int r = 0; r < 3; r++) {
         for(int c = 0; c < 3; c++) {
            // whoa - wild! This took me a solid hour to figure out (at least)
            // This translates between the "spot" in the 9x9 Sudoku board
            // and a new mini square of 3x3
            mini[r][c] = myBoard[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
         }
      }
      return mini;
   }
   
    public boolean isSolved() {
        if (!isValid()) return false;

        for (int i = 0; i < SIZE; i++) {
            boolean[] rowSeen = new boolean[10];
            boolean[] colSeen = new boolean[10];
            boolean[] boxSeen = new boolean[10];

            for (int j = 0; j < SIZE; j++) {
                int rowChar = myBoard[i][j];
                int colChar = myBoard[j][i];
                int boxRow = 3 * (i / 3) + (j / 3);
                int boxCol = 3 * (i % 3) + (j % 3);
                int boxChar = myBoard[boxRow][boxCol];

                // Must all be digits 1–9
                if (rowChar < '1' || rowChar > '9') return false;
                if (colChar < '1' || colChar > '9') return false;
                if (boxChar < '1' || boxChar > '9') return false;

                int rowVal = rowChar - '0';
                int colVal = colChar - '0';
                int boxVal = boxChar - '0';

                rowSeen[rowVal] = true;
                colSeen[colVal] = true;
                boxSeen[boxVal] = true;
            }

            for (int k = 1; k <= 9; k++) {
                if (!rowSeen[k] || !colSeen[k] || !boxSeen[k]) return false;
            }
        }
        return true;
    }
}