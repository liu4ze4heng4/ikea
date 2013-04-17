package com.fec.shop.gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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

import com.fec.shop.util.IkeaUtils;
public class StockinfoGui extends JFrame{
	JFrame frame;
	JPanel panel1;
	JPanel inputer, display1,  display2;
	JLabel text_id = new JLabel("宜家货号：");
	JLabel log = new JLabel("运行结果");
	JLabel error;
	JTextArea ProductId;
	JTextArea Log1 = new JTextArea(10, 30);
	JButton button, clear,exit;

	void init() {
		frame = new JFrame("趣活!库存&重量&体积查询器");
		panel1 = new JPanel(new BorderLayout());
		inputer = new JPanel(new FlowLayout());
		display1 = new JPanel(new BorderLayout());
//		display1.setLayout(new BoxLayout(display1, BoxLayout.Y_AXIS));
		display2 = new JPanel();
		display2.setLayout(new BoxLayout(display2, BoxLayout.Y_AXIS));
		ProductId = new JTextArea(1, 8);
		ProductId.setLineWrap(true);
		ProductId.requestFocus();
		button = new JButton("GET!");
		button.addActionListener(new SubmitListener());
		clear = new JButton("清除");
		clear.addActionListener(new ClearListener());
		exit = new JButton("退出");
		exit.addActionListener(new ExitListener());
		inputer.add(text_id);
		inputer.add(ProductId);
		inputer.add(button);
		display1.add(BorderLayout.NORTH,log);
		display1.add(BorderLayout.EAST,Log1);
		display1.add(BorderLayout.SOUTH,clear);

		panel1.add(BorderLayout.NORTH, inputer);
		panel1.add(BorderLayout.WEST, display1);
		panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel1);
		frame.pack();// 将选项卡加入到窗口中
		frame.setVisible(true);
	}

	public static void main(String[] args)

	{
		StockinfoGui gui = new StockinfoGui();
		gui.init();
	}

	class SubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Thread t = new Thread(new BackRun());
			t.start();
		}
	}

	class BackRun implements Runnable {
		public void run() {
			
			Log1.setText("进行中……");
			IkeaUtils.getStockInfo(ProductId.getText(),true,true,true);
			Log1.setText("库存信息："+"产品信息：\n");
			Log1.append(IkeaUtils.getQuantity()+"\n");
			BigDecimal weight = new BigDecimal(IkeaUtils.getWholeweight());
			Log1.append("货物总重："+weight.setScale(1,2)+"KG\n");
			BigDecimal size = new BigDecimal(IkeaUtils.getWholesize());

			if(IkeaUtils.getWholesize()<9999)
			Log1.append("货物总体积："+size.setScale(2,2)+"L\n");
			else
				Log1.append("无体积信息\n");

			Log1.append(IkeaUtils.getInfo()+"\n");
			if(IkeaUtils.getIsheavy()==0)
			Log1.append("货物类型：轻抛货\n");
			else if(IkeaUtils.getIsheavy()==1)
			Log1.append("货物类型：重货\n");
			else if(IkeaUtils.getIsheavy()==-1)
				Log1.append("货物类型未知\n");
				frame.repaint();
			
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
