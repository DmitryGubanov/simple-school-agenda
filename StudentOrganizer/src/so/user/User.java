package so.user;

import java.util.ArrayList;
import java.util.List;

import so.calculator.Calculator;
import so.data.Course;

/** A User of this application. */
public class User {

	/** This User's courses. */
	private List<Course> courses;

	private Calculator calculator;

	/**
	 * Constructs a user with an empty list of courses.
	 */
	public User() {
		this.courses = new ArrayList<Course>();
		this.calculator = new Calculator();
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
		return calculator.calculateCGPA(this.courses);
	}

}
