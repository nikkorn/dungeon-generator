package dungen;

/**
 * A dungeon cell.
 */
public class Cell {
	/**
	 * The cell type.
	 */
	private String type;
	/**
	 * The cell details.
	 */
	private String details;
	
	/**
	 * Creates a new instance of the Cell class.
	 * @param type The cell type.
	 * @param details The cell details.
	 */
	public Cell(String type, String details) {
		this.type    = type;
		this.details = details;
	}

	public String getType() {
		return type;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
