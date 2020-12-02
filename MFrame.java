package ch20;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*현재 여기서 쓰인것들 : 상속, 오버워딩, this, 익명클래스*/
public class MFrame extends Frame {
	
	/*public MFrame() {
		setSize(300, 300);
		setBackground(new Color(220,220,220));
		setLayout(new FlowLayout());
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	 */
	public MFrame() {
		this(300,300,new Color(220,220,220));
	}
	public MFrame(int w, int h) {
		this(w,h, new Color(220,220,220));
	}
	public MFrame(Color c) {
		this(300,300, c);
	}
	public MFrame(int w, int h, Color c) {
		setSize(w, h);
		setBackground(c);
		//setLayout(new FlowLayout());
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		validate();//추가시켰음. 기능?? 새로고침의 기능을 내포하고 있다. 
	}
}//--class






