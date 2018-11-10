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
import javafx.scene.control.Button;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



public abstract class BoardSquare extends Button {
    // Holds the location of the square on the board represented by its index
    // in the board array. Should only be accessable by subclasses. Should not be
    // mutable once tile is created.
    protected final int location;
    protected final SquareColor squareColor;
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
            SquareColor squareShade;
            // assign apporpriate square color based on board location
            squareShade = GameUtilities.calculateSquareColor(i);
            unoccupiedSquareMap.put(i, new UnoccupiedBoardSquare(i, squareShade));
        }
        // Return an unmodifiable map to ensure that the cache cannot be mutated
        return Collections.unmodifiableMap(unoccupiedSquareMap);
    }

    // Factory for creating a new BoardSquare (Only public way to create a new square)
    public static BoardSquare createBoardSquare(final int loc, final Piece p, final SquareColor squareColor) {
        if (p != null) {
            // if a piece is passed create an occupied square with the piece
            return new OccupiedBoardSquare(loc, p, squareColor);
        } else {
            // if no piece is passed, return the correct unoccupied square based on passed location
            return UNOCCUPIED_SQUARES_CACHE.get(loc);
        }
    }

    // Constcutor taking a location to create an instance of BoardSquare object
    // with a given location
    protected BoardSquare(int loc, final SquareColor squareColor) {
        this.location = loc;
        this.squareColor = squareColor;
        this.setPrefSize(80, 80);
        this.setOnMouseEntered(e -> handleMouseEntered());
        this.setOnMouseExited(e -> handleMouseExited());
    }

    // method for returning if a square is occupied. Will be definied individually
    // in OccupiedBoardSquare and UnoccupiedBoardSquare classes
    public abstract boolean isOccupied();

    // Method for returning the piece contained by a square. Will return either a piece
    // object or null (if square is unoccupied)
    public abstract Piece getPiece();

    // Method to handle hovering a board square with the mouse
    private void handleMouseEntered() {
        this.getStyleClass().add("hovered-board-square");
        // setStyle("-fx-background-color: rgba(167, 129, 107, 1) ;" + "-fx-border-color: black;" + "-fx-text-fill: white;");
    }

    // Method to handle when square is no longer hovered
    private void handleMouseExited() {
        this.getStyleClass().remove("hovered-board-square");
        this.getStyleClass().add(this.squareColor.getSquareCSS());
    }

    // Enum for representing if this is a dark or light board square
    public enum SquareColor {
        LIGHT {
            @Override
            public String getSquareCSS() {
                return "light-board-square";
            }
        },
        DARK {
            @Override
            public String getSquareCSS() {
                return "dark-board-square";
            }
        };

        // Method returns a CSS class name for styling the square according to its SquareColor
        public abstract String getSquareCSS();
    }
}
