package webserver;

public class StringParser {

	public String pathParse(String line) {
		String[] str = line.split(" ");
		return str[1];
	}
}
