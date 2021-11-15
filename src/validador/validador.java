
package validador;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class validador {
    
    public boolean validarTexto(String texto){
        return texto.equals("");
    }
    
    public boolean validarCorreo(String correo){
       Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");   
       Matcher mat = pat.matcher(correo);
       return mat.find();
    }
    
    public boolean validarRut(String rut){
       Pattern pat = Pattern.compile("^([1-9])([0-9]{6,7})-([0-9k]{1})$");   
       Matcher mat = pat.matcher(rut);
       return mat.find();
    }
   
}   
