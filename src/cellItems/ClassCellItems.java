package cellItems;

public class ClassCellItems {
	private int subjectId;
	private String className;
	private String professorName;
	private String day;
	private String color;
	
	public ClassCellItems(){
		
	}
	
	public ClassCellItems(String className, String professorName, String day){
		this.className = className;
		this.professorName = professorName;
		this.day = day;
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
}
