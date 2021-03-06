package cluedo.model.gameObjects;

/**
 * Class that represents the characters in the game.
 *
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class CluedoCharacter implements GameObject {

	/**
	 * List of possible suspects.
	 *
	 * @author Cameron Bryers, Hannah Craighead.
	 *
	 */
	public enum Suspect {

		MISS_SCARLET(1), COLONEL_MUSTARD(2), MRS_WHITE(3), THE_REVEREND_GREEN(4), MRS_PEACOCK(
				5), PROFESSOR_PLUM(6);

		private final int value;

		Suspect(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public String toString() {
			switch (value) {
			case 2:
				return "Colonel Mustard";
			case 1:
				return "Miss Scarlet";
			case 5:
				return "Mrs. Peacock";
			case 3:
				return "Mrs. White";
			case 6:
				return "Professor Plum";
			case 4:
				return "The Reverend Green";
			default:
				return "Error";
			}
		}

		public String toMiniString(){
			switch (value) {
			case 2:
				return "M";
			case 1:
				return "S";
			case 5:
				return "p";
			case 3:
				return "W";
			case 6:
				return "P";
			case 4:
				return "G";
			default:
				return "Error";
			}
		}
	}



	private Suspect m_suspect;

	public CluedoCharacter(Suspect suspect) {
		this.m_suspect = suspect;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((m_suspect == null) ? 0 : m_suspect.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CluedoCharacter other = (CluedoCharacter) obj;
		if (m_suspect != other.m_suspect)
			return false;
		return true;
	}



	@Override
	public String getName() {
		switch (m_suspect) {
		case COLONEL_MUSTARD:
			return "Colonel Mustard";
		case MISS_SCARLET:
			return "Miss Scarlet";
		case MRS_PEACOCK:
			return "Mrs. Peacock";
		case MRS_WHITE:
			return "Mrs. White";
		case PROFESSOR_PLUM:
			return "Professor Plum";
		case THE_REVEREND_GREEN:
			return "The Reverend Green";
		default:
			return null;
		}
	}

	public Suspect getSuspect() {
		return m_suspect;
	}
}
