package com.fec.ikea;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class TabbedGui extends JFrame {
	JFrame frame;
	JPanel panel1, panel2;
	JPanel manual_input, display1, multi_input, display2;
	JTabbedPane tabbedPane;
	JLabel id = new JLabel("宜家货号：");
	JLabel logd1 = new JLabel("运行结果");
	JLabel error;
	JTextArea ProductId, StartId, EndId;
	JTextField multi_url = new JTextField(10);
	JTextArea Log1 = new JTextArea(10, 30);
	JTextArea Log2 = new JTextArea(10, 30);

	JButton button, exit, button_multi;

	void init() {
		// GUIPrintStream guiPrintStream = new GUIPrintStream(System.out, Log1);
		// System.setOut(guiPrintStream);
		JScrollPane sp2 = new JScrollPane(Log1);
		sp2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame = new JFrame("Ikea_o0");
		panel1 = new JPanel(new BorderLayout());
		manual_input = new JPanel();
		manual_input.setLayout(new BoxLayout(manual_input, BoxLayout.Y_AXIS));
		display1 = new JPanel();
		display1.setLayout(new BoxLayout(display1, BoxLayout.Y_AXIS));
		panel2 = new JPanel(new BorderLayout());
		multi_input = new JPanel();
		multi_input.setLayout(new BoxLayout(multi_input, BoxLayout.X_AXIS));
		display2 = new JPanel();
		display2.setLayout(new BoxLayout(display2, BoxLayout.Y_AXIS));
		ProductId = new JTextArea(10, 8);
		ProductId.setLineWrap(true);
		ProductId.requestFocus();
		JScrollPane sp1 = new JScrollPane(ProductId);
		sp1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane sp3 = new JScrollPane(Log2);
		sp3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tabbedPane = new JTabbedPane();
		tabbedPane.add("手动抓取", panel1); // 将面板添加到选项卡中
		tabbedPane.add("批量抓取", panel2);

		button = new JButton("抓取");
		button.addActionListener(new SubmitListener());
		JButton Clear1 = new JButton("清除");
		Clear1.addActionListener(new ClearListener());
		exit = new JButton("退出");
		exit.addActionListener(new ExitListener());
		button_multi = new JButton("抓取");
		button_multi.addActionListener(new MultiListener());
		manual_input.add(id);
		manual_input.add(sp1);
		manual_input.add(button);
		display1.add(logd1);
		display1.add(sp2);
		display1.add(Clear1);
		multi_input.add(multi_url);
		multi_input.add(button_multi);
		display2.add(sp3);
		// display1.add(sp2);
		display2.add(exit);
		panel1.add(BorderLayout.WEST, manual_input);
		panel1.add(BorderLayout.EAST, display1);
		panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel2.add(BorderLayout.NORTH, multi_input);
		panel2.add(BorderLayout.SOUTH, display2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(tabbedPane);
		frame.pack();// 将选项卡加入到窗口中
		frame.setVisible(true);
	}

	public static void main(String[] args)

	{
		TabbedGui gui = new TabbedGui();
		gui.init();
	}

	class SubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Thread t = new Thread(new BackRun());
			t.start();
			GUIPrintStream guiPrintStream = new GUIPrintStream(System.out, Log1);
			System.setOut(guiPrintStream);
		}
	}

	class BackRun implements Runnable {
		public void run() {
			IkeaFilter demo = new IkeaFilter();
			try {
				String tmp = ProductId.getText();
				// System.out.print(tmp+"\n");
				ArrayList<String> tmps = new ArrayList<String>();
				String[] tmplist = tmp.split("\n");
				for (int j = 0; j < tmplist.length; j++)
					tmps.add(tmplist[j]);
				for (int i = 0; i < tmps.size(); i++) {
					demo.SaveFile(tmps.get(i), ".\\products\\");
					demo.SavePic(tmps.get(i), 4, ".\\products\\");

				}

				demo.saveCSV(tmps, new File("products.csv"), ".\\products\\");
				// error.setText("成功获取！");
				frame.repaint();
				// demo.captureJavascript("107818590577");
			} catch (Exception e) {
				e.printStackTrace();
				// System.out.println("123b");
				frame.repaint();
			}
		}
	}

	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}

	class ClearListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// System.out.print("ClearButtonTester");
			Log1.setText("");
		}
	}

	class MultiListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Thread t2 = new Thread(new MultiRun());
			t2.start();
			GUIPrintStream guiPrintStream = new GUIPrintStream(System.out, Log2);
			System.setOut(guiPrintStream);
		}
	}

	class MultiRun implements Runnable {
		public void run() {
			MultiFilter a1 = new MultiFilter();
			String tmp = multi_url.getText();
			try {
				a1.go(tmp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class GUIPrintStream extends PrintStream {
	private JTextArea text;

	public GUIPrintStream(OutputStream out, JTextArea component) {
		super(out);
		this.text = component;
	}

	public void write(int b) {
		text.append(String.valueOf((char) b));
	}

	public void write(byte b[]) throws IOException {
		text.append(new String(b));
	}

	public void write(byte b[], int off, int len) {
		text.append(new String(b, off, len));
	}
}
