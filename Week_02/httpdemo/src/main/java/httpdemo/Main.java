package httpdemo;

import java.io.BufferedReader;
import java.io.IOException;

import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Main {

	private static OkHttpClientFactory okHttpClientFactory = new OkHttpClientFactory(new ClientPropertie());
	
	
	public static void main(String[] args) throws IOException {
		printf(execute("http://www.baidu.com"));
	}
	
	private static Response execute(String url) throws IOException {
		Request request = buildHttpRequest(url);
		Response resp = okHttpClientFactory.create().newCall(request).execute();
		return resp;
	}
	
	private static void printf(Response resp) throws IOException {
		ResponseBody body = resp.body(); 
		BufferedReader reader = new BufferedReader(body.charStream());
		String line = null;
		while (null != (line = reader.readLine())) {
			System.out.println(line);
		}
		
	}
	
	private static Request buildHttpRequest(String url) {
		Builder reqBuilder = new Request.Builder().url(url);
		return reqBuilder.build();
	}
	
	
}
