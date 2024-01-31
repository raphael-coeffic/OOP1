
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

class Movement{
    private Position from;
    private Position to;
    //queue of the pieces that dead because the movement
    public Queue<Piece> deads = new LinkedList<>();
    int distance;

    Movement(Position a, Position b){
        this.from = a;
        this.to = b;
        //calculate the distance of the movement
        if (a.getX() != b.getX())
            this.distance = Math.abs(a.getX() - b.getX());
        if (a.getY() != b.getY())
            this.distance = Math.abs(a.getY() - b.getY());

    }

    public Position getFrom() {
        return from;
    }
    public Position getTo() {
        return to;
    }

    public Queue<Piece> getDeads(){
        return this.deads;
    }

    /**
     * The 4 methods check if we eat a piece in the movement and return the piece that we eat
     */
    private Piece eatUp(Position pos, GameLogic gameLogic) {
        /**
         *  ↑   ↑   ↑
         */
        //check if we are on the north border of the board and if there is a piece on the top
        if (pos.up() != null) {
            if (pos.up().inTheBoard() && gameLogic.getPieceAtPosition(pos.up()) != null) {
                //check if the piece one case on top of our case that is a piece of the other player or null
                if (!gameLogic.getPieceAtPosition(pos.up()).sameOwner(gameLogic.getPieceAtPosition(pos))) {

                    if (!gameLogic.getPieceAtPosition(pos.up()).isPawn()) {  // if our neighbor is the king
                        this.kingIsDead(gameLogic); // we check if the king is dead
                    }

                    else {
                        //check if after the piece on top of our case we have a piece
                        if (pos.up().up() != null) {
                            if (pos.up().up().inTheBoard() && gameLogic.getPieceAtPosition(pos.up().up()) != null) {
                                if (gameLogic.getPieceAtPosition(pos.up().up()).sameOwner(gameLogic.getPieceAtPosition(pos))) {

                                    //eat the piece
                                    return this.realEat(pos, pos.up(), -1, "y", gameLogic);
                                }
                            }
                            return null;
                        }
                        //the peace on the up is on the border
                        return this.realEat(pos, pos.up(), -1, "y", gameLogic);
                    }
                }
            }
        }
        return null;
    }

    private Piece eatDown(Position pos, GameLogic gameLogic) {
        /**
         *  ↓  ↓  ↓
         */
        //check if we are on the south border of the board and if there is a piece under us
        if (pos.down() != null) {
            if (pos.down().inTheBoard() && gameLogic.getPieceAtPosition(pos.down()) != null) {
                //check if the piece one case under our case that is a piece of the other player or null
                if (!gameLogic.getPieceAtPosition(pos.down()).sameOwner(gameLogic.getPieceAtPosition(pos))) {
                    if (!gameLogic.getPieceAtPosition(pos.down()).isPawn()) {  // if our neighbor is the king
                        this.kingIsDead(gameLogic); // we check if the king is dead
                    } else {
                        //check if after the piece under our case we have a piece
                        if (pos.down().down() != null) {
                            if (pos.down().down().inTheBoard() && gameLogic.getPieceAtPosition(pos.down().down()) != null) {
                                if (gameLogic.getPieceAtPosition(pos.down().down()).sameOwner(gameLogic.getPieceAtPosition(pos))) {
                                    //eat the piece
                                    return this.realEat(pos, pos.down(), 1, "y", gameLogic);
                                }
                            }
                            return null;

                        }
                        //the peace on the up is on the border
                        //eat the piece
                        return this.realEat(pos, pos.down(), 1, "y", gameLogic);
                    }
                }
            }
        }
        return null;
    }

    private Piece eatRight(Position pos, GameLogic gameLogic) {
        /**
         *  →   →   →
         */
        //check if we are on the east border of the board and if there is a piece on the right
        if (pos.right() != null) {
            if (pos.right().inTheBoard() && gameLogic.getPieceAtPosition(pos.right()) != null) {
                //check if the piece one case on right of our case that is a piece of the other player or null
                if (!gameLogic.getPieceAtPosition(pos.right()).sameOwner(gameLogic.getPieceAtPosition(pos))) {
                    if (!gameLogic.getPieceAtPosition(pos.right()).isPawn()) {  // if our neighbor is the king
                        this.kingIsDead(gameLogic); // we check if the king is dead
                    } else {
                        //check if after the piece on right of our case we have a piece
                        if (pos.right().right() != null) {
                            if (pos.right().right().inTheBoard() && gameLogic.getPieceAtPosition(pos.right().right()) != null) {
                                if (gameLogic.getPieceAtPosition(pos.right().right()).sameOwner(gameLogic.getPieceAtPosition(pos))) {
                                    //eat the piece
                                    return this.realEat(pos, pos.right(), 1, "x", gameLogic);
                                }
                            }
                            return null;

                        }
                        //the peace on the right is on the border
                        //eat the piece
                        return this.realEat(pos, pos.right(), 1, "x", gameLogic);
                    }
                }
            }
        }
        return null;
    }

