/**
 * The Cryptocurrency class is a configuration object used to hold the name of a cryptocoin
 * 
 * @author Rami Istwani
 */

package cryptoTrader.utils;

public class Cryptocurrency {
	private String name;

	public Cryptocurrency(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
