package so.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import so.data.Recordable;

/** A file manager for the application. */
public class FileManager<R extends Recordable> implements Serializable {

	/** This FileManager's UID. */
	private static final long serialVersionUID = 6811424858481685790L;

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
	 * Returns this FileManager's directory.
	 * 
	 * @return This FileManager's directory.
	 */
	public File getDir() {
		return this.directory;
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
	 * @throws FileNotFoundException
	 */
	public void deleteFromFile(R recordable) throws FileNotFoundException {
		File file = new File(directory, recordable.getFileName());
		Scanner scanner = new Scanner(new FileInputStream(file.getPath()));
		
		List<String> newData = new ArrayList<String>();
		while(scanner.hasNextLine()) {
			if (!(scanner.nextLine().equals(recordable.getText()))) {
				newData.add(scanner.nextLine());
			}
		}
		
		file.delete();
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileOutputStream outputStream = new FileOutputStream(file, true);
		
		for (String dataItem : newData) {
			try {
				outputStream.write(dataItem.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
