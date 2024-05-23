package model;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {
    public TableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }
}
