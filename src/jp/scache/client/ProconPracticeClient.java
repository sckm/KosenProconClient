package jp.scache.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ProconPracticeClient {
	final String BASE_URL = "http://procon2014-practice.oknct-ict.org/problem/ppm/";
	HttpURLConnection inputConnection = null;
	InputStream responseInputStream = null;
	
	public static void main(String[] args) {
		ProconPracticeClient p = new ProconPracticeClient();
	}
	
	public ProconPracticeClient() {
	}

	public InputStream getInputStream(int number) {
		try {
			URL url = new URL(BASE_URL + number);
			inputConnection = (HttpURLConnection)url.openConnection();
			inputConnection.setRequestMethod("GET");
			// リダイレクトを自動で許可
			inputConnection.setInstanceFollowRedirects(true);

			responseInputStream = inputConnection.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseInputStream;
	}
	
	public void closeInputStream(){
		try {
			responseInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
