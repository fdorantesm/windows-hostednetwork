import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;

@SuppressWarnings("serial") class Info extends JDialog{
    
    public Info(JFrame ventana) throws IOException{
        super(ventana,"Información de la red hospedada",true);
        String[] inf = VirtualWifi.info();  
        setSize(300,("Iniciado".equals(VirtualWifi.status(inf)))?253:173);
        setVisible(false);
        setLocationRelativeTo(ventana);
        setLayout(null);
        setResizable(false);
        ArrayList<JItem> item = new ArrayList<>();
              
        item.add(new JItem("Modo:",VirtualWifi.modo(inf)));
        item.add(new JItem("SSID:",VirtualWifi.ssid(inf)));
        item.add(new JItem("KEY:",VirtualWifi.loadKey()));
        item.add(new JItem("N° Máximo de clientes:",VirtualWifi.maxClientes(inf)));
        item.add(new JItem("Autentificación:",VirtualWifi.autentificacion(inf)));
        item.add(new JItem("Cifrado:",VirtualWifi.cifrado(inf)));
        item.add(new JItem("Estado:",VirtualWifi.status(inf)));        
        if("Iniciado".equals(VirtualWifi.status(inf))){
            item.add(new JItem("MAC:",VirtualWifi.bssid(inf)));
            item.add(new JItem("Radio:",VirtualWifi.radio(inf)));
            item.add(new JItem("Canal:",VirtualWifi.canal(inf)));
            item.add(new JItem("Conectados ahora:",VirtualWifi.clientes(inf)));
        }
        
        for (int i = 0; i < item.size(); i++) {
                item.get(i).setBounds(0, (i*20), 300, 20);
                add(item.get(i));
            }
    }
    
}