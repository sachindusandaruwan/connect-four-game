package lk.ijse.dep.service;

public class HumanPlayer extends Player {
    public HumanPlayer(Board newBoard) {
        super(newBoard);
    }

    @Override
    public void movePiece(int col) {
        if(board.isLegalMove(col)){
            board.updateMove(col,Piece.BLUE);
            board.getBoardUI().update(col,true);
            Winner win=board.findWinner();
            if(board.findWinner().getWinningPiece()==Piece.EMPTY){
                //balen
                if(!board.existLegalMoves()){
                    board.getBoardUI().notifyWinner(win);
                }
            }
            else {
                board.getBoardUI().notifyWinner(win);
            }
        }
    }
}
