package Cells;

import CellItems.ClassCellItems;
import javafx.scene.control.ListCell;

public class ListViewCell extends ListCell<ClassCellItems>{
	@Override
	public void updateItem(ClassCellItems cell, boolean empty){
		super.updateItem(cell, empty);
		if(cell != null){
			CellData cellData = new CellData();
			cellData.setInfo(cell);
			setGraphic(cellData.getGridPane());
			
		}
	}
}
