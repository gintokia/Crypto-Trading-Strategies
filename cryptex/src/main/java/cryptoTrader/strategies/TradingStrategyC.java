/**
 * TradingStrategyC implements the TradingStrategy interface. The rule that is implemented to compute
 * any trade is: if the price of solana is less than or equal to $130 and the price of tether is less than
 * $1.24, then buy $136 worth of solana, otherwise by 50 coins of solana
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

public class TradingStrategyC implements TradingStrategy {
	private String tradingStrategyName;
	private String tradingBrokerName;
	private String solanaPrice;
	private String tetherPrice;

	/**
	 * Computes the trade by using the class's unique trading rule
	 */
	public TradeResult trade(ArrayList<Cryptocurrency> coins) {
		TradeResult data = new TradeResult(tradingBrokerName, tradingStrategyName);
		DataFetcher fetcher = new DataFetcher();
		Date date = new Date();
		if (validate(coins)) {
			double[] prices = fetcher.getPricesForCoins("solana", "tether");
			solanaPrice = String.format("%.1f", prices[0]);
			tetherPrice = String.format("%.1f", prices[1]);

			if ((Double.parseDouble(solanaPrice) <= 130) && (Double.parseDouble(tetherPrice) < 1.24)) {
				data.setCoinName("SOL");
				data.setAction("Buy");
				data.setQuantity("110");
				data.setPrice(solanaPrice);
				data.setDate(date);
			} else {
				data.setCoinName("SOL");
				data.setAction("Buy");
				data.setQuantity("50");
				data.setPrice(solanaPrice);
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
		boolean solanaInList = false;
		boolean tetherInList = false;

		for (int i = 0; i < coins.size(); i++) {
			if ((coins.get(i).getName().equals("SOL"))) {
				solanaInList = true;
			} else if ((coins.get(i).getName().equals("USDT"))) {
				tetherInList = true;
			}
		}

		if (solanaInList && tetherInList) {
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

	public String getsolanaPrice() {
		return solanaPrice;
	}

	public void setsolanaPrice(String solanaPrice) {
		this.solanaPrice = solanaPrice;
	}

	public String gettetherPrice() {
		return tetherPrice;
	}

	public void settetherPrice(String tetherPrice) {
		this.tetherPrice = tetherPrice;
	}

}
