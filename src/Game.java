import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game extends JFrame implements ActionListener {
    static private Horse horse1, horse2, horse3, horse4;
    static JTextArea jtaLog = new JTextArea();

    static JLabel jlMoney = new JLabel("3,000,000");
    static int _jlMoney = 0;
    static int _jtfMoney = 0;

    static int betNum = 0;
    private JRadioButton jrb1 = new JRadioButton("1号马");
    private JRadioButton jrb2 = new JRadioButton("2号马");
    private JRadioButton jrb3 = new JRadioButton("3号马");
    private JRadioButton jrb4 = new JRadioButton("4号马");

    private JButton jbStart = new JButton("开始");
    static JButton jbRestart = new JButton("再来一次");
    static JButton jbExit = new JButton("退出");

    private Thread tHorse1, tHorse2, tHorse3, tHorse4;

    private Game() {

        this.setTitle("赛马游戏");
        this.setSize(800, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Container cBody = this.getContentPane();

        cBody.setLayout(new GridLayout(2, 1));

        JPanel jpHorse = new JPanel();
        cBody.add(jpHorse);

        jpHorse.setLayout(new GridLayout(4, 1));
        horse1 = new Horse(1);
        horse2 = new Horse(2);
        horse3 = new Horse(3);
        horse4 = new Horse(4);
        jpHorse.add(horse1);
        jpHorse.add(horse2);
        jpHorse.add(horse3);
        jpHorse.add(horse4);
        horse1.setBackground(Color.white);
        horse2.setBackground(Color.white);
        horse3.setBackground(Color.white);
        horse4.setBackground(Color.white);

        JPanel jpBottom = new JPanel();
        cBody.add(jpBottom);

        jpBottom.setLayout(new GridLayout(2, 1));

        JPanel jpUpperBottom = new JPanel();
        jpBottom.add(jpUpperBottom);

        jpUpperBottom.setLayout(new GridLayout(1, 4));

        JPanel jpSelection = new JPanel();
        jpUpperBottom.add(jpSelection);
        jpSelection.add(new JLabel("押注:"));

        JPanel jpRadioButton = new JPanel();
        jpUpperBottom.add(jpRadioButton);
        jpRadioButton.setLayout(new GridLayout(4, 1));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(jrb1);
        buttonGroup.add(jrb2);
        buttonGroup.add(jrb3);
        buttonGroup.add(jrb4);

        jrb1.addActionListener(this);
        jrb2.addActionListener(this);
        jrb3.addActionListener(this);
        jrb4.addActionListener(this);

        jpRadioButton.add(jrb1);
        jpRadioButton.add(jrb2);
        jpRadioButton.add(jrb3);
        jpRadioButton.add(jrb4);

        JPanel jpMoney = new JPanel();
        jpUpperBottom.add(jpMoney);
        jpMoney.add(new JLabel("现有金钱 $"));
        jpMoney.add(jlMoney);

        JPanel jpPlay = new JPanel();
        jpUpperBottom.add(jpPlay);
        jpPlay.setLayout(new GridLayout(4, 1));
        JTextField jtfMoney = new JTextField();
        jtfMoney.addKeyListener(new KeyAdapter() {
                                    public void keyTyped(KeyEvent e) {
                                        int keyChar = e.getKeyChar();
                                        if (!(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)) {
                                            e.consume(); //关键，屏蔽掉非法输入
                                        }
                                    }
                                }
        );
        jbStart.addActionListener(e -> {
            _jlMoney = Integer.parseInt(jlMoney.getText().replaceAll(",", ""));
            if (0 != jtfMoney.getText().length() && 9 >= jtfMoney.getText().length()) {
                _jtfMoney = Integer.parseInt(jtfMoney.getText());
            }
            if (betNum == 0) {
                JOptionPane.showMessageDialog(null, "请选择马匹", "请注意", JOptionPane.WARNING_MESSAGE);
            } else if (0 >= _jtfMoney || _jlMoney < _jtfMoney) {
                JOptionPane.showMessageDialog(null, "您投入的金钱不合理", "请注意", JOptionPane.WARNING_MESSAGE);
            } else {
                jtfMoney.setEnabled(false);
                jbStart.setEnabled(false);
                jbRestart.setEnabled(false);
                jbExit.setEnabled(false);
                jrb1.setEnabled(false);
                jrb2.setEnabled(false);
                jrb3.setEnabled(false);
                jrb4.setEnabled(false);

                tHorse1 = new Thread(horse1);
                tHorse2 = new Thread(horse2);
                tHorse3 = new Thread(horse3);
                tHorse4 = new Thread(horse4);

                tHorse1.start();
                tHorse2.start();
                tHorse3.start();
                tHorse4.start();
            }
        });
        jbRestart.addActionListener(e -> {
            jtfMoney.setEnabled(true);
            jbStart.setEnabled(true);
            jrb1.setEnabled(true);
            jrb2.setEnabled(true);
            jrb3.setEnabled(true);
            jrb4.setEnabled(true);
            jtaLog.setText("");
            horse1.setRestart();
            horse2.setRestart();
            horse3.setRestart();
            horse4.setRestart();
            betNum = 0;
            buttonGroup.clearSelection();
            jtfMoney.setText("");
            _jtfMoney = 0;
        });
        jbExit.addActionListener(e -> {
            System.exit(0);
        });
        jpPlay.add(jtfMoney);
        jpPlay.add(jbStart);
        jpPlay.add(jbRestart);
        jpPlay.add(jbExit);

        jpBottom.add(jtaLog);
        jtaLog.setBackground(new Color(238, 238, 238));

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Game();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jrb1) {
            betNum = 1;
        }
        if (e.getSource() == jrb2) {
            betNum = 2;
        }
        if (e.getSource() == jrb3) {
            betNum = 3;
        }
        if (e.getSource() == jrb4) {
            betNum = 4;
        }
        jtaLog.setText("");
        jtaLog.append("您已选择 " + betNum + " 号马\n比赛即将开始\n");
    }
}
