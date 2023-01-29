/**
 * The TradeResult class is a configuration object used to store information about the result of a trade
 * 
 * @author Nicholas Cooper
 */
package cryptoTrader.utils;

import java.util.Date;

public class TradeResult {
	
	private String tradingBrokerName;
	private String tradingStrategyName;
	private String coinName;
	private String action;
	private String quantity;
	private String price;
	private Date date;
	
	public TradeResult(String tradingBrokerName, String tradingStrategyName) {
		this.tradingBrokerName = tradingBrokerName;
		this.tradingStrategyName = tradingStrategyName;
		
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
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
