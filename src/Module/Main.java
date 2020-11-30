package Module;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException  {
		//enter the pesudo choisit
		String  s; 
		System.out.println("Choisir votre pseudo : ");
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        s = bufferRead.readLine();
        bufferRead.close();
        User user = new User(s);
        //MulticastReceiver MR = new MulticastReceiver(user);
        new Thread(new MulticastReceiver(user)).start(); 
        MulticastSender MS = new MulticastSender(user,2444) ;
        MS.send_getUserList();
	}
}