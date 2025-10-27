// Aaron Lee
// CS 143
// Sudoku board: ...
//
// This program will take a file and load into a 2d array, making it a sudoku board if there are 9 in each row or column

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SudokuBoard {
    public int[][] board;
    public SudokuBoard(String fileName) throws FileNotFoundException {
        board = new int[9][9];
        File file = new File(fileName);
        Scanner input = new Scanner(file);

        int row = 0;
        while (input.hasNextLine() && row < 9) {
            String line = input.nextLine().trim();

            if (line.length() != 9) {
                throw new IllegalArgumentException("needs to have 9 characters");
            }

            for (int col = 0; col < 9; col++) {
                char ch = line.charAt(col);
                if (ch == '.') {
                    board[row][col] = 0;
                } else if (ch >= '1' && ch <= '9') {
                    board[row][col] = ch - '0';
                } else {
                    throw new IllegalArgumentException("Invalid character: " + ch);
                }
            }

            row++;
        }

        if (row != 9) {
            throw new IllegalArgumentException("needs to have 9 rows");
        }

        input.close();
    }

//pre board of 9x9
//post adds lines before the numbers every 3
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      String line = "-------------------------\n";

      for (int row = 0; row < 9; row++) {
        if (row % 3 == 0) {
            sb.append(line);
        }
        for (int col = 0; col < 9; col++) {
            if (col % 3 == 0) {
                sb.append("| ");
            }
            int num = board[row][col];
            if (num == 0) {
                sb.append(". ");
            } else {
                sb.append(num + " ");
            }
        }

        sb.append("|\n");
    }

      sb.append(line);
      return sb.toString();
   }
   public boolean isValid() {
    Set<Integer> valid = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    for (int r = 0; r < board.length; r++) {
        for (int c = 0; c < board[r].length; c++) {
            if (!valid.contains(board[r][c])) {
                return false;
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
            mini[r][c] = board[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
         }
      }
      return mini;
   }
   
   public boolean isSolved() {
      Map<Integer, Integer> counts = new HashMap<>();
      for (int i = 1; i <= 9; i++) {
        counts.put(i, 0);
      }

      for (int x = 0; x < board.length; x++) {
         for (int j = 0; j < board[x].length; j++) {
            int val = board[x][j];
            if (val >= 1 && val <= 9) {
                counts.put(val, counts.get(val) + 1);
            }
        }
      }

      for (int i = 1; i <= 9; i++) {
        if (counts.get(i) != 9) return false;
      }

      return true;
   }
}
