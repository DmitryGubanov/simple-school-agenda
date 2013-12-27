package so.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import so.data.Recordable;

/** A file manager for the application. */
public class FileManager<R extends Recordable> {

	/** This FileManager's directory. */
	private File directory;

	/**
	 * Constructs a FileManager with a directory.
	 * 
	 * @param directory
	 *            This FileManager's directory.
	 */
	public FileManager(File directory) {
		this.directory = directory;
	}

	/**
	 * Writes a Recordable object to file.
	 * 
	 * @param recordable
	 *            The Recordable object
	 * @throws FileNotFoundException
	 */
	public void writeToFile(R recordable) throws FileNotFoundException {

		File file = new File(directory, recordable.getFileName());
		FileOutputStream outputStream = new FileOutputStream(file, true);

		try {
			outputStream.write(recordable.getText().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Reads the text from a file with a given file name.
	 * 
	 * @param fileName
	 *            The given file name.
	 * @return A list of strings, each of which is a line from the file.
	 * @throws FileNotFoundException
	 */
	public List<String> readFromFile(String fileName)
			throws FileNotFoundException {
		File file = new File(directory, fileName);
		Scanner scanner = new Scanner(new FileInputStream(file.getPath()));

		List<String> returnStrings = new ArrayList<String>();

		while (scanner.hasNext()) {
			returnStrings.add(scanner.nextLine());
		}

		return returnStrings;
	}

	/**
	 * Deletes a recordable object from its file.
	 * 
	 * @param recordable
	 *            The recordable object to be deleted.
	 */
	public void deleteFromFile(R recordable) {
		// TODO: complete this method.
	}

}