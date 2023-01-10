package webserver;

public class RequestUtils {

	public String pathParse(String line) {
		String[] str = line.split(" ");
		return str[1];
	}
}
