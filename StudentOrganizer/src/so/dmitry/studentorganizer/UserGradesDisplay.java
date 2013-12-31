package so.dmitry.studentorganizer;

import java.util.List;

import so.data.Course;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/** An activity which displays all the user's grades. */
public class UserGradesDisplay extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_grades_display);

		if (MainCoursesDisplay.user.getCourses().size() == 0) {
			setVisibilities(View.GONE, View.VISIBLE, View.GONE);
		} else {
			if (MainCoursesDisplay.user.getCGPA() < 0) {
				setVisibilities(View.GONE, View.VISIBLE, View.VISIBLE);
			} else {
				setVisibilities(View.VISIBLE, View.GONE, View.VISIBLE);
				((TextView) findViewById(R.id.usergradesdisplay_cgpa))
						.setText(String.valueOf(MainCoursesDisplay.user
								.getCGPA()));
			}

			loadUserGrades(MainCoursesDisplay.user.getCourses());
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_grades_display, menu);
		return true;
	}

	/**
	 * Sets the visibilities of the views in this UserGradesDisplay activity.
	 * 
	 * @param vCGPA
	 *            The visibility of the view that displays the CGPA
	 * @param vNoMarks
	 *            The visibility of the view that says there are no marks
	 * @param vCourseMarksLayout
	 *            The visibility of the course marks list layout
	 */
	private void setVisibilities(int vCGPA, int vNoMarks, int vCourseMarksLayout) {
		((LinearLayout) findViewById(R.id.usergradedisplay_cgpalayout))
				.setVisibility(vCGPA);
		((TextView) findViewById(R.id.usergradesdisplay_nomarks))
				.setVisibility(vNoMarks);
		((LinearLayout) findViewById(R.id.usergradesdisplay_coursemarkslayout))
				.setVisibility(vCourseMarksLayout);

	}

	/**
	 * Loads the user's course grades, given a list of courses.
	 * 
	 * @param courses
	 *            The given list of courses.
	 */
	private void loadUserGrades(List<Course> courses) {
		LinearLayout courseList = (LinearLayout) findViewById(R.id.usergradesdisplay_coursemarks);

		for (Course course : courses) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater
					.inflate(R.layout.listitem_courseandgrade, null);

			((TextView) view.findViewById(R.id.listitemcourseandgrade_course))
					.setText(course.toString());
			((TextView) view.findViewById(R.id.listitemcourseandgrade_weight))
					.setText(String.valueOf(course.getWeight()));
			if (course.getMark() == 999) {
				((TextView) view.findViewById(R.id.listitemcourseandgrade_mark))
						.setText(getString(R.string.none));
				((TextView) view.findViewById(R.id.listitemcourseandgrade_gpv))
						.setText(getString(R.string.none));
			} else {
				((TextView) view.findViewById(R.id.listitemcourseandgrade_mark))
						.setText(String.valueOf(course.getMark()));
				((TextView) view.findViewById(R.id.listitemcourseandgrade_gpv))
						.setText(String.valueOf(course.getGPV()));
			}

			courseList.addView(view);
		}

	}

}
