import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 *  @author Piro
 *  @version 1
 *  
 * Clase diseñada para validar JTextField's.
 * Métodos:
 *      <ul>
 *          <li>ssid(String str)</li>
 *          <li>key(String str)</li>
 *      </ul>
 */

@SuppressWarnings("serial") abstract class Validar {
    /**
     *   <p>
     *       El método recibe como argumento un objeto de tipo String.
     *       Valida con una expresión regular: <i>^[a-zA-Z0-9 áéíóúüñ._-]+$</i>
     *   </p>
     *          La expresión permite:
     *          <ul>
     *              <li>Letras</li>
     *              <li>Números</li>
     *              <li>Espacios</li>
     *              <li>Caracteres: á, é, í, ó, ú, ü, ñ, (.), (_) y (-).
     *          </ul>
     * @param nombre Es la cadena a validar, debe ser String.
     * @return boolean
     * 
     */
    public static final boolean ssid(String nombre){
        Pattern patron = Pattern.compile("^[#a-zA-Z0-9 áéíóúñü._-]+$");
        Matcher match = patron.matcher(nombre);
        
        return (match.find());
        
    }
    
    
    /**
     * Valida que la longitud de objeto de tipo <b>String</b> 
     * sea mínimo de 8 caracteres cualesquiera.
     * @param pass as String
     * @return 
     *      <ul>
     *          <li><b>true</b> si la longitud de la cadena ≥ 8.</li>
     *          <li><b>false</b> si la longitud de la cadena menor a 8.</li>
     *      </ul>
     */
    
    public static final boolean key(String pass){
        Pattern patron = Pattern.compile("^(.){8,}$");
        Matcher match = patron.matcher(pass);
        return (match.find());
        
    }
}