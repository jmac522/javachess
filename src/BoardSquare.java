/* Ambiguous class which to define the square of a chess board
 * Will be related to 2 concrete subclasses which represent either an
 * unoccupied board square or a square with piece on it
 *
 * Will contain methods for:
 *	- returning if a square is occupied (boolean)
 *	- returning the piece a square contains (Piece object or null)
 *
 * a Board object will be built as an array of 64 board square objects [0...63]
 *
 */


// Import hashmap to create cache for unnocupied board squares
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



public abstract class BoardSquare {
    // Holds the location of the square on the board represented by its index
    // in the board array. Should only be accessable by subclasses. Should not be
    // mutable once tile is created.
    protected final int location;

    // Cache to hold all possible unoccupied squares of a Board to reference
    // in order to avoid having to generate new UnoccupiedBoardSquares when a piece
    // is moved or pawn is captured via en passant
    private static final Map<Integer, UnoccupiedBoardSquare> UNOCCUPIED_SQUARES_CACHE = generateUnoccupiedSquaresCache();

    private static Map<Integer, UnoccupiedBoardSquare> generateUnoccupiedSquaresCache() {
        // variable to hold the map cache to be returned
        final Map<Integer, UnoccupiedBoardSquare> unoccupiedSquareMap = new HashMap<>();

        for (int i = 0; i < 64; i++) {
            // for each index between 0 and 63 store an UBS with the i as its location
            // into the map at a key set to i
            unoccupiedSquareMap.put(i, new UnoccupiedBoardSquare(i));
        }

        // Return an unmodifiable map to ensure that the cache cannot be mutated
        return Collections.unmodifiableMap(unoccupiedSquareMap);
    }

    // Factory for creating a new BoardSquare (Only public way to create a new square)
    public static BoardSquare createBoardSquare(final int loc, final Piece p) {
        if (p != null) {
            // if a piece is passed create an occupied square with the piece
            return new OccupiedBoardSquare(loc, p);
        } else {
            // if no piece is passed, return the correct unoccupied square based on passed location
            return UNOCCUPIED_SQUARES_CACHE.get(loc);
        }
    }

    // Constcutor taking a location to create an instance of BoardSquare object
    // with a given location
    protected BoardSquare(int loc) {
        this.location = loc;
    }

    // method for returning if a square is occupied. Will be definied individually
    // in OccupiedBoardSquare and UnoccupiedBoardSquare classes
    public abstract boolean isOccupied();

    // Method for returning the piece contained by a square. Will return either a piece
    // object or null (if square is unoccupied)
    public abstract Piece getPiece();
}
