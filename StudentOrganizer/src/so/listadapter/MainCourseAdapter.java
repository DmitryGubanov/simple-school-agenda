package so.listadapter;

import java.util.List;

import so.data.Course;
import so.dmitry.studentorganizer.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/** A BaseAdapter which adapts a list of Courses to a ListView. */
public class MainCourseAdapter extends BaseAdapter {

	/** This MainCourseAdapter's context. */
	private Context context;

	/** This MainCourseAdapter's list of courses. */
	private List<Course> courses;

	/**
	 * Constructs a MainCourseAdapter with a context and a list of courses.
	 * 
	 * @param context
	 *            This MainCourseAdapter's context.
	 * @param courses
	 *            This MainCourseAdapter's list of courses.
	 */
	public MainCourseAdapter(Context context, List<Course> courses) {
		this.context = context;
		this.courses = courses;
	}

	@Override
	public int getCount() {
		return courses.size();
	}

	@Override
	public Object getItem(int location) {
		return courses.get(location);
	}

	@Override
	public long getItemId(int itemId) {
		return itemId;
	}

	@Override
	public View getView(int location, View view, ViewGroup viewGroup) {
		Course course = courses.get(location);
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.listviewitem_course, null);
		}
		
		((TextView) view.findViewById(R.id.courselistviewitem_name)).setText(course.getName());
		((TextView) view.findViewById(R.id.courselistviewitem_code)).setText(course.getCode());
		
		return view;
	}

}
