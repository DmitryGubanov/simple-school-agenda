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
				courses.add(new Course(courseData[0], courseData[1], Double
						.parseDouble(courseData[2])));
			}

			for (Course course : courses) {
				try {
					dataFromFile = fileManager.readFromFile(course.getCode()
							+ ".txt");
				} catch (FileNotFoundException e) {
					dataFromFile = null;
				}

				if (dataFromFile != null) {
					for (String assessmentString : dataFromFile) {
						String[] assessmentData = assessmentString.split(";");
						course.addAssessment(new Assessment(assessmentData[0],
								Double.parseDouble(assessmentData[1]), Double
										.parseDouble(assessmentData[2])));
					}
				}
			}
		}

		return courses;
	}

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

}
