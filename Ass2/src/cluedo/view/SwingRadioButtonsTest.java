package cluedo.view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SwingRadioButtonsTest extends JApplet {
  private JTextField t = new JTextField(15);

  private ButtonGroup g = new ButtonGroup();

  private JRadioButton rb1 = new JRadioButton("one", false),
      rb2 = new JRadioButton("two", false), rb3 = new JRadioButton(
          "three", false);

  private ActionListener al = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      t.setText("Radio button "
          + ((JRadioButton) e.getSource()).getText());
    }
  };

  public void init() {
    rb1.addActionListener(al);
    rb2.addActionListener(al);
    rb3.addActionListener(al);
    g.add(rb1);
    g.add(rb2);
    g.add(rb3);
    t.setEditable(false);
    Container cp = getContentPane();
    cp.setLayout(new FlowLayout());
    cp.add(t);
    cp.add(rb1);
    cp.add(rb2);
    cp.add(rb3);
  }

  public static void main(String[] args) {
    run(new SwingRadioButtonsTest(), 200, 100);
  }

  public static void run(JApplet applet, int width, int height) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(applet);
    frame.setSize(width, height);
    applet.init();
    applet.start();
    frame.setVisible(true);
  }
} 
