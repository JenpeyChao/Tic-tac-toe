import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.*; 

public class Adjustable_Grid_Size {
    static final Scanner myObj = new Scanner(System.in);
    static final int SIZE= 0;
    static char USER = 'X';
    static char[][] createBoard(int size){
        char[][] board = new char[size][size];
        for (int i = 0; i < size;i++){
            for (int j = 0; j < size ; j++){
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
        
        char symbol;
        if (user){
            symbol = USER;
        }else{
            symbol = 'O';
        }
        
        do{
            
            System.out.println("Pick a row and column:");
            row = myObj.nextInt();
            myObj.nextLine();
            column = myObj.nextInt();
            myObj.nextLine();
            if (board[row][column] != ' '){
                System.out.println("Try again");
            }
            else{
                board[row][column] = symbol;
                break;
            }
            
        }while(board[row][column] != ' ');
        
        return board;
    }


    static boolean win(char[][] board,char symbol,int size){
        Integer x = 0;
        Integer o = 0;
        
        for (int i =0;i<size;i++){
            if (board[i][i] == symbol){
                x+=1;
            }else{
                o +=1;
            }
            
        }

        if (x.equals(size)){
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
        if (x == size){
            return true;
        }
        //row
        for(int i = 0; i <size;i++){
            x = 0; o = 0;
            for(int j = 0;j<size;j++){
                if (board[i][j] == symbol){
                    x+=1;
                }else{
                    o+=1;
                }
            }

            if (x == size){
                return true;
            }
        }

        //col
        for(int i = 0; i <size;i++){
            x = 0; o = 0;
            for(int j = 0;j<size;j++){
                if (board[j][i] == symbol){
                    x+=1;
                }else{
                    o+=1;
                }
            }
            if (x == size){
                return true;
            }     
        }

        return false;
    }
    public static void main(String[] args){
        
        System.out.println("Welcome to tic-tac-toe!");
        int move = 0;
        System.out.println("Choose a grid size");
        int size = myObj.nextInt();
        myObj.nextLine();
        char[][] board = createBoard(size);
        //while its not a draw it keeps running
        while (move < (size*size)){
            //prints the board and asks the first player for his move 
            printBoard(board,size);
            board = moves(board,true);
            //checks if the user won
            //if they did it stops the loop
            if (win(board,'X',size)){
                System.out.println("WINNER WINNER CHICKEN DINNER, X WON");
                break;
            }
            move+=1;
            //checks if its a draw, if it is then it stops
            if (move>=(3*3)){
                System.out.println("Draw");
                break;
            }
            //starts the next players turn
            printBoard(board,size);
            board = moves(board,false);
            //checks if they won
            if(win(board,'O',size)){
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
