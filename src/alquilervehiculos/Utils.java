/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alquilervehiculos;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Alberto
 */
public class Utils {    

    public static String formateaFecha(Date fecha) {
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MMMM-yyyy");
        return formateador.format(fecha);
    }

    public static void muestraAlerta(Exception ex) {
        JOptionPane.showMessageDialog(null, "Ha ocurrido un error: "+ex);
    }
}
