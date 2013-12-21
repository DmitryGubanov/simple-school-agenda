package so.user;

/** A User of this application. */
public class User {

	/** This User's name. */
	private String name;

	/**
	 * Constructs a user with a name
	 * 
	 * @param name
	 *            This User's name
	 */
	public User(String name) {
		this.name = name;
	}

	/**
	 * Returns this User's name
	 * 
	 * @return This User's name
	 */
	public String getName() {
		return this.name;
	}

}
