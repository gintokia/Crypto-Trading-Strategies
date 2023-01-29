/**
 * The DataFetcher class is a class used to retrieve data from CoinGecko's API by making a GET request
 * 
 * @author Abdul Khan
 */

package cryptoTrader.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DataFetcher {

	/**
	 * Makes an HTTP GET Request to CoinGecko's API to retrieve JsonObject
	 * 
	 * @param id:  id of first cryptocoin
	 * @param id2: id of second cryptocoin
	 * @return a JsonObject containing requested data
	 */
	private JsonObject getDataForCrypto(String id, String id2) {

		String urlString = String.format("https://api.coingecko.com/api/v3/simple/price?ids=%s%%2C%s&vs_currencies=cad",
				id, id2);

		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				String inline = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
				JsonObject jsonObject = new JsonParser().parse(inline).getAsJsonObject();
				return jsonObject;
			}

		} catch (IOException e) {
			System.out.println("Something went wrong with the API call.");
		}
		return null;
	}

	/**
	 * Retrieves prices of two given cryptocoins
	 * 
	 * @param id:  id of first cryptocoin
	 * @param id2: id of second cryptocoin
	 * @return a double array with prices of both cryptocoins
	 */
	public double[] getPricesForCoins(String id, String id2) {
		double[] prices = new double[2];

		JsonObject jsonObject = getDataForCrypto(id, id2);

		if (jsonObject != null) {
			JsonObject firstID = jsonObject.get(id).getAsJsonObject();
			prices[0] = firstID.get("cad").getAsDouble();
			JsonObject secondID = jsonObject.get(id2).getAsJsonObject();
			prices[1] = secondID.get("cad").getAsDouble();
		}

		return prices;
	}
	
	public ArrayList<Double> getAllPricesForCoins(ArrayList<String> coinNames) {
		ArrayList<Double> prices = new ArrayList<Double>();
				
		
		for(int i = 0; i < coinNames.size(); i++) {
			JsonObject jsonObject = getDataForCrypto(coinNames.get(i), coinNames.get(i));
			JsonObject id = jsonObject.get(coinNames.get(i)).getAsJsonObject();
			double price = id.get("cad").getAsDouble();
			prices.add(price);
		}
		
		return prices;
	}
	

}
