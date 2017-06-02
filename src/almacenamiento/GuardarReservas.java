/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacenamiento;

import alquilervehiculos.Utils;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JTable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author Alberto
 */
public class GuardarReservas implements IDepositable {

    @Override
    public void guardarXml(JTable tabla, File archivo) {

        Document doc = null;

        // Creamos el doc
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.newDocument();
        } catch (ParserConfigurationException ex) {
            Utils.muestraAlerta(ex);
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
            Transformer trans = tf.newTransformer();
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            StreamResult sr = new StreamResult(archivo);
            DOMSource domSource = new DOMSource(doc);
            trans.transform(domSource, sr);
        } catch (IllegalArgumentException | TransformerException ex) {
            Utils.muestraAlerta(ex);
        }
    }

    @Override
    public void guardarExcel(JTable tabla, java.io.File archivo) {
        DataOutputStream out = null;

        try {
            out = new DataOutputStream(new FileOutputStream(archivo));
            WritableWorkbook w = Workbook.createWorkbook(out);

            WritableSheet s = w.createSheet("Hoja Reservas", 0);

            //Para que salga el titulo de las columnas
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                for (int j = 0; j < tabla.getRowCount(); j++) {
                    Object titulo = tabla.getColumnName(i);
                    s.addCell(new Label(i, j, String.valueOf(titulo)));
                }
            }
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                for (int j = 0; j < tabla.getRowCount(); j++) {
                    Object object = tabla.getValueAt(j, i);
                    s.addCell(new Label(i, j + 1, String.valueOf(object)));
                }
            }

            w.write();
            w.close();

        } catch (FileNotFoundException ex) {
            Utils.muestraAlerta(ex);
        } catch (IOException | WriteException ex) {
            Utils.muestraAlerta(ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Utils.muestraAlerta(ex);
            }
        }
    }
}
