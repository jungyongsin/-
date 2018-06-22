import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

public class PingOutline extends JFrame {
	
	String[] titles;
	Object[][] stats;
	
	public PingOutline() {
		super("Network Scanner");
		String myIP;
		String myHostName;
		
		InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		myIP = ia.getHostAddress();
		myHostName = ia.getHostName();
		
		String fixedIP = myIP.substring(0, myIP.lastIndexOf(".") + 1);
		
		System.out.println(myIP + "," + myHostName + "," + fixedIP );
		
		// menu begin
		JMenuBar menuBar = new JMenuBar();
		
		setJMenuBar(menuBar);
		
		//상위메뉴
		JMenu scanMenu = new JMenu("Scan");
		JMenu gotoMenu = new JMenu("Go to");
		JMenu cmdMenu = new JMenu("Commands");
		JMenu favoriteMenu = new JMenu("Favorites");
		JMenu toolsMenu = new JMenu("Tools");
		JMenu helpMenu = new JMenu("Help");
		
		//상위메뉴를 메뉴바에 붙임
		menuBar.add(scanMenu);
		menuBar.add(gotoMenu);
		menuBar.add(cmdMenu);
		menuBar.add(favoriteMenu);
		menuBar.add(toolsMenu);
		menuBar.add(helpMenu);
		
		//하위메뉴
		JMenuItem loadFromFileAction = new JMenuItem("Load From Item");
		JMenuItem exportAllAction = new JMenuItem("Export All");
		JMenuItem exportSelectionAction = new JMenuItem("Export Selection");
		JMenuItem quitAction = new JMenuItem("Quit");
		
		//하위메뉴를 상위메뉴에 붙임
		scanMenu.add(loadFromFileAction);
		scanMenu.add(exportAllAction);
		scanMenu.add(exportSelectionAction);
		scanMenu.addSeparator();
		scanMenu.add(quitAction);
		
		
		JMenuItem nextAliveHostAction = new JMenuItem("Next alive host");
		JMenuItem nextOpenPortAction = new JMenuItem("Next open port");
		JMenuItem nextDeadHostAction = new JMenuItem("Next dead host");
		JMenuItem previousAlivehostAction = new JMenuItem("Previous alive host");
		JMenuItem previousOpenPortAction = new JMenuItem("Previous open port");
		JMenuItem preciousdeadHostAction = new JMenuItem("Previous dead host");
		JMenuItem findAction = new JMenuItem("Find");
		
		gotoMenu.add(nextAliveHostAction);
		gotoMenu.add(nextOpenPortAction);
		gotoMenu.add(nextDeadHostAction);
		gotoMenu.addSeparator();
		gotoMenu.add(previousAlivehostAction);
		gotoMenu.add(previousOpenPortAction);
		gotoMenu.add(preciousdeadHostAction);
		gotoMenu.addSeparator();
		gotoMenu.add(findAction);
		
		JMenuItem showDetailsAction = new JMenuItem("Show details");
		JMenuItem rescanIPAction = new JMenuItem("Rescan IP(s)");
		JMenuItem deletIPAction = new JMenuItem("Delete IP(s)");
		JMenuItem copyIPAction = new JMenuItem("Copy IP");
		JMenuItem copyDetailsAction = new JMenuItem("Copy details");
		JMenuItem openAction = new JMenuItem("Open");
		
		cmdMenu.add(showDetailsAction);
		cmdMenu.addSeparator();
		cmdMenu.add(rescanIPAction);
		cmdMenu.add(deletIPAction);
		cmdMenu.addSeparator();
		cmdMenu.add(copyIPAction);
		cmdMenu.add(copyDetailsAction);
		cmdMenu.addSeparator();
		cmdMenu.add(openAction);
		
		JMenuItem addCurrentAction = new JMenuItem("Add current");
		JMenuItem manageFavoriteAction = new JMenuItem("Manage favorites");
		
		favoriteMenu.add(addCurrentAction);
		favoriteMenu.add(manageFavoriteAction);
		favoriteMenu.addSeparator();
		
		JMenuItem prefrenceAction = new JMenuItem("Preference");
		JMenuItem fetchersAction = new JMenuItem("Fetchers");
		JMenuItem selectionAction = new JMenuItem("Selection");
		JMenuItem scanStaticsAction = new JMenuItem("Scan statistics");
		
		toolsMenu.add(prefrenceAction);
		toolsMenu.add(fetchersAction);
		toolsMenu.addSeparator();
		toolsMenu.add(selectionAction);
		toolsMenu.add(scanStaticsAction);
		
		JMenuItem gettingStartedAction = new JMenuItem("Getting Started");
		JMenuItem officialWebsiteAction = new JMenuItem("Official Website");
		JMenuItem faqAction = new JMenuItem("FAQ");
		JMenuItem reportAnIssueAction = new JMenuItem("Report an issue");
		JMenuItem pluginsAction = new JMenuItem("Plugins");
		JMenuItem commandLineUsageAction = new JMenuItem("Command-line usage");
		JMenuItem checkForNewerVersionAction = new JMenuItem("Check for newer version");
		JMenuItem aboutAction = new JMenuItem("About");

		helpMenu.add(gettingStartedAction);
		helpMenu.addSeparator();
		helpMenu.add(officialWebsiteAction);
		helpMenu.add(faqAction);
		helpMenu.add(reportAnIssueAction);
		helpMenu.add(pluginsAction);
		helpMenu.addSeparator();
		helpMenu.add(commandLineUsageAction);
		helpMenu.addSeparator();
		helpMenu.add(checkForNewerVersionAction);
		helpMenu.addSeparator();
		helpMenu.add(aboutAction);
		// menu end
		
		// toolbar begin
		Font myFont = new Font("Serif", Font.BOLD, 16);
		JToolBar toolbar1 = new JToolBar();
		toolbar1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JToolBar toolbar2 = new JToolBar();
		toolbar2.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel iprangeLabel = new JLabel("IP Range:");
		JTextField ipStartTextField = new JTextField(10);
		JLabel toLabel = new JLabel("to ");
		JTextField ipEndTextField = new JTextField(10);
		JComboBox sourceSelectionComboBox = new JComboBox();
		sourceSelectionComboBox.addItem("IP Range");
		sourceSelectionComboBox.addItem("Random");
		sourceSelectionComboBox.addItem("File");
		ImageIcon menIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\menu (1).png");
		Image menImage = menIcon.getImage();
		menImage = menImage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon menuIcon = new ImageIcon(menImage);
		JButton menuButton = new JButton(menuIcon);
		
		iprangeLabel.setFont(myFont);
		toLabel.setFont(myFont);
		iprangeLabel.setPreferredSize(new Dimension(75, 30));
		toLabel.setPreferredSize(new Dimension(20, 30));
		sourceSelectionComboBox.setPreferredSize(new Dimension(80, 22));
		
		toolbar1.add(iprangeLabel);
		toolbar1.add(ipStartTextField);
		toolbar1.add(toLabel);
		toolbar1.add(ipEndTextField);
		toolbar1.add(sourceSelectionComboBox);
		toolbar1.add(menuButton);
		
		JLabel hostNameLabel = new JLabel("Hostname: ");
		JTextField hostNameTextField = new JTextField(10);
		JButton ipButton = new JButton("IP ↑");
		ImageIcon im = new ImageIcon("angy-ip-scanner-icon (2).png");
		JButton iconButton = new JButton(im);
		JComboBox netMaskComboBox = new JComboBox();
		netMaskComboBox.addItem("Netmask");
		netMaskComboBox.addItem("/26");
		netMaskComboBox.addItem("/24");
		netMaskComboBox.addItem("/16");
		netMaskComboBox.addItem("255...192");
		netMaskComboBox.addItem("255...128");
		netMaskComboBox.addItem("255...0");
		netMaskComboBox.addItem("255..0.0");
		netMaskComboBox.addItem("255.0.0.0");
		JButton startButton = new JButton("▶ Start");
		ImageIcon setIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\setting.png");
		Image setImage = setIcon.getImage();
		setImage = setImage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		ImageIcon settingIcon = new ImageIcon(setImage);
		JButton settingButton = new JButton(settingIcon);
	
		
		hostNameLabel.setFont(myFont);
		hostNameLabel.setPreferredSize(new Dimension(78, 30));
		ipButton.setPreferredSize(new Dimension(40, 22));
		netMaskComboBox.setPreferredSize(new Dimension(92, 22));
		startButton.setPreferredSize(new Dimension(82, 22));
		
		toolbar2.add(hostNameLabel);
		toolbar2.add(hostNameTextField);
		toolbar2.add(ipButton);
		toolbar2.add(netMaskComboBox);
		toolbar2.add(startButton);
		toolbar2.add(settingButton);

		JPanel pane = new JPanel(new BorderLayout());
		pane.add(toolbar1, BorderLayout.NORTH);
		pane.add(toolbar2, BorderLayout.SOUTH);
		
		add(pane, BorderLayout.NORTH);
		// toolbar end
		
		// table begin
		titles = new String[] {"IP", "Ping", "TTL", "Hostname", "Port"};
		stats = initializeTable();
		
		JTable jTable = new JTable(stats, titles);
		
		JScrollPane jScrollPane = new JScrollPane(jTable);
		add(jScrollPane, BorderLayout.CENTER);
		// table end
		
		// status bar begin
		JPanel statusmainPanel = new JPanel();
		JPanel statusPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		statusPanel1.setPreferredSize(new Dimension(160, 20));
		JPanel statusPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		statusPanel2.setPreferredSize(new Dimension(60, 20));
		JPanel statusPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		statusPanel3.setPreferredSize(new Dimension(60, 20));
		statusPanel1.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel2.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel3.setBorder(new BevelBorder(BevelBorder.LOWERED));
		getContentPane().add(statusmainPanel, BorderLayout.SOUTH);
		JLabel currentStatusLabel = new JLabel("Ready");
		JLabel displayStatusLabel = new JLabel("Display: All");
		JLabel threadStatusLabel = new JLabel("Threads:0");
		statusPanel1.add(currentStatusLabel);
		statusPanel2.add(displayStatusLabel);
		statusPanel3.add(threadStatusLabel);
		statusmainPanel.setLayout(new BoxLayout(statusmainPanel, BoxLayout.X_AXIS));
		statusmainPanel.add(statusPanel1);
		statusmainPanel.add(statusPanel2);
		statusmainPanel.add(statusPanel3);
		JProgressBar progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(150, 20));
		statusmainPanel.add(progressBar);
		progressBar.setIndeterminate(false);
		// status bar end
		
