package controller;

import model.TableModel;
import view.GUIView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TableController {
    private final GUIView view;

    public TableController(GUIView view) {
        this.view = view;
        this.view.getAddButton().addActionListener(new AddButtonListener());
        this.view.getRemoveButton().addActionListener(new RemoveButtonListener());
        this.view.getByWeightButton().addActionListener(new ByWeightListener());
        this.view.getByProfitButton().addActionListener(new ByProfitListener());
        this.view.getByDensityButton().addActionListener(new ByDensitytListener());
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

    private class ByProfitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayBarangNamesByProfit();
        }
    }

    private class ByDensitytListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayBarangNamesByDensity();
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

    private List<float[]> getDataintolist() {
        List<Object[]> inputData = getInputDataFromTable();
        List<float[]> details = new ArrayList<>();
        for (int i = 0; i < inputData.size(); i++) {
            Object[] row = inputData.get(i);
            if (row[1] != null && row[2] != null) {
                try {
                    float no = i + 1;
                    float weight = Float.parseFloat(row[1].toString());
                    float profit = Float.parseFloat(row[2].toString());
                    float density = profit / weight;
                    details.add(new float[]{no, weight, profit, density});
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number in row: " + row[0]);
                }
            }
        }
        return details;
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

    private int getConstraintValue() {
        int constraint = 0;
        try {
            constraint = Integer.parseInt(view.getConstraintField().getText());
        } catch (NumberFormatException e) {
            System.err.println("Invalid constraint: " + view.getConstraintField().getText());
        }

        return constraint;
    }

    private void displayBarangNamesByWeight() {
        List<float[]> details = getDataintolist();
        details.sort(Comparator.comparingDouble(a -> a[1]));
        List<String> namabarang = new ArrayList<>();
        float constraint = getConstraintValue();

        for (float[] detail : details) {
            if (constraint >= detail[1]){
                DecimalFormat df = new DecimalFormat("#");
                namabarang.add(df.format(detail[0]));
                constraint -= detail[1];
            }
        }
        String hasilBarang = String.join(", ", namabarang);
        view.getHasilBarangField().setText(hasilBarang);
    }

    private void displayBarangNamesByProfit() {
        List<float[]> details = getDataintolist();
        details.sort(Comparator.comparingDouble(a -> ((float[])a)[2]).reversed());
        List<String> namabarang = new ArrayList<>();
        float constraint = getConstraintValue();

        for (float[] detail : details) {
            if (constraint >= detail[1]) {
                DecimalFormat df = new DecimalFormat("#");
                namabarang.add(df.format(detail[0]));
                constraint -= detail[1];
            }
        }
        String hasilBarang = String.join(", ", namabarang);
        view.getHasilBarangField().setText(hasilBarang);
    }

    private void displayBarangNamesByDensity() {
        List<float[]> details = getDataintolist();
        details.sort(Comparator.comparingDouble(a -> ((float[])a)[3]).reversed());
        List<String> namabarang = new ArrayList<>();
        float constraint = getConstraintValue();

        for (float[] detail : details) {
            if (constraint >= detail[1]) {
                DecimalFormat df = new DecimalFormat("#");
                namabarang.add(df.format(detail[0]));
                constraint -= detail[1];
            }
        }
        String hasilBarang = String.join(", ", namabarang);
        view.getHasilBarangField().setText(hasilBarang);
    }
}
