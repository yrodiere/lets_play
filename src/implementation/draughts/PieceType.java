package implementation.draughts;

public enum PieceType {
	MAN, KING;

	public static PieceType fromHash(int typeHashCode) {
		if (typeHashCode == MAN.hashCode()) {
			return MAN;
		} else if (typeHashCode == KING.hashCode()) {
			return KING;
		} else {
			throw new IllegalArgumentException("typeHashCode is invalid : " + typeHashCode);
		}
	}
}
