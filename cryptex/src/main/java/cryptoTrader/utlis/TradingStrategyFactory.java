/**
 * The TradingStrategyFactory class implements the Factory Method design pattern by providing a getInstance
 * method for getting the proper trading strategy class according to what the user has selected 
 * 
 * @author Abdul Khan
 */
package cryptoTrader.utils;

import cryptoTrader.strategies.TradingStrategyA;
import cryptoTrader.strategies.TradingStrategyB;
import cryptoTrader.strategies.TradingStrategyC;
import cryptoTrader.strategies.TradingStrategyD;

public class TradingStrategyFactory {
	
	public TradingStrategy getInstance(String str) {
		if(str.equals("TradingStrategyA")) {
			return new TradingStrategyA();
		}
		else if(str.equals("TradingStrategyB")) {
			return new TradingStrategyB();
		}
		else if(str.equals("TradingStrategyC")) {
			return new TradingStrategyC();
		}
		else if(str.equals("TradingStrategyD")) {
			return new TradingStrategyD();
		}
		return null;
	}

}
