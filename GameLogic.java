import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.Stack;

public class GameLogic implements PlayableLogic {


    private final ConcretePlayer playerOne;
    private final ConcretePlayer playerTwo;
    protected ConcretePiece[][] board; // the board himself
    protected int[][] boardPositions = new int[this.getBoardSize()][this.getBoardSize()]; // num of stepping on each position of the board
    public ArrayList<Position> steps = new ArrayList<>();
    private Player winner;
    private boolean isFinish = false;


    // this stack stock every movement of the game
    private Stack<Movement> info = new Stack<>();
    // this king represent the king of the game
    protected King theKing;
    protected ArrayList<ConcretePiece> allPieces = new ArrayList<>();

    GameLogic() {
        this.playerOne = new ConcretePlayer(0, 1);
        this.playerTwo = new ConcretePlayer(0, 2);
        this.initBoard();
        this.theKing = new King(playerOne, new Position(5, 5));
    }

    private void initBoard() {
        this.board = new ConcretePiece[11][11];

        // tha attack
        int help = 1;
        for (int i = 3; i <= 7; i++) {
            board[i][0] = new Pawn(playerTwo, help, new Position(i, 0));
            boardPositions[i][0] = 1;
            board[i][10] = new Pawn(playerTwo, (help + 19), new Position(i, 10));
            boardPositions[i][10] = 1;
            help++;
        }

        help = 7;
        for (int i = 3; i <= 5; i++) {
            board[0][i] = new Pawn(playerTwo, help, new Position(0, i));
            boardPositions[0][i] = 1;
            help++;
            board[10][i] = new Pawn(playerTwo, help, new Position(10, i));
            boardPositions[10][i] = 1;
            help++;
        }
        board[10][5] = new Pawn(playerTwo, help, new Position(10, 5));
        boardPositions[10][5] = 1;
        help += 2;
        for (int i = 6; i <= 7; i++) {
            board[0][i] = new Pawn(playerTwo, help, new Position(0, i));
            boardPositions[0][i] = 1;
            help++;
            board[10][i] = new Pawn(playerTwo, help, new Position(10, i));
            boardPositions[10][i] = 1;
            help++;
        }

        board[1][5] = new Pawn(playerTwo, 12, new Position(1, 5));
        boardPositions[1][5] = 1;
        board[9][5] = new Pawn(playerTwo, 13, new Position(9, 5));
        boardPositions[9][5] = 1;
        board[5][1] = new Pawn(playerTwo, 6, new Position(5, 1));
        boardPositions[5][1] = 1;
        board[5][9] = new Pawn(playerTwo, 19, new Position(5, 9));
        boardPositions[5][9] = 1;


        // the defense

        //white king
        board[5][5] = new King(playerOne, new Position(5, 5));
        boardPositions[5][5] = 1;

        help = 1;
        for (int i = 4; i <= 6; i++) {
            help++;
            for (int j = 4; j <= 6; j++) {
                if (help == 7)
                    help++;
                if (j != i || i != 5 || j != 5) {
                    board[j][i] = new Pawn(playerOne, help, new Position(j, i));
                    boardPositions[j][i] = 1;
                    help++;
                }
            }
        }
        board[3][5] = new Pawn(playerOne, 5, new Position(3, 5));
        boardPositions[3][5] = 1;
        board[5][3] = new Pawn(playerOne, 1, new Position(5, 3));
        boardPositions[5][3] = 1;
        board[7][5] = new Pawn(playerOne, 9, new Position(7, 5));
        boardPositions[7][5] = 1;
        board[5][7] = new Pawn(playerOne, 13, new Position(5, 7));
        boardPositions[5][7] = 1;

    }


