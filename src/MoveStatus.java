// Enum for assiting with move execution

public enum MoveStatus {
	DONE {
		@Overrides
		public boolean isDone() {
			return true;
		}
	},
	ILLEGAL_MOVE{
		@Overrides
		public boolean isDone() {
			return false;
		}
	},
	PLAYER_IN_CHECK {
		@Override
		public boolean isDone() {
			return false;
		}
	};
	
	public abstract boolean isDone();
}
