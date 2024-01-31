public class Board {
    protected Piece[][] board = new ConcretePiece[11][11]; // the board himself
    protected int[][] boardPositions = new int[11][11]; // num of stepping on each position of the board
    private ConcretePlayer playerOne;
    private ConcretePlayer playerTwo;
    Board(ConcretePlayer p1, ConcretePlayer p2){
        this.playerOne = p1;
        this.playerTwo = p2;
        this.initBoard();

    }
    public void initBoard() {
        this.board = new Piece[11][11];
        Position position;

        // tha attack

        // the pieces of the top and the down of the board
        int help = 1;
        for (int i = 3; i <= 7; i++) {
            position = new Position(i, 0);
            this.putPieceAtPosition(position, new Pawn(playerTwo, help, position));
            this.addStep(position, 1);
            position = new Position(i, 10);
            this.putPieceAtPosition(position, new Pawn(playerTwo, help + 19, position));
            this.addStep(position, 1);
            help++;
        }

        // the pieces of the right and the left of the board
        help = 7;
        for (int i = 3; i <= 5; i++) {
            position = new Position(0, i);
            this.putPieceAtPosition(position, new Pawn(playerTwo, help, position));
            this.addStep(position, 1);
            help++;
            position = new Position(10, i);
            this.putPieceAtPosition(position, new Pawn(playerTwo, help, position));
            this.addStep(position, 1);
            help++;
        }
        board[10][5] = new Pawn(playerTwo, help, new Position(10, 5));
        boardPositions[10][5] = 1;
        help += 2;
        for (int i = 6; i <= 7; i++) {
            position = new Position(0, i);
            this.putPieceAtPosition(position, new Pawn(playerTwo, help, position));
            this.addStep(position, 1);
            help++;
            position = new Position(10, i);
            this.putPieceAtPosition(position, new Pawn(playerTwo, help, position));
            this.addStep(position, 1);
            help++;
        }

        position = new Position(1, 5);
        this.putPieceAtPosition(position, new Pawn(playerTwo, 12, position));
        this.addStep(position, 1);
        position = new Position(9, 5);
        this.putPieceAtPosition(position, new Pawn(playerTwo, 13, position));
        this.addStep(position, 1);
        position = new Position(5, 1);
        this.putPieceAtPosition(position, new Pawn(playerTwo, 6, position));
        this.addStep(position, 1);
        position = new Position(5, 9);
        this.putPieceAtPosition(position, new Pawn(playerTwo, 19, position));
        boardPositions[5][9] = 1;


        // the defense

        //white king
        position = new Position(5, 5);
//        board[5][5] = new King(playerOne, new Position(5, 5));
        this.putPieceAtPosition(position, new King(playerOne, position));
//        boardPositions[5][5] = 1;
        this.addStep(position, 1);

        help = 1;
        for (int i = 4; i <= 6; i++) {
            help++;
            for (int j = 4; j <= 6; j++) {
                if (help == 7)
                    help++;
                if (j != i || i != 5 || j != 5) {
                    position = new Position(j, i);
                    this.putPieceAtPosition(position, new Pawn(playerOne, help, position));
                    this.addStep(position, 1);
                    help++;
                }
            }
        }
        position = new Position(3, 5);
        this.putPieceAtPosition(position, new Pawn(playerOne, 5, position));
        this.addStep(position, 1);
        position = new Position(5, 3);
        this.putPieceAtPosition(position, new Pawn(playerOne, 1, position));
        this.addStep(position, 1);
        position = new Position(7, 5);
        this.putPieceAtPosition(position, new Pawn(playerOne, 9, position));
        this.addStep(position, 1);
        position = new Position(5, 7);
        this.putPieceAtPosition(position, new Pawn(playerOne, 13, position));
        this.addStep(position, 1);

    }

    /** update a position of the board with a piece */
    public void putPieceAtPosition(Position position, Piece piece){
        this.board[position.getX()][position.getY()] = piece;
    }

    public Piece getPieceAtPosition(Position position){
        if (this.board[position.getX()][position.getY()] != null)
            return this.board[position.getX()][position.getY()];
        return null;
    }

    /** update the num of step of a position of the board */
    public void addStep(Position position, int x){
        this.boardPositions[position.getX()][position.getY()] += x;
    }
    public int getNumOfStep(Position position){
        return this.boardPositions[position.getX()][position.getY()];
    }
}
