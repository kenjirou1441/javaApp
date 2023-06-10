import java.awt.BorderLayout; // BorderLayoutをインポート
import java.awt.Container; // Containerをインポート
import java.awt.FlowLayout; // FlowLayoutをインポート
import java.awt.event.ActionEvent; // ActionEventをインポート
import java.awt.event.ActionListener; // ActionListenerをインポート
import java.io.BufferedReader; // BufferedReaderをインポート
import java.io.BufferedWriter; // BufferedWriterをインポート
import java.io.FileReader; // FileReaderをインポート
import java.io.FileWriter; // FileWriterをインポート
import java.io.IOException; // IOExceptionをインポート
import java.io.PrintWriter; // PrintWriterをインポート
import java.util.ArrayList; // ArrayListをインポート

import javax.swing.BoxLayout; // BoxLayoutをインポート
import javax.swing.JButton; // JButtonをインポート
import javax.swing.JFrame; // JFrameをインポート
import javax.swing.JLabel; // JLabelをインポート
import javax.swing.JPanel; // Jpanelをインポート
import javax.swing.JTextField; // JTextFieldをインポート

public class Search_Book extends JFrame implements ActionListener {
	private Container cntnr; // コンテナを宣言
	private JButton button1, button2, button3, button4; // ボタンを宣言
	private JPanel jpanel1, jpanel2, jpanel3, jpanel4, jpanel5, jpanel6, jpanel7, jpanel8, jpanel9, jpanel10; // japanelを宣言
	private FlowLayout flow; // FlowLayoutを宣言
	private JTextField text1, text2, text3, text4, text5, text6; // テキストフィールド
	private JLabel label1, label2, label3, label4, label5, label6, label7; // ラベル
	private ArrayList<String> array = new ArrayList<String>(); // ArrayListを宣言
	private int indexnum = 0;
	private ArrayList<String> search = new ArrayList<String>(); // ArrayListを宣言
	private int index = 0; // int型変数を宣言

	// コンストラクタ
	public Search_Book(String title) {
		super(title); // タイトルの設定
		setBounds(50, 50, 500, 400); // 位置とサイズの設定
		// フレームを閉じたらプログラム終了
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		// 自クラスのインスタンスを生成
		Search_Book frame;
		frame = new Search_Book("Search_Book");
		frame.defineVal(); // 表示の準備
		frame.setVisible(true); // 窓を表示
	}

	// 要素読み込み用メソッド
	public void elem() {
		BufferedReader fin = null; // 読み込み用バッファ
		try {
			fin = new BufferedReader(new FileReader("booklist.csv")); // booklistファイル
			array.clear(); // arrayの要素を全て消去する
			String line; // 読み込み用文字列を宣言
			while ((line = fin.readLine()) != null) { // lineがnullになるまで
				String[] splitStr1 = line.split(","); // ,区切りで保存
				for (String yoso : splitStr1) {// splitStrの文字列をyosoに
					array.add(yoso); // arrayにyosoに代入
				}
			}
		} catch (IOException e) { // エラー発生時
			System.out.println("読み込みエラーです"); // メッセージ表示
		} finally { // 後処理
			try { // 後処理にも例外が発生する場合がある
				fin.close(); // 読み込みファイルを閉じる
			} catch (Exception e) { // 後処理時の例外捕捉
				System.out.println("終了処理エラーです"); // メッセージ表示
			}
		}
	}

	// 文字列を検索しウィンドウに出力するメソッド
	public void find() {
		String query = text1.getText(); // 検索文字列を宣言
		search.clear(); // searchの要素を全て消去する
		index = 0; // indexに0を代入する
		for (int i = 0; i < (int) array.size() / 5; i++) { // arrayの要素数を5で割った値の文だけ繰り返す
			if ((array.get(i * 5).indexOf(query) != -1 || array.get(i * 5 + 1).indexOf(query) != -1
					|| array.get(i * 5 + 2).indexOf(query) != -1 || array.get(i * 5 + 3).indexOf(query) != -1
					|| array.get(i * 5 + 4).indexOf(query) != -1)) { // arrayに検索文字列を含む時
				search.add(array.get(i * 5)); // searchにi*5番目のarrayの要素を追加する
				search.add(array.get(i * 5 + 1)); // searchにi*5+1番目のarrayの要素を追加する
				search.add(array.get(i * 5 + 2)); // searchにi*5+2番目のarrayの要素を追加する
				search.add(array.get(i * 5 + 3)); // searchにi*5+3番目のarrayの要素を追加する
				search.add(array.get(i * 5 + 4)); // searchにi*5+4番目のarrayの要素を追加する
				index += 1; // indexに1を加算
				indexnum = 1; // indexnumに1を代入
			}
		}
		Access instE = new Access(search.get(0), search.get(1), search.get(2), search.get(3), search.get(4)); // インスタンス生成
		text2.setText(instE.getTitle()); // テキストフィールドにタイトルを表示
		text3.setText(instE.getEditor()); // テキストフィールドに著者を表示
		text4.setText(instE.getCompany()); // テキストフィールドに出版社を表示
		text5.setText(instE.getYear()); // テキストフィールドに出版年を表示
		text6.setText(instE.getIsbn()); // テキストフィールドにISBNを表示
	}

