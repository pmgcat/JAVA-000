package nuul;

import nuul.server.HttpboundServer;

public class NuulLauncher {

	public static void main(String[] args) {
        HttpboundServer server = new HttpboundServer(9998);
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
}
