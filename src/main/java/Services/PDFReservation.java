package Services;
import Entite.Activity;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PDFReservation {
    public static void generateActivityPdf(Activity activite, int membreId) {
        Document document = new Document();
        try {
            String filePath = System.getProperty("user.dir") + "/reservation_activite_" + activite.getActivityId() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            document.open();

            // Ajouter un titre
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
            Paragraph title = new Paragraph("Détails de la Réservation", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Création du tableau avec 2 colonnes (Clé - Valeur)
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Ajout des cellules d'en-tête
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.WHITE);
            PdfPCell header1 = new PdfPCell(new Phrase("Détail", headerFont));
            PdfPCell header2 = new PdfPCell(new Phrase("Valeur", headerFont));
            header1.setBackgroundColor(BaseColor.DARK_GRAY);
            header2.setBackgroundColor(BaseColor.DARK_GRAY);
            header1.setHorizontalAlignment(Element.ALIGN_CENTER);
            header2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(header1);
            table.addCell(header2);

            // Ajout des données dans le tableau
            addTableRow(table, "Activité", activite.getActivityName());
            addTableRow(table, "Description", activite.getDescription());
            addTableRow(table, "Date", formatDate(activite.getDate()));
            addTableRow(table, "Heure", formatTime(activite.getHour()));
            addTableRow(table, "Durée", activite.getDuration() + " minutes");
            addTableRow(table, "Places disponibles", String.valueOf(activite.getMaxMembers()));
            addTableRow(table, "Réservé par", "Membre ID " + membreId);

            // Ajouter le tableau au document
            document.add(table);

            document.close();
            System.out.println("✅ PDF généré avec succès : " + filePath);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void addTableRow(PdfPTable table, String key, String value) {
        PdfPCell cell1 = new PdfPCell(new Phrase(key));
        PdfPCell cell2 = new PdfPCell(new Phrase(value));
        cell1.setPadding(5);
        cell2.setPadding(5);
        table.addCell(cell1);
        table.addCell(cell2);
    }

    private static String formatDate(Date date) {
        if (date == null) return "Non défini";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    private static String formatTime(Time time) {
        if (time == null) return "Non défini";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(time);
    }
}
