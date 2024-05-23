package controller;

import model.TableModel;
import view.GUIView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TableController {
    private GUIView view;

    public TableController(GUIView view) {
        this.view = view;
        this.view.getAddButton().addActionListener(new AddButtonListener());
        this.view.getRemoveButton().addActionListener(new RemoveButtonListener());
        this.view.getbyweight().addActionListener(new ByWeightListener());
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addRow();
        }
    }

    private class RemoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            removeRow();
        }
    }

    private class ByWeightListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayBarangNamesByWeight();
        }
    }

    private void addRow() {
        TableModel tableModel = (TableModel) view.getTable().getModel();
        String barang = getNextBarang(tableModel.getRowCount());
        tableModel.addRow(new Object[]{barang, "", ""});
    }

    private void removeRow() {
        TableModel tableModel = (TableModel) view.getTable().getModel();
        int rowCount = tableModel.getRowCount();
        if (rowCount > 0) {
            tableModel.removeRow(rowCount - 1);
        }
    }

    private String getNextBarang(int rowCount) {
        return String.valueOf((char) ('A' + rowCount));
    }

    private void displayBarangNamesByWeight() {
        TableModel tableModel = (TableModel) view.getTable().getModel();
        List<String> barangNames = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object barang = tableModel.getValueAt(i, 0);
            if (barang != null) {
                barangNames.add(barang.toString());
            }
        }
        System.out.print(barangNames);
        String hasilBarang = String.join(", ", barangNames);
        view.getHasilBarangField().setText(hasilBarang);
    }
}
