package controller;

import model.TableModel;
import view.GUIView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TableController {
    private final GUIView view;

    public TableController(GUIView view) {
        this.view = view;
        this.view.getAddButton().addActionListener(new AddButtonListener());
        this.view.getRemoveButton().addActionListener(new RemoveButtonListener());
        this.view.getBruteForceButton().addActionListener(new ByBruteForceListener());
        this.view.getDynamicProgrammingButton().addActionListener(new ByDynamicProgramming());
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

    private class ByBruteForceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayBarangByBruteForce();
        }
    }

    private class ByDynamicProgramming implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { displayBarangByDynamicProgramming(); }
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

    private int getConstraintValue() {
        int constraint = 0;
        try {
            constraint = Integer.parseInt(view.getConstraintField().getText());
        } catch (NumberFormatException e) {
            System.err.println("Invalid constraint: " + view.getConstraintField().getText());
        }

        return constraint;
    }

    private List<int[]> getDataintolist() {
        List<Object[]> inputData = getInputDataFromTable();
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

    private void displayBarangByBruteForce() {
        long StartTime = System.currentTimeMillis();
        List<int[]> details = getDataintolist();
        int n = details.size();
        int k = getConstraintValue();
        int maxProfit = 0;
        String selectedItems = "";

        for (int i = 0; i < (1 << n); i++) {
            int totalWeight = 0;
            int totalProfit = 0;
            StringBuilder currentItem = new StringBuilder();

            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    totalWeight += details.get(j)[1];
                    totalProfit += details.get(j)[2];
                    if (currentItem.length() != 0) {
                        currentItem.append(", ");
                    }
                    currentItem.append(details.get(j)[0]);
                }
            }
            if (totalWeight <= k && totalProfit > maxProfit) {
                maxProfit = totalProfit;
                selectedItems = currentItem.toString();
            }
        }
        long endTime = System.currentTimeMillis();
        double duration = endTime - StartTime;
        String durationString = String.format("%.10f", (double)duration/1024.0);
        System.out.println(durationString);
        view.getHasilBarangField().setText(selectedItems);
        JOptionPane.showMessageDialog(view, "Execution time: " + durationString + " second ");
    }

    private void displayBarangByDynamicProgramming() {
        long StartTime = System.currentTimeMillis();
        List<int[]> details = getDataintolist();
        int k = getConstraintValue();
        int n = details.size();
        int [][] dp = new int[n+1][k+1];

        for (int i = 1; i <= n; i++){
            for (int w = 1; w <= k; w++) {
                if (details.get(i - 1)[1] <= w) {
                    dp[i][w] = Math.max(details.get(i - 1)[2]+ dp[i - 1][w - details.get(i - 1)[1]], dp[i-1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        StringBuilder selectedItems = new StringBuilder();
        int i = n, w = k;
        while (i > 0 && w > 0) {
            if (dp[i][w] != dp[i - 1][w]) {
                if (selectedItems.length() != 0) {
                    selectedItems.insert(0, ", ");
                }
                selectedItems.insert(0, details.get(i - 1)[0]);
                w -= details.get(i - 1)[1];
            }
            i--;
        }
        long endTime = System.currentTimeMillis();
        double duration = endTime - StartTime;
        String durationString = String.format("%.10f", (double)duration/1024.0);
        System.out.println(durationString);
        view.getHasilBarangField().setText(selectedItems.toString());
        JOptionPane.showMessageDialog(view, "Execution time: " + durationString + " second ");
    }

}

