package projectInterface;

import File.SerializableImage;
import domain.Block;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;

public class MainWindow extends JFrame implements ActionListener, MouseListener {

    JLabel lblSelectMaze, lblMessageLevel1, lblMessageLevel2, lblMessageLevel3,
            lblImageLevel1, lblImageLevel2, lblImageLevel3, lblSelectCharacter1,
            lblSelectCharacter2, lblMessageCharacter1, lblMessageCharacter2,
            lblMessageCharacter3, lblImageCharacter1, lblImageCharacter2,
            lblImageCharacter3, lblQuantityCharacter1, lblQuantityCharacter2,
            lblQuantityCharacter3, lblBackground;
    JSpinner spnQuantityCharacter1, spnQuantityCharacter2, spnQuantityCharacter3;
    JButton btnStart;
    private Font font, fontDos;
    private AnimationWindow animationWindow;

    public MainWindow() {
        this.setLayout(null);
        this.setTitle("Maze of threads");
        this.font = new Font("Century gothic", Font.BOLD, 14);
        this.fontDos = new Font("Century gothic", Font.BOLD, 15);
        
        lblSelectMaze = new JLabel("Select the maze in which you want to play.");
        lblSelectMaze.setBounds(370, 0, 500, 25);
        lblSelectMaze.setForeground(Color.getHSBColor(209, 57, 57));
        lblSelectMaze.setFont(fontDos);
        

        lblMessageLevel1 = new JLabel("Level 1");
        lblMessageLevel1.setBounds(150, 30, 60, 20);
        lblMessageLevel1.setFont(font);
        lblMessageLevel1.setForeground(Color.getHSBColor(209, 57, 57));
        
        lblImageLevel1 = new JLabel(new ImageIcon("./ProjectImages/casadefin.jpg"));
        lblImageLevel1.setBounds(10, 60, 340, 300);
        lblImageLevel1.setBorder(new LineBorder(Color.white, 2));
        lblImageLevel1.setToolTipText("Simple maze, with one input and one output.");
        lblImageLevel1.setEnabled(true);
        lblImageLevel1.addMouseListener(this);

        lblMessageLevel2 = new JLabel("Level 2");
        lblMessageLevel2.setBounds(500, 30, 60, 20);
        lblMessageLevel2.setForeground(Color.getHSBColor(209, 57, 57));
        lblMessageLevel2.setFont(font);
        lblImageLevel2 = new JLabel(new ImageIcon("./ProjectImages/dulceReino.jpg"));
        lblImageLevel2.setBounds(360, 60, 340, 300);
        lblImageLevel2.setBorder(new LineBorder(Color.white, 2));
        lblImageLevel2.setToolTipText("Maze with medium level of difficulty,"
                + " has only one entry and one exit .");
        lblImageLevel2.setEnabled(true);
        lblImageLevel2.addMouseListener(this);

        lblMessageLevel3 = new JLabel("Level 3");
        lblMessageLevel3.setBounds(860, 30, 60, 20);
        lblMessageLevel3.setFont(font);
        lblMessageLevel3.setForeground(Color.getHSBColor(209, 57, 57));
        lblImageLevel3 = new JLabel(new ImageIcon("./ProjectImages/reinoHelado.png"));
        lblImageLevel3.setBounds(710, 60, 340, 300);
        lblImageLevel3.setBorder(new LineBorder(Color.white, 2));
        lblImageLevel3.setToolTipText("Maze with high level of difficulty, it has"
                + " two inputs and two outputs.");
        lblImageLevel3.setEnabled(true);
        lblImageLevel3.addMouseListener(this);

        lblSelectCharacter1 = new JLabel("Select the number of each type of character you want to add to the labyrinth.");
        lblSelectCharacter1.setBounds(260, 380, 600, 20);
        lblSelectCharacter1.setFont(fontDos);
        lblSelectCharacter1.setForeground(Color.getHSBColor(209, 57, 57));

        SpinnerNumberModel spinnerModel1 = new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(99),
                new Integer(1));
        SpinnerNumberModel spinnerModel2 = new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(99),
                new Integer(1));
        SpinnerNumberModel spinnerModel3 = new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(99),
                new Integer(1));

        lblMessageCharacter1 = new JLabel("Fast");
        lblMessageCharacter1.setBounds(335, 410, 100, 20);
        lblMessageCharacter1.setFont(font);
        lblMessageCharacter1.setForeground(Color.getHSBColor(209, 57, 57));
        lblImageCharacter1 = new JLabel(new ImageIcon("./ProjectImages/finAdelante2.png"));
        lblImageCharacter1.setBounds(300, 440, 100, 100);
        lblImageCharacter1.setBorder(new LineBorder(Color.white, 2));
        lblImageCharacter1.setToolTipText("Quick characters, but after a few seconds"
                + " must stop for recover energy.");
        lblQuantityCharacter1 = new JLabel("Quantity");
        lblQuantityCharacter1.setForeground(Color.getHSBColor(209, 57, 57));
        lblQuantityCharacter1.setFont(font);
        lblQuantityCharacter1.setBounds(295, 550, 80, 20);
        spnQuantityCharacter1 = new JSpinner(spinnerModel1);
        spnQuantityCharacter1.setBounds(355, 550, 50, 20);
        spnQuantityCharacter1.getEditor().setEnabled(false);
        ((DefaultEditor) spnQuantityCharacter1.getEditor()).getTextField().
                setEditable(false);

        lblMessageCharacter2 = new JLabel("Furious");
        lblMessageCharacter2.setBounds(530, 410, 100, 20);
        lblMessageCharacter2.setFont(font);
        lblMessageCharacter2.setForeground(Color.getHSBColor(209, 57, 57));
        lblImageCharacter2 = new JLabel(new ImageIcon("./ProjectImages/cakeAbajo2.png"));
        lblImageCharacter2.setBounds(500, 440, 100, 100);
        lblImageCharacter2.setBorder(new LineBorder(Color.white, 2));
        lblImageCharacter2.setToolTipText("Slow characters, but never stop its"
                + " way any circumstances.");

        lblQuantityCharacter2 = new JLabel("Quantity");
        lblQuantityCharacter2.setBounds(495, 550, 80, 20);
        lblQuantityCharacter2.setFont(font);
        lblQuantityCharacter2.setForeground(Color.getHSBColor(209, 57, 57));
        spnQuantityCharacter2 = new JSpinner(spinnerModel2);
        spnQuantityCharacter2.setBounds(555, 550, 50, 20);
        ((DefaultEditor) spnQuantityCharacter2.getEditor()).getTextField().
                setEditable(false);

        lblMessageCharacter3 = new JLabel("Smart");
        lblMessageCharacter3.setBounds(735, 410, 100, 20);
        lblMessageCharacter3.setFont(font);
        lblMessageCharacter3.setForeground(Color.getHSBColor(209, 57, 57));
        lblImageCharacter3 = new JLabel(new ImageIcon("./ProjectImages/JakeFrente2.png"));
        lblImageCharacter3.setBounds(700, 440, 100, 100);
        lblImageCharacter3.setBorder(new LineBorder(Color.white, 2));
        lblImageCharacter3.setToolTipText("Medium speed characters, when they are"
                + " an item increases its speed.");
        lblQuantityCharacter3 = new JLabel("Quantity");
        lblQuantityCharacter3.setFont(font);
        lblQuantityCharacter3.setBounds(695, 550, 80, 20);
        lblQuantityCharacter3.setForeground(Color.getHSBColor(209, 57, 57));
        spnQuantityCharacter3 = new JSpinner(spinnerModel3);
        spnQuantityCharacter3.setBounds(755, 550, 50, 20);
        ((DefaultEditor) spnQuantityCharacter3.getEditor()).getTextField().
                setEditable(false);

        btnStart = new JButton("Start game");
        btnStart.setBounds(500, 600, 100, 40);
        btnStart.setFont(font);
        btnStart.addActionListener(this);
        btnStart.setBackground(new Color(36,47,65));
        btnStart.setForeground(Color.WHITE);
        btnStart.setBorder(null);
        
        lblBackground = new JLabel(new ImageIcon("./ProjectImages/fondo.jpg"));
        lblBackground.setBounds(0, 0, 1070, 700);

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
        this.add(lblSelectCharacter1);
        this.add(lblImageLevel3);
        this.add(lblMessageLevel3);
        this.add(lblImageLevel2);
        this.add(lblMessageLevel2);
        this.add(lblImageLevel1);
        this.add(lblSelectMaze);
        this.add(lblMessageLevel1);
        this.add(lblBackground);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(160, 10);
        this.setSize(1070, 700);
        this.setResizable(false);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
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
                SerializableImage mySer = new SerializableImage();
                Object[] toReturn = mySer.readObject(level);
                blocks = (ArrayList<Block>) toReturn[0];
                entranceExit = (int[]) toReturn[1];
            } catch (IOException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AnimationWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                this.animationWindow = new AnimationWindow(fast, furious, smart, 1, blocks, entranceExit);
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getSource() == lblImageLevel1) {
            lblImageLevel1.setBorder(new LineBorder(Color.red, 2));
            lblImageLevel2.setBorder(new LineBorder(Color.black, 2));
            lblImageLevel3.setBorder(new LineBorder(Color.black, 2));
            lblImageLevel1.setEnabled(true);
            lblImageLevel2.setEnabled(false);
            lblImageLevel3.setEnabled(false);
        } else if (me.getSource() == lblImageLevel2) {
            lblImageLevel1.setBorder(new LineBorder(Color.black, 2));
            lblImageLevel2.setBorder(new LineBorder(Color.red, 2));
            lblImageLevel3.setBorder(new LineBorder(Color.black, 2));
            lblImageLevel1.setEnabled(false);
            lblImageLevel2.setEnabled(true);
            lblImageLevel3.setEnabled(false);
        } else if (me.getSource() == lblImageLevel3) {
            lblImageLevel1.setBorder(new LineBorder(Color.black, 2));
            lblImageLevel2.setBorder(new LineBorder(Color.black, 2));
            lblImageLevel3.setBorder(new LineBorder(Color.red, 2));
            lblImageLevel1.setEnabled(false);
            lblImageLevel2.setEnabled(false);
            lblImageLevel3.setEnabled(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

}
