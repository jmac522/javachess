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
    };

    // Method for getting movement direction based on side (Black is moving in Positive direction, White in Negative)
    public abstract int getMovementDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();
    public abstract Player choosePlayer(Player whitePlayer, Player blackPlayer);
    }
}
