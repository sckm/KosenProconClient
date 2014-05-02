package jp.scache.kosenprocon;


public class ProblemImage {
	/** 縦の分割数 **/
	public int verticalDivision;
	
	/** 横の分割数 **/
	public int horizontalDivision;
	
	/** 選択可能回数 **/
	public int choiseCount;
	
	/** 選択コスト変換レート **/
	public int choiseRate;
	
	/** 交換コスト変換レート **/
	public int replaceRate;
	
	/** 幅 **/
	public int width;
	
	/** 高さ **/
	public int height;
	
	/** 最大輝度 **/
	public int maxBrightness;

	/**
	 * 各座標のRGB値<br>
	 * (i,j)のデータを取得するにはimage[i*width+j][0〜2]<br>
	 * R: 0<br>
	 * G: 1<br>
	 * B: 2<br>
	 * 
	 */
	public int[][] image;
	
	public ProblemImage(int verticalDivision, int horizontalDivision, 
			int choiseCount, int choiseRate, int replaceRate, int maxBrightness, int[][] image, int width, int height) {
		this.verticalDivision = verticalDivision;
		this.horizontalDivision = horizontalDivision;
		this.choiseCount = choiseCount;
		this.choiseRate = choiseRate;
		this.replaceRate = replaceRate;
		this.maxBrightness = maxBrightness;
		this.image = image;
		this.width = width;
		this.height = height;
	}
	
	
}
