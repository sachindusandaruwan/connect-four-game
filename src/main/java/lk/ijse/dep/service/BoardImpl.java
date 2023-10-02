package lk.ijse.dep.service;

public class BoardImpl implements Board {
    private Piece[][] pieces;
    private BoardUI boardUI;

    public BoardImpl(BoardUI boardUI) {
        this.boardUI = boardUI;
        pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                pieces[i][j] = Piece.EMPTY;

            }
        }
    }


    @Override
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col) {
        for (int i = 0; i < 5; i++) {
            if (pieces[col][i] == Piece.EMPTY) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isLegalMove(int col) {
        int findbol = findNextAvailableSpot(col);
        if (findbol != -1) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean existLegalMoves() {
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    return true;
                }

            }
        }
        return false;
    }

    @Override
    public void updateMove(int col, Piece move) {
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            if (pieces[col][i] == Piece.EMPTY) {
                pieces[col][i] = move;
                break;
            }
        }

    }

    @Override
    public Winner findWinner() {
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                Piece currentP = pieces[i][j];
                if (currentP != Piece.EMPTY) {
                    if (j + 3 < NUM_OF_ROWS &&
                            currentP == pieces[i][j + 1] &&
                            currentP == pieces[i][j + 2] &&
                            currentP == pieces[i][j + 3]) {
                        return new Winner(currentP, i, j, i, j + 3);
                    }
                    if (i + 3 < NUM_OF_COLS &&
                            currentP == pieces[i + 1][j] &&
                            currentP == pieces[i + 2][j] &&
                            currentP == pieces[i + 3][j]) {
                        return new Winner(currentP, i, j, i + 3, j);
                    }
                }

            }

        }
        return new Winner(Piece.EMPTY);
    }
}


