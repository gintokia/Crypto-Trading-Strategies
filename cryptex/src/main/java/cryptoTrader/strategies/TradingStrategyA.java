/**
 * TradingStrategyA implements the TradingStrategy interface. The rule that is implemented to compute any
 * trade is: if the price of bitcoin is less than or equal to $50000 and the price of cardano is more than $2, 
 * then buy 100 ADA coins, otherwise by 200 ADA coins
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

public class TradingStrategyA implements TradingStrategy {
	private String tradingStrategyName;
	private String tradingBrokerName;
	private String bitcoinPrice;
	private String cardanoPrice;

	/**
	 * Computes the trade by using the class's unique trading rule
	 */
	public TradeResult trade(ArrayList<Cryptocurrency> coins) {
		TradeResult data = new TradeResult(tradingBrokerName, tradingStrategyName);
		DataFetcher fetcher = new DataFetcher();
		Date date = new Date();
		if (validate(coins)) {
			double[] prices = fetcher.getPricesForCoins("bitcoin", "cardano");
			bitcoinPrice = String.format("%.1f", prices[0]);
			cardanoPrice = String.format("%.1f", prices[1]);

			if ((Double.parseDouble(bitcoinPrice) <= 60000) && (Double.parseDouble(cardanoPrice) > 2)) {
				data.setCoinName("ADA");
				data.setAction("Buy");
				data.setQuantity("100");
				data.setPrice(cardanoPrice);
				data.setDate(date);
			} else {
				data.setCoinName("ADA");
				data.setAction("Buy");
				data.setQuantity("200");
				data.setPrice(cardanoPrice);
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
		boolean bitcoinInList = false;
		boolean cardanoInList = false;

		for (int i = 0; i < coins.size(); i++) {
			if ((coins.get(i).getName().equals("BTC"))) {
				bitcoinInList = true;
			} else if ((coins.get(i).getName().equals("ADA"))) {
				cardanoInList = true;
			}
		}

		if (bitcoinInList && cardanoInList) {
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

	public String getBitcoinPrice() {
		return bitcoinPrice;
	}

	public void setBitcoinPrice(String bitcoinPrice) {
		this.bitcoinPrice = bitcoinPrice;
	}

	public String getCardanoPrice() {
		return cardanoPrice;
	}

	public void setCardanoPrice(String cardanoPrice) {
		this.cardanoPrice = cardanoPrice;
	}

}
