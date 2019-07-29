import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial") class Acerca extends JDialog{

    private static JLabel titulo,copy;
    private static JPanel cabecera,cuerpo,pie;        
    private static Fuente Fuente;

    public Acerca(JFrame ventana) throws FontFormatException, IOException{
        super(ventana, "Acerca", true);
        setVisible(false);
        setSize(600,480);
        setResizable(false);
        setLocationRelativeTo(ventana);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        titulo = new JLabel("Acerca");
        cabecera = new JPanel();
        cuerpo = new JPanel();
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



        pie.setBounds(0,430,600,50);
        pie.setBackground(new Color(230,230,230));  
        pie.add(copy,JPanel.LEFT_ALIGNMENT);

        add(cabecera);
        add(cuerpo);
        add(pie);
    }
}