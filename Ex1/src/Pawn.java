
public class Pawn extends ConcretePiece {

    private int num_of_kills;
    Pawn(Player owner, int name, Position pos) {
        super(owner, owner.isPlayerOne() ? "♙" : "♟", owner.isPlayerOne() ? "D"+name : "A"+name, pos);
        this.num_of_kills = 0;
    }
    public void setKills(int num){
        this.num_of_kills += num;
    }
    public int getNum_of_kills(){
        return this.num_of_kills;
    }

}