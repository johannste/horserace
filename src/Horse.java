import javax.swing.*;
import java.awt.*;

public class Horse extends JPanel implements Runnable {

    private int left;
    private Integer id;
    private Image image;
    static private int victory = 0;

    Horse(int id) {
        this.id = id;
        image = new ImageIcon("resources/horses.gif").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, left, 0, image.getWidth(this),
                image.getHeight(this), this);
    }

    void setRestart() {
        this.left = 0;
        victory = 0;
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            if (left < 650) {
                left += Math.random() * 50;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                victory = id;
                if (victory != 0) {
                    Game.jtaLog.append(id + " 号马赢得比赛\n");
                    if (victory == Game.betNum) {
                        Game.jtaLog.append("您赢得了 $" + Game._jtfMoney + "\n");
                        Game.jlMoney.setText(Game._jlMoney + Game._jtfMoney + "");
                    } else {
                        Game.jtaLog.append("您输掉了 $" + Game._jtfMoney + "\n");
                        Game.jlMoney.setText(Game._jlMoney - Game._jtfMoney + "");
                    }
                    Game.jbRestart.setEnabled(true);
                    Game.jbExit.setEnabled(true);
                }
            }
            if (victory != 0) {
                flag = false;
            }
        }
    }
}
