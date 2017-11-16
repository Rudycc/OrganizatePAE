package cells;

import cellItems.TaskCellItems;
import javafx.scene.control.ListCell;

public class TaskListViewCell extends ListCell<TaskCellItems> {
	@Override
	public void updateItem(TaskCellItems cell, boolean empty){
		super.updateItem(cell, empty);
		if(cell != null && !empty){
			TaskCellData classCellData = new TaskCellData();
			classCellData.setInfo(cell);
			setGraphic(classCellData.getGridPane());
		}else {
			setGraphic(null);
		}
	}

}
