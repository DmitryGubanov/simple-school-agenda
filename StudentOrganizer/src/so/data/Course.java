package so.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import so.calculator.Calculator;

/** A Course. */
public class Course implements Gradable, Recordable, Serializable {

	/** This Course's UID. */
	private static final long serialVersionUID = 6528187084972101144L;

	/** This Course's name */
	private String name;

	/** This Course's code. */
	private String code;

	/** This Course's mark. */
	private double mark;

	/** This Course's weight. */
	private double weight;

	/** This Course's calculator. */
	private Calculator<Assessment> calculator;

	/** This Course's assessments. */
	private List<Assessment> assessments;

	/**
	 * Constructs a new Course with a name and code.
	 * 
	 * @param name
	 *            This Course's name.
	 * @param code
	 *            This Course's code.
	 */
	public Course(String name, String code, double weight) {
		this.name = name;
		this.code = code;
		this.weight = weight;
		this.mark = 999;
		assessments = new ArrayList<Assessment>();
		calculator = new Calculator<Assessment>();
	}

	/**
	 * Returns this Course's name.
	 * 
	 * @return This Course's name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns this Course's code.
	 * 
	 * @return This Course's code.
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Sets this User's mark to the given mark.
	 * 
	 * @param mark
	 *            The given mark.
	 */
	public void setMark(double mark) {
		this.mark = mark;
	}

	/**
	 * Returns this Course's mark.
	 * 
	 * @return This Course's mark.
	 */
	public int getMark() {
		return (int) Math.ceil(this.mark);
	}

	/**
	 * Returns this Course's grade point value.
	 * 
	 * @return This Course's grade point value.
	 */
	public double getGPV() {
		return calculator.markToGPV(this.mark);
	}

	/**
	 * Adds an assessment to this Course's assessments.
	 * 
	 * @param assessment
	 *            The assessment to be added.
	 */
	public void addAssessment(Assessment assessment) {
		assessment.setFileName(this.code + ".txt");
		assessments.add(assessment);
		setMark(calculator.calculateAvgGrade(assessments));
	}

	/**
	 * Returns this Course's assessments.
	 * 
	 * @return This Course's assessments.
	 */
	public List<Assessment> getAssessments() {
		return this.assessments;
	}

	/**
	 * Returns an Assessment with a given name.
	 * 
	 * @param name
	 *            The name.
	 * @return The Assessment with the name.
	 */
	public Assessment getAssessment(String name) {
		for (Assessment assessment : assessments) {
			if (assessment.getName().equals(name)) {
				return assessment;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.code + " - " + this.name;
	}

	@Override
	public double getGrade() {
		return getGPV();
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	@Override
	public String getFileName() {
		return "courses.txt";
	}

	@Override
	public String getText() {
		return this.name + ";" + this.code + ";" + this.weight + "\n";
	}

}
