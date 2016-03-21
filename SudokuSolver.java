/*
       Made by:

                  //////   ///       /////////  ///       //  ///    ///
               /////       ///          ///     /////     //  ///   ///
             /////         ///          ///     /// //    //  ///  ///
             /////         ///          ///     ///  //   //  //////
             /////         ///          ///     ///   //  //  /// ///
               /////       ///          ///     ///    /////  ///  ///
                 ///////   ////////  /////////  ///      ///  ///   ///
 */


import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SudokuSolver
{
    static int backtrack = 0;

    public static void main(String[] args)
    {
        final long startTime1 = System.currentTimeMillis();

        State initialState = new State();
        System.out.println("\nInitial State of the board: ");
        System.out.println(initialState);

        if(solveWithBackTracking(0, 0, initialState))
        {
            System.out.println("\nSolution State of the board: ");
            System.out.println(initialState);
            System.out.println("Final # of BackTracks: " + backtrack);
        }
        else
        {
            System.out.println("\nNo solution exists.");
        }

        final long endTime = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.println("Execution time is " + formatter.format((endTime - startTime1) / 1000d) + " seconds");
    }

    public static boolean solveWithBackTracking(int theRow, int theCol, State initial)
    {
        /*Traverse the board. If found a zero, then replace it
          with a number starting at 1 and check to see if it is
          valid. If it is not valid, continue with the for loop
        */

        //if the row inputted equals 9
        //then go to the next row
        if (theRow == 9)
        {
            theRow = 0;

            //if the next column equals 8
            if (theCol++ == 8)
            {
                //we solved the problem
                return true;
            }
        }

        //If not found an empty slot, then continue on
        if (initial.board[theRow][theCol] != 0)
        {
            //Go to the next row
            return solveWithBackTracking(theRow + 1, theCol, initial);
        }

        //Otherwise, did find an empty slot, so input
        //a number starting at one and test to see if
        //it is a valid number
        for (int val = 1; val < 10; val++)
        {
            if (initial.isValid(initial.board, theRow, theCol, val))
            {
                //if it is valid then go set the value
                //equal to the empty slot
                initial.board[theRow][theCol] = val;
                System.out.println(initial);
                if (solveWithBackTracking(theRow + 1, theCol, initial))
                {
                    return true;
                }
            }
        }

        //otherwise reset on the backtrack
        initial.board[theRow][theCol] = 0;
        backtrack++;
        System.out.println("\n\nBackTacking: " + backtrack + "\n\n");
        return false;
    }
}

class State
{
    int board[][] =
            {
                {4, 3, 0, 0, 6, 9, 7, 8, 0},
                {6, 0, 2, 5, 0, 1, 4, 9, 0},
                {0, 9, 7, 8, 0, 4, 0, 6, 2},
                {0, 2, 6, 0, 9, 5, 0, 4, 7},
                {3, 7, 0, 6, 0, 2, 9, 1, 0},
                {9, 0, 1, 7, 4, 0, 0, 2, 8},
                {0, 1, 9, 3, 2, 0, 8, 0, 4},
                {2, 0, 8, 0, 5, 7, 0, 3, 6},
                {7, 6, 0, 4, 1, 0, 2, 0, 9}
            };

//          Harder board just to test and
//          see if it works for all cases
//            {
//                    {0,0,2,3,0,8,4,0,0},
//                    {0,0,0,5,0,0,0,0,2},
//                    {5,0,0,0,0,0,9,8,1},
//                    {0,0,0,0,0,0,0,3,0},
//                    {0,0,0,7,2,5,0,0,0},
//                    {0,8,0,0,0,0,0,0,0},
//                    {1,4,3,0,0,0,0,0,6},
//                    {9,0,0,0,0,1,0,0,0},
//                    {0,0,6,8,0,4,1,0,0}
//            };

    int count;

    public State()
    {
       count = 0;
    }

    //Just a function that counts the number of zeroes
    public void count(State state)
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (state.board[i][j] == 0)
                {
                    count++;
                }
            }
        }
        System.out.println("Count: " + count);
    }

    //if there is a same number in a row then return true
    public boolean rowDuplicate(int board[][], int row, int num)
    {
        for (int col = 0; col < 9; col++)
        {
            if (board[row][col] == num)
            {
                return true;
            }
        }
        return false;
    }

    //if there is a same number in a col then return true
    public boolean colDuplicate(int board[][], int col, int num)
    {
        for (int row = 0; row < 9; row++)
        {
            if (board[row][col] == num)
            {
                return true;
            }
        }
        return false;
    }

    //if there is a same number in a box then return true
    public boolean boxDuplicate(int board[][], int startRow, int startCol, int num)
    {
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                if (board[row + startRow][col + startCol] == num)
                {
                    return true;
                }
            }
        }
        return false;
    }

    //Determines if adding a number does not cause
    //a duplicate number in any case
    public boolean isValid(int board[][], int row, int col, int num)
    {
        System.out.println("Testing: " + num);
        if(!rowDuplicate(board, row, num) && !colDuplicate(board, col, num) && !boxDuplicate(board, (row - row % 3), (col - col % 3), num))
        {
            System.out.println("Inputting: " + num);
            return true;
        }
        System.out.println("It is not valid.");
        return false;
    }

    public String toString()
    {
        String output = "";

        for (int row = 0; row < board.length; row++)
        {
            if (row % 3 == 0)
            {
                output += "-------------------------";
                output += "\n";
            }
            for (int col = 0; col < board.length; col++)
            {
                if (col % 3 == 0)
                {
                    output += "| ";
                }

                output += (board[row][col] + " ");
            }
            output += "| ";
            output += "\n";
        }
        output += "-------------------------";
        output += "\n";
        return output;
    }
}