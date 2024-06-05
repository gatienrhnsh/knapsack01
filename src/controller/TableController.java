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
//        this.view.getByProfitButton().addActionListener(new ByProfitListener());
//        this.view.getByDensityButton().addActionListener(new ByDensitytListener());
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

//    private class ByProfitListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            displayBarangNamesByProfit();
//        }
//    }

//    private class ByDensitytListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            displayBarangNamesByDensity();
//        }
//    }

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

//    private List<float[]> getDataintolist() {
//        List<Object[]> inputData = getInputDataFromTable();
//        List<float[]> details = new ArrayList<>();
//        for (int i = 0; i < inputData.size(); i++) {
//            Object[] row = inputData.get(i);
//            if (row[1] != null && row[2] != null) {
//                try {
//                    float no = i + 1;
//                    float weight = Float.parseFloat(row[1].toString());
//                    float profit = Float.parseFloat(row[2].toString());
//                    float density = profit / weight;
//                    details.add(new float[]{no, weight, profit, density});
//                } catch (NumberFormatException e) {
//                    System.err.println("Invalid number in row: " + row[0]);
//                }
//            }
//        }
//        return details;
//    }

    private List<int[]> getDataintolistDP() {
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

//    private String greedyknapsack(List<float[]> details){
//        List<String> namabarang = new ArrayList<>();
//        float constraint = getConstraintValue();
//
//        for (float[] detail : details) {
//            if (constraint >= detail[1]){
//                DecimalFormat df = new DecimalFormat("#");
//                namabarang.add(df.format(detail[0]));
//                constraint -= detail[1];
//            }
//        }
//        return String.join(", ", namabarang);
//    }

    private void displayBarangByBruteForce() {
        List<int[]> details = getDataintolistDP();
        int n = details.size();
        int k = getConstraintValue();
        int maxProfit = 0;
        String selectedItems = "";

        long StartTime = System.currentTimeMillis();
        for (int i = 0; i < (1 << n); i++) {
            int totalWeight = 0;
            int totalProfit = 0;
            StringBuilder currentItem = new StringBuilder();

            System.out.print("Subset " + (i + 1) + ": ");

            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    totalWeight += details.get(j)[1];
                    totalProfit += details.get(j)[2];
                    if (!currentItem.isEmpty()) {
                        currentItem.append(", ");
                    }
                    currentItem.append(details.get(j)[0]);

                    System.out.println(details.get(j)[0]+ " ");
                }
            }
            if (totalWeight <= k && totalProfit > maxProfit) {
                maxProfit = totalProfit;
                selectedItems = currentItem.toString();
            }
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - StartTime;
        JOptionPane.showMessageDialog(view, "Solusi diselesaikan dalam " + duration + " milliseconds", "Information", JOptionPane.INFORMATION_MESSAGE);

        view.getHasilBarangField().setText(selectedItems);
    }

    private void displayBarangByDynamicProgramming() {
        List<int[]> details = getDataintolistDP();
        int k = getConstraintValue();
        int n = details.size();
        int [][] dp = new int[n+1][k+1];

        long StartTime = System.currentTimeMillis();
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
                if (!selectedItems.isEmpty()) {
                    selectedItems.insert(0, ", ");
                }
                selectedItems.insert(0, details.get(i - 1)[0]); // Assuming item[0] is the item number
                w -= details.get(i - 1)[1];
            }
            i--;
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - StartTime;
        JOptionPane.showMessageDialog(view, "Solusi diselesaikan dalam " + duration + " milliseconds", "Information", JOptionPane.INFORMATION_MESSAGE);

        view.getHasilBarangField().setText(selectedItems.toString());
    }

//    private void displayBarangNamesByDensity() {
//        List<int[]> details = getDataintolistDP();
//        int n = details.size();
//        int k = getConstraintValue();
//        int maxProfit = 0;
//        String selectedItems = "";
//
//        for (int i = 0; i < (1 << n); i++) {
//            int totalWeight = 0;
//            int totalProfit = 0;
//            StringBuilder currentItem = new StringBuilder();
//
//            System.out.print("Subset " + (i + 1) + ": ");
//
//            for (int j = 0; j < n; j++) {
//                if ((i & (1 << j)) != 0) {
//                    totalWeight += details.get(j)[1];
//                    totalProfit += details.get(j)[2];
//                    if (!currentItem.isEmpty()) {
//                        currentItem.append(", ");
//                    }
//                    currentItem.append(details.get(j)[0]);
//
//                    System.out.println(details.get(j)[0]+ " ");
//                }
//            }
//            if (totalWeight <= k && totalProfit > maxProfit) {
//                maxProfit = totalProfit;
//                selectedItems = currentItem.toString();
//            }
//        }
//        view.getHasilBarangField().setText(selectedItems);
//    }

//    private void displayBarangByDynamicProgramming(){
//        List<int[]> details = getDataintolistDP();
//        int k = getConstraintValue();
//        int n = details.size();
//        int [][] dp = new int[n+1][k+1];
//
//        for (int i = 1; i <= n; i++){
//            for (int w = 1; w <= k; w++) {
//                if (details.get(i - 1)[1] <= w) {
//                    dp[i][w] = Math.max(details.get(i - 1)[2]+ dp[i - 1][w - details.get(i - 1)[1]], dp[i-1][w]);
//                } else {
//                    dp[i][w] = dp[i - 1][w];
//                }
//            }
//        }
//
//        StringBuilder selectedItems = new StringBuilder();
//        int i = n, w = k;
//        while (i > 0 && w > 0) {
//            if (dp[i][w] != dp[i - 1][w]) {
//                if (!selectedItems.isEmpty()) {
//                    selectedItems.insert(0, ", ");
//                }
//                selectedItems.insert(0, details.get(i - 1)[0]); // Assuming item[0] is the item number
//                w -= details.get(i - 1)[1];
//            }
//            i--;
//        }
//        view.getHasilBarangField().setText(selectedItems.toString());
//    }
}

