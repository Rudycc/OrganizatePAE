package Cells;

import CellItems.ClassCellItems;
import javafx.scene.control.ListCell;

public class ClassListViewCell extends ListCell<ClassCellItems>{
	@Override
	public void updateItem(ClassCellItems cell, boolean empty){
		super.updateItem(cell, empty);
		if(cell != null){
			ClassCellData classCellData = new ClassCellData();
			classCellData.setInfo(cell);
			setGraphic(classCellData.getGridPane());
			
		}
	}
}
