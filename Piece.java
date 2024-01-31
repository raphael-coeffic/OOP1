import java.util.Queue;

/**
 * The src.Piece interface defines the characteristics of a game piece in a chess-like game.
 * Implementing classes should provide information about the player who owns the piece
 * and return a Unicode character representing the type of the piece (e.g., ♟ for pawn,
 * ♞ for knight, ♜ for rook, etc.).
 */
public interface Piece {

    /**
     * Get the player who owns the piece.
     *
     * @return The player who is the owner of this game piece.
     */
    Player getOwner();

    /**
     * Get a Unicode character representing the type of the game piece.
     *  <a href="https://en.wikipedia.org/wiki/Chess_symbols_in_Unicode">...</a>
     * @return A Unicode character representing the type of this game piece
     *         (e.g., ♟ for pawn, ♞ for knight, ♜ for rook, etc.).
     *
     */
    String getType();
    String getName();

    boolean isPawn();

    boolean sameOwner(Piece pieceAtPosition);
    
    Position getPosition_of_death();

//    void addMove(Movement movement);

//    Queue<Movement> getAllMovesQueue();

//    void undoMove();

    void setPositionOfDeath(Position up);

    void addDistance(int distance);

    void soutAllPositions();

    void addMove(Movement movement);

    void setPosition(Position b);
}
