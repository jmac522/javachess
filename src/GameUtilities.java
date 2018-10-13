public class GameUtilities {
    // Constant variables for checking if a position is in a given row or column
    //
    public static final boolean[] COLUMN_A = generateColumnArray(0);
    public static final boolean[] COLUMN_H = generateColumnArray(7);
    public static final boolean[] COLUMN_B = generateColumnArray(1);
    public static final boolean[] COLUMN_G = generateColumnArray(6);
    public static final boolean[] ROW_TWO = generateRowArray(2);
    public static final boolean[] ROW_SEVEN = generateRowArray(7);
    public static final int TOTAL_TILES = 64;
    public static final int TILES_PER_ROW = 8;


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
}
