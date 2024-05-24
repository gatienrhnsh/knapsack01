package controller;

import model.TableModel;
import view.GUIView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TableController {
    private final GUIView view;

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
        return String.valueOf(rowCount + 1);
    }

    private void displayBarangNamesByWeight() {
        List<Object[]> inputData = getInputDataFromTable();
        System.out.println("Input Data: " + inputData);

        List<int[]> details = new ArrayList<>();
        for (int i = 0; i < inputData.size(); i++) {
            Object[] row = inputData.get(i);
            if (row[1] != null && row[2] != null) {
                try {
                    int no = i + 1;
                    int weight = Integer.parseInt(row[1].toString());
                    int profit = Integer.parseInt(row[2].toString());
                    details.add(new int[]{no, weight, profit});
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in row: " + row[0]);
                }
            }
        }

        // Sort details based on weight in ascending order
        details.sort(Comparator.comparingInt(a -> a[1]));

        List<String> barangDetails = new ArrayList<>();
        for (int[] detail : details) {
            barangDetails.add("(" + detail[0] + ", " + detail[1] + ", " + detail[2] + ")");
        }
        String hasilBarang = String.join(", ", barangDetails);
        view.getHasilBarangField().setText(hasilBarang);

        System.out.println("Details: " + barangDetails);
    }


    private List<Object[]> getInputDataFromTable() {
        TableModel tableModel = (TableModel) view.getTable().getModel();
        List<Object[]> data = new ArrayList<>();
        int rowCount = tableModel.getRowCount();
        int colCount = tableModel.getColumnCount();

        for (int i = 0; i < rowCount; i++) {
            Object[] row = new Object[colCount];
            for (int j = 0; j < colCount; j++) {
                row[j] = tableModel.getValueAt(i, j);
            }
            data.add(row);
        }
        return data;
    }
}
