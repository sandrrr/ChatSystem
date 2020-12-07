package Module ;
import java.io.BufferedReader      ;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException   {
		Agent agent = new Agent();
		try {
			agent.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}