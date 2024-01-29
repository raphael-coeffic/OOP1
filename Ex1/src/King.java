
public class King extends ConcretePiece {


    private boolean isAlive = true;
    King(Player owner, Position position) {
        super(owner, "♔" , "K7", position);
    }

    //this functions check is the king is alive
    protected boolean isAlive(){
        return this.isAlive;
    }

    //kill the king
    protected void dead(){
        this.isAlive = false;
    }

    //resurrect the king
    protected void resurrection(){
        this.isAlive = true;
        this.setPosition(new Position(5, 5));
    }

    @Override
    public int getNum_of_kills() {
        return 0;
    }


//    @Override
//    public void setKills(int i) {
//    }
}