    @Override
    public boolean move(Position a, Position b) {


        //check if we can move to b
        if (b.inTheBoard() && this.board[b.getX()][b.getY()] == null &&
                this.getPieceAtPosition(a).getOwner().isTurn()) {

            if (b.isCorner() &&
                    (this.getPieceAtPosition(a).isPawn())){
                return false;
            }
            //not in diagonal
            if (a.getY() == b.getY() || a.getX() == b.getX()) {
                if (this.goOverboard(a, b)) {
                    if (!this.getPieceAtPosition(a).isPawn()) {
                        this.theKing.setPosition(b);
                    }
                    Movement movement = new Movement(a, b);
                    this.changePos(a, b);
                    this.boardPositions[b.getX()][b.getY()]++;
                    this.getPieceAtPosition(b).addDistance(movement.distance);
                    //Queue of kills
                    Queue<Piece> eat = movement.eat(b, this);
                    this.info.push(movement);
                    this.getPieceAtPosition(b).addMove(movement);

                    //update th num of kills of the piece
                    if (eat != null) {
                        //update the num of pieces of the players
                        if (this.getPieceAtPosition(b).getOwner().isPlayerOne()) {
                            this.playerTwo.set_num_of_pieces((eat.size()) * (-1));
                            this.playerTwo.addPieces(eat);
                        }
                        if (this.getPieceAtPosition(b).getOwner().isPlayerTwo()) {
                            this.playerOne.set_num_of_pieces((eat.size()) * -1);
                            this.playerOne.addPieces(eat);
                        }

                    }

                    //change the turn
                    playerOne.setTurn(!playerOne.isTurn());
                    playerTwo.setTurn(!playerTwo.isTurn());

                    theKing = (King) board[theKing.getPosition().getX()][theKing.getPosition().getY()];

                    // check if this move finish the game
                    this.checkIfTheGameTheGameIsFinished();
                    theKing = (King) board[theKing.getPosition().getX()][theKing.getPosition().getY()];
                    return true;
                }
            }
        }
        theKing = (King) board[theKing.getPosition().getX()][theKing.getPosition().getY()];
        return false;
    }

