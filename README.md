procon-practice2014_API Client for Java
=======================
##使い方
<pre>
	ProconPracticeClient p = new ProconPracticeClient(1);
	
	// 問題取得用InputStreamを取得
	InputStream in = p.getGetInputStream();
	Scanner sc = new Scanner(in);
	System.out.println(sc.nextLine());
	System.out.println(sc.nextLine());
	System.out.println(sc.nextLine());
	System.out.println(sc.nextLine());
	p.closeGetInputStream();


	// 回答送信
	// 回答フォーマットに従って文字列にしたものを送る
	String ans = "2\r\n11\r\n21\r\nURDDLLURRDLLUURDDLUUD\r\n11\r\n40\r\nURDLURLDLURDRDLURUDLURDLLRDLUURRDLLURRDL\r\n";
	HashMap<String, String> map = p.sendAnswer(userName, password, ans);
	System.out.println("Status : " + map.get("status"));
	System.out.println(map.toString());
</pre>