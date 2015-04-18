package com.agoraio.btcapp;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-5-10
 * Time: 9:43:42
 */
public class GridBagLayOutTest2 {
    public static void main(String[] args) {
        JFrame jf = new JFrame("界面");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(240, 180);


        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        double screenWith = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();
        System.out.println("screenWith:" + screenWith);
        System.out.println("screenHeight:" + screenHeight);
        int x = (int) ((screenWith - jf.getWidth()) / 2);
        int y = (int) ((screenHeight - jf.getHeight()) / 2);
        jf.setLocation(x, y);

        GridBagLayout gbl = new GridBagLayout();
        JPanel jp = new JPanel(gbl);
        jf.setContentPane(jp);

        GridBagConstraints constraints=new GridBagConstraints();
        // 当可用空间大于控件时,控件填充可用空间的方式,如在拖大窗口时,需 要重新填充可用空间,
        // 共有4种方式,NONE,VERTICAL,HORIZONTAL,BOTH
        constraints.fill = GridBagConstraints.BOTH;
        // 当可用空间大于控件时,不填充控件的情况下或填充后还比可用空间小的情况下,要将组件放置在何处,有
        //Center,NORTH,NORTHEAST,EAST,SOUTHER 等等.
        constraints.anchor= GridBagConstraints.CENTER;

        // 当窗口放大或缩小时控件放大或缩小的比例.
        constraints.weightx=0.1;
        constraints.weighty=0.1;

        //设置组件之间彼此的间距,它有4个参数,分别是上,左,下,右
        constraints.insets= new Insets(10,0,0,0);

        constraints.ipadx=60;
        constraints.ipady=0;

        add(jp,new JTextField(15) ,constraints,0,0,5,1);

        add(jp,new JButton("0-1"),constraints,0,1,1,1);
        add(jp,new JButton("1-1"),constraints,1,1,2,1);
        add(jp,new JButton("2-1"),constraints,3,1,1,1);

        jf.setVisible(true);

    }

    public static void add(JPanel jp,Component comp, GridBagConstraints constraints, int x, int y, int w, int h) {
        constraints.gridx =x;
        constraints.gridy =y;
        constraints.gridwidth =w;
        constraints.gridheight=h;
        jp.add(comp,constraints);

    }
}