/**
 * The DataSet class is an object used to store information about a trading action, 
 * so that it can given to the DataVisualizationCreator and rendered
 * 
 * @author Rami Istwani
 */

package cryptoTrader.utils;

import java.util.ArrayList;

public class DataSet {
	private String tradingBrokerName;
	private String tradingStrategyName;
	private int numberOfTrades;

	/**
	 * Constructor creates a new DataSet object and initializes fields
	 * 
	 * @param tradingBrokerName: name of trading broker
	 * @param tradingStrategyName: name of trading strategy
	 */
	public DataSet(String tradingBrokerName, String tradingStrategyName) {
		this.tradingBrokerName = tradingBrokerName;
		this.tradingStrategyName = tradingStrategyName;
		this.numberOfTrades = 1;
	}

	/**
	 * Checks to see if data set with the same tradingBrokerName and tradingStrategyName is in list of data sets
	 * 
	 * @param dataSets: list of data sets
	 * @param tradingBrokerName: name of trading broker
	 * @param tradingStrategyName: name of trading strategy
	 * @return
	 */
	public boolean alreadyExists(ArrayList<DataSet> dataSets, String tradingBrokerName, String tradingStrategyName) {
		boolean alreadyExists = false;

		for (int i = 0; i < dataSets.size(); i++) {
			if (tradingBrokerName.equals(dataSets.get(i).getTradingBrokerName())
					&& tradingStrategyName.equals(dataSets.get(i).getTradingStrategyName())) {
				alreadyExists = true;
			}
		}
		return alreadyExists;

	}

	/**
	 * Increases the number of trades by 1
	 */
	public void incrementTrades() {
		this.numberOfTrades++;
	}

	public String getTradingBrokerName() {
		return tradingBrokerName;
	}

	public void setTradingBrokerName(String tradingBrokerName) {
		this.tradingBrokerName = tradingBrokerName;
	}

	public String getTradingStrategyName() {
		return tradingStrategyName;
	}

	public void setTradingStrategyName(String tradingStrategyName) {
		this.tradingStrategyName = tradingStrategyName;
	}

	public int getNumberOfTrades() {
		return numberOfTrades;
	}

	public void setNumberOfTrades(int numberOfTrades) {
		this.numberOfTrades = numberOfTrades;
	}

}