    private Piece eatLeft(Position pos, GameLogic gameLogic) {
        /**
         *  ←   ←   ←
         */
        //check if we are on the west border of the board and if there is a piece on the left
        if (pos.left() != null) {
            if (pos.left().inTheBoard() && gameLogic.getPieceAtPosition(pos.left()) != null) {
                //check if the piece one case on left of our case that is a piece of the other player or null
                if (!gameLogic.getPieceAtPosition(pos.left()).sameOwner(gameLogic.getPieceAtPosition(pos))) {
                    if (!gameLogic.getPieceAtPosition(pos.left()).isPawn()) {  // if our neighbor is the king
                        this.kingIsDead(gameLogic); // we check if the king is dead
                    } else {
                        //check if after the piece on left of our case we have a piece
                        if (pos.left().left() != null) {
                            if (pos.left().left().inTheBoard() && gameLogic.getPieceAtPosition(pos.left().left()) != null) {
                                if (gameLogic.getPieceAtPosition(pos.left().left()).sameOwner(gameLogic.getPieceAtPosition(pos))) {
                                    //eat the piece
                                    return this.realEat(pos, pos.left(), -1, "x", gameLogic);
                                }
                            }
                            return null;
                        }
                        //the peace on the right is on the border
                        //eat the piece
                        return this.realEat(pos, pos.left(), -1, "x", gameLogic);
                    }
                }
            }
        }
        return null;
    }

    /** this function execute the eating */
    private Piece realEat(Position pos, Position next, int degree, String direction, GameLogic gameLogic){
        gameLogic.getPieceAtPosition(next).setPositionOfDeath(next);
        Piece eaten = gameLogic.getPieceAtPosition(next);
        if (Objects.equals(direction, "x"))
            gameLogic.board[pos.getX() + degree][pos.getY()] = null;
        if (Objects.equals(direction, "y"))
            gameLogic.board[pos.getX()][pos.getY() + degree] = null;
        return eaten;
    }

    /** return a queue of all the pieces that we eat in this movement */
    protected Queue<Piece> eat(Position pos, GameLogic gameLogic) {
        if (gameLogic.getPieceAtPosition(pos).isPawn()) {
            Queue<Piece> eaten = new LinkedList<>();
            Piece piece;
            piece = this.eatUp(pos, gameLogic);
            if (piece != null) eaten.offer(piece);
            piece = this.eatDown(pos, gameLogic);
            if (piece != null) eaten.offer(piece);
            piece = this.eatRight(pos, gameLogic);
            if (piece != null) eaten.offer(piece);
            piece = this.eatLeft(pos, gameLogic);
            if (piece != null) eaten.offer(piece);
            ((Pawn) gameLogic.getPieceAtPosition(pos)).setKills(eaten.size());
            this.deads.addAll(eaten);
            return eaten;
        }
        return null;
    }

    /**
     * This 4 methods check if the king is on danger and if he is dead
     */
    private int dangerFromTheNorth(GameLogic gameLogic){
        if (gameLogic.theKing.getPosition().up() != null) {
            Position up = new Position(gameLogic.theKing.getPosition().up().getX(), gameLogic.theKing.getPosition().up().getY());
            if (up.inTheBoard()) {
                if (gameLogic.getPieceAtPosition(up) != null) {
                    if (gameLogic.getPieceAtPosition(up).getOwner().isPlayerTwo()) {
                        return 1; // he is between two enemies
                    }
                }
            }
            return 0; //not in danger
        }
        return 1; //he is between an enemy to the limit of the board
    }
    private int dangerFromTheSouth(GameLogic gameLogic){
        if (gameLogic.theKing.getPosition().down() != null) {
            Position down = new Position(gameLogic.theKing.getPosition().down().getX(), gameLogic.theKing.getPosition().down().getY());
            if (down.inTheBoard()) {
                if (gameLogic.getPieceAtPosition(down) != null) {
                    if (gameLogic.getPieceAtPosition(down).getOwner().isPlayerTwo()) {
                        return 1;// he is between two enemies
                    }
                }
            }
            return 0;//not in danger
        }
        return 1; //he is between an enemy to the limit of the board
    }
    private int dangerFromTheEast(GameLogic gameLogic){
        if (gameLogic.theKing.getPosition().right() != null) {
            Position right = new Position(gameLogic.theKing.getPosition().right().getX(), gameLogic.theKing.getPosition().right().getY());
            if (right.inTheBoard()) {
                if (gameLogic.getPieceAtPosition(right) != null) {
                    if (gameLogic.getPieceAtPosition(right).getOwner().isPlayerTwo()) {
                        return 1; // he is between two enemies
                    }
                }
            }
            return 0; //not in danger
        }
        return 1; //he is between an enemy to the limit of the board
    }
    private int dangerFromTheWest(GameLogic gameLogic){
        if (gameLogic.theKing.getPosition().left() != null) {
            Position left = new Position(gameLogic.theKing.getPosition().left().getX(), gameLogic.theKing.getPosition().left().getY());
            if (left.inTheBoard()) {
                if (gameLogic.getPieceAtPosition(left) != null) {
                    if (gameLogic.getPieceAtPosition(left).getOwner().isPlayerTwo()) {
                        return 1;// he is between two enemies
                    }
                }
            }
            return 0; //not in danger
        }
        return 1; //he is between an enemy to the limit of the board
    }

    /** this method execute the other methods who check if
     * the king is dead and if it's true this function kill the king
     */
    protected void kingIsDead(GameLogic gameLogic){
        int a = this.dangerFromTheNorth(gameLogic);
        int b = this.dangerFromTheSouth(gameLogic);
        int c = this.dangerFromTheWest(gameLogic);
        int d = this.dangerFromTheEast(gameLogic);
        int sum = a + b + c + d;
        if (sum >= 4) {
            gameLogic.theKing.dead(); // the king is dead
        }
    }

    public String toString(){
        return "From: " + from.getX()+","+from.getY()+" To: "+to.getX()+","+to.getY();
    }

}
