package cellItems;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import database.SettingsDatabaseController;

public class ClassCellItems {
	private int subjectId;
	private String className;
	private String professorName;
	private String day;
	private String color;
	private String semester;
	private List<ScheduleItem> times;
	private ResourceBundle rb;

	public ClassCellItems() {

	}

	public ClassCellItems(String className, String professorName, String day) {
		this.className = className;
		this.professorName = professorName;
		this.day = day;
		this.semester = "";
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getProfessorName() {
		return professorName;
	}

	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<ScheduleItem> getTimes() {
		return times;
	}

	public void setTimes(List<ScheduleItem> times) {
		this.times = times;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getSemester() {
		return this.semester;
	}

	public String toString() {
		this.rb = ResourceBundle.getBundle("resources.UIResources",
				new Locale(SettingsDatabaseController.getLanguage()));
		StringBuilder str = new StringBuilder();

		str.append(rb.getString("newSubjectName"));
		str.append(": ");
		str.append(getClassName());
		str.append("\n");
		str.append(rb.getString("newSubjectProfName"));
		str.append(": ");
		str.append(getProfessorName());
		str.append("\n");
		str.append(rb.getString("newSubjectSemester"));
		str.append(": ");
		str.append(getSemester());

		return str.toString();
	}
}
