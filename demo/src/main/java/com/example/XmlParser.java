package com.example;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class XmlParser {

    public String parseXml(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            return "XML-Datei erfolgreich geladen!\n\n" + "Inhalt der Datei:\n" + doc.getDocumentElement().getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
            return "Fehler beim Laden der XML-Datei";
        }
    }
}
