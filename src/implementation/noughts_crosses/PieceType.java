package implementation.noughts_crosses;

public enum PieceType {
	MAIN;

	public static PieceType fromHash(int typeHashCode) {
		if (typeHashCode == MAIN.hashCode()) {
			return MAIN;
		} else {
			throw new IllegalArgumentException("typeHashCode is invalid");
		}
	}
}
