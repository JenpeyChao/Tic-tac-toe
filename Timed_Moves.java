
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.io.*; 

public class Timed_Moves {
    static public class timer{
        //referenced your timer using buffered reader and executors
        private final BufferedReader getInput = new BufferedReader(new InputStreamReader(System.in));
        private final ExecutorService executors = Executors.newVirtualThreadPerTaskExecutor();
        private int num;
       
        
        void input(){
            try{
                //if the buffered reader isnt ready then we sleep
                while(!getInput.ready()){Thread.sleep(7);}
                //gets the row/col and turns it into a int
                String input = getInput.readLine();
                num = Integer.parseInt(input);
                //if its done then it closes all threads
                executors.shutdownNow();
            }catch(IOException |InterruptedException e){
    
            }
        }
        void countDown(){
            //referenced your timer/countdown
            IntStream.range(1,4)
            .mapToObj(i -> i)
            .toList()
            .reversed()
            .forEach(i -> {
                try{
                    Thread.sleep(1000);

                }catch(InterruptedException e ){
                    throw new RuntimeException(e);
                }
            });
            
        }
        private timer() throws InterruptedException{
            //default not valid num
            num = -1;
            //calls both threads
            executors.submit(this::input);
            executors.submit(this::countDown);
            //gives the user 4 seconds 
            executors.awaitTermination(4000, TimeUnit.MILLISECONDS);
            //if they didnt put anything then it prints this and closes all threads 
            if(num == -1 ){
                System.out.println("You slow poke");
            }
            executors.shutdownNow();
        }
        static int getInput() throws InterruptedException{
            return new timer().num;
        }
    }
    
    static final Scanner myObj = new Scanner(System.in);
    static char USER = 'X';

    static char[][] createBoard(int size){
        //creates the board with empty slots
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
            if (i != 0){System.out.println("---".repeat(size));}
            
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
    
    static char[][] moves(char[][] board,boolean user)throws InterruptedException{
        int row,column;
        char symbol;
        if (user){
            symbol = USER;
        }else{
            symbol = 'O';
        }
        do{
            //gets the row pick
            System.out.println("Pick your row");
            row = timer.getInput();
            //if they didnt pick the row then they lose their turn
            if (row ==-1){
                break;
            }
            //gets the col
            System.out.println("Pick your col");
            column = timer.getInput();
            //if they picked a row but not a col, they lose their turn
            if(column == -1){
                break;
            }
            //if the place they chose was already taken, then they get to try again
            if (board[row][column] != ' '){
                System.out.println("Try again");
            }
            else{
                //if its not taken then the board updates 
                //and ends the loop
                board[row][column] = symbol;
                break;
            }
        //keeps going until they pick a valid place
        }while(board[row][column] != ' ');
        
        return board;
    }

    static boolean win(char[][] board,char symbol,int size){
        Integer x = 0;
        //checks if the symbol has n in a row, if they do then it returns true, else it would return false
        //dia
        for (int i =0;i<size;i++){
            if (board[i][i] == symbol){
                x+=1;
            }
        }

        if (x.equals(size)){
            return true;
        }
        //anti-dia
        x = 0;
        for (int i =0;i<3;i++){
            if (board[i][3-1-i] == symbol){
                x+=1;
            }
        }
        if (x == size){
            return true;
        }
        //row
        for(int i = 0; i <size;i++){
            x = 0; 
            for(int j = 0;j<size;j++){
                if (board[i][j] == symbol){
                    x+=1;
                }
            }
            if (x == size){
                return true;
            }
        }

        //col
        for(int i = 0; i <size;i++){
            x = 0; 
            for(int j = 0;j<size;j++){
                if (board[j][i] == symbol){
                    x+=1;
                }
            }
            if (x == size){
                return true;
            }     
        }
        return false;
    }
    public static void main(String[] args)throws InterruptedException{
        
        System.out.println("Welcome to tic-tac-toe!");
        int move = 0;
        System.out.println("Choose a grid size");
        int size = myObj.nextInt();
        myObj.nextLine();
        char[][] board = createBoard(size);
        //runs until the loop breaks
        while (true){
            //X's turn
            printBoard(board,size);
            System.out.println("X's Turn");
            //gets the updated board from the players move
            board = moves(board,true);
            //if the board changes 
            
            //we check if the X's won, if they did then we break from the while loop
            if (win(newBoard,'X',size)){
                System.out.println("WINNER WINNER CHICKEN DINNER, X WON");
                break;
            }
                //if they didnt we update hte board and increase the moves
                
            move+=1;
            
            //O's Turn
            printBoard(board,size);
            System.out.println("O's Turn");
            //gets the updated board from the players move
            board = moves(board,false);
            //checks if the board changes
            
            //check if O's won, if they did then we break from the while loop
            if(win(board,'O',size)){
                System.out.println("Imagine losing to O");
                break;
            }
                //if they didnt we update the board and increment the move
            move+=1;
            
            //checks if the board is filled; 
            //ex if the players did 9 moves and its a 3x3 and no one won then its a draw
            //if it is then its a draw and we break
            if (move>=(size*size)){
                System.out.println("Draw");
                break;
            }
        }
        System.out.println("Thanks for playing");
        

    }
}
