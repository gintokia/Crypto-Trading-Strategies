/**
 * The MainUI class is a user interface of the actual program that implements
 * the Singleton deign pattern and is displayed after the user validates their credentials
 * 
 * @author Saaketh Chenna
 */

package cryptoTrader.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import cryptoTrader.utils.AvailableCryptoList;
import cryptoTrader.utils.Cryptocurrency;
import cryptoTrader.utils.DataFetcher;
import cryptoTrader.utils.DataSet;
import cryptoTrader.utils.DataVisualizationCreator;
import cryptoTrader.utils.TradeResult;
import cryptoTrader.utils.TradingBroker;

public class MainUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static MainUI instance;
	private JPanel stats, chartPanel, tablePanel;

	// Should be a reference to a separate object in actual implementation
	private List<String> selectedList;

	private JTextArea selectedTickerList;
//	private JTextArea tickerList;
	private JTextArea tickerText;
	private JTextArea BrokerText;
	private JComboBox<String> strategyList;
	private Map<String, List<String>> brokersTickers = new HashMap<String, List<String>>();
	private Map<String, String> brokersStrategies = new HashMap<String, String>();
	private List<String> selectedTickers = new ArrayList<String>();
	private String selectedStrategy = "";
	private DefaultTableModel dtm;
	private JTable table;
	private ArrayList<TradingBroker> tradingBrokers;
	private ArrayList<TradeResult> dataObject;
	private ArrayList<DataSet> dataSets;

	/**
	 * Gets instance of Singleton UI object
	 * 
	 * @return class instance
	 */
	public static MainUI getInstance() {
		if (instance == null)
			instance = new MainUI();

		return instance;
	}

	/**
	 * Constructor class creates MainUI and renders the user interface
	 */
	private MainUI() {

		// Set window title
		super("Crypto Trading Strategies");

		// Set top bar

		JPanel north = new JPanel();		

		JButton trade = new JButton("Perform Trade");
		trade.setActionCommand("refresh");
		trade.addActionListener(this);

		JPanel south = new JPanel();

		south.add(trade);

		dtm = new DefaultTableModel(new Object[] { "Trading Client", "Coin List", "Strategy Name" }, 1);
		table = new JTable(dtm);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Trading Client Actions", TitledBorder.CENTER, TitledBorder.TOP));
		Vector<String> strategyNames = new Vector<String>();
		strategyNames.add("TradingStrategyA");
		strategyNames.add("TradingStrategyB");
		strategyNames.add("TradingStrategyC");
		strategyNames.add("TradingStrategyD");
		TableColumn strategyColumn = table.getColumnModel().getColumn(2);
		JComboBox comboBox = new JComboBox(strategyNames);
		strategyColumn.setCellEditor(new DefaultCellEditor(comboBox));
		JButton addRow = new JButton("Add Row");
		JButton remRow = new JButton("Remove Row");
		addRow.setActionCommand("addTableRow");
		addRow.addActionListener(this);
		remRow.setActionCommand("remTableRow");
		remRow.addActionListener(this);

		scrollPane.setPreferredSize(new Dimension(600, 300));
		table.setFillsViewportHeight(true);

		JPanel east = new JPanel();
//		east.setLayout();
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
//		east.add(table);
		east.add(scrollPane);
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.add(addRow);
		buttons.add(remRow);
		east.add(buttons);
//		east.add(selectedTickerListLabel);
//		east.add(selectedTickersScrollPane);

		// Set charts region
		JPanel west = new JPanel();
		west.setPreferredSize(new Dimension(1250, 650));
		stats = new JPanel();
		stats.setLayout(new GridLayout(2, 2));

		west.add(stats);

		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(east, BorderLayout.EAST);
		getContentPane().add(west, BorderLayout.CENTER);
		getContentPane().add(south, BorderLayout.SOUTH);
//		getContentPane().add(west, BorderLayout.WEST);

		dataSets = new ArrayList<DataSet>();
		dataObject = new ArrayList<TradeResult>();
	}

	public void updateStats(JComponent component) {
		stats.add(component);
		stats.revalidate();
	}

	public static void main(String[] args) {
		JFrame frame = MainUI.getInstance();
		frame.setSize(900, 600);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Listens for actions performed on the UI such as the Perform Trade button or
	 * Add Row, etc
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if ("refresh".equals(command)) {
			tradingBrokers = new ArrayList<TradingBroker>();

			for (int count = 0; count < dtm.getRowCount(); count++) {
				Object traderObject = dtm.getValueAt(count, 0);
				if (traderObject == null) {
					JOptionPane.showMessageDialog(this, "please fill in Trader name on line " + (count + 1));
					return;
				}
				String traderName = traderObject.toString();
				Object coinObject = dtm.getValueAt(count, 1);
				if (coinObject == null) {
					JOptionPane.showMessageDialog(this, "please fill in cryptocoin list on line " + (count + 1));
					return;
				}
				String[] coinNames = coinObject.toString().split(",");
				Object strategyObject = dtm.getValueAt(count, 2);
				if (strategyObject == null) {
					JOptionPane.showMessageDialog(this, "please fill in strategy name on line " + (count + 1));
					return;
				}
				String strategyName = strategyObject.toString();
				System.out.println(traderName + " " + Arrays.toString(coinNames) + " " + strategyName);

				TradingBroker newTradingBroker = new TradingBroker(traderName, coinNames, strategyName);
				for (int i = 0; i < tradingBrokers.size(); i++) {
					if (newTradingBroker.getName().equals(tradingBrokers.get(i).getName())) {
						JOptionPane.showMessageDialog(this, "Trading Broker could not be added as one with the name \""
								+ traderName + "\" already exists.");
						return;
					}
				}
				tradingBrokers.add(newTradingBroker);

			}
			stats.removeAll();

			performTrade();

			notify(dataObject, dataSets);
			
			notifyBrokers();
			
		} else if ("addTableRow".equals(command)) {
			dtm.addRow(new String[3]);

		} else if ("remTableRow".equals(command)) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1)
				dtm.removeRow(selectedRow);
		}

	}

	/**
	 * Performs a trade for every trading broker in the list of trading brokers
	 */
	public void performTrade() {
		for (int i = 0; i < tradingBrokers.size(); i++) {
			TradeResult tradeResult = tradingBrokers.get(i).getTradingStrategy()
					.trade(tradingBrokers.get(i).getCoins());

			dataObject.add(tradeResult);

			DataSet dataSet = new DataSet(tradingBrokers.get(i).getName(),
					tradingBrokers.get(i).getTradingStrategy().getTradingStrategyName());

			if (dataSet.alreadyExists(dataSets, dataSet.getTradingBrokerName(), dataSet.getTradingStrategyName())) {
				incrementTrades(dataSet.getTradingBrokerName(), dataSet.getTradingStrategyName());
			} else {
				dataSets.add(dataSet);
			}

		}

	}
	
	public void notifyBrokers() {
		AvailableCryptoList availableCryptoList = new AvailableCryptoList();
		DataFetcher df = new DataFetcher();
		ArrayList<String> coinSymbols = new ArrayList<String>();
		ArrayList<String> coinNames = new ArrayList<String>();
		ArrayList<Double> allCoinPrices = new ArrayList<Double>();
		
		for(int i = 0; i < tradingBrokers.size(); i++) {
			TradingBroker tradingBroker = tradingBrokers.get(i);
			ArrayList<Cryptocurrency> coins = tradingBroker.getCoins();
			for(int j = 0; j < coins.size(); j++) {
				coinSymbols.add(coins.get(j).getName().toLowerCase());
			}
		}
		
		coinNames = availableCryptoList.getNames(coinSymbols);
		
		allCoinPrices = df.getAllPricesForCoins(coinNames);
			
		String message = "Retrieved Prices: \n";
		
		int count = 0;
		for(int i = 0; i < tradingBrokers.size(); i++) {
			TradingBroker tradingBroker = tradingBrokers.get(i);
			message = message + tradingBroker.getName() + " -> ";
			for(int j = 0; j < tradingBroker.getCoins().size(); j++, count++) {
				message = message + coinSymbols.get(count).toUpperCase() + ": " + allCoinPrices.get(count) + " ";
			}
			message += "\n";
			
		}
		JOptionPane.showMessageDialog(this, message);
		
		
		
	}

	/**
	 * Increments the number of trades that are done multiple times
	 * 
	 * @param tradingBrokerName: name of trading broker
	 * @param tradingStrategyName: name of trading strategy
	 */
	public void incrementTrades(String tradingBrokerName, String tradingStrategyName) {
		for (int i = 0; i < dataSets.size(); i++) {
			if (tradingBrokerName.equals(dataSets.get(i).getTradingBrokerName())
					&& tradingStrategyName.equals(dataSets.get(i).getTradingStrategyName())) {
				dataSets.get(i).incrementTrades();
			}
		}

	}

	/**
	 * Notifies the DataVisualizationCreator class by providing it objects
	 * with updated data from user selections
	 * 
	 * @param dataObject: object that is used to create table
	 * @param dataSets: object that is used to create bar graph
	 */
	public void notify(ArrayList<TradeResult> dataObject, ArrayList<DataSet> dataSets) {
		DataVisualizationCreator creator = new DataVisualizationCreator();
		creator.createCharts(dataObject, dataSets);
	}

}