	// 検索保存メソッド
	public void save() {
		PrintWriter fout=null; // 読み込み用バッファ
		try {
			 fout = new PrintWriter(new BufferedWriter(new FileWriter("result.csv"))); // 出力用ファイル
			for (int i = 0; i < (int) search.size() / 5; i++) { // searchの要素を5で割った値の分繰り返す
				Access instE = new Access(search.get((i) * 5), search.get((i) * 5 + 1),
						search.get((i) * 5 + 2), search.get(i * 5 + 3),
						search.get((i) * 5 + 4)); // インスタンス生成
				fout.println(instE.getTitle() + "," + instE.getEditor() + "," + instE.getCompany() + ","
						+ instE.getYear() + "," + instE.getIsbn()); // ,区切りでファイルに出力
			}
		} catch (IOException i) { // エラー発生時
			System.out.println("エラーです"); // メッセージ表示
		}finally { // 後処理
			try { // 後処理にも例外が発生する場合がある
				fout.close(); // 読み込みファイルを閉じる
			} catch (Exception e) { // 後処理時の例外捕捉
				System.out.println("終了処理エラーです"); // メッセージ表示
			}
		}

	}

	private void defineVal() {
		cntnr = getContentPane(); // 表示領域を取得
		flow = new FlowLayout(); // 11設定するレイアウトのインスタンス
		cntnr.setLayout(new BorderLayout()); // レイアウト設定
		jpanel1 = new JPanel(); // jpanelインスタンス
		jpanel1.setLayout(flow); // レイアウト設定
		jpanel2 = new JPanel(); // jpanelインスタンス
		jpanel2.setLayout(flow); // レイアウト設定
		jpanel3 = new JPanel(); // jpanelインスタンス
		jpanel3.setLayout(flow); // レイアウト設定
		jpanel4 = new JPanel(); // jpanelインスタンス
		jpanel4.setLayout(flow); // レイアウト設定
		jpanel5 = new JPanel(); // jpanelインスタンス
		jpanel5.setLayout(flow); // レイアウト設定
		jpanel6 = new JPanel(); // jpanelインスタンス
		jpanel6.setLayout(flow); // レイアウト設定
		jpanel7 = new JPanel(); // jpanelインスタンス
		jpanel7.setLayout(flow); // レイアウト設定
		jpanel8 = new JPanel(); // jpanelインスタンス
		jpanel8.setLayout(flow); // レイアウト設定
		jpanel9 = new JPanel(); // jpanelインスタンス
		jpanel9.setLayout(new BoxLayout(jpanel9, BoxLayout.Y_AXIS)); // レイアウト設定
		jpanel10 = new JPanel(); // jpanelインスタンス
		jpanel10.setLayout(new BoxLayout(jpanel10, BoxLayout.Y_AXIS)); // レイアウト設定
		label1 = new JLabel("検索語:"); // 検索語を表示
		jpanel1.add(label1); // jpanel1にlabel1を追加
		text1 = new JTextField(20); // テキストフィールドインスタンス
		jpanel1.add(text1); // jpanel1にtext1を追加
		button1 = new JButton("検索"); // 検索ボタン
		jpanel1.add(button1); // jpanel1にbutton1を追加
		label2 = new JLabel("結果:"); // 結果を表示
		jpanel8.add(label2); // jpanel8にlabel2を追加
		label3 = new JLabel("タイトル:"); // タイトルを表示
		jpanel2.add(label3); // jpanel2にlabel3を追加
		text2 = new JTextField(20); // テキストフィールドインスタンス
		jpanel2.add(text2); // jpanel2にtext2を追加
		label4 = new JLabel("著者:"); // 著者を表示
		jpanel3.add(label4); // jpanel3にlabel4を代入
		text3 = new JTextField(20); // テキストフィールドインスタンス
		jpanel3.add(text3); // jpanel3にtext3を代入
		label5 = new JLabel("出版社:"); // 出版社を表示
		jpanel4.add(label5); // jpanel4にlabel5を代入
		text4 = new JTextField(20); // テキストフィールドインスタンス
		jpanel4.add(text4); // jpanel4にtext4を代入
		label6 = new JLabel("出版年:"); // 出版年を表示
		jpanel5.add(label6); // jpanel5にlabel5を代入
		text5 = new JTextField(20); // テキストフィールドインスタンス
		jpanel5.add(text5); // japne5にtext5を代入
		label7 = new JLabel("ISBN:"); // ISBNを表示
		jpanel6.add(label7); // jpanel6にlabel7を代入
		text6 = new JTextField(20); // テキストフィールドインスタンス
		jpanel6.add(text6); // jpanel6にtext6を代入
		button2 = new JButton("<"); // <ボタン
		jpanel7.add(button2); // jpanel7にボタン2を代入
		button3 = new JButton(">"); // >ボタン
		jpanel7.add(button3); // jpanel7にボタン3を代入
		button4 = new JButton("検索結果保存"); // クリアボタン
		jpanel7.add(button4); // jpanel7にボタン4を代入
		jpanel9.add(jpanel1); // jpanel9にjpanel1を代入
		jpanel10.add(jpanel8); // jpanel10にjpanel8を代入
		jpanel10.add(jpanel2); // jpanel10にjpanel2を代入
		jpanel10.add(jpanel3); // jpanel10にjpanel3を代入
		jpanel10.add(jpanel4); // jpanel10にjpanel4を代入
		jpanel10.add(jpanel5); // jpanel10にjpanel5を代入
		jpanel10.add(jpanel6); // jpanel10にjpanel6を代入
		jpanel10.add(jpanel7); // jpanel10にjpanel7を代入
		cntnr.add(jpanel9, BorderLayout.NORTH); // 上部にjpanel9を実装
		cntnr.add(jpanel10, BorderLayout.CENTER); // 中央にjpanel10を実装
		button1.addActionListener(this); // ボタン1の入力を感知
		button2.addActionListener(this); // ボタン2の入力を感知
		button3.addActionListener(this); // ボタン3の入力を感知
		button4.addActionListener(this); // ボタン4の入力を感知
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1) { // ボタン1を感知したら
			elem(); // 要素生成メソッドを呼び出し
			find(); // countを引数にfindメソッドを呼び出す
		} else if (e.getSource() == button2) { // ボタン2を感知したら
			if (indexnum == 0) { // inddexnumが0の時、何もしない
			} else if (2 <= indexnum) { // indexnumが2以上なら
				indexnum--;
				Access instE = new Access(search.get((indexnum - 1) * 5), search.get((indexnum - 1) * 5 + 1),
						search.get((indexnum - 1) * 5 + 2), search.get((indexnum - 1) * 5 + 3),
						search.get((indexnum - 1) * 5 + 4)); // インスタンス生成
				text2.setText(instE.getTitle()); // テキストフィールドにタイトルを表示
				text3.setText(instE.getEditor()); // テキストフィールドに著者を表示
				text4.setText(instE.getCompany()); // テキストフィールドに出版社を表示
				text5.setText(instE.getYear()); // テキストフィールドに出版年を表示
				text6.setText(instE.getIsbn()); // テキストフィールドにISBNを表示
			}
		} else if (e.getSource() == button3) { // ボタン3を感知したら 
			if (indexnum == 0) { // indexnumが0の時
			} else if (index > indexnum) { // indexがindexnumより大きい時
				indexnum++; // indexnumをインクリメント
				Access instE = new Access(search.get((indexnum - 1) * 5), search.get((indexnum - 1) * 5 + 1),
						search.get((indexnum - 1) * 5 + 2), search.get((indexnum - 1) * 5 + 3),
						search.get((indexnum - 1) * 5 + 4)); // インスタンス生成
				text2.setText(instE.getTitle()); // テキストフィールドにタイトルを表示
				text3.setText(instE.getEditor()); // テキストフィールドに著者を表示
				text4.setText(instE.getCompany()); // テキストフィールドに出版社を表示
				text5.setText(instE.getYear()); // テキストフィールドに出版年を表示
				text6.setText(instE.getIsbn()); // テキストフィールドにISBNを表示
			}
		} else if (e.getSource() == button4) { // ボタン4を感知したら
			save(); // 検索保存メソッドを呼び出し
		}
	}
}