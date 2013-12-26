package so.calculator;

import java.util.List;

import so.data.Course;

/** A Calculator used for calculating GPV, GPA, averages, etc. */
public class Calculator {

	/**
	 * Returns the GPV associated with the given mark
	 * 
	 * @param mark
	 *            The given mark
	 * @return The GPV
	 */
	public double markToGPV(double mark) {
		if (mark >= 85) {
			return 4.0;
		} else if (mark >= 80) {
			return 3.7;
		} else if (mark >= 77) {
			return 3.3;
		} else if (mark >= 73) {
			return 3.0;
		} else if (mark >= 70) {
			return 2.7;
		} else if (mark >= 67) {
			return 2.3;
		} else if (mark >= 63) {
			return 2.0;
		} else if (mark >= 60) {
			return 1.7;
		} else if (mark >= 57) {
			return 1.3;
		} else if (mark >= 53) {
			return 1.0;
		} else if (mark >= 50) {
			return 0.7;
		} else {
			return 0.0;
		}
	}

	/**
	 * Calculates a CGPA given a list of Courses.
	 * 
	 * @param courses
	 *            A list of Courses.
	 * @return a CGPA.
	 */
	public double calculateCGPA(List<Course> courses) {
		double weightedTotalGPV = 0;
		double sumOfWeights = 0;
		
		for (Course course : courses) {
			weightedTotalGPV = weightedTotalGPV + course.getGPV()*course.getWeight();
			sumOfWeights = sumOfWeights + course.getWeight();
		}
		
		return weightedTotalGPV/sumOfWeights;
	}

}
