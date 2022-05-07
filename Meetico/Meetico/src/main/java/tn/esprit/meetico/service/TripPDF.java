package tn.esprit.meetico.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDPage;

import lombok.AllArgsConstructor;
import tn.esprit.meetico.entity.FileTrip;
import tn.esprit.meetico.entity.Trip;
import tn.esprit.meetico.entity.User;

@AllArgsConstructor
public class TripPDF {

	private Trip trip;
	private Set<User> listuser;

	private void addTableHeader(PdfPTable table) {
		Stream.of(" ", "Data").forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(columnTitle));
			table.addCell(header);
		});
	}

	private void addRows(PdfPTable table) throws URISyntaxException, IOException {
		String id = trip.getIdTrip().toString();
		String destination = trip.getDestination();
		String startdate = trip.getStartDate().toString();
		String enddate = trip.getEndDate().toString();
		String objet = trip.getObject();
		String entrepreneur = trip.getUser().getEmail();
		PdfPCell horizontalAlignCell1 = new PdfPCell(new Phrase("ID trip :"));
		horizontalAlignCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(horizontalAlignCell1);
		PdfPCell verticalAlignCell11 = new PdfPCell(new Phrase(id));
		verticalAlignCell11.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(verticalAlignCell11);
		PdfPCell horizontalAlignCell2 = new PdfPCell(new Phrase("destination :"));
		horizontalAlignCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(horizontalAlignCell2);
		PdfPCell verticalAlignCell22 = new PdfPCell(new Phrase(destination));
		verticalAlignCell22.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(verticalAlignCell22);
		PdfPCell horizontalAlignCell3 = new PdfPCell(new Phrase("start date"));
		horizontalAlignCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(horizontalAlignCell3);
		PdfPCell verticalAlignCell33 = new PdfPCell(new Phrase(startdate));
		verticalAlignCell33.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(verticalAlignCell33);
		PdfPCell horizontalAlignCell4 = new PdfPCell(new Phrase("end date"));
		horizontalAlignCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(horizontalAlignCell4);
		PdfPCell verticalAlignCell44 = new PdfPCell(new Phrase(enddate));
		verticalAlignCell44.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(verticalAlignCell44);
		PdfPCell horizontalAlignCell5 = new PdfPCell(new Phrase("Objet"));
		horizontalAlignCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(horizontalAlignCell5);
		PdfPCell verticalAlignCell55 = new PdfPCell(new Phrase(objet));
		verticalAlignCell55.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(verticalAlignCell55);
		PdfPCell horizontalAlignCell6 = new PdfPCell(new Phrase("entrepreneur"));
		horizontalAlignCell6.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(horizontalAlignCell6);
		PdfPCell verticalAlignCell66 = new PdfPCell(new Phrase(entrepreneur));
		verticalAlignCell66.setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.addCell(verticalAlignCell66);
		/*
		 * Image img = Image.getInstance(path.toAbsolutePath().toString());
		 * img.scalePercent(10);
		 * 
		 * PdfPCell imageCell = new PdfPCell(img); table.addCell(imageCell);
		 */
	}
	

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();

		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);

		cell.setPhrase(new Phrase("first name", font));

		table.addCell(cell);

		cell.setPhrase(new Phrase("last name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("gender", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("email", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("city", font));
		table.addCell(cell);

		
	}
	private void writeTableData(PdfPTable table) {
		for (User user : listuser) {
			table.addCell(user.getFirstName());
			table.addCell(user.getLastName());
			table.addCell(user.getGender().toString());
			table.addCell(user.getEmail());
			table.addCell(user.getCity());
			
		}
	}


	public void export(HttpServletResponse response) throws DocumentException, IOException, URISyntaxException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(BaseColor.BLUE);

		Paragraph p = new Paragraph("detail of Trip", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f });
		table.setSpacingBefore(10);

		addTableHeader(table);
		addRows(table);

		document.add(table);

	    FileTrip files = trip.getFiles();
		
		
		PdfPTable table1 = new PdfPTable(5);
		table1.setWidthPercentage(100f);
		table1.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 3.0f, 1.5f });
		table1.setSpacingBefore(10);

		writeTableHeader(table1);
		writeTableData(table1);

		document.add(table1);
		
		
		
		
			
			byte[] byt = files.getData();
			// ImageIcon imageIcon = new ImageIcon(byt);
			// imageIcon.getImage();

			ByteArrayInputStream bais = new ByteArrayInputStream(byt);
			BufferedImage img = ImageIO.read(bais);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "png", baos);
			Image iTextImage = Image.getInstance(baos.toByteArray());
			iTextImage.scaleAbsolute(100, 100);
			document.add(iTextImage);
			
			
			

		
		document.close();
	}

}
