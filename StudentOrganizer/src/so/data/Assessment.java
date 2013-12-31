package so.data;

import java.io.Serializable;

/** An Assessment for a course. */
public class Assessment implements Gradable, Recordable, Serializable {

	/** This Assessment's UID. */
	private static final long serialVersionUID = -148079633200902587L;

	/** This Assessment's name. */
	private String name;

	/** This Assessment's weight. */
	private double weight;

	/** This Assessment's mark. */
	private double mark;

	/** This Assessment's file name. */
	private String fileName;

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
