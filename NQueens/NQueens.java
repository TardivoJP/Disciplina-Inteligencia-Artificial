import java.util.ArrayList;

public class NQueens{
    public static final int BOARDSIZE = 12;

    public static void main(String[] args){
        int[][] board = new int[BOARDSIZE][BOARDSIZE];

        for(int i = 0; i<BOARDSIZE; i++){
            for(int j = 0; j<BOARDSIZE; j++){
                board[i][j] = 0;
            }
        }

        nQueens(board, 1);
    }

    public static boolean nQueens(int[][] board, int depth){
        if(depth > BOARDSIZE){
            return false;
        }

        ArrayList<Position> possibleSquares = new ArrayList<>();

        for(int i = 0; i<BOARDSIZE; i++){
            for(int j = 0; j<BOARDSIZE; j++){
                if(board[i][j] == 0){
                    possibleSquares.add(new Position(i, j));
                }
            }
        }

        if(possibleSquares.size() == 0){
            return false;
        }

        for(int i=0; i<possibleSquares.size(); i++){
            Position currentPosition = possibleSquares.get(i);
            int row = currentPosition.row;
            int column = currentPosition.column;

            int[][] boardCopy = copyboard(board);

            calculateKillZone(boardCopy, row, column);
            boardCopy[row][column] = 1;

            if(depth == BOARDSIZE){
                printBoard(boardCopy);
                return true;
            }

            if(nQueens(boardCopy, depth+1)){
                return true;
            }
        }

        return false;
    }

    public static void calculateKillZone(int[][] board, int x, int y){
        for(int i = 0; i < BOARDSIZE; i++){
            board[x][i] = 2;
            board[i][y] = 2;
        }

        for(int i=x, j=y; i>=0 && j>=0; i--, j--){
            board[i][j] = 2;
        }

        for(int i=x, j=y; i<BOARDSIZE && j<BOARDSIZE; i++, j++){
            board[i][j] = 2;
        }

        for(int i=x, j=y; i>=0 && j<BOARDSIZE; i--, j++){
            board[i][j] = 2;
        }
        
        for(int i=x, j=y; i<BOARDSIZE && j>=0; i++, j--){
            board[i][j] = 2;
        }

    }

    public static int[][] copyboard(int[][] board){
        int[][] boardCopy = new int[BOARDSIZE][BOARDSIZE];

        for(int i = 0; i<BOARDSIZE; i++){
            for(int j = 0; j<BOARDSIZE; j++){
                boardCopy[i][j] = board[i][j];
            }
        }

        return boardCopy;
    }

    public static void printBoard(int[][] board){
        for(int i = 0; i<BOARDSIZE; i++){
            for(int j = 0; j<BOARDSIZE; j++){
                System.out.printf("%d",board[i][j]);
            }
            System.out.printf("\n");
        }
    }
}