		quitAction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		officialWebsiteAction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String url_open = "https://angryip.org/";
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		faqAction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String url_open = "https://angryip.org/faq/";
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		reportAnIssueAction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String url_open = "https://github.com/angryip/ipscan/issues";
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		pluginsAction.addActionListener(new ActionListener() {
	
		@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String url_open = "https://angryip.org/contribute/plugins.html";
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(() -> {
					progressBar.setIndeterminate(true);
					Pinging[] pg = new Pinging[254];
					
					for(int i=0; i<254; i++) {
						pg[i] = new Pinging(fixedIP+(i+1));
						pg[i].start();
					}
					for(int i=0; i<254; i++) {
						Object[] msg = pg[i].getMsg();
						stats[i][0] = msg[0];
						if(msg[1] != null)
							stats[i][1] = msg[1];
						else
							stats[i][1] = "[n/a]";
						if(msg[2] != null)
							stats[i][2] = msg[2];
						else
							stats[i][2] = "[n/s]";
						if(msg[3] != null)
							stats[i][3] = msg[3];
						else
							stats[i][3] = "[n/s]";
					}
					for(int i=0; i<254; i++) {
						//port scan
						if(stats[i][1]!="[n/a]"||stats[i][2]!="[n/s]"||stats[i][3]!="[n/s]") {
							PortScanner ps = new PortScanner();
							final ExecutorService es = Executors.newFixedThreadPool(50);
							final int timeout = 200;
							final List<Future<ScanResult>> futures = new ArrayList<>();
							
							for(int port=1; port<=1024; port++) {
								futures.add(ps.portIsOpen(es, fixedIP + i, port, timeout));
							}
							try {
								es.awaitTermination(200L, TimeUnit.MILLISECONDS);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							int openPorts = 0;
							for(final Future<ScanResult> f: futures) {
								try {
									if(f.get().isOpen()) {
										openPorts++;
										stats[i][4] = (stats[i][4] == null)?f.get().getPort(): (stats[i][4].toString() + "," +f.get().getPort());
									}
								} catch (InterruptedException | ExecutionException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							es.shutdown();
						}
						else	stats[i][4]="[n/s]";
						if(stats[i][4]==null) stats[i][4]="[n/s]";
					}
					jTable.repaint();
					progressBar.setIndeterminate(false);
				}).start();
			}
		});
		
		ipStartTextField.setText(fixedIP + 1);
		ipEndTextField.setText(fixedIP + 254);
		hostNameTextField.setText(myHostName);
		
		
		setSize(700,700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private Object[][] initializeTable() {
		// TODO Auto-generated method stub
		Object[][] results = new Object[254][titles.length];
		return results;
	}
	private Object[][] getPingStats(String string) {
		Object[][] results = new Object[254][titles.length];
		for(int i=0; i<254; i++) {
			results[i][0] = string + (i + 1);
			results[i][1] = "[n/a]";
			results[i][2] = "[n/s]";
			results[i][3] = "[n/s]";
			results[i][4] = "[n/s]";
		}
		return results;
	}
	
	public static void main(String[] args) {
		PingOutline po = new PingOutline();
	}
}