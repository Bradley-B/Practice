package tables;

import java.util.regex.Pattern;

/**
 * verifies IP addresses
 * @author Bradley
 *
 */
public class Verification {

	public static void main(String[] args) {
		System.out.println(validate("127.0.0.1"));
	}

	private static final Pattern PATTERN = Pattern.compile(
	        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	public static boolean validate(final String ip) {
	    return PATTERN.matcher(ip).matches();
	}
}
