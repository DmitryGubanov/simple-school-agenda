package so.dmitry.studentorganizer;

import java.util.List;

import so.data.Course;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

/** An activity which displays all the user's grades. */
public class UserGradesDisplay extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_grades_display);
		
		// TODO: this is really messy, so clean it up when you're less lazy

		TextView vCGPA = (TextView) findViewById(R.id.usergradesdisplay_cgpa);
		TextView vNoMarks = (TextView) findViewById(R.id.usergradesdisplay_nomarks);
		LinearLayout CGPALayout = (LinearLayout) findViewById(R.id.usergradedisplay_cgpalayout);
		LinearLayout courseMarksTitle = (LinearLayout) findViewById(R.id.usergradesdisplay_coursemarkstitle);
		LinearLayout courseMarks = (LinearLayout) findViewById(R.id.usergradesdisplay_coursemarks);
		double CGPA = MainCoursesDisplay.user.getCGPA();
		
		
		if (MainCoursesDisplay.user.getCourses().size() == 0) {
			courseMarksTitle.setVisibility(View.GONE);
			courseMarks.setVisibility(View.GONE);
			CGPALayout.setVisibility(View.GONE);
		} else {
			if (CGPA >= 0) {
				vNoMarks.setVisibility(View.GONE);
				vCGPA.setText(String.valueOf(CGPA));
			} else {			
				CGPALayout.setVisibility(View.GONE);
			}

			List<Course> courses = MainCoursesDisplay.user.getCourses();

			for (Course course : courses) {
				TextView courseTV = new TextView(this);
				courseTV.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				if (course.getMark() == 999) {
					courseTV.setText(course + ": no mark");
				} else {
					courseTV.setText(course + ": " + course.getMark());
				}
				
				courseMarks.addView(courseTV);
			}
		}
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_grades_display, menu);
		return true;
	}

}
