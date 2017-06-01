/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenamiento;

import java.io.File;
import javax.swing.JTable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author Alberto
 */
public class GuardarReservas implements IDepositable {

    @Override
    public void guardarXml(JTable tabla) {
        File archivo = new File("reservas.xml");
        Document doc = null;

        // Creamos el doc
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.newDocument();
        } catch (ParserConfigurationException ex) {
            System.out.println(ex);
        }

        Element root = doc.createElement("listaReservas");

        doc.appendChild(root);

        // Recorremos la tabla
        for (int i = 0; i < tabla.getRowCount(); i++) {

            Element reserva = doc.createElement("reserva");
            root.appendChild(reserva);

            for (int j = 0; j < tabla.getColumnCount(); j++) {
                Object o = tabla.getValueAt(i, j);
                
                // Eliminamos los espacios y las barras, no están permitidos en xml tags
                String columna = tabla.getColumnName(j).replaceAll("\\s", "").replaceAll("/", "-");

                Element nombre = doc.createElement(columna);
                Text nombreTxt = doc.createTextNode(o.toString());
                reserva.appendChild(nombre);
                nombre.appendChild(nombreTxt);
            }
        }

        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            tf.setAttribute("indent-number", 4);
            Transformer trans = tf.newTransformer();
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            StreamResult sr = new StreamResult(archivo);
            DOMSource domSource = new DOMSource(doc);
            trans.transform(domSource, sr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void guardarExcel(JTable tabla) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
