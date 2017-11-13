package cellItems;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private int IDSubject_Time;
	private String day;
	private LocalTime time;
	private float duration;
	private LocalDate start;
	private LocalDate end;

	public ScheduleItem() {

	}

	public void setIDSubject_Time(int IDSubject_Time) {
		this.IDSubject_Time = IDSubject_Time;
	}

	public int getIDSubject_Time() {
		return this.IDSubject_Time;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}
}
