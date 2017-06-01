/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenamiento;

import java.io.File;
import javax.swing.JTable;

/**
 *
 * @author Alberto
 */
public interface IDepositable {
     public void guardarXml(JTable tabla, File archivo);
     public void guardarExcel(JTable tabla);
}
