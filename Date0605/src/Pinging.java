import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Pinging extends Thread {
	
	private Object[] msg;
	private String ip;
	
	public Pinging(String ip) {
		//this.ip = "192.168.3." + ip;
		this.ip = ip;
		msg = new Object[4];
	}
	
	@Override
	public void run() {
		BufferedReader br = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("ping -a " + ip);
			msg[0] = ip;
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String lineInput = null;
			while ((lineInput = br.readLine()) != null) {
				if (lineInput.indexOf("[") >= 0) {
					msg[3] = lineInput.substring(5, lineInput.indexOf("[") - 1);
				}
				if (lineInput.indexOf("ms") >= 0) {
					msg[1] = lineInput.substring(lineInput.indexOf("ms") - 1, lineInput.indexOf("ms") + 2);
					msg[2] = lineInput.substring(lineInput.indexOf("TTL=") + 4, lineInput.indexOf("TTL=") + 7);
					break;
				}
			} 
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				br.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}	
		}
	}
	public Object[] getMsg() {
		try {
			join();
		} catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return msg;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pinging[] pg = new Pinging[254];
		String ip = "192.168.3.119";
		for(int i=0; i<254; i++) {
			pg[i] = new Pinging(ip+(i+1));
			pg[i].start();
		}
		for(int i=0; i<254; i++) {
			Object[] msg = pg[i].getMsg();
			if(msg==null) {
				System.out.println(msg[0] + "die");
			} else {
				System.out.println(msg[0] + "alive");
			}
		}
	}

}
