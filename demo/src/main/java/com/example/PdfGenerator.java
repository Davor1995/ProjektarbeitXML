package com.example;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class PdfGenerator {

    public void generatePdfFromXml(String xmlFilePath, String pdfFilePath) {
        try {
            File xmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            PdfWriter writer = new PdfWriter(pdfFilePath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Adding a header
            document.add(new Paragraph("Lieferschein").setBold().setFontSize(20));

            // Extracting and adding Firm and Customer Information
            addFirmCustomerInfo(document, doc);

            // Extracting and adding Product Information
            addProductInfo(document, doc);

            // Closing the document
            document.close();
            System.out.println("PDF wurde erfolgreich erstellt.");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Fehler beim Erstellen des PDFs: " + e.getMessage());
        }
    }

    private void addFirmCustomerInfo(Document document, org.w3c.dom.Document doc) {
        NodeList firmaNodes = doc.getElementsByTagName("Firma");
        if (firmaNodes.getLength() > 0) {
            Element firma = (Element) firmaNodes.item(0);
            document.add(new Paragraph("Firma: " + firma.getElementsByTagName("Name").item(0).getTextContent()));
            document.add(new Paragraph("Adresse: "
                    + firma.getElementsByTagName("Straße").item(0).getTextContent() + ", "
                    + firma.getElementsByTagName("PLZ").item(0).getTextContent() + " "
                    + firma.getElementsByTagName("Stadt").item(0).getTextContent() + ", "
                    + firma.getElementsByTagName("Land").item(0).getTextContent()));
        }

        NodeList kundeNodes = doc.getElementsByTagName("Kunde");
        if (kundeNodes.getLength() > 0) {
            Element kunde = (Element) kundeNodes.item(0);
            document.add(new Paragraph("Kunde: " + kunde.getElementsByTagName("Name").item(0).getTextContent()));
            document.add(new Paragraph("Adresse: "
                    + kunde.getElementsByTagName("Straße").item(0).getTextContent() + ", "
                    + kunde.getElementsByTagName("PLZ").item(0).getTextContent() + " "
                    + kunde.getElementsByTagName("Stadt").item(0).getTextContent() + ", "
                    + kunde.getElementsByTagName("Land").item(0).getTextContent()));
        }
    }

    private void addProductInfo(Document document, org.w3c.dom.Document doc) {
        NodeList positions = doc.getElementsByTagName("Position");
        Table table = new Table(new float[]{1, 3, 1, 1, 1, 1});
        table.addHeaderCell("Artikelnummer");
        table.addHeaderCell("Bezeichnung");
        table.addHeaderCell("Menge");
        table.addHeaderCell("Einheit");
        table.addHeaderCell("Einzelpreis");
        table.addHeaderCell("Gesamtpreis");

        for (int i = 0; i < positions.getLength(); i++) {
            Element position = (Element) positions.item(i);
            table.addCell(new Cell().add(new Paragraph(position.getElementsByTagName("Artikelnummer").item(0).getTextContent())));
            table.addCell(new Cell().add(new Paragraph(position.getElementsByTagName("Bezeichnung").item(0).getTextContent())));
            table.addCell(new Cell().add(new Paragraph(position.getElementsByTagName("Menge").item(0).getTextContent())));
            table.addCell(new Cell().add(new Paragraph(position.getElementsByTagName("Einheit").item(0).getTextContent())));
            table.addCell(new Cell().add(new Paragraph(position.getElementsByTagName("Einzelpreis").item(0).getTextContent())));
            table.addCell(new Cell().add(new Paragraph(position.getElementsByTagName("Gesamtpreis").item(0).getTextContent())));
        }

        document.add(table);
    }
}
