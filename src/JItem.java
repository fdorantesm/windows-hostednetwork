import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial") class JItem extends JPanel{
    
    private final JPanel panel [];
    
    public JItem(String clave, String valor){
        setLayout(null);
        panel = new JPanel [2];
        for(int i = 0; i<panel.length; i++){
            panel[i] = new JPanel();
            panel[i].setSize(150,20);
            panel[i].setBounds((i*150),0,150,20);
            panel[i].setLayout(new FlowLayout((i==0)?FlowLayout.RIGHT:FlowLayout.LEFT));
            panel[i].add(new JLabel((i==0)?clave:valor));
            add(panel[i]);
        }             
    }
}