    /** check if in a given move I don't go overboard an other piece */
    private boolean goOverboard(Position a, Position b){
        Position position = new Position(0,0);
        if (a.getX() == b.getX()){
            if (a.getY() > b.getY()){
                for (int i = a.getY() - 1; i >= b.getY() ; i--) {
                    position.setPosition(a.getX(), i);
                    if (this.getPieceAtPosition(position) != null){
                        return false;
                    }
                }

            }
            if (a.getY() < b.getY()){
                for (int i = a.getY() + 1; i <= b.getY() ; i++) {
                    position.setPosition(a.getX(), i);
                    if (this.getPieceAtPosition(position) != null){
                        return false;
                    }
                }
            }
        }
        if (a.getY() == b.getY()){
            if (a.getX() > b.getX()){
                for (int i = a.getX() - 1; i >= b.getX() ; i--) {
                    position.setPosition(i, a.getY());
                    if (this.getPieceAtPosition(position) != null){
                        return false;
                    }
                }

            }
            if (a.getX() < b.getX()){
                for (int i = a.getX() + 1; i <= b.getX() ; i++) {
                    position.setPosition(i, a.getY());
                    if (this.getPieceAtPosition(position) != null){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /** execute the real move */
    private void changePos(Position a, Position b) {
        board[b.getX()][b.getY()] = board[a.getX()][a.getY()];
        board[a.getX()][a.getY()] = null;
        board[b.getX()][b.getY()].setPosition(b);
    }

    @Override
    public Piece getPieceAtPosition(Position position) {
        return this.board[position.getX()][position.getY()];
    }

    @Override
    public Player getFirstPlayer() {
        return this.playerOne;
    }

    @Override
    public Player getSecondPlayer() {
        return this.playerTwo;
    }

    @Override
    public boolean isGameFinished() {
        return isFinish;
    }

    /** This function print the stat in the end of the game
     */
    private void printStat(ConcretePlayer winner, ConcretePlayer looser){
        winner.win = true;
        winner.sortByNumOfMovements();
        for (int i = 0; i < winner.pieces.size(); i++) {
            if (winner.pieces.get(i).allPositions.size() > 1)
                winner.pieces.get(i).soutAllPositions();
        }
        looser.sortByNumOfMovements();
        for (int i = 0; i < looser.pieces.size(); i++) {
            if (looser.pieces.get(i).allPositions.size() > 1)
                looser.pieces.get(i).soutAllPositions();
        }
        for (int i = 0; i < 75; i++) {
            System.out.print("*");
        }
        System.out.print("\n");
        Collections.sort(allPieces, Comparator::compareByKills);
        for (int i = 0; i < this.allPieces.size(); i++) {
            if (allPieces.get(i).getNum_of_kills() > 0)
                System.out.println(allPieces.get(i).getName().toString()+ ": " + allPieces.get(i).getNum_of_kills() + " kills");
        }
        for (int i = 0; i < 75; i++) {
            System.out.print("*");
        }
        System.out.print("\n");
        Collections.sort(allPieces, Comparator::compareByDistance);
        for (int i = 0; i < this.allPieces.size(); i++) {
            if (allPieces.get(i).getDistance() > 0)
                System.out.println(allPieces.get(i).getName().toString()+ ": " + allPieces.get(i).getDistance() + " squares");
        }
        for (int i = 0; i < 75; i++) {
            System.out.print("*");
        }
        System.out.print("\n");
        Collections.sort(steps, Comparator::compareThePositions);
        for (int i = 0; i < this.steps.size(); i++) {
            System.out.println(steps.get(i).toString() + steps.get(i).getNum_of_stepping() + " pieces");
        }
        for (int i = 0; i < 75; i++) {
            System.out.print("*");
        }
        System.out.print("\n");
        winner.win = false;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return playerTwo.isTurn();
    }

    @Override
    public void reset() {
        this.initBoard();
        this.playerOne.resetPlayer();
        this.playerTwo.resetPlayer();
        this.theKing.resurrection();
        this.playerOne.setTurn(false);
        this.playerTwo.setTurn(true);
        this.isFinish = false;
    }

    @Override
    public void undoLastMove() {
        if (!this.info.empty()){
            // take the last move of the game
            Movement lastMove = this.info.pop();
            // inverse the move
            changePos(lastMove.getTo(), lastMove.getFrom());
            this.boardPositions[lastMove.getTo().getX()][lastMove.getTo().getY()]--;
            // this queue contain the pieces that deads bescause the movement
            Queue<Piece> pieces_that_deads = lastMove.getDeads();
            Piece eaten;
            if (pieces_that_deads != null) {
                if (this.getPieceAtPosition(lastMove.getFrom()).getOwner().equals(this.playerTwo))
                    this.playerOne.set_num_of_pieces(pieces_that_deads.size());
                else this.playerTwo.set_num_of_pieces(pieces_that_deads.size());
                // we put back on the board the pieces that deads because the movement
                while (!pieces_that_deads.isEmpty()) {
                    eaten = pieces_that_deads.poll();
                    board[eaten.getPosition_of_death().getX()][eaten.getPosition_of_death().getY()] = (ConcretePiece) eaten;
                    ((Pawn) this.getPieceAtPosition(lastMove.getFrom())).setKills(-1);

                    this.getPieceAtPosition(lastMove.getFrom()).addDistance(lastMove.distance * (-1));
                }
            }

            //change the turn
            playerOne.setTurn(!playerOne.isTurn());
            playerTwo.setTurn(!playerTwo.isTurn());
        }
    }

    @Override
    public int getBoardSize() {
        return 11;
    }

    /** at the end of the game this function take every information of the pieces and
     the squares of the game and stock them into different places */
    private void helpForFinish(){
        ArrayList<ConcretePiece> p1 = new ArrayList<>();
        ArrayList<ConcretePiece> p2 = new ArrayList<>();
        this.allPieces = new ArrayList<>();
        this.allPieces.addAll(this.playerOne.pieces);
        this.allPieces.addAll(this.playerTwo.pieces);
        for (int i = 0; i < this.getBoardSize(); i++) {
            for (int j = 0; j < this.getBoardSize(); j++) {
                if (this.boardPositions[i][j] > 1) {
                    Position position = new Position(i,j);
                    position.setNum_of_stepping(this.boardPositions[i][j]);
                    this.steps.add(position);

                }
                if (this.board[i][j] != null && this.board[i][j].getOwner().isPlayerTwo()) {
                    p2.add(this.board[i][j]);
                }
                if (this.board[i][j] != null && this.board[i][j].getOwner().isPlayerOne()) {
                    p1.add(this.board[i][j]);
                }
            }
        }
        this.playerOne.addAllMyPieces(p1);
        this.playerTwo.addAllMyPieces(p2);
        this.allPieces = this.playerOne.pieces;
        this.allPieces.addAll(this.playerTwo.pieces);
    }

    /**
     * This function check if one of the 4 options of the end of the game
     * is true
     */
    private void checkIfTheGameTheGameIsFinished(){
        // the king is on the corner of the board
        if (this.theKing.getPosition().isCorner()) {
            this.playerOne.setWins(1);
            this.helpForFinish();
            this.printStat(this.playerOne, this.playerTwo);
            isFinish = true;
        }
        // the king is dead
        if (!this.theKing.isAlive()) {
            this.playerTwo.setWins(1);
            this.helpForFinish();
            this.printStat(this.playerTwo, this.playerOne);
            isFinish = true;
        }
        // Player num one have 0 pieces
        if (this.playerOne.getNum_of_pieces() == 0){
            this.playerTwo.setWins(1);
            this.helpForFinish();
            this.printStat(this.playerTwo, this.playerOne);
            isFinish = true;
        }
        // Player num two have 0 pieces
        if (this.playerTwo.getNum_of_pieces() == 0 ){
            this.playerOne.setWins(1);
            this.helpForFinish();
            this.printStat(this.playerOne, this.playerTwo);
            isFinish = true;
        }
    }



}

