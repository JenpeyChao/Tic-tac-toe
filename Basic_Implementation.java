import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.*; 

public class Basic_Implementation {
    static char USER = 'X';
    static char[][] createBoard(){
        //creates a new board
        char[][] board = new char[3][3];
        for (int i = 0; i < 3;i++){
            for (int j = 0; j < 3 ; j++){
                board[i][j] = ' ';
            }
        }
        return board;
    }
    static void printBoard(char[][] board,int size){
        //prints the board
        for (int i = 0; i < size;i++){
            System.out.print("  "+i+" ");
        }
        System.out.println();
        
        for (int i = 0; i < size;i++){
            if (i != 0){System.out.println("  "+"---".repeat(size));}
            System.out.print(i+" ");
            for (int j = 0; j < size ; j++){
                if (j == size-1){
                    System.out.print(board[i][j]);
                    System.out.println();
                }else{
                    System.out.print(board[i][j]+" | ");
                }
            }
        }
    }
    static char[][] moves(char[][] board,boolean user){
        
        int row,column;
        Scanner myObj = new Scanner(System.in);
        char symbol;
        //sees if its the X or O user
        if (user){
            symbol = USER;
        }else{
            symbol = 'O';
        } 
        
        do{
            //asks the user for row and col
            printBoard(board);
            System.out.println("Pick a row and column:");
            row = myObj.nextInt();
            myObj.nextLine();
            column = myObj.nextInt();
            myObj.nextLine();
            //if the square isnt empty then it prompts again
            if (board[row][column] != ' '){
                System.out.println("Try again");
            }
            else{
                //if it is empty then it fills it with the symbol
                board[row][column] = symbol;
                break;
            }
            
        }while(board[row][column] != ' ');
        
        return board;
    }


    static boolean win(char[][] board,char symbol){
        Integer x = 0;
        Integer o = 0;
        Integer range = 3;
        for (int i =0;i<3;i++){
            if (board[i][i] == symbol){
                x+=1;
            }else{
                o +=1;
            }
            
        }

        if (x.equals(range)){
            return true;
        }

        x = 0;o=0;
        for (int i =0;i<3;i++){
            if (board[i][3-1-i] == symbol){
                x+=1;
            }else{
                o+=1;
            }
            
        }
        if (x == 3){
            return true;
        }
        //row
        for(int i = 0; i <3;i++){
            x = 0; o = 0;
            for(int j = 0;j<3;j++){
                if (board[i][j] == symbol){
                    x+=1;
                }else{
                    o+=1;
                }
            }

            if (x == 3){
                return true;
            }
        }

        //col
        for(int i = 0; i <3;i++){
            x = 0; o = 0;
            for(int j = 0;j<3;j++){
                if (board[j][i] == symbol){
                    x+=1;
                }else{
                    o+=1;
                }
            }
            if (x == 3){
                return true;
            }     
        }

        return false;
    }
    public static void main(String[] args){
        //creates the board
        System.out.println("Welcome to tic-tac-toe!");
        char[][] board = createBoard();
        int move = 0;
        //while its not a draw it keeps running
        while (move < (3*3)){
            //prints the board and asks the first player for his move 
            board = moves(board,true);
            //checks if the user won
            if (win(board,'X')){
                System.out.println("WINNER WINNER CHICKEN DINNER, X WON");
                break;
            }

            move+=1;
            //checks if its a draw, if it is then it stops
            if (move>=(3*3)){
                System.out.println("Draw");
                break;
            }
            //starts the next persons turn
            board = moves(board,false);
            printBoard(board,size);
            //sees if they won
            if(win(board,'O')){
                System.out.println("Imagine losing to O");
                break;
            }
            move+=1;
            //checks if its a draw, if it is then it stops
            if (move>=(3*3)){
                System.out.println("Draw");
                break;
            }
        }
        System.out.println("Thanks for playing");
        

    }
}
