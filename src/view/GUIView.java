package view;

import model.TableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class GUIView extends JFrame {
    private final JTable table;
    private final JButton addButton;
    private final JButton removeButton;
    private final JTextField constraintField;
    private final JTextField hasilBarangField;
    private final JButton BruteForceButton;
    private final JButton DynamicProgrammingButton;

    public GUIView() {
        setTitle("Knapsack 0/1");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        TableModel tableModel = new TableModel(new Object[]{"Barang", "Weight", "Profit"}, 0);
        table = new JTable(tableModel);

        TableColumn column = table.getColumnModel().getColumn(0);
        column.setMaxWidth(70);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel addRemovePanel = new JPanel();
        addRemovePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        addButton = new JButton("+");
        addRemovePanel.add(addButton);

        removeButton = new JButton("-");
        addRemovePanel.add(removeButton);

        add(addRemovePanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        BruteForceButton = new JButton("Brute Force");
        buttonPanel.add(BruteForceButton);

        DynamicProgrammingButton = new JButton("Dynamic Programming");
        buttonPanel.add(DynamicProgrammingButton);

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel combinedTextFieldPanel = new JPanel();
        combinedTextFieldPanel.setLayout(new GridLayout(2, 1));

        JPanel textFieldPanelConstraint = new JPanel();
        textFieldPanelConstraint.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel constraintLabel = new JLabel("Constraint: ");
        textFieldPanelConstraint.add(constraintLabel);

        constraintField = new JTextField(4);
        textFieldPanelConstraint.add(constraintField);

        JLabel constraintTextLabel = new JLabel("kg");
        textFieldPanelConstraint.add(constraintTextLabel);

        combinedTextFieldPanel.add(textFieldPanelConstraint);

        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel hasilBarangLabel = new JLabel("Hasil Barang: ");
        textFieldPanel.add(hasilBarangLabel);

        hasilBarangField = new JTextField(30);
        hasilBarangField.setEditable(false);
        textFieldPanel.add(hasilBarangField);

        combinedTextFieldPanel.add(textFieldPanel);

        bottomPanel.add(combinedTextFieldPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public JTable getTable() {
        return table;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getBruteForceButton() {
        return BruteForceButton;
    }

    public JButton getDynamicProgrammingButton() {
        return DynamicProgrammingButton;
    }

    public JTextField getHasilBarangField() {
        return hasilBarangField;
    }

    public JTextField getConstraintField() {
        return constraintField;
    }
}
