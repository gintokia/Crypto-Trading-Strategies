/**
 * TradingStrategyD implements the TradingStrategy interface. The rule that is implemented to compute
 * any trade is: if the price of binance usd is less than or equal to 0.95 and the price of usd coin is
 * less than 0.95, then buy binance usd coins of $2070 worth, otherwise buy binance usd coins
 * of $1080 worth
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

public class TradingStrategyD implements TradingStrategy {
	private String tradingStrategyName;
	private String tradingBrokerName;
	private String binanceusdPrice;
	private String usdcoinPrice;

	/**
	 * Computes the trade by using the class's unique trading rule
	 */
	public TradeResult trade(ArrayList<Cryptocurrency> coins) {
		TradeResult data = new TradeResult(tradingBrokerName, tradingStrategyName);
		DataFetcher fetcher = new DataFetcher();
		Date date = new Date();
		if (validate(coins)) {
			double[] prices = fetcher.getPricesForCoins("binance-usd", "usd-coin");
			binanceusdPrice = String.format("%.1f", prices[0]);
			usdcoinPrice = String.format("%.1f", prices[1]);

			if ((Double.parseDouble(binanceusdPrice) <= 0.95) && (Double.parseDouble(usdcoinPrice) < 0.95)) {
				data.setCoinName("USDC");
				data.setAction("Buy");
				data.setQuantity("2300");
				data.setPrice(usdcoinPrice);
				data.setDate(date);
			} else {
				data.setCoinName("USDC");
				data.setAction("Buy");
				data.setQuantity("1200");
				data.setPrice(usdcoinPrice);
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
		boolean binanceusdInList = false;
		boolean usdcoinInList = false;

		for (int i = 0; i < coins.size(); i++) {
			if ((coins.get(i).getName().equals("BUSD"))) {
				binanceusdInList = true;
			} else if ((coins.get(i).getName().equals("USDC"))) {
				usdcoinInList = true;
			}
		}

		if (binanceusdInList && usdcoinInList) {
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

	public String getbinanceusdPrice() {
		return binanceusdPrice;
	}

	public void setbinanceusdPrice(String binanceusdPrice) {
		this.binanceusdPrice = binanceusdPrice;
	}

	public String getusdcoinPrice() {
		return usdcoinPrice;
	}

	public void setusdcoinPrice(String usdcoinPrice) {
		this.usdcoinPrice = usdcoinPrice;
	}

}
