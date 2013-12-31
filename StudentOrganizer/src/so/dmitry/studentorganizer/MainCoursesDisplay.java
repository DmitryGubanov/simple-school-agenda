package so.dmitry.studentorganizer;

import java.util.List;

import so.data.Course;
import so.data.Recordable;
import so.listadapter.MainCourseAdapter;
import so.manager.DatabaseManager;
import so.user.User;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/** An activity which displays all the courses in a list. */
public class MainCoursesDisplay extends Activity implements OnItemClickListener {

	/** This MainCourseDisplay activity's user. */
	public static User user;

	/** This MainCourseDisplay activity's database manager. */
	private DatabaseManager<Recordable> manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_courses_display);
		setTitle(R.string.maincoursedisplay_title);

		setVisibilities(View.VISIBLE, View.GONE, View.GONE, View.GONE);

		setupAddCourseButton((TextView) findViewById(R.id.maindisplay_addcourse));
		setupCancelButton((TextView) findViewById(R.id.maindisplay_canceladdcourse));
		setupSaveButton((TextView) findViewById(R.id.maindisplay_savecourse));

		manager = new DatabaseManager<Recordable>(getApplicationContext()
				.getFilesDir());
		user = new User(manager.createDatabase());

		loadCourseList(user.getCourses());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_courses_display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_viewmarks:
			startActivity(new Intent(this, UserGradesDisplay.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Sets the visibilities of views in this MainCourseDisplay activity.
	 * 
	 * @param vAddCourse
	 *            The desired visibility of the add course view.
	 * @param vAddCourseLayout
	 *            The desired visibility of the add course layout.
	 * @param vEmptyField
	 *            The desired visibility of the empty field warning.
	 */
	private void setVisibilities(int vAddCourse, int vAddCourseLayout,
			int vEmptyField, int vCheckboxError) {
		((TextView) findViewById(R.id.maindisplay_addcourse))
				.setVisibility(vAddCourse);
		if (vAddCourseLayout == View.GONE) {
			EditText nameField = (EditText) findViewById(R.id.maindisplay_addcourselayout_namefield);
			EditText codeField = (EditText) findViewById(R.id.maindisplay_addcourselayout_codefield);

			InputMethodManager imm = (InputMethodManager) this
					.getSystemService(Service.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(nameField.getWindowToken(), 0);
			imm.hideSoftInputFromWindow(codeField.getWindowToken(), 0);
		}
		((LinearLayout) findViewById(R.id.maindisplay_addcourselayout))
				.setVisibility(vAddCourseLayout);
		((TextView) findViewById(R.id.maindisplay_addcourselayout_emptyfield))
				.setVisibility(vEmptyField);
		((TextView) findViewById(R.id.maindisplay_addcourselayout_checkboxerror))
				.setVisibility(vCheckboxError);
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
				setVisibilities(View.GONE, View.VISIBLE, View.GONE, View.GONE);
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
				setVisibilities(View.VISIBLE, View.GONE, View.GONE, View.GONE);
				clearNewCourseFields();
			}
		});

	}

	/**
	 * Sets up the save button which attempts to add a new course with the given
	 * data.
	 * 
	 * @param saveView
	 *            The Save text view.
	 */
	private void setupSaveButton(TextView saveView) {
		saveView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View saveView) {
				createNewCourse();
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
		((CheckBox) findViewById(R.id.maindisplay_addcourselayout_halfyearbox))
				.setChecked(false);
		((CheckBox) findViewById(R.id.maindisplay_addcourselayout_fullyearbox))
				.setChecked(false);
	}

	/**
	 * Creates a new course with the information given in the fields.
	 */
	private void createNewCourse() {
		String name = ((EditText) findViewById(R.id.maindisplay_addcourselayout_namefield))
				.getText().toString();
		String code = ((EditText) findViewById(R.id.maindisplay_addcourselayout_codefield))
				.getText().toString();
		boolean halfyear = ((CheckBox) findViewById(R.id.maindisplay_addcourselayout_halfyearbox))
				.isChecked();
		boolean fullyear = ((CheckBox) findViewById(R.id.maindisplay_addcourselayout_fullyearbox))
				.isChecked();

		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(code)) {
			setVisibilities(View.GONE, View.VISIBLE, View.VISIBLE, View.GONE);
		} else if ((halfyear && fullyear) || !(halfyear || fullyear)) {
			setVisibilities(View.GONE, View.VISIBLE, View.GONE, View.VISIBLE);
		} else {
			setVisibilities(View.VISIBLE, View.GONE, View.GONE, View.GONE);
			clearNewCourseFields();
			double weight;
			if (halfyear) {
				weight = 0.5;
			} else {
				weight = 1.0;
			}
			Course course = new Course(name, code, weight);
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
		Intent intent = new Intent(this, IndvCourseDisplay.class);
		intent.putExtra("course", user.getCourses().get(position).getCode());
		intent.putExtra("manager", manager);
		startActivity(intent);
	}

}
