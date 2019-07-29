import java.awt.Desktop;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

@SuppressWarnings("serial") class VirtualWifi extends JFrame implements ActionListener, KeyListener{
    
    private static JTextField campo[];
    private static JLabel label[];
    private static JButton power;
    private static String info;
    private final JMenuBar barra;
    private final JMenu archivo, ayuda, opciones;
    private final JMenuItem isalir, iacerca,icontacto,iayuda,ver,renombrar,cambiarKey;
    private final JCheckBox decrypt;
    private static String password;
    
    public void menu(){
        barra.add(archivo).add(isalir).addActionListener(this);
        JMenu nets = barra.add(opciones);
        JMenu help = barra.add(ayuda);
        
        nets.add(ver);
        nets.add(renombrar);
        nets.add(cambiarKey);
        renombrar.addActionListener(this);
        ver.addActionListener(this);
        
        help.add(icontacto).addActionListener(this);
        help.add(iayuda).addActionListener(this);
        help.add(iacerca).addActionListener(this);        
    }
    
    
    public VirtualWifi() throws IOException{
        
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
        
        }
        
        if(info().length<10){
            Runtime.getRuntime().exec("netsh wlan set hostednetwork mode=allow ssid=\""+System.getProperty("user.name")+"\" key=wpdesigns");
            key("wpdesigns");
        }
        
        if("No disponible".equals(status(info()))){
            for (JTextField c : campo) {
                c.setEnabled(false);
            }
            power.setEnabled(false);
        }
        
        
        barra = new JMenuBar();
        archivo = new JMenu("Archivo");
        opciones = new JMenu("Opciones");
        ayuda = new JMenu("Ayuda");
        
        isalir = new JMenuItem("Salir");
        icontacto = new JMenuItem("Contacto");
        iayuda = new JMenuItem("Ayuda");
        iacerca = new JMenuItem("Acerca");
        ver = new JMenuItem("Ver información de la red");
        renombrar = new JMenuItem("Cambiar nombre de la red");
        cambiarKey = new JMenuItem("Cambiar contraseña de la red");
        menu();        
        
        power=new JButton(("No iniciado".equals(status(info())))?"Iniciar":"Detener");
        power.setFocusable(false);
        power.setFocusPainted(false);
        power.setBounds(330,29,75,31);
               
        campo = new JTextField[3];
        label = new JLabel[2];
        
        password = (!"".equals(loadKey()))?loadKey():"";
        
        campo[0] = new JTextField((!"".equals(ssid(info())))?ssid(info()):"");        
        campo[1] = new JPasswordField(password);
        campo[2] = new JTextField(password);
        campo[2].setVisible(false);
        
        
        if ("Iniciado".equals(status(info()))){
            for (JTextField input : campo) {
                input.setEnabled(false);
            }            
            repaint();
        }
                
        label[0] = new JLabel("SSID:");
        label[1] = new JLabel("Contraseña:");
        
        decrypt = new JCheckBox("Ver");
        
        if(!"".equals(loadKey()) && campo[1].getText()!=""){
            decrypt.setVisible(true);
        }else{
            decrypt.setVisible(false);
        }
        
        decrypt.setBounds(280, 0, 60, 30);
        decrypt.addActionListener(this);
        decrypt.setFocusPainted(false);
        decrypt.setBorderPainted(false);
        
        setTitle("Virtual HostedNetwork");
        setSize(420,130);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setJMenuBar(barra);
        
        label[0].setBounds(10, 0, 150, 30);
        label[1].setBounds(170,0,150,30);
        
        campo[0].setBounds(10, 30, 150,30);
        campo[1].setBounds(170,30,150,30);
        campo[2].setBounds(170,30,150,30);
        
        for (JLabel i:label) {
            add(i);
        }
        
        for(JTextField i:campo){
            add(i);
        }

        add(power);
        add(decrypt);
        
