package application;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class StatisticGame implements Constants {
    JDialog dialog = null;
    JTable table = null;

    public StatisticGame(int length, int numberOfTurns, int bestLength) {

    dialog = new JDialog();
    dialog.setTitle("Statistic");

    DefaultTableModel model = new DefaultTableModel(
        new Object[] {"Average turn","The longest snake", "Average length snake"}, 0);
    table = new JTable(model);
    dialog.setLayout(new BorderLayout());
    dialog.add(new JScrollPane(table), BorderLayout.CENTER);
    model.addRow(new Object[] {numberOfTurns, length , bestLength});
    dialog.pack();
    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    dialog.setResizable(false);
    dialog.setVisible(true);
  }
}