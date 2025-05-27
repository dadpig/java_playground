package org.example;

public class Nqueen {

    private int[][] board;
    private int size;

    public Nqueen(int size) {
        this.size = size;
        this.board = new int[size][size];
    }

    public void solve(int size) {
        if (placeQueens(size)) {
            printBoard();
        } else {
            System.out.println("There is no solution...");
        }
    }
    public boolean placeQueens(int row) {
        if (row == size) {
            return true;
        }
        for (int i = 0; i < size; i++) {
            if (check(row, i)) {
                board[row][i] = 1;
                if (placeQueens(row + 1)) {
                    return true;
                }
                board[row][i] = 0;
            }
        }
        return false;
    }
    public boolean check(int row, int index){
        for (int i = 0; i < row; i++) {
            if (board[i][index] == 1) {
                return false;
            }
        }
        for (int i = row, j = index; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        for (int i = row, j = index; i >= 0 && j < size; i--, j++) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        return true;
    }

    public void printBoard() {
        for(int i=0; i<size; i++){
            System.out.println("-".repeat(size));
            String line = "";
            for (int j=0; j<size; j++){
                line +=board[i][j] + " ";
            }
            System.out.println(line+"\n");
        }
    }

}
