import java.awt.BorderLayout;
import java.io.File;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TableFeature extends JFrame {
	String titles[] = new String[] {
			"Directory?", "File Name", "Read?", "Write?", "Size", "Last Modified"
	};
	
	public TableFeature() {
		super("Simple JTable Test");
		setSize(800,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		File pwd = new File(".");
		Object[][] stats = getFileStats(pwd);
		
		JTable jTable = new JTable(stats, titles);
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		jTable.setColumnSelectionAllowed(true);
		
		JScrollPane jScrollPane = new JScrollPane(jTable);
		getContentPane().add(jScrollPane, BorderLayout.CENTER);
	}
	
	private Object[][] getFileStats(File pwd) {
		String files[] =pwd.list();
		Object[][] results = new Object[files.length][titles.length];
		
		for(int i=0; i<files.length; i++)
		{
			File temp = new File(files[i]);
			results[i][0] = temp.isDirectory();
			results[i][1] = temp.getName();
			results[i][2] = temp.canRead();
			results[i][3] = temp.canWrite();
			results[i][4] = temp.length();
			results[i][5] = new Date(temp.lastModified());
		}
		return results;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TableFeature tableFeature = new TableFeature();
		tableFeature.setVisible(true);
	}

}
