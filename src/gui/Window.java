/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import data.MazeFile;
import domain.Block;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;

/**
 *
 * @author mary_
 */
public class Window extends JFrame implements ActionListener, MouseListener {

    JLabel lblSelectMaze, lblMessageLevel1, lblMessageLevel2, lblMessageLevel3,
            lblImageLevel1, lblImageLevel2, lblImageLevel3, lblSelectCharacter1,
            lblSelectCharacter2, lblMessageCharacter1, lblMessageCharacter2,
            lblMessageCharacter3, lblImageCharacter1, lblImageCharacter2,
            lblImageCharacter3, lblQuantityCharacter1, lblQuantityCharacter2,
            lblQuantityCharacter3, lblBackground;
    JSpinner spnQuantityCharacter1, spnQuantityCharacter2, spnQuantityCharacter3;
    JButton btnStart;
    //private AnimationWindow animationWindow;

    public Window() {
        this.setLayout(null);
        this.setTitle("Maze of threads");
        lblSelectMaze = new JLabel("Select the maze in which you want to play.");
        lblSelectMaze.setBounds(450, 5, 300, 20);

        lblMessageLevel1 = new JLabel("Level 1");
        lblMessageLevel1.setBounds(150, 30, 60, 20);
        lblImageLevel1 = new JLabel();
        lblImageLevel1.setBounds(10, 50, 340, 300);
        lblImageLevel1.setBorder(new LineBorder(Color.red, 2));
        lblImageLevel1.setToolTipText("Simple maze, with one input and one output.");
        lblImageLevel1.setEnabled(true);
        lblImageLevel1.addMouseListener(this);

        lblMessageLevel2 = new JLabel("Level 2");
        lblMessageLevel2.setBounds(500, 30, 60, 20);
        lblImageLevel2 = new JLabel();
        lblImageLevel2.setBounds(360, 50, 340, 300);
        lblImageLevel2.setBorder(new LineBorder(Color.black, 2));
        lblImageLevel2.setToolTipText("Maze with medium level of difficulty,"
                + " has only one entry and one exit .");
        lblImageLevel2.setEnabled(true);
        lblImageLevel2.addMouseListener(this);

        lblMessageLevel3 = new JLabel("Level 3");
        lblMessageLevel3.setBounds(860, 30, 60, 20);
        lblImageLevel3 = new JLabel();
        lblImageLevel3.setBounds(710, 50, 340, 300);
        lblImageLevel3.setBorder(new LineBorder(Color.black, 2));
        lblImageLevel3.setToolTipText("Maze with high level of difficulty, it has"
                + " two inputs and two outputs.");
        lblImageLevel3.setEnabled(true);
        lblImageLevel3.addMouseListener(this);

        lblSelectCharacter1 = new JLabel("Select the number of each type of character");
        lblSelectCharacter1.setBounds(430, 360, 300, 20);
        lblSelectCharacter2 = new JLabel("you want to add to the maze.");
        lblSelectCharacter2.setBounds(470, 375, 200, 20);

        SpinnerNumberModel spinnerModel1 = new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(99),
                new Integer(1));
        SpinnerNumberModel spinnerModel2 = new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(99),
                new Integer(1));
        SpinnerNumberModel spinnerModel3 = new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(99),
                new Integer(1));

        lblMessageCharacter1 = new JLabel("Fast");
        lblMessageCharacter1.setBounds(335, 400, 100, 20);
        lblImageCharacter1 = new JLabel();
        lblImageCharacter1.setBounds(300, 420, 100, 100);
        lblImageCharacter1.setBorder(new LineBorder(Color.black, 2));
        lblImageCharacter1.setToolTipText("Quick characters, but after a few seconds"
                + " must stop for recover energy.");
        lblQuantityCharacter1 = new JLabel("Quantity");
        lblQuantityCharacter1.setBounds(300, 530, 80, 20);
        spnQuantityCharacter1 = new JSpinner(spinnerModel1);
        spnQuantityCharacter1.setBounds(355, 530, 50, 20);
        spnQuantityCharacter1.getEditor().setEnabled(false);
        ((JSpinner.DefaultEditor) spnQuantityCharacter1.getEditor()).getTextField().
                setEditable(false);

        lblMessageCharacter2 = new JLabel("Furious");
        lblMessageCharacter2.setBounds(530, 400, 100, 20);
        lblImageCharacter2 = new JLabel("");
        lblImageCharacter2.setBounds(500, 420, 100, 100);
        lblImageCharacter2.setBorder(new LineBorder(Color.black, 2));
        lblImageCharacter2.setToolTipText("Slow characters, but never stop its"
                + " way any circumstances.");
        lblQuantityCharacter2 = new JLabel("Quantity");
        lblQuantityCharacter2.setBounds(500, 530, 80, 20);
        spnQuantityCharacter2 = new JSpinner(spinnerModel2);
        spnQuantityCharacter2.setBounds(555, 530, 50, 20);
        ((JSpinner.DefaultEditor) spnQuantityCharacter2.getEditor()).getTextField().
                setEditable(false);

        lblMessageCharacter3 = new JLabel("Smart");
        lblMessageCharacter3.setBounds(735, 400, 100, 20);
        lblImageCharacter3 = new JLabel();
        lblImageCharacter3.setBounds(700, 420, 100, 100);
        lblImageCharacter3.setBorder(new LineBorder(Color.black, 2));
        lblImageCharacter3.setToolTipText("Medium speed characters, when they are"
                + " an item increases its speed.");
        lblQuantityCharacter3 = new JLabel("Quantity");
        lblQuantityCharacter3.setBounds(700, 530, 80, 20);
        spnQuantityCharacter3 = new JSpinner(spinnerModel3);
        spnQuantityCharacter3.setBounds(755, 530, 50, 20);
        ((JSpinner.DefaultEditor) spnQuantityCharacter3.getEditor()).getTextField().
                setEditable(false);

        btnStart = new JButton("Start game");
        btnStart.setBounds(500, 580, 100, 40);
        btnStart.addActionListener(this);

        this.add(btnStart);
        this.add(spnQuantityCharacter3);
        this.add(lblQuantityCharacter3);
        this.add(lblImageCharacter3);
        this.add(lblMessageCharacter3);
        this.add(spnQuantityCharacter2);
        this.add(lblQuantityCharacter2);
        this.add(lblImageCharacter2);
        this.add(lblMessageCharacter2);
        this.add(spnQuantityCharacter1);
        this.add(lblQuantityCharacter1);
        this.add(lblImageCharacter1);
        this.add(lblMessageCharacter1);
        this.add(lblSelectCharacter2);
        this.add(lblSelectCharacter1);
        this.add(lblImageLevel3);
        this.add(lblMessageLevel3);
        this.add(lblImageLevel2);
        this.add(lblMessageLevel2);
        this.add(lblImageLevel1);
        this.add(lblSelectMaze);
        this.add(lblMessageLevel1);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(160, 10);
        this.setSize(1070, 700);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int fast = (int) spnQuantityCharacter1.getValue();
        int furious = (int) spnQuantityCharacter2.getValue();
        int smart = (int) spnQuantityCharacter3.getValue();
        ArrayList<Block> blocks = new ArrayList<Block>();
        int[] entranceExit;
        String level;
        if (lblImageLevel1.isEnabled() == true) {
            level = "MazeLevel1";
            entranceExit = new int[4];
        } else if (lblImageLevel2.isEnabled() == true) {
            level = "MazeLevel2";
            entranceExit = new int[4];
        } else {
            level = "MazeLevel3";
            entranceExit = new int[8];
        }
        if ((fast + furious + smart) != 0) {

            try {
                MazeFile mySer = new MazeFile();
                Object[] toReturn = mySer.readObject(level);
                blocks = (ArrayList<Block>) toReturn[0];
                entranceExit = (int[]) toReturn[1];
            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == lblImageLevel1) {
            lblImageLevel1.setBorder(new LineBorder(Color.red, 2));
            lblImageLevel2.setBorder(new LineBorder(Color.black, 2));
            lblImageLevel3.setBorder(new LineBorder(Color.black, 2));
            lblImageLevel1.setEnabled(true);
            lblImageLevel2.setEnabled(false);
            lblImageLevel3.setEnabled(false);
        } else if (e.getSource() == lblImageLevel2) {
            lblImageLevel1.setBorder(new LineBorder(Color.black, 2));
            lblImageLevel2.setBorder(new LineBorder(Color.red, 2));
            lblImageLevel3.setBorder(new LineBorder(Color.black, 2));
            lblImageLevel1.setEnabled(false);
            lblImageLevel2.setEnabled(true);
            lblImageLevel3.setEnabled(false);
        } else if (e.getSource() == lblImageLevel3) {
            lblImageLevel1.setBorder(new LineBorder(Color.black, 2));
            lblImageLevel2.setBorder(new LineBorder(Color.black, 2));
            lblImageLevel3.setBorder(new LineBorder(Color.red, 2));
            lblImageLevel1.setEnabled(false);
            lblImageLevel2.setEnabled(false);
            lblImageLevel3.setEnabled(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
