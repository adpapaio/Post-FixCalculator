/*
 * Title: Post Fix Calculator
 * Author: Aristotelis Papaioannou
 * Date Created: 11/16/2015
 * Description: Make a Post fix calculator using Javafx and Stacks
 */
package post.fix.calculator;

import java.util.Stack;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PostFixCalculator extends Application
{
    private Text label = new Text(""); //setting the screen to be blank
    private final Text bottom = new Text("          Post-Fix Calculator");//just the bottom of the window
    String finalAns;
    Boolean answerPres = false; //if the answer is on the screen
    Boolean errorPres = false;  //if the error is being displayed
    private final String[] allButs = {"7","8","9","/",
                                        "4","5","6","*","1","2",
                                        "3","-","C","0","_","+","Calculate"};//The string array for the buttons
       String input[];  //String for the user input
     
    @Override
    public void start(Stage primaryStage)
    {
        label.setFont(new Font("Century Gothic", 20)); //change the labael font

        GridPane grid = new GridPane(); 
        grid.setHgap(12);   //setting the horizontal gap between buttons
        grid.setVgap(10);   //setting the vertical gap between buttons 
        
        BorderPane border = new BorderPane(); //Initializing the new border
        border.setCenter(grid);             //center the grid
        border.setTop(label);               //set the label to display on top
        border.setBottom(bottom);           //Set the Name to the Bottom of the Window
        grid.setStyle("-fx-background-color: #d3d3d3;"); //Light Gray
        
        for(int i = 0, k = 0, j = 0, l = 0; i < allButs.length; i++)//For loop to create the button layout
        {
            //Button layout 
            // 7  8  9  /
            // 4  5  6  *
            // 1  2  3  -
            // C  0  _  + 
            // Calculate
            
            Button rowButt = new Button(allButs[i]);   //Creates the button
            
            if(i < 4)//Sets the first row of buttons
            {
               grid.add(rowButt, i + 1, 1);    
            }
            else if(i < 8 && i > 3)//Second row of Buttons
            {
               grid.add(rowButt, k + 1, 2);
               k++;
            }
            else if(i < 12 && i > 7)//Third row of Buttons
            {
                grid.add(rowButt, j + 1, 3);
                j++;
            }
            else if(i < 16 && i > 11)//Fourth row of Buttons
            {
                grid.add(rowButt, l + 1, 4);
                l++;
            }
            else//Set the row just for the Calculate Button
            {
                grid.add(rowButt, 2, 5);
                GridPane.setColumnSpan(rowButt,3);  //Setting the span of the button to keep it in the center
            }
            
        final int p = i;        //Finalizing the variable
        
        rowButt.setOnAction(new EventHandler<ActionEvent>() //Event handler
        {
            @Override
            public void handle(ActionEvent event)//Handle Button Click
            {
                try
                {
                butStack(allButs[p]); //Go to the Button Stack Method
                }
                catch(java.util.EmptyStackException e)//if the stack is empty when trying to calculate
                {
                    label.setText("ERROR: INV OP"); //tell the user there is an error
                    errorPres = true;
                    throw e;                        //throw the exception
                    
                }
            }
            
            //Method for the Buttons
            private void butStack(String variable)
            { 
             if("_".equals(variable)) //If the user clicks the space button
                variable = " "; //Make it a blank space rather than an underscore
                
            Stack<String> stack = new Stack();//The Stack that is going to be used
            
               
               if ("C".equals(variable)) //Clearing the calculator
               {
                   while(!stack.empty())    //While the Stack is not empty
                       stack.pop();         //Pop the stack to clear it
                   
                   Text clear = new Text();
                   clear.setFont(new Font("Century Gothic", 20)); //change the screen font
                   label = clear;           //Make the screen cleared
                   
               }
               else if("Calculate".equals(variable)) //Calculate the problem entered
               {
                   input = label.getText().split(" ");  //Make an array by splitting the string at the spaces
                   
                   for(int i = 0; i < input.length; i++)
                   {
                       
                       if(!"+".equals(input[i]) && !"-".equals(input[i]) && !"*".equals(input[i]) && !"/".equals(input[i]))
                       {
                           stack.push(input[i]);    //if the variable is a number put it on the stack
                       }
                       else if("+".equals(input[i]))    //Addition 
                       {
                            int numb2 = Integer.valueOf(stack.pop());
                            int numb1 = Integer.valueOf(stack.pop());

                            String addAns = Integer.toString(numb1 + numb2);
                            stack.push(addAns);     //Put the answer on the stack
                       }
                       else if("-".equals(input[i]))    //Subtraction
                       {
                           int numb2 = Integer.valueOf(stack.pop());
                           int numb1 = Integer.valueOf(stack.pop());
                           
                           String subAns = Integer.toString(numb1 - numb2);
                           stack.push(subAns);  //Put the answer on the stack
                       }
                       else if("/".equals(input[i]))    //Division
                       {    
                           int numb2 = Integer.valueOf(stack.pop());
                           int numb1 = Integer.valueOf(stack.pop());
                            
                           if(numb2 == 0)//If the user tries to divide by zero
                           {
                               stack.push("ERROR: DIV BY 0");
                           }
                           else//If it is a valid division
                           {
                            String divAns = Integer.toString(numb1/numb2);
                            stack.push(divAns); //Put the answer on the stack
                           }
                       }
                       else if("*".equals(input[i]))    //Multiplication
                       {
                           int numb2 = Integer.valueOf(stack.pop());
                           int numb1 = Integer.valueOf(stack.pop());
                           
                           String mulAns = Integer.toString(numb1 * numb2);
                           stack.push(mulAns);  //Put the answer on the stack
                       }
                    }
                   
                    finalAns = stack.pop(); //Pop the final answer on the stack
                    label.setText(finalAns);       //Display the Final Answer
                    answerPres = true;      //Tells the program that an answer is in the label
               }
               else 
               {   
                   if(answerPres.equals(true) || errorPres.equals(true))  //resets the screen if the answer/error to the previous question is displayed
                   {
                       label.setText("");
                       answerPres = false;//no answer on screen
                       errorPres = false; //no error on screen
                   }
                   
                   Text screen = new Text(label.getText() + variable); //WHat is entered on the screen
                   screen.setFont(new Font("Century Gothic", 20)); //change the screen font
                   label = screen;
               }
               border.setTop(label);    //Set the updated version of the screen
            }
           
        });        
        }
             
       Scene scene = new Scene(border, 162, 230);
        primaryStage.setTitle("Post-Fix Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }    
    
    public static void main(String[] args)
    {
        launch(args);   //Launch the Application
    }
}   

