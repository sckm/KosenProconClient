package jp.scache.kosenprocon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import jp.scache.kosenprocon.client.ProconPracticeClient;

public class FragmentOfMemory {
	ProblemImage problemImage;
	ProconPracticeClient client;
	
	public static void main(String[] args){
		FragmentOfMemory p = new FragmentOfMemory();
//		p.problemImage = p.getProblemImage(1);
		p.solve();
	}

	public FragmentOfMemory() {
		client = new ProconPracticeClient(1);
	}

	public ProblemImage getProblemImage(){
		InputStream in = client.getGetInputStream();

		ProblemImage problemImage = null;
		try {
			// ヘッダの読み取り
			int newLineCount = 0;
			byte[] header = new byte[1024];
			int headerIndex = 0;
			while(newLineCount < 6){
				header[headerIndex] = (byte)in.read();
				if(header[headerIndex]==0x000a)
					newLineCount++;
				headerIndex++;
			}
			System.out.println(new String(header));
			Scanner sc = new Scanner(new String(header));
			sc.next();	// P6
			sc.next();	// #
			int verticalDivision = sc.nextInt();
			int horizontalDivision = sc.nextInt();
			sc.next();	// #
			int choiseCount = sc.nextInt();
			sc.next();	// #
			int choiseRate = sc.nextInt();
			int replaceRate = sc.nextInt();
			int width = sc.nextInt();
			int height = sc.nextInt();
			int maxBrightness = sc.nextInt();
			
			// 画像データの読み取り
			int[][] imageData = new int[height*width][3];
			for(int i=0;i<height;i++){
				for(int j=0;j<width;j++){
					for(int k=0;k<3;k++){
						imageData[i*width+j][k] = in.read();
						if(imageData[i*width+j][k] == -1)
							throw new Exception("not enough response");
					}
				}
			}
			
			problemImage = new ProblemImage(verticalDivision, horizontalDivision,
					choiseCount, choiseRate, replaceRate, maxBrightness, imageData, width, height);
			
		}catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			client.closeGetInputStream();	
		}	
		
		return problemImage;
	}
	
	public void solve() {
		// do something

		sendAnswer();
	}
	
	public void sendAnswer(){
		OutputStream out = client.getPostOutputStream();
		PrintWriter pw = new PrintWriter(out);
		
		String s = "codenumber=1&username=testkpcp&passwd=1234&answer_text=2\r\n11\r\n21\r\nURDDLLURRDLLUURDDLUUD\r\n11\r\n40\r\nURDLURLDLURDRDLURUDLURDLLRDLUURRDLLURRDL\r\n";
		pw.print(s);
		pw.flush();
		pw.close();

		client.closePostOutputStream();
		getResponse();
	}

	public HashMap<String, String> getResponse(){
		try {
			InputStream in = client.getPostInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String s;
			while((s=br.readLine()) != null)
				System.out.println(s);

			br.close();
			client.closePostInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return new HashMap<String, String>();
	}

}
