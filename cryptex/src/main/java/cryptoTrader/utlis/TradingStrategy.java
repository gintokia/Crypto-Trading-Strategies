/**
 * The TradingStrategy interface is used to implement the design pattern Strategy, by having
 * numerous subclasses implement it that are different types of trading strategies with their
 * own respective algorithms for computing a trade
 */
package cryptoTrader.utils;

import java.util.ArrayList;

public interface TradingStrategy {
	
	public TradeResult trade(ArrayList<Cryptocurrency> coins);

	public String getTradingStrategyName();
	
	public void setTradingStrategyName(String tradingStrategyName);
	
	public String getTradingBrokerName();
	
	public void setTradingBrokerName(String tradingBrokerName);
	

	
}
