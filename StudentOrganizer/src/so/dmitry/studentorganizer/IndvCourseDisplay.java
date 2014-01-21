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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CheckBox;

/** An activity which displays all the information for an individual course. */
public class IndvCourseDisplay extends Activity {

	/** This IndvCourseDisplay activity's course. */
	private Course course;

	/** This IndvCourseDisplay activity's database manager. */
	private DatabaseManager<Recordable> manager;

	/** This IndvCourseDisplay's current action represented as a string. */
	private String currentAction;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indv_course_display);

		course = MainCoursesDisplay.user.getCourse(getIntent().getIntExtra("courseid", 0));
		manager = (DatabaseManager<Recordable>) getIntent()
				.getSerializableExtra("manager");

		fillFields();
		loadAssessments(course.getAssessments());

		if (savedInstanceState != null) {
			restoreViews(savedInstanceState.getString("currentAction"));
		} else {
			setVisibilities(View.GONE, View.GONE, View.VISIBLE, View.GONE,
					View.GONE, true, true, true);
		}

		setupWeightCheckbox((CheckBox) findViewById(R.id.indcoursedisplay_editcourseinfolayout_halfyearbox));
		setupWeightCheckbox((CheckBox) findViewById(R.id.indcoursedisplay_editcourseinfolayout_fullyearbox));

		setupSaveEditedCourseButton((TextView) findViewById(R.id.indcoursedisplay_editcourseinfolayout_save));

		if (course.getMark() == 999) {
			setupSetMarkButton((TextView) findViewById(R.id.indcoursedisplay_setmark));
			setupSetCalculateMarkButton((TextView) findViewById(R.id.indcoursedisplay_calculatemark));
		}
		setupSaveMarkButton((TextView) findViewById(R.id.indcoursedisplay_setmarklayout_savemark));
		setupCalculateMarkButton((TextView) findViewById(R.id.indcoursedisplay_setmarklayout_calculatemark));

		setupAddAssessmentButton((TextView) findViewById(R.id.indcoursedisplay_addassessment));
		setupSaveAssessmentButton((TextView) findViewById(R.id.indcoursedisplay_addassessment_save));
		setupCancelAddAssessmentButton((TextView) findViewById(R.id.indcoursedisplay_addassessment_cancel));

		setupEditButtons();
		setupCancelButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.indv_course_display, menu);
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("currentAction", currentAction);
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
		// TODO: possibly make a list adapter that does this.
		System.out.println(assessments.size());
		// reference the assessment list
		LinearLayout assessmentList = (LinearLayout) findViewById(R.id.indcoursedisplay_assessmentlist);
		// remove all the views so there are no duplicates when it is
		// repopulated
		assessmentList.removeAllViewsInLayout();

		// if there are assessments, populate list
		if (assessments.size() > 0) {
			Integer i = 0;
			for (Assessment assessment : assessments) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater
						.inflate(R.layout.listitem_assessment, null);

				// reference all the views in each item
				TextView assessmentName = (TextView) view
						.findViewById(R.id.listitem_assessment_name);
				TextView assessmentWeight = (TextView) view
						.findViewById(R.id.listitem_assessment_weight);
				TextView assessmentMark = (TextView) view
						.findViewById(R.id.listitem_assessment_mark);
				LinearLayout editAssessmentLayout = (LinearLayout) view
						.findViewById(R.id.listitem_assessment_editassessmentlayout);
				TextView editView = (TextView) view
						.findViewById(R.id.listitem_assessment_editassessment);
				TextView deleteView = (TextView) view
						.findViewById(R.id.listitem_assessment_deleteassessment);
				TextView saveView = (TextView) view
						.findViewById(R.id.listitem_assessment_saveassessment);
				TextView cancelEditView = (TextView) view
						.findViewById(R.id.listitem_assessment_canceleditassessment);

				saveView.setTag(i);
				// set name, weight, and mark
				assessmentName.setText(assessment.getName());
				assessmentWeight
						.setText(String.valueOf(assessment.getWeight()));
				assessmentMark.setText(String.valueOf(assessment.getMark()));
				// set visibility of edit button, edit layout, and delete button
				// to gone
				editView.setVisibility(View.GONE);
				editAssessmentLayout.setVisibility(View.GONE);
				deleteView.setVisibility(View.GONE);
				deleteView.setTag(assessment.getID());

				setupEditIndvAssessmentButton(editView);
				setupDeleteIndvAssessmentButton(deleteView);
				setupSaveEditedAssessmentButton(saveView);
				setupCancelEditIndvAssessmentButton(cancelEditView);

				assessmentList.addView(view);
				i++;
			}
		} else {
			View view = new View(this);
			view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			assessmentList.addView(view);

		}
	}

	// TODO: finish this.
	private void restoreViews(String currentAction) {

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
	private void setVisibilities(int vEditCourseInfoLayout, int vSetMarkLayout,
			int vAddAssessment, int vAddAssessmentLayout, int vFillFields,
			Boolean editCourseIsVisible, Boolean editMarkIsVisible,
			Boolean editAssessmentsIsVisible) {
		// TODO: this is sort of ridiculous, but it works, so decided whether or
		// not you want to make it better/neater

		if (vEditCourseInfoLayout == View.VISIBLE) {
			((LinearLayout) findViewById(R.id.indcoursedisplay_courseinfolayout))
					.setVisibility(View.GONE);
		} else {
			((LinearLayout) findViewById(R.id.indcoursedisplay_courseinfolayout))
					.setVisibility(View.VISIBLE);
		}
		((LinearLayout) findViewById(R.id.indcoursedisplay_editcourseinfolayout))
				.setVisibility(vEditCourseInfoLayout);
		((LinearLayout) findViewById(R.id.indcoursedisplay_setmarklayout))
				.setVisibility(vSetMarkLayout);
		// if there's no course mark and the set mark layout isn't visible, show
		// SET MARK and CALCULATE MARK
		if (course.getMark() == 999 && (vSetMarkLayout == View.GONE)) {
			((LinearLayout) findViewById(R.id.indcoursedisplay_gradinginfo))
					.setVisibility(View.GONE);
			((LinearLayout) findViewById(R.id.indcoursedisplay_setandcalcmarklayout))
					.setVisibility(View.VISIBLE);
			if (course.getAssessments().size() == 0) {
				((TextView) findViewById(R.id.indcoursedisplay_calculatemark))
						.setVisibility(View.GONE);
				((TextView) findViewById(R.id.indcoursedisplay_setmarklayout_calculatemark))
						.setVisibility(View.GONE);
				((TextView) findViewById(R.id.indcoursedisplay_setmark))
						.setGravity(Gravity.LEFT);
			} else {
				((TextView) findViewById(R.id.indcoursedisplay_calculatemark))
						.setVisibility(View.VISIBLE);
				((TextView) findViewById(R.id.indcoursedisplay_setmarklayout_calculatemark))
						.setVisibility(View.GONE);
				((TextView) findViewById(R.id.indcoursedisplay_setmark))
						.setGravity(Gravity.CENTER);
			}
			editMarkIsVisible = null;
			// if there is a course mark and the set mark layout is gone, show
			// grading info
		} else if (vSetMarkLayout == View.GONE) {
			((LinearLayout) findViewById(R.id.indcoursedisplay_gradinginfo))
					.setVisibility(View.VISIBLE);
			((LinearLayout) findViewById(R.id.indcoursedisplay_setandcalcmarklayout))
					.setVisibility(View.GONE);
		} else {
			((LinearLayout) findViewById(R.id.indcoursedisplay_gradinginfo))
					.setVisibility(View.GONE);
			((LinearLayout) findViewById(R.id.indcoursedisplay_setandcalcmarklayout))
					.setVisibility(View.GONE);
		}
		// if add assessment layout is gone, hide keyboard
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
		// visibilities of "+ add assessment", add assessment layout, and fill
		// fields error
		((TextView) findViewById(R.id.indcoursedisplay_addassessment))
				.setVisibility(vAddAssessment);
		((LinearLayout) findViewById(R.id.indcoursedisplay_addassessmentlayout))
				.setVisibility(vAddAssessmentLayout);
		((TextView) findViewById(R.id.indcoursedisplay_addassessmentlayout_fillfields))
				.setVisibility(vFillFields);
		// set EDIT and CANCEL view visibilities for the course information,
		// grades, and assessments layouts
		setEditAndCancelVisibilities(
				(TextView) findViewById(R.id.indcoursedisplay_canceleditcourse),
				(TextView) findViewById(R.id.indcoursedisplay_editcourse),
				editCourseIsVisible);
		setEditAndCancelVisibilities(
				(TextView) findViewById(R.id.indcoursedisplay_canceleditmark),
				(TextView) findViewById(R.id.indcoursedisplay_editmark),
				editMarkIsVisible);
		if (course.getAssessments().size() == 0) {
			setEditAndCancelVisibilities(
					(TextView) findViewById(R.id.indcoursedisplay_canceleditassessments),
					(TextView) findViewById(R.id.indcoursedisplay_editassessments),
					null);
		} else {
			setEditAndCancelVisibilities(
					(TextView) findViewById(R.id.indcoursedisplay_canceleditassessments),
					(TextView) findViewById(R.id.indcoursedisplay_editassessments),
					editAssessmentsIsVisible);
		}
	}

	/**
	 * Sets the visibilities of provided EDIT and CANCEL views according to the
	 * value of the provided Boolean.
	 * 
	 * @param cancelView
	 *            The CANCEL view.
	 * @param editView
	 *            The EDIT view.
	 * @param isVisible
	 *            null if neither EDIT nor CANCEL should be visible, true if
	 *            only EDIT should be visible, and false if only CANCEL should
	 *            be visible.
	 */
	private void setEditAndCancelVisibilities(View cancelView, View editView,
			Boolean isVisible) {
		if (isVisible == null) {
			cancelView.setVisibility(View.GONE);
			editView.setVisibility(View.GONE);
		} else if (isVisible) {
			cancelView.setVisibility(View.GONE);
			editView.setVisibility(View.VISIBLE);
		} else {
			cancelView.setVisibility(View.VISIBLE);
			editView.setVisibility(View.GONE);
		}
	}

	/**
	 * Sets up a weight checkbox to deselect all other checkboxes in the layout
	 * when clicked.
	 * 
	 * @param weightBox
	 *            The weight checkbox to which is functionality should apply.
	 */
	private void setupWeightCheckbox(CheckBox weightBox) {
		weightBox.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View weightBox) {
				ViewGroup checkboxLayout = (ViewGroup) weightBox.getParent();
				for (int i = 0; i < 2; i++) {
					CheckBox checkbox = (CheckBox) checkboxLayout.getChildAt(i);
					if (checkbox != weightBox) {
						checkbox.setChecked(false);
					}
				}
			}
		});
	}

	/**
	 * Sets up the SAVE view in the edit course layout to act as a button which
	 * saves the changes when clicked.
	 * 
	 * @param saveCourseView
	 *            The SAVE view.
	 */
	private void setupSaveEditedCourseButton(View saveCourseView) {
		saveCourseView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View saveCourseView) {
				// TODO: reset errors when clicking cancel.
				EditText nameField = (EditText) findViewById(R.id.indcoursedisplay_editcourseinfolayout_name);
				EditText codeField = (EditText) findViewById(R.id.indcoursedisplay_editcourseinfolayout_code);
				CheckBox halfyearBox = (CheckBox) findViewById(R.id.indcoursedisplay_editcourseinfolayout_halfyearbox);
				CheckBox fullyearBox = (CheckBox) findViewById(R.id.indcoursedisplay_editcourseinfolayout_fullyearbox);

				TextView fieldError = (TextView) findViewById(R.id.indcoursedisplay_editcourseinfolayout_errorempty);
				TextView pickOneError = (TextView) findViewById(R.id.indcoursedisplay_editcourseinfolayout_errorpickone);

				String name = nameField.getText().toString();
				String code = codeField.getText().toString();
				boolean halfyear = halfyearBox.isChecked();
				boolean fullyear = fullyearBox.isChecked();

				if (TextUtils.isEmpty(name) || TextUtils.isEmpty(code)) {
					fieldError.setVisibility(View.VISIBLE);
				} else if (!(halfyear || fullyear)) {
					fieldError.setVisibility(View.GONE);
					pickOneError.setVisibility(View.VISIBLE);
				} else {
					double weight = 0.0;
					if (halfyear) weight = 0.5;
					else if (fullyear) weight = 1.0;
					manager.editCourseInfo(course, name, code, weight);
					fillFields();
					fieldError.setVisibility(View.GONE);
					pickOneError.setVisibility(View.GONE);
					setVisibilities(View.GONE, View.GONE, View.VISIBLE,
							View.GONE, View.GONE, true, true, true);
				}
			}
		});
	}

	/**
	 * Sets up the SET MARK view in the grading info layout to act as a button
	 * that hides the mark layout and shows the set mark layout on click.
	 * 
	 * @param setMarkView
	 *            The SET MARK view in the grading info layout.
	 */
	private void setupSetMarkButton(View setMarkView) {
		setMarkView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View saveView) {
				setVisibilities(View.GONE, View.VISIBLE, View.GONE, View.GONE,
						View.GONE, null, false, null);
			}
		});
	}

	/**
	 * Sets up the CALCULATE view in the set and calculate layout to act as a
	 * button which sets this IndvCourseDisplay activity's course mark to the
	 * calculated mark.
	 * 
	 * @param calculateView
	 *            The CALCULATE view.
	 */
	private void setupSetCalculateMarkButton(TextView calculateView) {
		calculateView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View calculateView) {
				manager.editCourseMark(course,
						String.valueOf(course.calculateMark()));
				fillFields();
				setVisibilities(View.GONE, View.GONE, View.VISIBLE, View.GONE,
						View.GONE, true, true, true);
			}
		});
	}

	/**
	 * Sets up the SAVE view in the set mark layout to act as a button that
	 * hides the set mark layout and reveals the mark layout on click.
	 * 
	 * @param saveView
	 *            The SAVE view in the set mark layout.
	 */
	private void setupSaveMarkButton(View saveView) {
		saveView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View saveView) {
				manager.editCourseMark(
						course,
						((EditText) findViewById(R.id.indcoursedisplay_setmarklayout_markfield))
								.getText().toString());
				fillFields();
				setVisibilities(View.GONE, View.GONE, View.VISIBLE, View.GONE,
						View.GONE, true, true, true);
			}
		});
	}

	private void setupCalculateMarkButton(View calculateView) {
		calculateView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View calculateView) {
				((EditText) findViewById(R.id.indcoursedisplay_setmarklayout_markfield))
						.setText(String.valueOf(course.calculateMark()));
			}
		});
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
				setVisibilities(View.GONE, View.GONE, View.GONE, View.VISIBLE,
						View.GONE, null, null, null);
				((LinearLayout) findViewById(R.id.indcoursedisplay_setandcalcmarklayout))
						.setVisibility(View.GONE);
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
				setVisibilities(View.GONE, View.GONE, View.VISIBLE, View.GONE,
						View.GONE, true, true, true);
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
	 * Sets up the EDIT views for course info, grading info, and assessment
	 * layouts.
	 */
	private void setupEditButtons() {
		TextView editCourseInfo = (TextView) findViewById(R.id.indcoursedisplay_editcourse);
		TextView editGrades = (TextView) findViewById(R.id.indcoursedisplay_editmark);
		TextView editAssessments = (TextView) findViewById(R.id.indcoursedisplay_editassessments);

		editCourseInfo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View editCourseInfo) {
				setVisibilities(View.VISIBLE, View.GONE, View.GONE, View.GONE,
						View.GONE, false, null, null);
				((LinearLayout) findViewById(R.id.indcoursedisplay_setandcalcmarklayout))
						.setVisibility(View.GONE);
				((EditText) findViewById(R.id.indcoursedisplay_editcourseinfolayout_name))
						.setText(course.getName());
				((EditText) findViewById(R.id.indcoursedisplay_editcourseinfolayout_code))
						.setText(course.getCode());
				if (course.getWeight() == 0.5) {
					((CheckBox) findViewById(R.id.indcoursedisplay_editcourseinfolayout_halfyearbox))
							.setChecked(true);
					((CheckBox) findViewById(R.id.indcoursedisplay_editcourseinfolayout_fullyearbox))
							.setChecked(false);
				} else if (course.getWeight() == 1.0) {
					((CheckBox) findViewById(R.id.indcoursedisplay_editcourseinfolayout_fullyearbox))
							.setChecked(true);
					((CheckBox) findViewById(R.id.indcoursedisplay_editcourseinfolayout_halfyearbox))
							.setChecked(false);
				}
			}
		});

		editGrades.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View editGrades) {
				setVisibilities(View.GONE, View.VISIBLE, View.GONE, View.GONE,
						View.GONE, null, false, null);
				((EditText) findViewById(R.id.indcoursedisplay_setmarklayout_markfield))
						.setText(String.valueOf(course.getMark()));
			}
		});

		editAssessments.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View editAssessments) {
				setVisibilities(View.GONE, View.GONE, View.GONE, View.GONE,
						View.GONE, null, null, false);
				((LinearLayout) findViewById(R.id.indcoursedisplay_setandcalcmarklayout))
						.setVisibility(View.GONE);
				setEditAssessmentVisibilities(View.VISIBLE);
				setDeleteAssessmentVisibilities(View.VISIBLE);
			}
		});
	}

	/**
	 * Sets the visibility of the EDIT views beside each assessment.
	 * 
	 * @param vEditViews
	 *            The desired visibility of every EDIT view.
	 */
	private void setEditAssessmentVisibilities(int vEditViews) {
		LinearLayout assessmentList = (LinearLayout) findViewById(R.id.indcoursedisplay_assessmentlist);

		for (int i = 0; i < assessmentList.getChildCount(); i++) {
			assessmentList.getChildAt(i)
					.findViewById(R.id.listitem_assessment_editassessment)
					.setVisibility(vEditViews);
		}
	}

	/**
	 * Sets the visibility of the DELETE views beside each assessment.
	 * 
	 * @param vDeleteViews
	 *            The desired visibility of every DELETE view.
	 */
	private void setDeleteAssessmentVisibilities(int vDeleteViews) {
		LinearLayout assessmentList = (LinearLayout) findViewById(R.id.indcoursedisplay_assessmentlist);

		for (int i = 0; i < assessmentList.getChildCount(); i++) {
			assessmentList.getChildAt(i)
					.findViewById(R.id.listitem_assessment_deleteassessment)
					.setVisibility(vDeleteViews);
		}
	}

	/**
	 * Sets up the EDIT views for course info, grading info, and assessment
	 * layouts.
	 */
	private void setupCancelButtons() {
		TextView cancelEditCourseInfo = (TextView) findViewById(R.id.indcoursedisplay_canceleditcourse);
		TextView cancelEditGrades = (TextView) findViewById(R.id.indcoursedisplay_canceleditmark);
		TextView cancelEditAssessments = (TextView) findViewById(R.id.indcoursedisplay_canceleditassessments);

		cancelEditCourseInfo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View cancelEditCourseInfo) {
				((EditText) findViewById(R.id.indcoursedisplay_editcourseinfolayout_name))
						.setError(null);
				((EditText) findViewById(R.id.indcoursedisplay_editcourseinfolayout_code))
						.setError(null);

				setVisibilities(View.GONE, View.GONE, View.VISIBLE, View.GONE,
						View.GONE, true, true, true);
			}
		});

		cancelEditGrades.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View cancelEditGrades) {
				setVisibilities(View.GONE, View.GONE, View.VISIBLE, View.GONE,
						View.GONE, true, true, true);
			}
		});

		cancelEditAssessments.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View cancelEditAssessments) {
				setVisibilities(View.GONE, View.GONE, View.VISIBLE, View.GONE,
						View.GONE, true, true, true);
				if (course.getAssessments().size() > 0) {
					hideAllEditAssessmentLayouts();
					setEditAssessmentVisibilities(View.GONE);
					setDeleteAssessmentVisibilities(View.GONE);
				}
			}
		});
	}

	/**
	 * Sets up the individual EDIT views beside each assessment to open the
	 * respective edit assessment layout on click.
	 * 
	 * @param editView
	 *            The individual EDIT view.
	 */
	private void setupEditIndvAssessmentButton(View editView) {
		editView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View editView) {
				showEditAssessmentLayout((ViewGroup) editView.getParent()
						.getParent());
				setEditAssessmentVisibilities(View.GONE);
				setDeleteAssessmentVisibilities(View.GONE);
			}
		});
	}

	private void setupDeleteIndvAssessmentButton(TextView deleteView) {
		deleteView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View deleteView) {
				Assessment assessment = course.getAssessment((Integer) deleteView.getTag());
				course.getAssessments().remove(assessment);
				loadAssessments(course.getAssessments());
				manager.deleteItem(assessment);
				if (course.getAssessments().size() > 0) {
					setEditAssessmentVisibilities(View.VISIBLE);
					setDeleteAssessmentVisibilities(View.VISIBLE);
				}
			}
		});
	}

	/**
	 * Sets up the SAVE view in the individual edit assessment layout to act as
	 * a button that saves the changes made, hides the edit assessment layout,
	 * and shows the assessment layout.
	 * 
	 * @param saveView
	 *            The SAVE view in the individual edit assessment layout.
	 */
	private void setupSaveEditedAssessmentButton(View saveView) {
		saveView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View saveView) {
				ViewGroup listItem = (ViewGroup) saveView.getParent()
						.getParent();

				hideEditAssessmentLayout(listItem);

				EditText editNameView = (EditText) listItem
						.findViewById(R.id.listitem_assessment_editname);
				EditText editWeightView = (EditText) listItem
						.findViewById(R.id.listitem_assessment_editweight);
				EditText editMarkView = (EditText) listItem
						.findViewById(R.id.listitem_assessment_editmark);

				String name = editNameView.getText().toString();
				String weight = editWeightView.getText().toString();
				String mark = editMarkView.getText().toString();

				manager.editAssessment(
						course.getAssessments()
								.get((Integer) saveView.getTag()), name,
						weight, mark);

				InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editNameView.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(editWeightView.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(editMarkView.getWindowToken(), 0);

				fillFields();
				loadAssessments(course.getAssessments());
				setEditAssessmentVisibilities(View.VISIBLE);
				setDeleteAssessmentVisibilities(View.VISIBLE);
			}
		});
	}

	/**
	 * Sets up the individual CANCEL view inside each edit assessment layout to
	 * hide the edit assessment layout on click
	 * 
	 * @param cancelEditView
	 *            The individual CANCEL view inside an edit assessment layout
	 */
	private void setupCancelEditIndvAssessmentButton(TextView cancelEditView) {
		cancelEditView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View cancelEditView) {
				hideEditAssessmentLayout((ViewGroup) cancelEditView.getParent()
						.getParent());
				setEditAssessmentVisibilities(View.VISIBLE);
				setDeleteAssessmentVisibilities(View.VISIBLE);
			}
		});
	}

	/**
	 * Replaces an individual assessment layout with the edit assessment layout
	 * and fills the EditText views with the information of the respective
	 * assessment.
	 * 
	 * @param AssessmentListItem
	 *            The entire assessment list item, i.e. the parent of the
	 *            assessment layout and the edit assessment layout.
	 */
	private void showEditAssessmentLayout(ViewGroup AssessmentListItem) {
		AssessmentListItem.findViewById(
				R.id.listitem_assessment_assessmentlayout).setVisibility(
				View.GONE);
		AssessmentListItem.findViewById(
				R.id.listitem_assessment_editassessmentlayout).setVisibility(
				View.VISIBLE);

		String name = ((TextView) AssessmentListItem
				.findViewById(R.id.listitem_assessment_name)).getText()
				.toString();
		String weight = ((TextView) AssessmentListItem
				.findViewById(R.id.listitem_assessment_weight)).getText()
				.toString();
		String mark = ((TextView) AssessmentListItem
				.findViewById(R.id.listitem_assessment_mark)).getText()
				.toString();

		((EditText) AssessmentListItem
				.findViewById(R.id.listitem_assessment_editname)).setText(name);
		((EditText) AssessmentListItem
				.findViewById(R.id.listitem_assessment_editweight))
				.setText(weight);
		((EditText) AssessmentListItem
				.findViewById(R.id.listitem_assessment_editmark)).setText(mark);
	}

	/**
	 * Hides a specific edit assessment layout, given the list item, and
	 * replaces it with the respective assessment layout.
	 * 
	 * @param AssessmentListItem
	 *            The given list item, in which exists the edit assessment
	 *            layout that will be hidden.
	 */
	private void hideEditAssessmentLayout(ViewGroup AssessmentListItem) {
		AssessmentListItem.findViewById(
				R.id.listitem_assessment_editassessmentlayout).setVisibility(
				View.GONE);
		AssessmentListItem.findViewById(
				R.id.listitem_assessment_assessmentlayout).setVisibility(
				View.VISIBLE);
	}

	/**
	 * Hides all the edit assessment layouts and replaces them with their
	 * respective assessment layouts.
	 */
	private void hideAllEditAssessmentLayouts() {
		LinearLayout assessmentList = (LinearLayout) findViewById(R.id.indcoursedisplay_assessmentlist);

		for (int i = 0; i < assessmentList.getChildCount(); i++) {
			LinearLayout listItem = (LinearLayout) assessmentList.getChildAt(i);

			listItem.findViewById(R.id.listitem_assessment_editassessmentlayout)
					.setVisibility(View.GONE);
			listItem.findViewById(R.id.listitem_assessment_assessmentlayout)
					.setVisibility(View.VISIBLE);
		}
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
			setVisibilities(View.GONE, View.GONE, View.GONE, View.VISIBLE,
					View.VISIBLE, null, null, null);
		} else {
			clearNewAssessmentFields();
			Assessment assessment = new Assessment(name,
					Double.parseDouble(weight), Double.parseDouble(mark));
			course.addAssessment(assessment);
			manager.addItem(assessment);
			setVisibilities(View.GONE, View.GONE, View.VISIBLE, View.GONE,
					View.GONE, true, true, true);
			fillFields();
			loadAssessments(course.getAssessments());
		}
	}

}
