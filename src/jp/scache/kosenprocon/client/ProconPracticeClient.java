package jp.scache.kosenprocon.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ProconPracticeClient {
	private final String BASE_URL = "http://procon2014-practice.oknct-ict.org/";

	/** GET Method用InputStream **/
	private InputStream responseInputStream = null;
	
	private HttpURLConnection postConnection = null;
	
	/** POST Method用OutStream **/
	private OutputStream postOutputStream = null;

	/** POST Method用InputStream **/
	private InputStream postInputStream = null;
	
	/** 問題番号 **/
	private int problemNumber;
	
	public static void main(String[] args) {
		ProconPracticeClient p = new ProconPracticeClient(1);
	}
	
	/**
	 * 
	 * @param number 問題番号
	 */
	public ProconPracticeClient(int number) {
		problemNumber = number;
	}

	/**
	 * 問題受信用InputStreamの取得
	 * @return
	 */
	public InputStream getGetInputStream() {
		try {
			URL url = new URL(BASE_URL + "problem/ppm/" + problemNumber);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			// リダイレクトを自動で許可
			connection.setInstanceFollowRedirects(true);

			responseInputStream = connection.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseInputStream;
	}
	
	/**
	 * 問題受信用InputStreamをclose
	 */
	public void closeGetInputStream(){
		try {
			responseInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解答送信用OutputStreamの取得
	 * @return
	 */
	public OutputStream getPostOutputStream(){
		try {
			URL url = new URL(BASE_URL + "solve/json/" + problemNumber);
			postConnection = (HttpURLConnection)url.openConnection();

			postConnection.setDoOutput(true);
			postConnection.setRequestMethod("POST");
			postConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			
			postOutputStream = postConnection.getOutputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return postOutputStream;
	}

	/**
	 * 解答送信用OutputStreamをclose
	 */
	public void closePostOutputStream(){
		try{
			postOutputStream.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 解答送信後の返答を受信するためのInputStreamを取得
	 * @return
	 */
	public InputStream getPostInputStream(){
		try {
			postInputStream = postConnection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return postInputStream;
	}
	
	/**
	 * 解答送信後の返答を受信するためのInputStreamをclose
	 */
	public void closePostInputStream(){
		try{
			postOutputStream.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
