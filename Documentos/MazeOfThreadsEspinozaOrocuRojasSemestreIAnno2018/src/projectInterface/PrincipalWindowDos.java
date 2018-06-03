package projectInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author Marco y Maria
 */
public class PrincipalWindowDos extends JFrame implements ActionListener, Serializable {

    private Dimension dimension;
    public static JButton start;
    private Font font, fontDos, fontTres;
    private JLabel welcome, other;

    public PrincipalWindowDos() {
        this.font = new Font("Amatic SC", Font.BOLD, 150);
        this.fontDos = new Font("Amatic SC", Font.BOLD, 50);
        this.fontTres = new Font("Amatic SC", Font.BOLD, 30);
        
        this.setTitle("Maze of threads");
        setLocationRelativeTo(null);
        setResizable(false);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        dimension = super.getToolkit().getScreenSize();
        // this.setIconImage(new ImageIcon("./ProjectImages/iconMain.jpg").getImage());
        super.setSize(dimension);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        start = new JButton("START");
        start.setBounds(570, 420, 200, 50);
        start.setEnabled(true);
        start.setBackground(new Color(36,47,65));
        start.setForeground(Color.white);
        start.addActionListener(this);
        start.setBorder(null);
        start.setFont(fontTres);

        welcome = new JLabel("¡ W E L C O M E !");
        welcome.setFont(font);
        welcome.setForeground(Color.getHSBColor(209, 57, 57));
        welcome.setBounds(420, 100, 800, 300);

        other = new JLabel("– P R E S S   S T A R T   T O   B E G I N –");
        other.setFont(fontDos);
        other.setForeground(Color.getHSBColor(209, 57, 57));
        other.setBounds(480, 200, 500, 300);

        add(start);
        add(welcome);
        add(other);
      
        JLabel lblBackground = new JLabel(new ImageIcon("./ProjectImages/casa.jpg"));
        lblBackground.setBounds(0, 0, 1440, 810);

        this.add(lblBackground);
    }  //constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            MainWindow m = new MainWindow();
            m.setVisible(true);
        }
    }
}//class

