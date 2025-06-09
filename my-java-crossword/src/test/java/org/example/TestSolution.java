package org.example;

import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestSolution {
    private static Solution solution;

    @BeforeAll
    static void setup(){
        String message = "RSPNJAFQTKYFGBMRJTOZGPQEWKODQVSRDTCHKPLUSOWTBAEXMMABBEAMZXRPCIMRTUUZWTYNTPMOCACHTHPOAKGPRHHVVFNMKIQG";
        solution = new Solution(message);
    }
    @Test
    public void testSuccess_BATMAN(){
        String word = "BATMAN";
        String expected = "r4:9 c4:4";
        String result = solution.findWordInMatrix(word);
        assertEquals(expected, result);
        System.out.println(word+" "+result);
    }
    @Test
    public void testSuccess_ROBIN(){
        String word = "ROBIN";
        String expected = "r3:7 c1:1";
        String result = solution.findWordInMatrix(word);
        assertEquals(expected, result);
        System.out.println(word+" "+result);
    }

    @Test
    public void testSuccess_BEAM(){
        String word = "BEAM";
        String expected = "r5:5 c2:5";
        String result = solution.findWordInMatrix(word);
        assertEquals(expected, result);
        System.out.println(word+" "+result);
    }

    @Test
    public void testSuccess_JOKEZ(){
        String word = "JOKEZ";
        String expected = "r1:5 c6:6";
        String result = solution.findWordInMatrix(word);
        assertEquals(expected, result);
        System.out.println(word+" "+result);
    }

    @Test
    public void testSuccess_PAZ(){
        String word = "PAZ";
        String expected = "r8:6 c7:7";
        String result = solution.findWordInMatrix(word);
        assertEquals(expected, result);
        System.out.println(word+" "+result);
    }

    @Test
    public void testSuccess_DOK(){
        String word = "DOK";
        String expected = "r2:2 c7:5";
        String result = solution.findWordInMatrix(word);
        assertEquals(expected, result);
        System.out.println(word+" "+result);
    }

    @Test
    public void testSuccess_CACOM(){
        String word = "CACOM";
        String expected = "r7:7 c8:4";
        String result = solution.findWordInMatrix(word);
        assertEquals(expected, result);
        System.out.println(word+" "+result);
    }

    @Test
    public void testFail_ALEX(){
        String word = "ALEX";
        String expected = "not found";
        String result = solution.findWordInMatrix(word);
        assertEquals(expected, result);
        System.out.println(word+" "+result);
    }


}
