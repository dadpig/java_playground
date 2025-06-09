package org.example;

import java.util.Arrays;

public class Solution {
    private String[][] matrix;

    public Solution(String message){
        this.matrix = generateMatrix(message);
    }

    public String findWordInMatrix(String word){
        int wordSize = word.length();
        String firstLetter = word.substring(0, 1);
        String lastLetter = word.substring(word.length()-1);
        String reversedWord = reverse(word);

        for(int line=0; line<matrix.length; line++){
            for(int col=0; col<matrix[line].length; col++){
                if(firstLetter.equals(matrix[line][col])){
                    if (wordSize <= matrix[line].length - col){
                        if(check(word, Arrays.copyOfRange(matrix[line], col, matrix[line].length )))
                            return String.format("r%s:%s c%s:%s", line, line, col, col+wordSize-1);
                    }
                    if (wordSize <= matrix.length - line){
                        String[] vert = getStrings(matrix, wordSize, line, col);
                        if(check(word, vert))
                            return String.format("r%s:%s c%s:%s", line, line+wordSize-1, col, col);
                    }
                } else if(lastLetter.equals(matrix[line][col])){
                    if (wordSize <= matrix[line].length - col){
                        if(check(reversedWord, Arrays.copyOfRange(matrix[line], col, matrix[line].length )))
                            return String.format("r%s:%s c%s:%s", line, line, col+wordSize-1, col);
                    }
                    if (wordSize <= matrix.length - line){
                        String[] vert = getStrings(matrix, wordSize, line, col);
                        if(check(reversedWord, vert))
                            return String.format("r%s:%s c%s:%s", line+wordSize-1, line, col, col);
                    }
                }
            }
        }
        return "not found";
    }

    private String[] getStrings(String[][] matrix, int wordSize, int line, int col) {
        String[] vert = new String[wordSize];
        for(int i = 0; i< wordSize; i++){
            vert[i]= matrix[line +i][col];
        }
        return vert;
    }



    private boolean check(String word, String[] range){
        String string = "";
        for(int i = 0; i<word.length(); i++){
            string+=range[i];
        }
        return word.equals(string);
    }
    private String reverse(String word){
        String reversedWord = "";
        for(int i = word.length(); i>0; i--){
            reversedWord += ""+word.charAt(i-1);
        }
        return reversedWord;
    }

    private String[][] generateMatrix(String input){
        String[][] matrix = new String[10][10];
        String[] chunks = input.split("(?<=\\G.{10})");
        for (int line = 0; line<chunks.length; line++){
            String out = "";
            for(int col = 0; col<chunks[line].length(); col++){
                out += chunks[line].charAt(col)+" ";
                matrix[line][col]=""+chunks[line].charAt(col);
            }
            System.out.println(out);
        }
        return matrix;
    }
}