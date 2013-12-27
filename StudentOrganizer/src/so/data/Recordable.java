package so.data;

/**
 * A Recordable object will be written to a file by being able to return the
 * file to which it will be written and the text that will be written to the
 * file.
 */
public interface Recordable {

	/**
	 * Returns this Recordable's file name to which text will be written.
	 * 
	 * @return This Recordable's file name.
	 */
	public String getFileName();

	/**
	 * Returns this Recordable's text which will be written to file.
	 * 
	 * @return This Recordable's text which will be written to file.
	 */
	public String getText();

}
