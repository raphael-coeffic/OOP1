import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

public class ConcretePlayer implements Player {

    private int num_wins;
    private final int player_num;
    private boolean turn;
    private int num_of_piece; // that alive
    public boolean win = false;
    public ArrayList<ConcretePiece> pieces = new ArrayList<>();

    ConcretePlayer(int num, int player_num) {
        this.num_wins = num;
        this.player_num = player_num;
        this.turn = !isPlayerOne();
        if (isPlayerOne())
            this.num_of_piece = 13;
        else this.num_of_piece = 24;
    }

    public void addPieces(Queue<Piece> deads){
        while (!deads.isEmpty()){
            this.pieces.add((ConcretePiece) deads.poll());
        }
    }
    public void addAllMyPieces(ArrayList<ConcretePiece> pieces){
        this.pieces.addAll(pieces);
    }

    public void sortByNumOfMovements(){
        Collections.sort(this.pieces, Comparator::compareBySteps);
    }

    public int getNum_of_pieces() {
        return this.num_of_piece;
    }

    public void set_num_of_pieces(int num) {
        this.num_of_piece += num;
    }

    public boolean isTurn() {
        return this.turn;
    }
    public boolean isWin(){
        return this.win;
    }

    /** change the turn after a move */
    public void setTurn(boolean b) {
        this.turn = b;
    }

    @Override
    public boolean isPlayerOne() {
        return this.player_num == 1;
    }

    public boolean isPlayerTwo() {
        return this.player_num == 2;
    }

    @Override
    public int getWins() {
        return this.num_wins;
    }

    public void setWins(int num) {
        this.num_wins += num;
    }

    /** init the player like in the beginning */
    public void resetPlayer(){
        this.pieces = new ArrayList<>();
        if (isPlayerOne())
            num_of_piece = 13;
        else num_of_piece = 24;

    }
}
