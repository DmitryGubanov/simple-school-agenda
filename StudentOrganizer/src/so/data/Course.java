package so.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import so.calculator.Calculator;

/** A Course. */
public class Course implements Gradable, Recordable, Serializable {

	/** This Course's UID. */
	private static final long serialVersionUID = 6528187084972101144L;

	/** This Course's ID. */
	private int id;

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
	 * Sets this Course's ID to the given ID.
	 * 
	 * @param id
	 *            The given ID.
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Returns this Course's ID.
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Sets this Course's name to a given name.
	 * 
	 * @param newName
	 *            The given name.
	 */
	public void setName(String newName) {
		this.name = newName;
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
	 * Sets this Course's course code to a given code.
	 * 
	 * @param newCode
	 *            The given code.
	 */
	public void setCode(String newCode) {
		this.code = newCode;
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

	public double calculateMark() {
		return calculator.calculateAvgGrade(assessments);
	}

	/**
	 * Returns this Course's mark.
	 * 
	 * @return This Course's mark.
	 */
	public double getMark() {
		return this.mark;
	}

	/**
	 * Returns this Course's grade point value.
	 * 
	 * @return This Course's grade point value.
	 */
	public double getGPV() {
		return calculator.markToGPV(getMark());
	}

	/**
	 * Adds an assessment to this Course's assessments.
	 * 
	 * @param assessment
	 *            The assessment to be added.
	 */
	public void addAssessment(Assessment assessment) {
		assessment.setFileName(this.id + ".txt");
		assessment.setCourse(this);
		assessments.add(assessment);
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
	public Assessment getAssessment(int id) {
		for (Assessment assessment : assessments) {
			if (assessment.getID() == id) {
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

	/**
	 * Sets this Course's weight to the given weight.
	 * 
	 * @param newWeight
	 *            The given weight.
	 */
	public void setWeight(double newWeight) {
		this.weight = newWeight;
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
		return this.id + ";" + this.name + ";" + this.code + ";" + this.weight
				+ ";" + this.mark + "\n";
	}

}
