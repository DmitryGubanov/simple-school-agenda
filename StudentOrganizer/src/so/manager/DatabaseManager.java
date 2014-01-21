package so.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import so.data.Assessment;
import so.data.Course;
import so.data.Recordable;

/** A database manager for this application. */
public class DatabaseManager<R extends Recordable> implements Serializable {

	/** This DatabaseManager's UID. */
	private static final long serialVersionUID = -7449348152144212503L;

	/** This DatabaseManager's file manager. */
	private FileManager<R> fileManager;

	/** This DatabaseManager's list of courses. */
	private List<Course> courses;

	/**
	 * Constructs a DatabaseManager with a directory.
	 * 
	 * @param directory
	 *            This DatabaseManager's directory.
	 */
	public DatabaseManager(File directory) {
		fileManager = new FileManager<R>(directory);
		courses = new ArrayList<Course>();
	}

	/**
	 * Creates a list of courses with all of their assessments, if they exist on
	 * file.
	 * 
	 * @return The list of courses.
	 */
	public List<Course> createDatabase() {

		List<String> dataFromFile;

		try {
			dataFromFile = fileManager.readFromFile("courses.txt");
		} catch (FileNotFoundException e) {
			return courses;
		}

		if (dataFromFile.size() > 0) {
			for (String courseString : dataFromFile) {
				String[] courseData = courseString.split(";");
				Course course = new Course(courseData[1], courseData[2],
						Double.parseDouble(courseData[3]));
				course.setID(Integer.parseInt(courseData[0]));
				course.setMark(Double.parseDouble(courseData[4]));
				courses.add(course);
			}

			for (Course course : courses) {
				try {
					dataFromFile = fileManager.readFromFile(course.getID()
							+ ".txt");
				} catch (FileNotFoundException e) {
					dataFromFile = null;
				}

				if (dataFromFile != null) {
					for (String assessmentString : dataFromFile) {
						String[] assessmentData = assessmentString.split(";");
						Assessment assessment = new Assessment(
								assessmentData[1],
								Double.parseDouble(assessmentData[2]),
								Double.parseDouble(assessmentData[3]));
						assessment.setID(Integer.parseInt(assessmentData[0]));
						course.addAssessment(assessment);
					}
				}
			}
		}

		return courses;
	}

	/**
	 * Adds an item to the database, i.e. its text file.
	 * 
	 * @param recordable
	 *            The item to be added.
	 */
	public void addItem(R recordable) {
		try {
			fileManager.writeToFile(recordable);
		} catch (FileNotFoundException e) {
			File file = new File(fileManager.getDir(), recordable.getFileName());
			try {
				file.createNewFile();
				fileManager.writeToFile(recordable);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Deletes an item from the database, i.e. from its text file.
	 * 
	 * @param recordable
	 *            The item to be deleted.
	 */
	public void deleteItem(R recordable) {
		try {
			fileManager.deleteFromFile(recordable);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Edits a given assessment to have the given name, weight, and mark.
	 * 
	 * @param theAssessment
	 *            The Assessment.
	 * @param newName
	 *            The desired name.
	 * @param newWeight
	 *            The desired weight.
	 * @param newMark
	 *            The desired mark.
	 */
	@SuppressWarnings("unchecked")
	public void editAssessment(Assessment theAssessment, String newName,
			String newWeight, String newMark) {
		theAssessment.setName(newName);
		theAssessment.setWeight(Double.parseDouble(newWeight));
		theAssessment.setMark(Double.parseDouble(newMark));

		fileManager.deleteFileFor((R) theAssessment);

		for (Assessment assessment : theAssessment.belongsTo().getAssessments()) {
			addItem((R) assessment);
		}
	}

	@SuppressWarnings("unchecked")
	public void editCourseMark(Course theCourse, String newMark) {
		theCourse.setMark(Double.parseDouble(newMark));

		fileManager.deleteFileFor((R) theCourse);

		for (Course course : courses) {
			if (course.getCode().equals(theCourse.getCode())) {
				course.setMark(Double.parseDouble(newMark));
			}
			addItem((R) course);
		}
	}

	/**
	 * Edits the given course to have the given information.
	 * 
	 * @param theCourse
	 *            The given Course.
	 * @param newName
	 *            The desired name for the Course.
	 * @param newCode
	 *            The desired code for the Course.
	 * @param newWeight
	 *            The desired weight for the Course.
	 */
	@SuppressWarnings("unchecked")
	public void editCourseInfo(Course theCourse, String newName,
			String newCode, double newWeight) {
		theCourse.setName(newName);
		theCourse.setCode(newCode);
		theCourse.setWeight(newWeight);
		
		fileManager.deleteFileFor((R) theCourse);

		for (Course course : courses) {
			if (course.getID() == theCourse.getID()) addItem((R) theCourse);
			else addItem((R) course);
		}
		
	}

}
