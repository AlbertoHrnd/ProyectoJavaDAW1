/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alquilervehiculos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DriverManager;
import org.apache.ibatis.jdbc.ScriptRunner;

/**
 *
 * @author daw120
 */
public class AlquilerVehiculos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        
        // Inicializamos la estructura de la base de datos para que cree
        // las tablas la primera vez que se ejecute
        String script = "src/META-INF/bbdd-estructura.sql";        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            new ScriptRunner(DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/alquilervehiculos", "ies", "juanbosco"))
                    .runScript(new BufferedReader(new FileReader(script)));
        } catch (Exception e) {
            System.err.println(e);
        }

        // Iniciamos la aplicación
        VentanaPrincipal v = new VentanaPrincipal();
        v.setTitle("Alquiler de Vehículos");
        v.setLocationRelativeTo(null);
        v.setVisible(true);
    }
}
