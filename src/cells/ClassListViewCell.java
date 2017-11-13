package cells;

import cellItems.ClassCellItems;
import javafx.scene.control.ListCell;

public class ClassListViewCell extends ListCell<ClassCellItems>{
	@Override
	public void updateItem(ClassCellItems cell, boolean empty){
		super.updateItem(cell, empty);
		if(cell != null && !empty){
			ClassCellData classCellData = new ClassCellData();
			classCellData.setInfo(cell);
			setGraphic(classCellData.getGridPane());
		}else {
			setGraphic(null);
		}
	}
}
