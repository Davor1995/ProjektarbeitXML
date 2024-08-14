package com.example;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class PdfGenerator {

    public void createPdf(File file) {
        // Implementierung der PDF-Erstellung
        System.out.println("Erstelle PDF aus Datei: " + file.getPath());
        // Weitere Implementierung...
    }

    public void generatePdfFromXml(String xmlFilePath, String pdfFilePath) {
        try {
            // XML-Datei einlesen und parsen
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // PDF-Dokument erstellen
            PdfWriter writer = new PdfWriter(pdfFilePath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Titel hinzufügen
            document.add(new Paragraph("Lieferschein"));

            // Kundeinformationen einlesen
            NodeList kundeList = doc.getElementsByTagName("kunde");
            Node kundeNode = kundeList.item(0);
            Element kundeElement = (Element) kundeNode;

            String kundeName = kundeElement.getElementsByTagName("name").item(0).getTextContent();
            String kundeAdresse = kundeElement.getElementsByTagName("adresse").item(0).getTextContent();
            String kundePLZOrt = kundeElement.getElementsByTagName("plzort").item(0).getTextContent();

            // Kundeninformationen zum PDF hinzufügen
            document.add(new Paragraph("Kunde: " + kundeName));
            document.add(new Paragraph("Adresse: " + kundeAdresse));
            document.add(new Paragraph("PLZ/Ort: " + kundePLZOrt));

            // Tabelle für die Artikel
            float[] pointColumnWidths = {150F, 150F, 150F};
            Table table = new Table(pointColumnWidths);
            table.addCell("Artikelnummer");
            table.addCell("Bezeichnung");
            table.addCell("Menge");

            // Artikelinformationen einlesen
            NodeList artikelList = doc.getElementsByTagName("artikel");
            for (int i = 0; i < artikelList.getLength(); i++) {
                Node artikelNode = artikelList.item(i);
                Element artikelElement = (Element) artikelNode;

                String artikelNummer = artikelElement.getElementsByTagName("artikelnummer").item(0).getTextContent();
                String artikelBezeichnung = artikelElement.getElementsByTagName("bezeichnung").item(0).getTextContent();
                String artikelMenge = artikelElement.getElementsByTagName("menge").item(0).getTextContent();

                table.addCell(artikelNummer);
                table.addCell(artikelBezeichnung);
                table.addCell(artikelMenge);
            }

            document.add(table);
            document.close();

            System.out.println("PDF wurde erfolgreich erstellt.");

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
