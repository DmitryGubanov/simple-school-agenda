package so.data;

/**
 * A Gradable object will be able to return a grade and weight which will be
 * used in calculations.
 */
public interface Gradable {

	/** Returns this Gradable's grade used for calculations. */
	public double getGrade();

	/** Returns this Gradable's weight used for calculations. */
	public double getWeight();

}
