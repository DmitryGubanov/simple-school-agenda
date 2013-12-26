package so.data;

/** An Assessment for a course. */
public class Assessment {

	/** This Assessment's name. */
	private String name;

	/** This Assessment's weight. */
	private double weight;

	private double grade;

	/**
	 * Constructs an assessment with a name and weight.
	 * 
	 * @param name
	 *            This Assessment's name.
	 * @param weight
	 *            This Assessment's weight.
	 */
	public Assessment(String name, double weight, double grade) {
		this.name = name;
		this.weight = weight;
		this.grade = grade;
	}

	/**
	 * Returns this Assessment's name.
	 * 
	 * @return This Assessment's name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns this Assessment's weight.
	 * 
	 * @return This Assessment's weight.
	 */
	public double getWeight() {
		return this.weight;
	}

	/**
	 * Sets this Assessment's grade to a given grade.
	 * 
	 * @param grade
	 *            The given grade.
	 */
	public void setGrade(double grade) {
		this.grade = grade;
	}
	
	/**
	 * Returns this Assessment's grade
	 * @return This Assessment's grade
	 */
	public double getGrade() {
		return this.grade;
	}

}
