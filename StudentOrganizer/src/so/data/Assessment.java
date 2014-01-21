package so.data;

import java.io.Serializable;

/** An Assessment for a course. */
public class Assessment implements Gradable, Recordable, Serializable {

	/** This Assessment's UID. */
	private static final long serialVersionUID = -148079633200902587L;

	/** This Assessment's ID. */
	private int id;

	/** This Assessment's name. */
	private String name;

	/** This Assessment's weight. */
	private double weight;

	/** This Assessment's mark. */
	private double mark;

	/** This Assessment's file name. */
	private String fileName;

	/** This Assessment's course. */
	private Course course;

	/**
	 * Constructs an assessment with a name and weight.
	 * 
	 * @param name
	 *            This Assessment's name.
	 * @param weight
	 *            This Assessment's weight.
	 * @param mark
	 *            This Assessment's mark.
	 */
	public Assessment(String name, double weight, double mark) {
		this.name = name;
		this.weight = weight;
		this.mark = mark;
	}

	/**
	 * Sets this Assessment's ID to the given ID.
	 * 
	 * @param id
	 *            The given ID.
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Returns this Assessment's ID.
	 * @return This Assessment's ID.
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Sets this Assessment's name to a given new name.
	 * 
	 * @param newName
	 *            This Assessment's desired new name.
	 */
	public void setName(String newName) {
		this.name = newName;
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
	 * Sets this Assessment's mark to a given mark.
	 * 
	 * @param mark
	 *            The given mark.
	 */
	public void setMark(double mark) {
		this.mark = mark;
	}

	/**
	 * Returns this Assessment's mark.
	 * 
	 * @return This Assessment's mark.
	 */
	public double getMark() {
		return this.mark;
	}

	/**
	 * Sets this Assessment's weight to the given new weight.
	 * 
	 * @param newWeight
	 *            This Assessment's desired new weight.
	 */
	public void setWeight(double newWeight) {
		this.weight = newWeight;
	}

	/**
	 * Sets this Assessment's course, i.e the Course to which this assessment
	 * belongs.
	 * 
	 * @param code
	 *            The Course to which this Assessment belongs.
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	public Course belongsTo() {
		return this.course;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public double getGrade() {
		return this.mark;
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	/**
	 * Sets this Assessment's file name to the given file name.
	 * 
	 * @param fileName
	 *            The given file name.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public String getText() {
		return this.name + ";" + this.weight + ";" + this.mark + "\n";
	}

}
