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
public class GuardarClientes implements IDepositable {

    @Override
    public void guardarXml(JTable tabla, File archivo) {

        Document doc = null;

        // Creamos el doc
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.newDocument();
        } catch (ParserConfigurationException ex) {
            System.out.println(ex);
        }

        Element root = doc.createElement("listaClientes");

        doc.appendChild(root);

        // Recorremos la tabla
        for (int i = 0; i < tabla.getRowCount(); i++) {

            Element cliente = doc.createElement("cliente");
            root.appendChild(cliente);

            for (int j = 0; j < tabla.getColumnCount(); j++) {
                Object o = tabla.getValueAt(i, j);

                Element nombre = doc.createElement(tabla.getColumnName(j));
                Text nombreTxt = doc.createTextNode(o.toString());
                cliente.appendChild(nombre);
                nombre.appendChild(nombreTxt);
            }
        }

        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer trans = tf.newTransformer();
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

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
