public class TicTacToeModel implements AbstractTicTacToe{

    Owner winner;
    Owner turn;
    Owner[][] board;

    public TicTacToeModel() {
        board = new Owner[3][3];
        restart();
    }

    @Override
    public void restart() {
        winner = Owner.NONE;
        turn = Owner.FIRST;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Owner.NONE;
            }
        }
    }

    @Override
    public Owner getTurn() {
        return turn;
    }

    @Override
    public Owner getSquare(int row, int column) {
        return validSquare(row, column)? board[row][column] : null;
    }

    @Override
    public Owner getWinner() {
       // Parcourir les lignes
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][2] != Owner.NONE) {
                winner = board[i][2];
            }
        }

        // Parcourir les colonnes
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j] && board[2][j] != Owner.NONE) {
                winner = board[2][j];
            }
        }

        // diagonal principale
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[2][2] != Owner.NONE) {
            winner = board[2][2];
        }

        // diagonal secondaire
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[2][0] != Owner.NONE) {
            winner = board[2][0];
        }
        return winner;
    }

    @Override
    public boolean validSquare(int row, int column) {
        return row >= 0 && row <= 2 && column >= 0 && column <= 2;
    }

    @Override
    public void nextPlayer() {
        turn = turn.opposite();
    }

    @Override
    public void play(int row, int column) {
        if (legalMove(row, column)) {
            board[row][column] = turn;
            nextPlayer();
        }
    }

    @Override
    public boolean legalMove(int row, int column) {
        if (validSquare(row, column) && !gameOver()) {
            return board[row][column] == Owner.NONE;
        }
        return false;
    }

    @Override
    public int numberOfRounds() {
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != Owner.NONE) {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    public boolean gameOver() {
        return winner != Owner.NONE || numberOfRounds() == 9;
    }
}
