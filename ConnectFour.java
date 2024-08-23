import java.util.Scanner; 

public class ConnectFour {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final char EMPTY_SLOT = '.';
    private static final char PLAYER_ONE = 'X';
    private static final char PLAYER_TWO = 'O';

    public static void main(String[] args) {
        char[][] board = new char[ROWS][COLS];
        initializeBoard(board);
        printBoard(board);
        boolean gameOver = false;
        char currentPlayer = PLAYER_ONE;

        Scanner scanner = new Scanner(System.in);

        while (!gameOver) {
            System.out.println("Player " + currentPlayer + ", choose a column (1-7): ");
            int col = scanner.nextInt() - 1;

            if (col < 0 || col >= COLS || board[0][col] != EMPTY_SLOT) {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            int row = dropDisc(board, col, currentPlayer);
            printBoard(board);

            if (checkWin(board, row, col, currentPlayer)) {
                System.out.println("Player " + currentPlayer + " wins!");
                gameOver = true;
            } else if (isBoardFull(board)) {
                System.out.println("It's a draw!");
                gameOver = true;
            }

            currentPlayer = (currentPlayer == PLAYER_ONE) ? PLAYER_TWO : PLAYER_ONE;
        }

        scanner.close();
    }

    private static void initializeBoard(char[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY_SLOT;
            }
        }
    }

    private static void printBoard(char[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("1 2 3 4 5 6 7");
    }

    private static int dropDisc(char[][] board, int col, char disc) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][col] == EMPTY_SLOT) {
                board[i][col] = disc;
                return i;
            }
        }
        return -1; // This shouldn't happen
    }

    private static boolean checkWin(char[][] board, int row, int col, char disc) {
        return (checkDirection(board, row, col, disc, 1, 0) >= 4 ||  // Horizontal
                checkDirection(board, row, col, disc, 0, 1) >= 4 ||  // Vertical
                checkDirection(board, row, col, disc, 1, 1) >= 4 ||  // Diagonal /
                checkDirection(board, row, col, disc, 1, -1) >= 4);  // Diagonal \
    }

    private static int checkDirection(char[][] board, int row, int col, char disc, int rowDir, int colDir) {
        int count = 0;

        for (int i = -3; i <= 3; i++) {
            int r = row + i * rowDir;
            int c = col + i * colDir;

            if (r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == disc) {
                count++;
                if (count >= 4) {
                    return count;
                }
            } else {
                count = 0;
            }
        }

        return count;
    }

    private static boolean isBoardFull(char[][] board) {
        for (int j = 0; j < COLS; j++) {
            if (board[0][j] == EMPTY_SLOT) {
                return false;
            }
        }
        return true;
    }
}
