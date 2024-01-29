public class Position {

    private int x;
    private int y;
    protected int num_of_stepping = 0;


    Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
    private void setX(int x){
        this.x = x;
    }
    private void setY(int y){
        this.y = y;
    }

    public void setNum_of_stepping(int num_of_stepping) {
        this.num_of_stepping += num_of_stepping;
    }
    public int getNum_of_stepping(){
        return this.num_of_stepping;
    }

    public boolean inTheBoard(){
        if (x >= 0 && x <= 10
                && y >= 0 && y <= 10){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        String ans = "(" + x + ", " + y + ")";
        return ans;
    }

    /**
     * @return us the position of a neighbor's case and if this is out of
     * the board return null
     */
    public Position up(){
        Position newPos = new Position(this.getX(), this.getY() - 1);
        if (newPos.inTheBoard())
            return newPos;
        return null;
    }
    public Position down(){
        Position newPos = new Position(this.getX(), this.getY() + 1);
        if (newPos.inTheBoard())
            return newPos;
        return null;
    }
    public Position right(){
        Position newPos = new Position(this.getX() + 1, this.getY());
        if (newPos.inTheBoard())
            return newPos;
        return null;
    }
    public Position left(){
        Position newPos = new Position(this.getX() - 1, this.getY());
        if (newPos.inTheBoard())
            return newPos;
        return null;
    }

    //check if the position is the corner of the board
    public boolean isCorner(){
        if (this.getY() == 0 && (this.getX() == 0 || this.getX() == 10))
            return true;
        if (this.getY() == 10 && (this.getX() == 0 || this.getX() == 10))
            return true;
        return false;
    }
    public void setPosition(int x, int y){
        this.setX(x);
        this.setY(y);
    }
}
