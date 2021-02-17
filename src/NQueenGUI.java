// NQueen Problem in JavaFX
// Class NQueenGUI
// Davis Insua

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.lang.Math;
import javafx.geometry.*;
import java.util.ArrayList;

public class NQueenGUI extends Application
{
   public static void main(String[] args)
   {
      launch(args);
   }
   
   // Create labels
   Label topLabel = new Label("Enter Board Size (2 <= N <= 20):");
   
   // Make textfields
   TextField enterText = new TextField();
   
   // Create Solve button
   Button solve = new Button("Solve");
   
   @Override
   public void start(Stage myStage)
   {
      // Stage options
      myStage.setTitle("NQueen");
      myStage.setResizable(false);
      
      // Create layout
      VBox root = new VBox();
      root.setAlignment(Pos.CENTER);
      root.getChildren().addAll(topLabel, enterText, solve);
      
      // Set action for solve button
      solve.setOnAction(e->handleSolveButton());
      
      // Create scene and show it
      Scene scene = new Scene(root, 225, 92, Color.WHITE);
      myStage.setScene(scene);
      myStage.show();
   }
   
   public void handleSolveButton()
   {
      try
      {
         // Get size of board
         int boardSize = Integer.parseInt(enterText.getText());
         // Call SolveWindow constructor, pass board size
         SolveWindow window = new SolveWindow(boardSize);
      }
      
      // Handle characters other than numbers being entered in the box
      catch(NumberFormatException ex)
      {  
         // Reset text field
         enterText.setText("");
      }
   }
}

class SolveWindow extends Stage
{   
   // Declare variable N to be used by the Solve window constructor and helper functions.
   public static int N;
   
   // SolveWindow constructor
   public SolveWindow(int N)
   {
      // Update N with passed value, define range variable
      this.N = N;
      boolean in_range = true;

      // Create stage, options
      Stage solveWindow = new Stage();
      solveWindow.setTitle("Solve "+N+" "+"x "+N+" Queen Problem");
      solveWindow.setResizable(true);
      
      // Create an Arraylist to hold all board/grid button elements.
      ArrayList<Button> gridElements = new ArrayList<Button>(N * N);
      
      // Create GridPane Layout to hold the grid button elements
      GridPane answerBoard = new GridPane();
      answerBoard.setAlignment(Pos.CENTER);
      
      // Create board for solver code, use abs to handle negative number error
      int[][] boardInt = new int[Math.abs(N)][Math.abs(N)];
      
      // Create GUI buttons for solverWindow
      Button close = new Button("Close");
      Label solution = new Label("Temporary");
      
      // Give confirm button action to close
      close.setOnAction(e->solveWindow.close());
      
      // if statement to not allow numbers not in range.
      if (N < 2 || N > 20 || N < 0)
      {
         solution.setText("Number not in range!");
         in_range = false;
      }
      
      // if the number is in range, this else statement will run
      else
      {
         // call helper function solveNQueen, run code below if it returns true
         if(solveNQueen(boardInt,0))
         {
            solution.setText("Has Solution!"); 
            // Counter for loop
            int x = 0;
            
            // Nested loops to add the grid buttons to the gridpane layout
            // Loop through row
            for (int i = 0; i < N; i++)
            {
               // Loop through col
               for (int j = 0; j < N; j++)
               {
                   // If the element in board position is 1
                   if (boardInt[i][j] == 1)
                   {
                       // Add the new "1" button to the arraylist
                       gridElements.add(new Button("1"));
                       // Style
                       gridElements.get(x).setStyle("-fx-background-color: #c900ff");
                       // Add to the gridpane layout
                       answerBoard.add(gridElements.get(x), j, i);
                       // Increment counter
                       x++;
                   }
                   
                   // If the element in board position is 0
                   else
                   {
                       // Similar logic to above
                       gridElements.add(new Button("0"));
                       answerBoard.add(gridElements.get(x), j, i);
                       x++;
                   }
               }
            }      
         }
         
         // Will run if solveNQueen returned false; no solution.
         else
            solution.setText("No Solution :("); 
      } 
     
      // Create layout
      VBox vBox = new VBox();
      vBox.setAlignment(Pos.CENTER);
      vBox.setSpacing(5);
      vBox.getChildren().addAll(solution,answerBoard,close);

      // Set adaptive window size
       int w_size = 110;
       // For sizes of N greater than 3, the window size will adapt to the N size.
       if(N > 3 && in_range)
           w_size = N * 40;

      // Create scene, using ternary operator to increase window size for larger values of N
      Scene scene = new Scene(vBox, w_size,  w_size);
      solveWindow.setScene(scene);
      solveWindow.show();
   }
  
  // Helper function for SolveNQueen, returns true if space is safe and false otherwise.
  public static boolean isSafe(int board[][], int row, int col)
  {
      // Loop through row
      for (int i = 0; i < N; i++)
      {
         // Loop through col
         for (int j = 0; j < N; j++)
         {
            // found space with queen
            if (board[i][j] == 1)
            {
               // Conditions that would make sapace unsafe
               if (i == row) return false;
               if (j == col) return false;
               if (Math.abs(i-row) == Math.abs(j-col)) return false;
            }
         }
      }
      // if no conditions that would make space unsafe are true, return false.
      return true;
  }
  
  // Helper function for solveWindow Function, produces solution for nqueen problem.
  public static boolean solveNQueen(int board[][], int col)
  {
       if (col >= N) return true;
       
       for (int row = 0; row < N; row++)
       {
           // Will run if space is safe
           if(isSafe(board, row, col))
           {
              // Place queen in position
              board[row][col] = 1;
              if (solveNQueen(board, col + 1)) return true;
              board[row][col] = 0;
           }       
       }
       return false;
  }
}