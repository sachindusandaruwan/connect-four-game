package lk.ijse.dep.service;

import java.util.ArrayList;
import java.util.List;

import static lk.ijse.dep.service.Piece.EMPTY;

public class BoardImpl implements Board {
    public int cols;
    public Piece piece;
    private Piece[][] pieces;
    private BoardUI boardUI;

    public BoardImpl(BoardUI boardUI) {
        this.boardUI = boardUI;
        pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];    //pieces=new Piece[6][5];
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                pieces[i][j] = EMPTY;

            }
        }
    }


    @Override
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override


    public int findNextAvailableSpot(int col) {
        for (int i = 0; i < 5; i++) {       //apita ewana column eke rows wala empty space ekak thiyeda balanawa...
            if (pieces[col][i] == EMPTY) {
                return i;      //empty space ekak thibboth row eke index eka return karanawa..
            }
        }
        return -1;   //empty row ekak nathi unoth -1 return karanawa...
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
                if (pieces[i][j] == EMPTY) {
                    return true;
                }

            }
        }
        return false;
    }

    @Override
    public void updateMove(int col, Piece move) {
        this.cols=col;
        this.piece=move;
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            if (pieces[col][i] == EMPTY) {
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
                if (currentP != EMPTY) {
                    //check vertical(siras)
                    if (j + 3 < NUM_OF_ROWS &&
                            currentP == pieces[i][j + 1] &&
                            currentP == pieces[i][j + 2] &&
                            currentP == pieces[i][j + 3]) {
                        return new Winner(currentP, i, j, i, j + 3);
                    }
                    //check horizontal(thiras)
                    if (i + 3 < NUM_OF_COLS &&
                            currentP == pieces[i + 1][j] &&
                            currentP == pieces[i + 2][j] &&
                            currentP == pieces[i + 3][j]) {
                        return new Winner(currentP, i, j, i + 3, j);
                    }
                }

            }

        }
        //no winner(match tied)
        return new Winner(EMPTY);
    }
    public BoardImpl(Piece[][] pieces, BoardUI boardUI) {
        this.pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];

        //copies existing 2D array to newly created array here
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                this.pieces[i][j] = pieces[i][j];
            }
        }
        this.boardUI = boardUI;
    }

    //return the boardimpl object
    @Override
    public BoardImpl getBoardImpl() {
        return this;
    }

    //checks the all next legal moves while expanding the tree (creating child nodes)
    public List<BoardImpl> getAllLegalNextMoves() {
        Piece nextPiece = piece == Piece.BLUE ? Piece.GREEN : Piece.BLUE;

        List<BoardImpl> nextMoves = new ArrayList<>();
        for (int col = 0; col < NUM_OF_COLS; col++) {
            if (findNextAvailableSpot(col) > -1) {
                BoardImpl legalMove = new BoardImpl(this.pieces, this.boardUI);
                legalMove.updateMove(col, nextPiece);
                nextMoves.add(legalMove);
            }
        }
        return nextMoves;
    }

    //randomly select child node just after expanding the parent node
    public BoardImpl getRandomLegalNextMove() {
        final List<BoardImpl> legalMoves = getAllLegalNextMoves();
        if (legalMoves.isEmpty()) {
            return null;
        }
        final int random;
        random =Board.RANDOM_GENERATOR.nextInt(legalMoves.size());
        return legalMoves.get(random);
    }

    //decide whether there's any empty piece or not
    public boolean getStatus() {
        if (!existLegalMoves()) {
            return false;
        }
        Winner winner = findWinner();
        if (winner.getWinningPiece() != EMPTY) {
            return false;
        }
        return true;
    }

}


