public class GameUtilities {
    // Constant variables for checking if a position is in a given row or column
    //                                           a      b      c      d      e      f      g      h
    public static final boolean[] COLUMN_A = { true , false, false, false, false, false, false, false, // 8
                                               true , false, false, false, false, false, false, false, // 7
                                               true , false, false, false, false, false, false, false, // 6
                                               true , false, false, false, false, false, false, false, // 5
                                               true , false, false, false, false, false, false, false, // 4
                                               true , false, false, false, false, false, false, false, // 3
                                               true , false, false, false, false, false, false, false, // 2
                                               true , false, false, false, false, false, false, false } ; // 1

    public static final boolean[] COLUMN_H = { false , false, false, false, false, false, false, true, // 8
                                               false , false, false, false, false, false, false, true, // 7
                                               false , false, false, false, false, false, false, true, // 6
                                               false , false, false, false, false, false, false, true, // 5
                                               false , false, false, false, false, false, false, true, // 4
                                               false , false, false, false, false, false, false, true, // 3
                                               false , false, false, false, false, false, false, true, // 2
                                               false , false, false, false, false, false, false, true } ; // 1

    public static final boolean[] COLUMN_B = { false , false, false, false, false, false, false, false, // 8
                                              false , false, false, false, false, false, false, false, // 7
                                              false , false, false, false, false, false, false, false, // 6
                                              false , false, false, false, false, false, false, false, // 5
                                              false , false, false, false, false, false, false, false, // 4
                                              false , false, false, false, false, false, false, false, // 3
                                              false , false, false, false, false, false, false, false, // 2
                                              false  , false, false, false, false, false, false, false } ; // 1

    public static final boolean[] COLUMN_G = { false  , false, false, false, false, false, true, false , // 8
                                                false , false, false, false, false, false, true, false, // 7
                                                false , false, false, false, false, false, true, false, // 6
                                                false , false, false, false, false, false, true, false, // 5
                                                false , false, false, false, false, false, true, false, // 4
                                                false , false, false, false, false, false, true, false, // 3
                                                false , false, false, false, false, false, true, false, // 2
                                                false , false, false, false, false, false, true, false } ; // 1

    // Static method for confirming if a given location int is within the 0-63 board range
    static boolean isValidBoardLocation(final int location) {
        return location >= 0 && location < 64;
    }
}
