package jp.scache.kosenprocon;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import jp.scache.kosenprocon.client.ProconPracticeClient;

public class FragmentOfMemory {
	ProblemImage problemImage;
	
	public static void main(String[] args) {
		FragmentOfMemory p = new FragmentOfMemory();
	}

	
	public FragmentOfMemory() {
		problemImage = getProblemImage(1);
		solve();
	}

	public ProblemImage getProblemImage(int number){
		ProconPracticeClient client = new ProconPracticeClient();
		InputStream in = client.getInputStream(number);

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
			client.closeInputStream();	
		}	
		
		return problemImage;
	}
	
	public void solve() {
		// do something
	}

}
