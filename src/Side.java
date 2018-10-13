/*
*  Side in an enum to hold WHITE and BLACK as constants to represent a piece's affiliation
*
*
*
* */

public enum Side  {
    WHITE {
        @Override
        public int getMovementDirection() {
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }
    },
    BLACK {
        @Override
        public int getMovementDirection() {
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }
    };

    // Method for getting movement direction based on side (Black is moving in Positive direction, White in Negative)
    public abstract int getMovementDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();
}