        power.addActionListener(this);
        campo[1].addKeyListener(this);
    
    }
    /**
     * 
     * Crea una red virtual
     * 
     * @param ssid Nombre de la red
     * @param key  Contraseña de la red 
     * @return 
     *      <b>true</b> - Si se pudo crear la red<br>
     *      <b>false</b> - Si no se pudo crear la red.
     * @throws IOException Si no se puede ejecutar el comando.
     */
    private boolean crear(String ssid, String key) throws IOException{
            Process cmd = Runtime.getRuntime().exec("netsh wlan set hostednetwork mode=allow ssid=\""+ssid+"\" key=\""+key+"\"");
            BufferedReader br = new BufferedReader (new InputStreamReader(cmd.getInputStream()));
            String aux = br.readLine();
            
            while (aux!=null){
                aux = br.readLine();
            }
            
            key(key);
            
        return (cmd.exitValue()==0);
    }
    /**
     * 
     * @return
     *      <b>true</b> - Si se pudo iniciar la red<br>
     *      <b>false</b> - Si no se pudo iniciar la red.
     * @throws IOException  Si no se puede ejecutar el comando.
     */
    private boolean iniciar() throws IOException{
       Process cmd = Runtime.getRuntime().exec("netsh wlan start hostednetwork");
       BufferedReader br = new BufferedReader (new InputStreamReader(cmd.getInputStream()));
            String aux = br.readLine();
            
            while (aux!=null){
                aux = br.readLine();
            }
            
        return (cmd.exitValue()==0);
    }
    /**
     * <p>Detiene la red hospedada</p>
     * 
     * @return
     *      <b>true</b> - Si se pudo detener la red<br>
     *      <b>false</b> - Si no se pudo detener la red.
     * @throws IOException Si no se puede ejecutar el comando
     */
    private boolean detener() throws IOException{
        Process cmd = Runtime.getRuntime().exec("netsh wlan stop hostednetwork");
        BufferedReader br = new BufferedReader (new InputStreamReader(cmd.getInputStream()));
            String aux = br.readLine();
            
            while (aux!=null){
                aux = br.readLine();
            }                       
        
        for (JTextField input : campo) {
            input.setEnabled(true);
        }
        
        repaint();
                
        return (cmd.exitValue()==0);
    }
   /**
    * <p>Obtiene la salida de consola</p>
    * 
    * @return info Un arreglo con las lineas de salida de la consola
    * @throws IOException Si no se puede ejecutar el comando
    */
    public static String[] info() throws IOException{
        Process cmd = Runtime.getRuntime().exec("netsh wlan show hostednetwork");
        Scanner leer = new Scanner(cmd.getInputStream());
        info = leer.nextLine();
        
        while (leer.hasNextLine()) {
            info += leer.nextLine()+"\n";
        }
        
        String f [] = info.split("\n");
        return f;
        
    }
    /**
     * <p>Devuelve el modo de la red.</p>
     * @param info Arreglo con las lineas de salida de la consola.
     * @return modo Permitodo | No permitido 
     */
    public static String modo(String info[]){
        String [] valor = info[2].split(": ");
        return valor[1];
    }
    /**
     * <p>Devuelve el nombre de la red.</p>
     * @param info Arreglo con las lineas de salida de la consola.
     * @return ssid  
     */
    public static String ssid(String info[]){
        String [] status = info[3].split(" : ");
        String dat[] = status[1].split("\"");
        return dat[1];
    }
    
    /**
     * @param key Contraseña, será guardada en el aarchivo de configuración. 
     * @return <b>true</b> - Si se pudo guardar la contraseña.<br>
     *         <b>false</b> - Si no se pudo guardar la contraseña.
     */
    public static boolean key(String key){
        String roam = System.getenv("appdata");
        String roaming =roam.replace('\\', '/');        
        File directorio = new File(roaming+"/HostedNetwork");
        directorio.mkdir();       
        try{
            FileWriter config = new FileWriter(directorio+"/config.cfg", false);
            PrintWriter pluma = new PrintWriter(config);
            pluma.println(key);
            config.close();
            return true;
        }        
        catch (IOException e){

        }
        return false;
    }
    /**
     * <p>Devuelve la contraseña del archivo de configuración.</p>
     * @return key Contraseña
     */
    public static String loadKey(){
        String roam = System.getenv("appdata");
        String roaming =roam.replace('\\', '/');        
        Scanner leer = null;
        String pass="";
        try {
            leer = new Scanner(new FileReader(roaming+"/HostedNetwork/config.cfg"));
            while (leer.hasNextLine()) {
                pass += leer.nextLine();
            }
        
            return pass;
        }catch (Exception e){
            return "";
        }
    
        finally {
            if (leer != null){
                leer.close();
            }
        }
    }
    /**
     * <p>Devuelve la cantidad máxima de clientes permitidos.</p>
     * @param info Arreglo con las lineas de salida de la consola.
     * @return clientes Número de clientes
     */
    public static String maxClientes(String info[]){
        String [] status = info[4].split(" : ");
        return status[1];
    }
    /**
     * <p>Devuelve el tipo de autentificación de la red.</p>
     * @param info Arreglo con las lineas de salida de la consola.
     * @return auth WPA
     */
    public static String autentificacion(String info[]){
        String [] status = info[5].split(" : ");
        return status[1];
    }
    /**
     * <p>Devuelve el modo de la red.</p>
     * @param info Arreglo con las lineas de salida de la consola.
     * @return cifrado CCMP
     */
    public static String cifrado(String info[]){
        String [] status = info[6].split(" : ");
        return status[1];
    }
    /**
     * <p>Devuelve el estado de la red.</p>
     * @param info Arreglo con las lineas de salida de la consola.
     * @return modo Iiniciado | No iniciado
     * @throws IOException Si no se puede ejecutar el comando
     */
    public static String status(String info[]) throws IOException{
        String [] status = info[10].split(" : ");
        return status[1];
    }
    /**
     * Devuelve la MAC ADRESS de la red
     * 
     * @param info Arreglo con las lineas de salida de la consola
     * @return mac La MAC de la red.
     * @throws IOException Si no se puede ejecutar el comando.
     */
    public static String bssid(String info[]) throws IOException{
        String [] status = info[11].split(" : ");
        return status[1];
    }
    /**
     * <p>Devuelve el radio de la red.</p>
     * @param info Arreglo con las lineas de salida de la consola.
     * @return radio 802.11 b/g
     * @throws IOException Si no se puede ejecutar el comando
     */
    public static String radio(String info[]) throws IOException{
        String [] status = info[12].split(" : ");
        return status[1];
    }
    /**
     * <p>Devuelve el canal de la red.</p>
     * @param info Arreglo con las lineas de salida de la consola.
     * @return canal 1-11
     * @throws IOException Si no se puede ejecutar el comando
     */
    public static String canal(String info[]) throws IOException{
        String [] status = info[13].split(" : ");
        return status[1];
    }
    /**
     * <p>Devuelve los clientes conectados en la red.</p>
     * @param info Arreglo con las lineas de salida de la consola.
     * @return clientes 0-100
     * @throws IOException Si no se puede ejecutar el comando
     */
    public static String clientes(String info[]) throws IOException{
        String [] status = info[14].split(" : ");
        return status[1];
    }
    
    /**
     * <p>Reintenta la ejecución de los métodos detener, crear e iniciar.</p>
     * @param info Arreglo con las lineas de salida de la consola.
     * @return true o false
     * @throws IOException Si no se puede ejecutar el comando
     */
    private void reintentar(int opc) throws IOException {
        switch(opc){
            case 0: detener(); break;
            case 1: crear(campo[0].getText(),campo[1].getText()); break;
            case 2: iniciar(); break;
        }
    }   
    
    public static void main(String[] args) throws IOException{
            new VirtualWifi();
    }

    public void actionPerformed(ActionEvent evento) {
        
        if(evento.getSource()==isalir){
            System.exit(0);
        }
        
        else if(evento.getSource()==icontacto){
            try {
                Desktop.getDesktop().browse(new URI("http://taringa.net/wyxx"));
            } 
            catch (URISyntaxException | IOException ex) {

            }
        }
        
        else if(evento.getSource()==iayuda){
            try {
                new Ayuda(this).setVisible(true);
            } catch(FontFormatException | IOException e){
            
            }
        }
        
        else if(evento.getSource()==iacerca){
            try {
                new Acerca(this).setVisible(true);
            } catch (FontFormatException | IOException ex) {
            
            }
        }
        
        else if(evento.getSource()==ver){
            try {
                new Info(this).setVisible(true);
            } catch (IOException ex) {

            }
        }
        
        else if(evento.getSource()==renombrar){
            String s;
            int x=0;
            do {
                s = JOptionPane.showInputDialog(this, "", "Ingrese el nuevo nombre de la red", (x==0)?JOptionPane.PLAIN_MESSAGE:JOptionPane.ERROR_MESSAGE);
                if(s==null){
                    s="asd";
                }else if(Validar.ssid(s)){
                    campo[0].setText(s);
                }
                x++;
            } while (!Validar.ssid(s));
            
        }
        
        else if(evento.getSource()==decrypt){
            if (campo[1].isVisible()){
                campo[1].setVisible(false);
                campo[2].setVisible(true);
                campo[2].setText(campo[1].getText());
            }else{
                campo[1].setVisible(true);
                campo[2].setVisible(false);
                campo[1].setText(campo[2].getText());
            }
            repaint();
        }
        
        else if(evento.getSource()==power){           
            try {
                switch (status(info())) {
                    case "No iniciado":
                        if(validar(campo)){
                            if (crear(campo[0].getText(),campo[1].getText())){
                                iniciar();
                                for (JComponent input : campo) {
                                    input.setEnabled(false);
                                }
                                repaint();
                                power.setText("Detener");   
                            }                           
                        } break;
                    case "Iniciado":
                        if (detener()) {
                            power.setText("Iniciar");
                            repaint();
                        }else{
                            reintentar(0);
                        }   break;
                }
            } catch (IOException ex) {

            }
            validate();
        }
    }
    
    /**
     * Valida que los campos tengan el protocolo.
     * 
     * @param campo Es un arreglo con los valores de los campos.
     * @return boolean Si se cumple o no la validación.
     * @see Validar
     */
    public boolean validar(JTextField [] campo){  
        
        if ("".equals(campo[0].getText())) {
            JOptionPane.showMessageDialog(this, "El campo SSID está vacío.", ":(", JOptionPane.ERROR_MESSAGE, null);
            return false;
        }
        
        else if(!Validar.ssid(campo[0].getText())){
            JOptionPane.showMessageDialog(this, "El campo SSID sólo permite números, letras y signos de puntuación.\nMás información en el menú de ayuda.", ":(", JOptionPane.ERROR_MESSAGE, null);
            return false;
        }
        
        else if("".equals(campo[1].getText())){
            JOptionPane.showMessageDialog(this, "El campo KEY está vacío.", ":(", JOptionPane.ERROR_MESSAGE, null);
            return false;
        }
        
        else if(!Validar.key(campo[1].getText())){
            JOptionPane.showMessageDialog(this, "El campo KEY debe tener mínimo 8 caracteres.", ":(", JOptionPane.ERROR_MESSAGE, null);
            return false;
        }
        
        return true;
    }
    
//    public static String informacion() throws IOException{
//        return "Modo: "+modo(info())+"\n"+
//                "SSID: "+ssid(info())+"\n"+
//                "KEY: " +loadKey()+"\n"+
//                "Nº máximo de clientes: " + maxClientes(info())+"\n"+
//                "Autentificación: "+ autentificacion(info())+"\n"+
//                "Cifrado: " + cifrado(info())+"\n"+
//                "Estado: " +  status(info())+"\n"+
//                (("iniciado".equals(status(info())))?
//                "BSSID: " + bssid(info())+"\n"+
//                "Radio: " + radio(info())+"\n"+
//                "Canal: " + canal(info())+"\n"+
//                "Conectados: " + clientes(info())
//                :"");
//    }

    
    
    public void keyTyped(KeyEvent e) {
       
    }

    public void keyPressed(KeyEvent e) {
        
    }

    public void keyReleased(KeyEvent e) {
        
    }
    
}