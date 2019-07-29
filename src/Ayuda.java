import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.html.HTMLEditorKit;

@SuppressWarnings("serial") class Ayuda extends JDialog{
        
        private static JLabel titulo,copy;
        private static JPanel cabecera,pie;    
        private static JEditorPane cuerpo;
        private static Fuente Fuente;
        
        public Ayuda(JFrame ventana) throws FontFormatException, IOException{
            super(ventana, "Ayuda", true);
            setVisible(false);
            setSize(600,480);
            setResizable(false);
            setLocationRelativeTo(ventana);
            setLayout(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            
            titulo = new JLabel("Ayuda");
            cabecera = new JPanel();
            cuerpo = new JEditorPane();
            pie = new JPanel();
            
            Fuente = new Fuente();
            
            titulo.setFont(Fuente.BebasNeue(48,Font.BOLD));            
            titulo.setForeground(new Color(80,80,80));
            
            copy = new JLabel("Desarrollado por wpdesigns");
            copy.setFont(Fuente.Redensek(16,Font.BOLD));            
            
            cabecera.setBounds(0,0,600,60);
            cabecera.add(titulo,JPanel.CENTER_ALIGNMENT);
            cabecera.setBackground(new Color(230,230,230));        
            
            cuerpo.setBounds(0, 60,600,370);
            cuerpo.setBackground(Color.white);
            //cuerpo.setLayout(new FlowLayout(FlowLayout.LEFT));
            cuerpo.setContentType("text/html");
            cuerpo.setText("<b>hi</b>");
            
            pie.setBounds(0,430,600,50);
            pie.setBackground(new Color(230,230,230));  
            pie.add(copy,JPanel.LEFT_ALIGNMENT);
            
            add(cabecera);
            add(cuerpo);
            add(pie);
        }
        
//        public static void main(String[] args) {
//            new Ayuda(null).setVisible(true);            
//        }
    }