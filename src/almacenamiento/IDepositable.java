/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenamiento;

import java.util.List;

/**
 *
 * @author Alberto
 */
public interface IDepositable {
     public void guardarXml(List lista);
     public void guardarExcel(List lista);
}
