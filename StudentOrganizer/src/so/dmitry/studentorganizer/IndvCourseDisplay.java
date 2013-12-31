package so.dmitry.studentorganizer;

import java.util.List;

import so.data.Assessment;
import so.data.Course;
import so.data.Recordable;
import so.manager.DatabaseManager;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/** An activity which displays all the information for an individual course. */
public class IndvCourseDisplay extends Activity {

	/** This IndvCourseDisplay activity's course. */
	private Course course;

	/** This IndvCourseDisplay activity's database manager. */
	private DatabaseManager<Recordable> manager;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indv_course_display);

		course = MainCoursesDisplay.user.getCourse((String) getIntent()
				.getSerializableExtra("course"));
		manager = (DatabaseManager<Recordable>) getIntent()
				.getSerializableExtra("manager");

		setVisibilities(View.VISIBLE, View.GONE, View.GONE);
		fillFields();
		loadAssessments(course.getAssessments());

		setupAddAssessmentButton((TextView) findViewById(R.id.indcoursedisplay_addassessment));
		setupSaveAssessmentButton((TextView) findViewById(R.id.indcoursedisplay_addassessment_save));
		setupCancelAddAssessmentButton((TextView) findViewById(R.id.indcoursedisplay_addassessment_cancel));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.indv_course_display, menu);
		return true;
	}

	/**
	 * Fills in this IndvCourseDisplay activity's fields.
	 */
	private void fillFields() {
		((TextView) findViewById(R.id.indcoursedisplay_name)).setText(course
				.getName());
		((TextView) findViewById(R.id.indcoursedisplay_code)).setText(course
				.getCode());
		if (course.getWeight() == 0.5) {
			((TextView) findViewById(R.id.indcoursedisplay_weight))
					.setText(R.string.half_year);
		} else if (course.getWeight() == 1.0) {
			((TextView) findViewById(R.id.indcoursedisplay_weight))
					.setText(R.string.full_year);
		}
		((TextView) findViewById(R.id.indcoursedisplay_mark)).setText(String
				.valueOf(course.getMark()));
		((TextView) findViewById(R.id.indcoursedisplay_gpv)).setText(String
				.valueOf(course.getGPV()));

	}

	/**
	 * Loads this IndvCourseDisplay activity's list of assessments.
	 * 
	 * @param assessments
	 *            The given list of assessments to load.
	 */
	private void loadAssessments(List<Assessment> assessments) {
		LinearLayout assessmentList = (LinearLayout) findViewById(R.id.indcoursedisplay_assessmentlist);
		assessmentList.removeAllViewsInLayout();

		if (assessments.size() > 0) {
			for (Assessment assessment : assessments) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater
						.inflate(R.layout.listitem_assessment, null);

				TextView assessmentName = (TextView) view
						.findViewById(R.id.listitem_assessment_name);
				TextView assessmentWeightAndGrade = (TextView) view
						.findViewById(R.id.listitem_assessment_weightandgrade);

				assessmentName.setText(assessment.getName());
				assessmentWeightAndGrade.setText("worth "
						+ assessment.getWeight() + "%, got "
						+ assessment.getMark() + "%.");

				assessmentList.addView(view);
			}
		}

	}

	/**
	 * Sets the visibilities of the views in this IndvCourseDisplay activity.
	 * 
	 * @param vAddAssessment
	 *            The visibility of the + Add New Assessment view
	 * @param vAddAssessmentLayout
	 *            The visibility of the add new assessment layout.
	 * @param vFillFields
	 *            The visibility of the * Fill in all the fields error.
	 */
	private void setVisibilities(int vAddAssessment, int vAddAssessmentLayout,
			int vFillFields) {
		if (course.getMark() == 999) {
			((LinearLayout) findViewById(R.id.indcoursedisplay_gradinginfolayout))
					.setVisibility(View.GONE);
		} else {
			((LinearLayout) findViewById(R.id.indcoursedisplay_gradinginfolayout))
					.setVisibility(View.VISIBLE);
		}
		((TextView) findViewById(R.id.indcoursedisplay_addassessment))
				.setVisibility(vAddAssessment);
		if (vAddAssessmentLayout == View.GONE) {
			EditText nameField = (EditText) findViewById(R.id.indcoursedisplay_addassessmentlayout_name);
			EditText weightField = (EditText) findViewById(R.id.indcoursedisplay_addassessmentlayout_weight);
			EditText markField = (EditText) findViewById(R.id.indcoursedisplay_addassessmentlayout_mark);

			InputMethodManager imm = (InputMethodManager) this
					.getSystemService(Service.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(nameField.getWindowToken(), 0);
			imm.hideSoftInputFromWindow(weightField.getWindowToken(), 0);
			imm.hideSoftInputFromWindow(markField.getWindowToken(), 0);
		}
		((LinearLayout) findViewById(R.id.indcoursedisplay_addassessmentlayout))
				.setVisibility(vAddAssessmentLayout);
		((TextView) findViewById(R.id.indcoursedisplay_addassessmentlayout_fillfields))
				.setVisibility(vFillFields);

	}

	/**
	 * Sets up the + Add New Assessment view to work as a button which displays
	 * the add new assessment layout on click.
	 * 
	 * @param addAssessmentView
	 *            The + Add New Assessment view.
	 */
	private void setupAddAssessmentButton(TextView addAssessmentView) {
		addAssessmentView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View addAssessmentView) {
				setVisibilities(View.GONE, View.VISIBLE, View.GONE);

			}
		});

	}

	/**
	 * Sets up the Cancel view to work as a button which clears the new
	 * assessment fields and hides the add assessment layout on click.
	 * 
	 * @param cancelView
	 *            The Cancel view.
	 */
	private void setupCancelAddAssessmentButton(TextView cancelView) {
		cancelView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View cancelView) {
				setVisibilities(View.VISIBLE, View.GONE, View.GONE);
				clearNewAssessmentFields();
			}
		});

	}

	/**
	 * Sets up the Save view to work as a button which creates a new assessment
	 * with the given information, if it's there, and reloads the assessment
	 * list.
	 * 
	 * @param saveView
	 *            The Save view.
	 */
	private void setupSaveAssessmentButton(TextView saveView) {
		saveView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View saveView) {
				createNewAssessment();

			}

		});

	}

	/**
	 * Clears all the fields in the add new assessment layout.
	 */
	private void clearNewAssessmentFields() {
		((EditText) findViewById(R.id.indcoursedisplay_addassessmentlayout_name))
				.setText("");
		((EditText) findViewById(R.id.indcoursedisplay_addassessmentlayout_weight))
				.setText("");
		((EditText) findViewById(R.id.indcoursedisplay_addassessmentlayout_mark))
				.setText("");
	}

	/**
	 * Creates a new assessment with the information in the new assessment
	 * layout.
	 */
	private void createNewAssessment() {
		String name = ((EditText) findViewById(R.id.indcoursedisplay_addassessmentlayout_name))
				.getText().toString();
		String weight = ((EditText) findViewById(R.id.indcoursedisplay_addassessmentlayout_weight))
				.getText().toString();
		String mark = ((EditText) findViewById(R.id.indcoursedisplay_addassessmentlayout_mark))
				.getText().toString();

		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(weight)
				|| TextUtils.isEmpty(mark)) {
			setVisibilities(View.GONE, View.VISIBLE, View.VISIBLE);
		} else {
			clearNewAssessmentFields();
			Assessment assessment = new Assessment(name,
					Double.parseDouble(weight), Double.parseDouble(mark));
			course.addAssessment(assessment);
			manager.addItem(assessment);
			setVisibilities(View.VISIBLE, View.GONE, View.GONE);
			fillFields();
			loadAssessments(course.getAssessments());

		}

	}

}
