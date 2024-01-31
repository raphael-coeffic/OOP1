import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

public abstract class ConcretePiece implements Piece {

    private Position position_of_death = null;
    private final Player owner;
    private final String type;
    private final String name;
    private Position pos;
    protected int distance = 0;
    // where was this piece
    protected Queue<Position> allPositions = new LinkedList<>();

    ConcretePiece(Player owner, String type, String name, Position pos) {
        this.owner = owner;
        this.type = type;
        this.name = name;
        this.pos = pos;
        this.allPositions.offer(pos);
    }
    @Override
    public Player getOwner() {
        return this.owner;
    }

    /** check if this and a given piece have the same owner */
    public boolean sameOwner(Piece piece){
        return this.getOwner().equals(piece.getOwner());
    }

    public void addDistance(int dis){
        this.distance += dis;
    }
    public int getDistance(){ return this.distance; }

    /** change the position of dead in the board */
    public void setPositionOfDeath(Position position){
        this.position_of_death = position;
    }
    public Position getPosition_of_death(){
        return this.position_of_death;
    }
    public String getName(){
        return this.name;
    }

    @Override
    public String getType() {
        return this.type;
    }
    public void setPosition(Position pos){
        this.pos = pos;
    }

    protected Position getPosition(){
        return this.pos;
    }

    /** return true if this piece is a pawn */
    public boolean isPawn(){
        return Objects.equals(this.type, "♟") || Objects.equals(this.type, "♙");
    }

    public void addMove(Movement movement){
        this.allPositions.offer(movement.getTo());
    }


    public Queue<Position> getAllPositions(){ return this.allPositions; }

    /** Print all the positions that this piece stepped on them */
    public void soutAllPositions() {
        String str = this.name.toString() + ": ";
        str += "[";
        while (!this.allPositions.isEmpty()) {
            str += (this.allPositions.poll().toString()) + ", ";
        }
        String ans = str.substring(0, str.length() - 2);
        ans += "]";
        System.out.println(ans);
    }

    public abstract int getNum_of_kills();


}
