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
        
        @Override
        public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer){
        	return whitePlayer;
        }

        @Override
        public String toString() {
            // to string will be used in assigning class names for appropriate piece assets
            return "white-";
        }

        @Override
        public String turnMessage() {
            return "White to move!";
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
        
        @Override
        public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer){
        	return blackPlayer;
        }

        @Override
        public String toString() {
            // to string will be used in assigning class names for appropriate piece assets
            return "black-";
        }

        @Override
        public String turnMessage() {
            return "Black to move!";
        }
    };

    // Method for getting movement direction based on side (Black is moving in Positive direction, White in Negative)
    public abstract int getMovementDirection();
    
    // Methods for determining if a given Side enum is white or black
    public abstract boolean isWhite();
    public abstract boolean isBlack();
    
    // Method used for returning a player based on a pieces Side affiliation
    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);

    public abstract String toString();
    public abstract String turnMessage();
}

