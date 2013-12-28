package so.dmitry.studentorganizer;

import so.data.Recordable;
import so.manager.DatabaseManager;
import so.user.User;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/** An activity which displays all the courses in a list. */
public class MainCoursesDisplay<E extends Recordable> extends Activity {

	/** True iff this MainCourseDisplay activity is already running. */
	private boolean isAppRunning;

	/** This MainCourseDisplay activity's user. */
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_courses_display);

		setVisibilities(View.VISIBLE, View.GONE);

		setupAddCourseButton((TextView) findViewById(R.id.maindisplay_addcourse));

		isAppRunning = getIntent().getBooleanExtra("launch", false);
		if (!isAppRunning) {
			DatabaseManager<E> databaseManager = new DatabaseManager<E>(
					getApplicationContext().getFilesDir());
			user = new User(databaseManager.createDatabase());
			isAppRunning = true;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_courses_display, menu);
		return true;
	}

	/**
	 * Sets the visibilities of views in this MainCourseDisplay activity.
	 * 
	 * @param vAddCourse
	 *            The desired visibility of the add course view.
	 * @param vAddCourseLayout
	 *            The desired visibility of the add course layout.
	 */
	private void setVisibilities(int vAddCourse, int vAddCourseLayout) {
		((TextView) findViewById(R.id.maindisplay_addcourse))
				.setVisibility(vAddCourse);
		((LinearLayout) findViewById(R.id.maindisplay_addcourselayout))
				.setVisibility(vAddCourseLayout);
	}

	/**
	 * Sets up the + Add Course view to act as a button in this
	 * MainCourseDisplay activity.
	 * 
	 * @param addCourseView
	 *            The + Add Course view
	 */
	private void setupAddCourseButton(TextView addCourseView) {
		addCourseView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View addCourseView) {
				setVisibilities(View.GONE, View.VISIBLE);
			}
		});
	}

}
