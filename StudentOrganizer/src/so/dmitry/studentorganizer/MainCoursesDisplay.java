package so.dmitry.studentorganizer;

import java.util.List;

import so.data.Course;
import so.data.Recordable;
import so.listadapter.MainCourseAdapter;
import so.manager.DatabaseManager;
import so.user.User;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/** An activity which displays all the courses in a list. */
public class MainCoursesDisplay extends Activity
		implements OnItemClickListener {

	/** This MainCourseDisplay activity's user. */
	private User user;
	
	/** This MainCourseDisplay activity's database manager. */
	private DatabaseManager<Recordable> manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_courses_display);
		setTitle(R.string.maincoursedisplay_title);

		setVisibilities(View.VISIBLE, View.GONE, View.GONE);

		setupAddCourseButton((TextView) findViewById(R.id.maindisplay_addcourse));
		setupCancelButton((TextView) findViewById(R.id.maindisplay_canceladdcourse));
		setupSaveButton((TextView) findViewById(R.id.maindisplay_savecourse));

		manager = new DatabaseManager<Recordable>(
				getApplicationContext().getFilesDir());
		user = new User(manager.createDatabase());

		loadCourseList(user.getCourses());
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
	private void setVisibilities(int vAddCourse, int vAddCourseLayout,
			int vEmptyField) {
		((TextView) findViewById(R.id.maindisplay_addcourse))
				.setVisibility(vAddCourse);
		((LinearLayout) findViewById(R.id.maindisplay_addcourselayout))
				.setVisibility(vAddCourseLayout);
		((TextView) findViewById(R.id.maindisplay_addcourselayout_emptyfield))
				.setVisibility(vEmptyField);
	}

	/**
	 * Sets up the + Add Course view to act as a button in this
	 * MainCourseDisplay activity.
	 * 
	 * @param addCourseView
	 *            The + Add Course text view.
	 */
	private void setupAddCourseButton(TextView addCourseView) {
		addCourseView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View addCourseView) {
				setVisibilities(View.GONE, View.VISIBLE, View.GONE);
			}
		});
	}

	/**
	 * Sets up the cancel button which closes the add new course layout.
	 * 
	 * @param cancelView
	 *            The Cancel text view.
	 */
	private void setupCancelButton(TextView cancelView) {
		cancelView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View cancelView) {
				setVisibilities(View.VISIBLE, View.GONE, View.GONE);
				clearNewCourseFields();
			}
		});

	}

	/**
	 * Sets up the save button which attemps to add a new course with the given
	 * data.
	 * 
	 * @param saveView
	 *            The Save text view.
	 */
	private void setupSaveButton(TextView saveView) {
		saveView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View saveView) {
				setVisibilities(View.VISIBLE, View.GONE, View.GONE);
				createNewCourse();
				clearNewCourseFields();
			}
		});
	}

	/**
	 * Clears the fields in the add new course layout
	 */
	private void clearNewCourseFields() {
		((EditText) findViewById(R.id.maindisplay_addcourselayout_namefield))
				.setText("");
		((EditText) findViewById(R.id.maindisplay_addcourselayout_codefield))
				.setText("");
		((EditText) findViewById(R.id.maindisplay_addcourselayout_weightfield))
				.setText("");
	}

	/**
	 * Creates a new course with the information given in the fields.
	 */
	private void createNewCourse() {
		String name = ((EditText) findViewById(R.id.maindisplay_addcourselayout_namefield))
				.getText().toString();
		String code = ((EditText) findViewById(R.id.maindisplay_addcourselayout_codefield))
				.getText().toString();
		String weight = ((EditText) findViewById(R.id.maindisplay_addcourselayout_weightfield))
				.getText().toString();

		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(code)
				|| TextUtils.isEmpty(weight)) {
			setVisibilities(View.GONE, View.VISIBLE, View.VISIBLE);
		} else {
			Course course = new Course(name, code, Double.parseDouble(weight));
			user.addCourse(course);
			manager.addItem(course);
		}

	}

	/**
	 * Loads the given course list onto this MainCourseDisplay activity's
	 * ListView
	 * 
	 * @param courses
	 *            The given course list
	 */
	private void loadCourseList(List<Course> courses) {
		ListView courseListView = (ListView) findViewById(R.id.maindisplay_courselist);
		courseListView.setAdapter(new MainCourseAdapter(this, courses));
		courseListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO: load IndvCourseDisplay with information from the selected
		// course
	}

}
