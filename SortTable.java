package application;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;


public class SortTable implements Constants {
  JDialog dialog;
  JTable table;

  public SortTable(String name, String[] strList) {

    dialog = new JDialog();
    dialog.setTitle(name);

    DefaultTableModel model = new DefaultTableModel(new Object[] {"Sorted games"}, 0);
    table = new JTable(model);
    dialog.setLayout(new BorderLayout());
    dialog.add(new JScrollPane(table), BorderLayout.CENTER);

    for (int tmp = 0; tmp < NUMBER; tmp++)
      model.addRow(new Object[] {strList[tmp]});
    dialog.pack();
    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    dialog.setResizable(false);
    dialog.setVisible(true);
  }
}