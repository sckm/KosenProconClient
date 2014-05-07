package jp.scache.kosenprocon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import jp.scache.kosenprocon.client.ProconPracticeClient;

public class FragmentOfMemory {
	ProblemImage problemImage;
	ProconPracticeClient client;
	
	public static void main(String[] args){
		FragmentOfMemory p = new FragmentOfMemory();
		long startTime = System.currentTimeMillis();
		p.problemImage = p.getProblemImage();
		long endTime = System.currentTimeMillis();
		System.out.println("Time: " + (endTime - startTime) / 1000.0 + " seconds");
		

		startTime = System.currentTimeMillis();
		p.solve();
		endTime = System.currentTimeMillis();
		System.out.println("Time: " + (endTime - startTime) / 1000.0 + " seconds");
		
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

		//String ans = "2\r\n11\r\n21\r\nURDDLLURRDLLUURDDLUUD\r\n11\r\n40\r\nURDLURLDLURDRDLURUDLURDLLRDLUURRDLLURRDL\r\n";
		//HashMap<String, String> map = client.sendAnswer("testkpcp", "1234", ans);
		//System.out.println(map.toString());
		int[] field = { 0, 7, 6, 8, 3, 1, 2, 4, 5};
//		int[] field = { 7, 6, 3, 10, 11, 8, 12, 13, 9, 14, 4, 0, 15, 1, 2, 5};
		calcRestoationCost2(problemImage.choiseCount, problemImage.replaceRate, field, problemImage.horizontalDivision, problemImage.verticalDivision);
	}

	// 幅優先探索で全探索 4*4ぐらいからメモリが足りなくなる
/*
	private String calcRestoationCost(int choiseRate, int replaceRate, int[] field, int w, int h){
		System.out.println(w+" " + h);
		HashMap<String, Integer> costMap = new HashMap<String, Integer>();
		
		// 1つだけ選択する時
		int[] dx = {-1, 0, 1, 0};
		int[] dy = {0, -1, 0, 1};
		LinkedList<State> queue = new LinkedList<State>();
		String target = "";
		String start = "";
		for(int i=0;i<w*h;i++){
			start += (char)i;
			target += (char)field[i];
		}
		costMap.put(start, 0);
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				State startState = new State(j, i, start, 0, 0);
				queue.offer(startState);
				
				while(!queue.isEmpty()){
					State state = queue.poll();
					for(int k=0;k<4;k++){
						int nx = state.x + dx[k];
						int ny = state.y + dy[k];
						
						if(0<=nx && nx<w && 0<=ny && ny<h){
							StringBuilder builder = new StringBuilder(state.state);
							char temp = builder.charAt(state.y*w+state.x);
							builder.setCharAt(state.y*w+state.x, builder.charAt(ny*w+nx));
							builder.setCharAt(ny*w+nx, temp);
							
							State nextState = new State(nx, ny, builder.toString(), state.choiceCost, state.replaceCost+replaceRate); 
							int nextCost = nextState.choiceCost + nextState.replaceCost;

							if(nextState.state.equals(target)){
								int lastCost = Integer.MAX_VALUE;
								if(costMap.containsKey(nextState.state)){
									lastCost = costMap.get(nextState.state);
								}
								costMap.put(nextState.state, Math.min(nextCost, lastCost));
								continue;
							}

							// まだ探索していないなら探索を続ける							
							if(costMap.containsKey(nextState.state)){
								int lastCost = costMap.get(nextState.state);
								
								// 以前のものよりコストが低いなら探索を続ける
								if(nextCost < lastCost){
									costMap.put(nextState.state, nextCost);
									queue.offer(nextState);
								}
							}else{
								costMap.put(nextState.state, nextCost);
								queue.offer(nextState);
							}
						}
					}
				}

				System.out.println(costMap.values().size());
			}
		}
		System.out.println(costMap.get(target));
		return start;	
	}
*/
	
	private String calcRestoationCost2(int choiseRate, int replaceRate, int[] field, int w, int h){
		HashMap<String, Integer> costMap = new HashMap<String, Integer>();
		
		// 1つだけ選択する時
		int[] dx = {-1, 0, 1, 0};
		int[] dy = {0, -1, 0, 1};
		LinkedList<State> queue = new LinkedList<State>();
		String target = "";
		String start = "";
		for(int i=0;i<w*h;i++){
			start += (char)i;
			target += (char)field[i];
		}
		costMap.put(start, 0);
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				State startState = new State(j, i, start, 0, 0);
				queue.offer(startState);
				
				while(!queue.isEmpty()){
					State state = queue.poll();
					for(int k=0;k<4;k++){
						int nx = state.x + dx[k];
						int ny = state.y + dy[k];
						
						if(0<=nx && nx<w && 0<=ny && ny<h){
							StringBuilder builder = new StringBuilder(state.state);
							char temp = builder.charAt(state.y*w+state.x);
							builder.setCharAt(state.y*w+state.x, builder.charAt(ny*w+nx));
							builder.setCharAt(ny*w+nx, temp);
							
							State nextState = new State(nx, ny, builder.toString(), state.choiceCost, state.replaceCost+replaceRate); 
							int nextCost = nextState.choiceCost + nextState.replaceCost;

							if(nextState.state.equals(target)){
								int lastCost = Integer.MAX_VALUE;
								if(costMap.containsKey(nextState.state)){
									lastCost = costMap.get(nextState.state);
								}
								costMap.put(nextState.state, Math.min(nextCost, lastCost));
								continue;
							}

							// まだ探索していないなら探索を続ける							
							if(costMap.containsKey(nextState.state)){
								int lastCost = costMap.get(nextState.state);
								
								// 以前のものよりコストが低いなら探索を続ける
								if(nextCost < lastCost){
									costMap.put(nextState.state, nextCost);
									queue.offer(nextState);
								}
							}else{
								costMap.put(nextState.state, nextCost);
								queue.offer(nextState);
							}
						}
					}
				}

				System.out.println(costMap.values().size());
			}
		}
		System.out.println(costMap.get(target));
		return start;	
	}
	
	
	private class State{
		public int x;
		public int y;
		public String state;
		public int choiceCost;
		public int replaceCost;
		
		public State(int x, int y, String state, int choiceCost, int replaceCost){
			this.x = x;
			this.y = y;
			this.state = state;
			this.choiceCost = choiceCost;
			this.replaceCost = replaceCost;
		}
	}
	
}
