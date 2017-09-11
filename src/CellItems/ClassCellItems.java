package CellItems;

public class ClassCellItems {
	private String className;
	private String professorName;
	private String day;
	
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
	

}
