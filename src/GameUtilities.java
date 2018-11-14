import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class GameUtilities {
    // Constants for checking if a position is in a given row or column
    //
    public static final boolean[] COLUMN_A = generateColumnArray(0);
    public static final boolean[] COLUMN_H = generateColumnArray(7);
    public static final boolean[] COLUMN_B = generateColumnArray(1);
    public static final boolean[] COLUMN_G = generateColumnArray(6);
    public static final int TOTAL_TILES = 64;
    public static final int TILES_PER_ROW = 8;
    public static final HashMap<Integer, String> moveNameLookup = generateMoveNameLookup();


    // Constants holding piece-square heuristic weights for adjusting base heuristic based on piece position
    public static final double[] WHITE_KING_PIECE_SQUARE = { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0,
                                                             -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0,
                                                             -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0,
                                                             -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0,
                                                             -2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0,
                                                             -1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0,
                                                             2.0 , 2.0 , 0.0 , 0.0 , 0.0 , 0.0 , 2.0 , 2.0 ,
                                                             2.0 , 3.0 , 1.0 , 0.0 , 0.0 , 1.0 , 3.0 , 2.0 };
    public static final double[] BLACK_KING_PIECE_SQUARE = reverseArray(WHITE_KING_PIECE_SQUARE);

    public static final double[] WHITE_QUEEN_PIECE_SQUARE = { -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0,
                                                              -1.0, 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , -1.0,
                                                              -1.0, 0.0 , 0.5 , 0.5 , 0.5 , 0.5 , 0.0 , -1.0,
                                                              -0.5, 0.0 , 0.5 , 0.5 , 0.5 , 0.5 , 0.0 , -0.5,
                                                              0.0 , 0.0 , 0.5 , 0.5 , 0.5 , 0.5 , 0.0 , -0.5,
                                                              -1.0, 0.5 , 0.5 , 0.5 , 0.5 , 0.5 , 0.0 , -1.0,
                                                              -1.0, 0.0 , 0.5 , 0.0 , 0.0 , 0.0 , 0.0 , -1.0 ,
                                                              -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0 };

    public static final double[] BLACK_QUEEN_PIECE_SQUARE = reverseArray(WHITE_QUEEN_PIECE_SQUARE);

    public static final double[] WHITE_ROOK_PIECE_SQUARE = { 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 ,
                                                             0.5 , 1.0 , 1.0 , 1.0 , 1.0 , 1.0 , 1.0 , 0.5 ,
                                                             -0.5, 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , -0.5,
                                                             -0.5, 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , -0.5,
                                                             -0.5, 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , -0.5,
                                                             -0.5, 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , -0.5,
                                                             -0.5, 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , -0.5,
                                                             0.0 , 0.0 , 0.0 , 0.5 , 0.5 , 0.0 , 0.0 , 0.0 };

    public static final double[] BLACK_ROOK_PIECE_SQUARE = reverseArray(WHITE_ROOK_PIECE_SQUARE);

    public static final double[] WHITE_BISHOP_PIECE_SQUARE = { -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0,
                                                               -1.0, 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , -1.0,
                                                               -1.0, 0.0 , 0.5 , 1.0 , 1.0 , 0.5 , 0.0 , -1.0,
                                                               -1.0, 0.5 , 0.5 , 1.0 , 1.0 , 0.5 , 0.5 , -1.0,
                                                               -1.0, 0.0 , 1.0 , 1.0 , 1.0 , 1.0 , 0.0 , -1.0,
                                                               -1.0, 1.0 , 1.0 , 1.0 , 1.0 , 1.0 , 1.0 , -1.0,
                                                               -1.0, 0.5 , 0.0 , 0.0 , 0.0 , 0.0 , 0.5 , -1.0,
                                                               -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0};
    public static final double[] BLACK_BISHOP_PIECE_SQUARE = reverseArray(WHITE_BISHOP_PIECE_SQUARE);

    public static final double[] WHITE_KNIGHT_PIECE_SQUARE = { -5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0,
                                                               -4.0, -2.0, 0.0 , 0.0 , 0.0 , 0.0 , -2.0, -4.0,
                                                               -3.0, 0.0 , 1.0 , 1.5 , 1.5 , 1.0 , 0.0 , -3.0,
                                                               -3.0, 0.5 , 1.5 , 2.0 , 2.0 , 1.5 , 0.5 , -3.0,
                                                               -3.0, 0.0 , 1.5 , 2.0 , 2.0 , 1.5 , 0.0 , -3.0,
                                                               -3.0, 0.5 , 1.0 , 1.5 , 1.5 , 1.0 , 0.5 , -3.0,
                                                               -4.0, -2.0, 0.0 , 0.5 , 0.5 , 0.0 , -2.0, -4.0,
                                                               -5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0};
    public static final double[] BLACK_KNIGHT_PIECE_SQUARE = reverseArray(WHITE_KNIGHT_PIECE_SQUARE);

    public static final double[] WHITE_PAWN_PIECE_SQUARE = { 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 ,
                                                             5.0 , 5.0 , 5.0 , 5.0 , 5.0 , 5.0 , 5.0 , 5.0 ,
                                                             1.0 , 1.0 , 2.0 , 3.0 , 3.0 , 2.0 , 1.0 , 1.0 ,
                                                             0.5 ,  0.5,  1.0,  2.5,  2.5,  1.0,  0.5,  0.5,
                                                             0.0 ,  0.0,  0.0,  2.0,  2.0,  0.0,  0.0,  0.0,
                                                             0.5 , -0.5, -1.0,  0.0,  0.0, -1.0, -0.5,  0.5,
                                                              0.5,  1.0, 1.0,  -2.0, -2.0,  1.0,  1.0,  0.5,
                                                             0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 , 0.0 };
    public static final double[] BLACK_PAWN_PIECE_SQUARE = reverseArray(WHITE_PAWN_PIECE_SQUARE);

    // Static method for confirming if a given location int is within the 0-63 board range
    static boolean isValidBoardLocation(final int location) {
        return location >= 0 && location < 64;
    }

    // general method for generating a boolean array to represent a given row number
    private static boolean[] generateRowArray(final int rowNumber) {
        // create an array of 64 booleans (default to false)
        final boolean[] row = new boolean[TOTAL_TILES];
        // use passed row number to get starting index of corresponding row
        int rowStart = TOTAL_TILES - (TILES_PER_ROW * rowNumber);
        // use the row starting index to determine the end of the row
        int rowEnd = rowStart + TILES_PER_ROW;
        do {
            row[rowStart] = true;
            rowStart += 1;
        } while(rowStart < rowEnd);
        return row;
    }

    // general method for generating a boolean array to represent a given Column number
    private static boolean[] generateColumnArray(int columnNumber) {
        // create an array of 64 booleans (default to false)
        final boolean[] column = new boolean[TOTAL_TILES];
        do {
            column[columnNumber] = true;
            columnNumber += TILES_PER_ROW;
        } while(columnNumber < TOTAL_TILES);
        return column;
    }


    // Method used to calculate the shade of a board square based on its position
    public static BoardSquare.SquareColor calculateSquareColor(int location) {
        BoardSquare.SquareColor squareShade;
        if ((location / 8) % 2 == 0) {
            if ((location + 1) % 2 == 0) {
                squareShade = BoardSquare.SquareColor.DARK;
            } else {
                squareShade = BoardSquare.SquareColor.LIGHT;
            }
        } else {
            if (( location % 2 ) == 0) {
                squareShade = BoardSquare.SquareColor.DARK;
            } else {
                squareShade = BoardSquare.SquareColor.LIGHT;
            }

        }
        return squareShade;
    }

    // Method to throw the checkmate alert
    public static void throwMateAlert(Alert alert, String winner) {
        alert.setHeaderText("Checkmate! " + winner + " wins!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().getText() == "New Game"){
            // set up a new game
            GameDriver.newGame();
        } else {
            // close the program
        }
    }

    // method to set up the alert box for the check mate alert
    public static void setUpMateAlert(Alert alert) {
        alert.setTitle("Game Over");
        alert.setHeaderText("Checkmate!");
        alert.setContentText("What would you like to do?");

        ButtonType newGameButton = new ButtonType("New Game");
        ButtonType quitButton = new ButtonType("Quit");

        alert.getButtonTypes().setAll(newGameButton, quitButton);
    }

    // Method to update gamedriver when executing a move
    public static void updateBoard(Move move) {
        // Execute the move if it's status shows it was successful, returns the new board object
        GameDriver.activeGameBoard = move.execute();

        Player currentPlayer = GameDriver.activeGameBoard.getCurrentPlayer();

        // Update the game's side to move based on the board that is returned
        GameDriver.sideToMove = currentPlayer.getSide();

        // Update the bottom message based on turn, and if active player is currently in check
        String bottomMessage = "";
        if (GameDriver.activeGameBoard.getCurrentPlayer().isInCheck() &&
                !GameDriver.activeGameBoard.getCurrentPlayer().isMated()) {
            bottomMessage += "Check! ";
        }
        bottomMessage += GameDriver.sideToMove.turnMessage();
        GameDriver.setBottomText(bottomMessage);
        GameDriver.drawUpdatedBoard();

        // Append the move notation for the executed move based on if white or black made teh move
        if (move.movingPiece.getColor() == Side.WHITE) {
            MovesTableCell moveCell = new MovesTableCell();
            moveCell.setText(move.toString());
            moveCell.getStyleClass().add("move-table-cell");
            GameDriver.movesPane.add(moveCell, 0, GameDriver.moveCounter);
            GameDriver.whiteMovesStack.getChildren().add(moveCell);

            // If a piece is being captured, add the appropriate captured piece icon
            if (move instanceof  CaptureMove || move instanceof  PawnCaptureMove ||
            move instanceof EnPassantCapture) {
                String pieceCSS = move.getAttackedPiece().color.toString() +
                        move.getAttackedPiece().getPieceType().toString() + "-icon";
                CapturedPieceCell capturedPieceCell = new CapturedPieceCell(pieceCSS);
                GameDriver.capturedPiecePane.add(capturedPieceCell, 1, GameDriver.capturedBlack);
                GameDriver.capturedBlack += 1;
            }
        } else {
            MovesTableCell moveCell = new MovesTableCell();
            moveCell.setText(move.toString());
            moveCell.getStyleClass().add("move-table-cell");
            GameDriver.movesPane.add(moveCell, 1, GameDriver.moveCounter);
            GameDriver.blackMovesStack.getChildren().add(moveCell);
            GameDriver.moveCounter += 1;

            // If a piece is being catpured, add the appropriate captured piece icon
            if (move instanceof  CaptureMove || move instanceof  PawnCaptureMove ||
            move instanceof EnPassantCapture) {
                String pieceCSS = move.getAttackedPiece().color.toString() +
                        move.getAttackedPiece().getPieceType().toString() + "-icon";
                CapturedPieceCell capturedPieceCell = new CapturedPieceCell(pieceCSS);
                GameDriver.capturedPiecePane.add(capturedPieceCell, 0, GameDriver.capturedWhite);
                GameDriver.capturedWhite += 1;
            }
        }

        // Play audio for making the associated move
        try {
            InputStream inputStream = GameUtilities.class.getResourceAsStream("img_assets/move_sound.wav");
            AudioStream audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
        } catch (Exception e){
            System.out.println("file not found? ");
        }
        // If a player is checkmated throw an alert message
        if (GameDriver.activeGameBoard.getCurrentPlayer().isMated()) {
            // Play victory music
            try {
                InputStream inputStream = GameUtilities.class.getResourceAsStream("img_assets/cormvat.wav");
                AudioStream audioStream = new AudioStream(inputStream);
                AudioPlayer.player.start(audioStream);
            } catch (Exception e){
                System.out.println("file not found? ");
            }
            // throw the alert
            GameUtilities.throwMateAlert(GameDriver.mateAlert,
                    GameDriver.activeGameBoard.getCurrentPlayer().getOpponent().getSide().name());
        } else if (currentPlayer.getPlayerType() == Player.PlayerType.COMPUTER) {
            // If after a move, the new player to move is a computer player, use the appropriate move method based
            // on the difficulty level
            if (GameDriver.isHard) {
                currentPlayer.miniMaxRootMove(3, GameDriver.activeGameBoard, true);
            } else {
                try {
                    TimeUnit.SECONDS.sleep((long) 0.5);
                    currentPlayer.computerRandomMove(currentPlayer);
                } catch (Exception e) {
                    System.out.println("Random move desync");
                }
            }
        }
    }

    private static HashMap<Integer, String> generateMoveNameLookup() {
        HashMap<Integer, String> moveMap = new HashMap<>();
        for (int i = 0; i < TOTAL_TILES; i ++) {
            String file = "";
            int rankPreFlip = (i / 8) + 1;
            int rankPostFlip = reverseNumber(rankPreFlip, 1, 8);
            String rank = Integer.toString(rankPostFlip);
            if (i % 8 == 0) {
                file = "a";
            } else if ( (i - 1) % 8 == 0 ) {
                file = "b";
            } else if ( (i - 2) % 8 == 0 ) {
                file = "c";
            } else if ( (i - 3) % 8 == 0 ) {
                file = "d";
            } else if ( (i - 4) % 8 == 0 ) {
                file = "e";
            } else if ( (i - 5) % 8 == 0 ) {
                file = "f";
            } else if ( (i - 6) % 8 == 0 ) {
                file = "g";
            } else if ( (i - 7) % 8 == 0 ) {
                file = "h";
            }

            moveMap.put(i, file + rank);
        }
        return moveMap;
    }

    // Helper method for reversing a numbers position in a range
    private static int reverseNumber(int num, int min, int max) {
        return (max + min) - num;
    }

    // Method for reversing array to assist in piece square constants
    private static double[] reverseArray(double[] array) {
        double[] newArray = new double[64];
        int size = array.length;
        for (int i = 0; i < size / 2; i++) {
            newArray[i] = -(array[size - i - 1]);
            newArray[size - i - 1] = -array[i];
        }
        return newArray;
    }
}
