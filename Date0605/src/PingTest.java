import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingTest {
	String ip = "ip address";
	String pingResult = "";
	
	public PingTest(String ip) {
		// TODO Auto-generated constructor stub
		this.ip = ip;
	}
	
	public Object[] ResultPing(){
		Object[] results = new Object[3];
		String pinCmd = "ping -a " + ip;
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec(pinCmd);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				Pattern pattern = Pattern.compile("(\\d+ms)\\s+TTL=(\\d+)");
				Matcher matcher = pattern.matcher(inputLine);
				Pattern pattern2 = Pattern.compile("Ping\\s+(.+)\\s+\\[");
				Matcher matcher2 = pattern2.matcher(inputLine);
				
				if (matcher2.find()) {
					results[2] = matcher2.group(1);
					System.out.println(matcher2.group(1));
				}
				if (matcher.find()) {
					results[0] = matcher.group(1);
					results[1] = matcher.group(2);
					System.out.println(matcher.group(1) + "," + matcher.group(2));
					break;
				}
			} 
			in.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return results;
	}
	
	public static void main(String[] args) {
		PingTest pt = new PingTest("192.168.3.119");
		pt.ResultPing();
	}
}
