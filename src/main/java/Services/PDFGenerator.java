package Services;

import Entite.Maintenance;
import Entite.Equipment;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PDFGenerator {
    public static void generateMaintenancePdf(List<Maintenance> maintenances) {
        Document document = new Document();
        try {
            String filePath = System.getProperty("user.dir") + "/maintenances_list.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            document.open();

            // Ajouter un titre
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
            Paragraph title = new Paragraph("Liste des Maintenances", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Création du tableau avec des colonnes
            PdfPTable table = new PdfPTable(6);  // MaintenanceID, EquipementID, Date, Description, Cout, Effectué par
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Ajout des cellules d'en-tête
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.WHITE);
            PdfPCell header1 = new PdfPCell(new Phrase("ID Maintenance", headerFont));
            PdfPCell header2 = new PdfPCell(new Phrase("ID Equipement", headerFont));
            PdfPCell header3 = new PdfPCell(new Phrase("Date", headerFont));
            PdfPCell header4 = new PdfPCell(new Phrase("Description", headerFont));
            PdfPCell header5 = new PdfPCell(new Phrase("Coût", headerFont));
            PdfPCell header6 = new PdfPCell(new Phrase("Effectué par", headerFont));

            header1.setBackgroundColor(BaseColor.DARK_GRAY);
            header2.setBackgroundColor(BaseColor.DARK_GRAY);
            header3.setBackgroundColor(BaseColor.DARK_GRAY);
            header4.setBackgroundColor(BaseColor.DARK_GRAY);
            header5.setBackgroundColor(BaseColor.DARK_GRAY);
            header6.setBackgroundColor(BaseColor.DARK_GRAY);

            header1.setHorizontalAlignment(Element.ALIGN_CENTER);
            header2.setHorizontalAlignment(Element.ALIGN_CENTER);
            header3.setHorizontalAlignment(Element.ALIGN_CENTER);
            header4.setHorizontalAlignment(Element.ALIGN_CENTER);
            header5.setHorizontalAlignment(Element.ALIGN_CENTER);
            header6.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(header1);
            table.addCell(header2);
            table.addCell(header3);
            table.addCell(header4);
            table.addCell(header5);
            table.addCell(header6);

            // Ajouter les données dans le tableau
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Maintenance maintenance : maintenances) {
                table.addCell(String.valueOf(maintenance.getMaintenanceID()));
                table.addCell(String.valueOf(maintenance.getEquipementId()));
                table.addCell(sdf.format(maintenance.getMaintenanceDate()));
                table.addCell(maintenance.getDescription());  // Text content
                table.addCell(String.valueOf(maintenance.getCout()));  // Numeric content
                table.addCell(String.valueOf(maintenance.getEffectuePar()));
            }

            // Ajouter le tableau au document
            document.add(table);

            document.close();
            System.out.println("✅ PDF des maintenances généré avec succès : " + filePath);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }


    public static void generateEquipementPdf(List<Equipment> equipements) {
        Document document = new Document();
        try {
            String filePath = System.getProperty("user.dir") + "/equipements_list.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            document.open();

            // Ajouter un titre
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
            Paragraph title = new Paragraph("Liste des Equipements", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Création du tableau avec des colonnes
            PdfPTable table = new PdfPTable(7);  // Adjusted to have columns for all fields
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Ajout des cellules d'en-tête
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.WHITE);
            PdfPCell header1 = new PdfPCell(new Phrase("ID Equipement", headerFont));
            PdfPCell header2 = new PdfPCell(new Phrase("Nom", headerFont));
            PdfPCell header3 = new PdfPCell(new Phrase("Catégorie", headerFont));
            PdfPCell header4 = new PdfPCell(new Phrase("Quantité", headerFont));
            PdfPCell header5 = new PdfPCell(new Phrase("Date d'Achat", headerFont));
            PdfPCell header6 = new PdfPCell(new Phrase("Date de Maintenance", headerFont));
            PdfPCell header7 = new PdfPCell(new Phrase("État", headerFont));

            header1.setBackgroundColor(BaseColor.DARK_GRAY);
            header2.setBackgroundColor(BaseColor.DARK_GRAY);
            header3.setBackgroundColor(BaseColor.DARK_GRAY);
            header4.setBackgroundColor(BaseColor.DARK_GRAY);
            header5.setBackgroundColor(BaseColor.DARK_GRAY);
            header6.setBackgroundColor(BaseColor.DARK_GRAY);
            header7.setBackgroundColor(BaseColor.DARK_GRAY);

            header1.setHorizontalAlignment(Element.ALIGN_CENTER);
            header2.setHorizontalAlignment(Element.ALIGN_CENTER);
            header3.setHorizontalAlignment(Element.ALIGN_CENTER);
            header4.setHorizontalAlignment(Element.ALIGN_CENTER);
            header5.setHorizontalAlignment(Element.ALIGN_CENTER);
            header6.setHorizontalAlignment(Element.ALIGN_CENTER);
            header7.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(header1);
            table.addCell(header2);
            table.addCell(header3);
            table.addCell(header4);
            table.addCell(header5);
            table.addCell(header6);
            table.addCell(header7);

            // Ajouter les données dans le tableau
            for (Equipment equipement : equipements) {
                table.addCell(String.valueOf(equipement.getEquipementID()));
                table.addCell(equipement.getEquipementName());
                table.addCell(equipement.getCategory());
                table.addCell(String.valueOf(equipement.getQuantity()));
                table.addCell(equipement.getAchatDate().toString());  // Convert Date to String
                table.addCell(equipement.getLastMaintenanceDate().toString());  // Convert Date to String
                table.addCell(equipement.getEtat().name());  // Use Enum value as String
            }

            // Ajouter le tableau au document
            document.add(table);

            document.close();
            System.out.println("✅ PDF des équipements généré avec succès : " + filePath);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

}
