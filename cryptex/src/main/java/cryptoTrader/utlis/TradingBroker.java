/**
 * The TradingBroker class is an object used to store information about the trading broker including,
 * its name, the list of cryptocoins it has declared interest in, and the trading strategy it would like to use
 * 
 * @author Saaketh Chenna
 * @author Nicholas Cooper
 */
package cryptoTrader.utils;

import java.util.ArrayList;

public class TradingBroker {
	private String name;
	private ArrayList<Cryptocurrency> coins;
	private TradingStrategy tradingStrategy;

	public TradingBroker(String name, String[] coinNames, String tradingStrategy) {
		this.name = name;
		this.coins = new ArrayList<Cryptocurrency>();

		for (int i = 0; i < coinNames.length; i++) {
			Cryptocurrency coin = new Cryptocurrency(coinNames[i].strip().toUpperCase());
			coins.add(coin);
		}
		TradingStrategyFactory tradingStrategyFactory = new TradingStrategyFactory();
		this.tradingStrategy = tradingStrategyFactory.getInstance(tradingStrategy);
		this.tradingStrategy.setTradingBrokerName(name);
		this.tradingStrategy.setTradingStrategyName(tradingStrategy);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Cryptocurrency> getCoins() {
		return coins;
	}

	public void setCoins(ArrayList<Cryptocurrency> coins) {
		this.coins = coins;
	}

	public TradingStrategy getTradingStrategy() {
		return tradingStrategy;
	}

	public void setTradingStrategy(TradingStrategy tradingStrategy) {
		this.tradingStrategy = tradingStrategy;
	}

	

}
