package so.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import so.calculator.Calculator;
import so.data.Course;

/** A User of this application. */
public class User implements Serializable {

	/** This User's UID. */
	private static final long serialVersionUID = -2767600027205482106L;

	/** This User's courses. */
	private List<Course> courses;

	/** This User's calculator. */
	private Calculator<Course> calculator;

	/**
	 * Constructs a user with a list of courses.
	 * 
	 * @param courses
	 *            This User's list of courses.
	 */
	public User(List<Course> courses) {
		this.courses = courses;
		this.calculator = new Calculator<Course>();
	}

	/**
	 * Adds a course to this User's list of courses
	 * 
	 * @param course
	 *            The course to be added
	 */
	public void addCourse(Course course) {
		this.courses.add(course);
	}

	/**
	 * Returns this User's list of courses.
	 * 
	 * @return This User's list of courses.
	 */
	public List<Course> getCourses() {
		return this.courses;
	}

	/**
	 * Get a course from this User's courses with the given code.
	 * 
	 * @param code
	 *            The given code.
	 * @return A Course
	 */
	public Course getCourse(String code) {
		for (Course course : this.courses) {
			if (course.getCode().equals(code))
				return course;
		}

		return null;
	}

	/**
	 * Returns this User's CGPA
	 * 
	 * @return This User's CGPA
	 */
	public double getCGPA() {
		List<Course> gradableCourses = new ArrayList<Course>();
		
		for (Course course : courses) {
			if (course.getMark() != 999) {
				gradableCourses.add(course);
			}
		}
		
		if (gradableCourses.size() > 0) {
			return calculator.calculateAvgGrade(gradableCourses);
		} else {
			return -1;
		}
	}

}
