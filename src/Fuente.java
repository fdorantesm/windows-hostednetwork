import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

class Fuente{    
    
    /**
     * Devuelve la fuente BebasNeue
     *
     * @param size Es el tamaño de la letra
     * @param type Tipo de fuente
     * @throws FontFormatException Se lanza si la fuente no es válida
     * @throws IOException Se lanza si el archivo no existe.
     * @return  Font BebasNeue
     * 
     */
    
    public final Font BebasNeue(int size, int type) throws FontFormatException, IOException{
        InputStream f = getClass().getResourceAsStream("resources/ttf/BebasNeue.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT,f);
        font = font.deriveFont(type, size);
        f.close();
        return font;
    }
    
    /**
     * Devuelve la fuente Redensek
     *
     * @param size Es el tamaño de la letra
     * @param type Tipo de la fuente
     * @throws FontFormatException Se lanza si la fuente no es válida
     * @throws IOException Se lanza si el archivo no existe.
     * @return  Font Redensek
     * 
     */
    public final Font Redensek(int size, int type) throws FontFormatException, IOException{
        InputStream f = getClass().getResourceAsStream("resources/ttf/Redensek.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT,f);
        font = font.deriveFont(type, size);
        f.close();
        return font;
    }
    
}