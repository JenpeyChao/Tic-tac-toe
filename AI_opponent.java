
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.Random;
import java.io.*; 

public class AI_opponent {
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
            IntStream.range(1,5)
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
            executors.awaitTermination(5000, TimeUnit.MILLISECONDS);
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
        for (int i =0;i<size;i++){
            if (board[i][size-1-i] == symbol){
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

    static boolean anyMovesLeft(char[][] board,int size){
        for (int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if (board[row][col] == ' '){
                    return true;
                }
            }
        }
        return false;
    }
    
    static char[][] easy(char[][] board,int size){
        int row ;
        int col ;
        Random rand = new Random();
        do{
            // creates a random number
            //if the spot on the board is taken, we try again
            row = rand.nextInt(size);
            col = rand.nextInt(size);
        }while(board[row][col] != ' ');
        board[row][col] = 'O';
        return board;
    }

    static int[] block(char[][]board,int size){
        //initial spots are invalid 
        int[] spot = new int[2];
        spot[0] = -1; 
        spot[1] = -1;
        int count = 0;

        //cache
        int[] row = new int[size];
        int[] col = new int[size];
        int[] dia = new int[2]; //index 0 is dia; index 1 is anti-dia
        
        //loops through the board and counts all the X's
        for (int i  = 0;i < size; i++){
            for(int j = 0; j < size;j++){
                if (board[i][j] == 'X'){
                    row[i]+=1;
                    col[j]+=1;
                }
            }
            if(board[i][i] == 'X'){
                dia[0]+=1;
            }
            if(board[i][size-1-i] == 'X'){
                dia[1]+=1;
            }
        }
        
        //tries to find the spot that has the most X's so its optimally blocked
        //loops through the board
        for (int i  = 0;i < size; i++){
            for(int j = 0; j < size;j++){
                if (board[i][j] == ' '){ // if the spot is empty
                    //and calculate the amount of X's in that spot
                    //if the spot has more X's than the current one, 
                    //then we save the spot and the amount of X's
                    if(i == j && count < ((row[i]+col[j]+ dia[0]))){
                        //spot that is in dia
                        count = (row[i]+col[j]+dia[0]);
                        spot[0] = i;
                        spot[1] = j;
                    }else if (i+j == size-1 && count < ((row[i]+col[j]+ dia[1]))){ 
                        // spot that is in anti-dia
                        count = (row[i]+col[j]+dia[1]);
                        spot[0] = i;
                        spot[1] = j;
                    }else if (count < ((row[i]+col[j]))){//everything else
                        count = (row[i]+col[j]);
                        spot[0] = i;
                        spot[1] = j;
                    }
                }
            }
        }
        //then we return the spot
        return spot;

    }
    
    static char[][] medium(char[][] board, int size){
        int[] spot = block(board, size);
        //gets the spot to block
        //if we didnt find a spot then we call the easy function 
        //so we randomly place a spot
        if (spot[0] == -1 || spot[1] == -1){
            easy(board, size);
        }else{
            //changes the board
            board[spot[0]][spot[1]] = 'O';
        }
        return board;
    }
    
    static int score(char[][] board,int size,int depth){
        if (win(board,'O',size)){
            return 10; //if the bot wins
        }else if (win(board,'X',size)){//opponent
            return -10; // if the user wins 
        }
        return 0;
    }

    static int miniMax(char[][] board,int size, int depth, boolean aI){
        //this functions keeps going until someone wins or its tie
        //then returns the score of the first recursion call from the function findBestMove
        //this function only cares about the outcome of the move 
        int val = score(board, size,depth);
        //base case, when someone wins or if its a tie
        if (val == -10 || val == 10 ||anyMovesLeft(board, size) == false){
            return val;
        }
        //if its the AI's turn
        if(aI){
            int bestmove = -200;
            //for every free slot/move 
            for(int row = 0;row < size;row++){
                for(int col = 0; col < size; col++){
                    if (board[row][col] == ' '){
                        //figure out which if the move is the best
                        board[row][col] = 'O';
                        
                        bestmove = Math.max(bestmove,miniMax(board, size, depth+1, !aI));
                        board[row][col] = ' ';
                    }
                }
            }
            return bestmove;
        //if its the users turn
        }else{
            int bestmove = 200;
            //for every free slot/move 
            for(int row = 0;row < size;row++){
                for(int col = 0; col < size; col++){
                    if (board[row][col] == ' '){
                        //figure out which if the move is the best
                        board[row][col] = 'X';
                        bestmove = Math.min(bestmove,miniMax(board, size, depth+1, !aI));
                        board[row][col] = ' ';
                    }
                }
            }
            return bestmove;
        }
    }

    static int[] findBestMove(char[][]board,int size){
        int[] moves = new int[2];
        moves[0] = -1; //invalid row
        moves[1] = -1; //invalid col
        int bestmove = -200;
        //for every free slot/move 
        for(int row = 0;row < size;row++){
            for(int col = 0; col < size; col++){
                if (board[row][col] == ' '){
                    //figure out which if the move is the best
                    board[row][col] = 'O';
                    int val = miniMax(board,size, 0, false);
                    board[row][col] = ' ';
                    //if the value is better than the current value
                    //we save the row/col
                    if (bestmove< val){
                        bestmove = val;
                        moves[0] = row;
                        moves[1] = col;
                    }
                }
            }
        }
        return moves;
    }
    static char[][] hard(char[][] board, int size){
        int[] bestMove = new int[2];
        bestMove = findBestMove(board, size);

        board[bestMove[0]][bestMove[1]] = 'O';
        return board;
    }
    public static void main(String[] args)throws InterruptedException{
        
        System.out.println("Welcome to tic-tac-toe!");
        System.out.println("Choose a grid size");
        int size = myObj.nextInt();
        myObj.nextLine();
        System.out.println("Pick a difficulty levels (easy, medium, hard)");
        String diff = myObj.nextLine();
        diff = diff.toLowerCase();
        char[][] board = createBoard(size);
        //runs until the loop breaks
        while (anyMovesLeft(board,size)){
            //X's turn
            printBoard(board,size);
            System.out.println("X's Turn");
            //gets the updated board from the players move
            board = moves(board,true);
            //if the board changes 
            
            //we check if the X's won, if they did then we break from the while loop
            if (win(board,'X',size)){
                System.out.println("WINNER WINNER CHICKEN DINNER, X WON");
                break;
            }
            if (!anyMovesLeft(board, size)){
                break;
            }
            //O's Turn
            switch (diff) {
                case "easy":
                    board = easy(board, size);
                    break;
                case"medium":
                    board = medium(board, size);
                case "hard":
                    board = hard(board, size);
                default:
                    break;
            }
            if (win(board,'O',size)){
                System.out.println("Dam you lost to an AI");
                break;
            }
            //checks if the board is filled; 
            //ex if the players did 9 moves and its a 3x3 and no one won then its a draw
            //if it is then its a draw and we break
        }
        if (!anyMovesLeft(board,size)){
            System.out.println("Its a Draw");
        }
        printBoard(board, size);
        System.out.println("Thanks for playing");
        

    }
}
