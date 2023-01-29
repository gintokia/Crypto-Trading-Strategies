/**
 * TradingStrategyB implements the TradingStrategy interface. The rule that is implemented to
 * compute any trade is: if the price of cardano is less than or equal to $2 and the price of ethereum 
 * is less than $3500, then buy cardano coins worth of $1000, otherwise sell 2 ethereum coins
 * 
 * @author Saaketh Chenna
 */

package cryptoTrader.strategies;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import cryptoTrader.gui.MainUI;
import cryptoTrader.utils.Cryptocurrency;
import cryptoTrader.utils.DataFetcher;
import cryptoTrader.utils.TradeResult;
import cryptoTrader.utils.TradingStrategy;

public class TradingStrategyB implements TradingStrategy {
	private String tradingStrategyName;
	private String tradingBrokerName;
	private String cardanoPrice;
	private String ethereumPrice;

	/**
	 * Computes the trade by using the class's unique trading rule
	 */
	public TradeResult trade(ArrayList<Cryptocurrency> coins) {
		TradeResult data = new TradeResult(tradingBrokerName, tradingStrategyName);
		DataFetcher fetcher = new DataFetcher();
		Date date = new Date();
		if (validate(coins)) {
			double[] prices = fetcher.getPricesForCoins("cardano", "ethereum");
			cardanoPrice = String.format("%.1f", prices[0]);
			ethereumPrice = String.format("%.1f", prices[1]);

			if ((Double.parseDouble(cardanoPrice) <= 2) && (Double.parseDouble(ethereumPrice) < 3500)) {
				data.setCoinName("ADA");
				data.setAction("Buy");
				data.setQuantity("560");
				data.setPrice(cardanoPrice);
				data.setDate(date);
			} else {
				data.setCoinName("ETH");
				data.setAction("Sell");
				data.setQuantity("2");
				data.setPrice(ethereumPrice);
				data.setDate(date);
			}
		} else {
			data.setCoinName("Null");
			data.setAction("Fail");
			data.setPrice("Null");
			data.setQuantity("Null");
			data.setDate(date);

			JOptionPane.showMessageDialog(MainUI.getInstance(),
					"The Trading Strategy cannot be applied. \nEntry causing error: " + tradingBrokerName + " - "
							+ tradingStrategyName);

		}

		return data;
	}

	/**
	 * Checks to see if the coins used in this class's trading strategy are in the
	 * trading broker's list of declared coins
	 * 
	 * @param coins: list of trading broker's declared coins
	 * @return true if coins used to compute algorithm are in the trading broker's
	 *         list of coins, false otherwise
	 */
	public boolean validate(ArrayList<Cryptocurrency> coins) {
		boolean cardanoInList = false;
		boolean ethereumInList = false;

		for (int i = 0; i < coins.size(); i++) {
			if ((coins.get(i).getName().equals("ADA"))) {
				cardanoInList = true;
			} else if ((coins.get(i).getName().equals("ETH"))) {
				ethereumInList = true;
			}
		}

		if (cardanoInList && ethereumInList) {
			return true;
		} else {
			return false;
		}
	}

	public String getTradingStrategyName() {
		return tradingStrategyName;
	}

	public void setTradingStrategyName(String tradingStrategyName) {
		this.tradingStrategyName = tradingStrategyName;
	}

	public String getTradingBrokerName() {
		return tradingBrokerName;
	}

	public void setTradingBrokerName(String tradingBrokerName) {
		this.tradingBrokerName = tradingBrokerName;
	}

	public String getcardanoPrice() {
		return cardanoPrice;
	}

	public void setcardanoPrice(String cardanoPrice) {
		this.cardanoPrice = cardanoPrice;
	}

	public String getethereumPrice() {
		return ethereumPrice;
	}

	public void setethereumPrice(String ethereumPrice) {
		this.ethereumPrice = ethereumPrice;
	}

}
