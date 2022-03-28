package tn.esprit.meetico.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.qrcode.WriterException;
import tn.esprit.meetico.entity.Reclamation;
import tn.esprit.meetico.repository.ReclamationRepository;

@Component("pdfReclamationGenerator")
@Service
public class ReclamationExporter {
	@Value("C:/Meetico/")
	private String pdfDir;

	@Value("dd MMMM yyyy HH:mm:ss")
	private String localDateFormat;

	@Value("C:/Meetico/meetico.png")
	private String logoImgPath;

	@Autowired
	ReclamationRepository reclamationRepository;

	private static Font COURIER = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);
	private static Font COURIER_MEDIUM = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);
	private static Font COURIER_SMALL = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);

	public void generatePdfReport(Integer idReclamation)
			throws WriterException, IOException, DocumentException, com.itextpdf.text.DocumentException {
		Document document = new Document();
		Reclamation reclamation = reclamationRepository.findById(idReclamation).orElse(null);

		try {

			PdfWriter.getInstance(document, new FileOutputStream(pdfDir + reclamation.getTitle() + ".pdf"));
			document.open();
			addLogo(document);
			addDocTitle(document);
			createBadge(document, reclamation);
			document.close();

		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
	}

	private void addLogo(Document document) throws DocumentException, com.itextpdf.text.DocumentException {
		try {
			Image img = Image.getInstance(logoImgPath);
			img.scalePercent(25, 25);
			img.setAlignment(Element.ALIGN_RIGHT);
			document.add(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addDocTitle(Document document)
			throws DocumentException, DocumentException, com.itextpdf.text.DocumentException {
		String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(localDateFormat));
		Paragraph p1 = new Paragraph();
		p1.add(new Paragraph("Reclamation Software", COURIER));
		p1.setAlignment(Element.ALIGN_CENTER);
		leaveEmptyLine(p1, 1);
		p1.add(new Paragraph("Subscription generated on " + localDateString, COURIER_MEDIUM));
		document.add(p1);
	}

	private void createBadge(Document document, Reclamation reclamation)
			throws DocumentException, com.itextpdf.text.DocumentException {
		Paragraph paragraph = new Paragraph();
		leaveEmptyLine(paragraph, 2);
		paragraph.add(new Paragraph("Title : " + reclamation.getTitle(), COURIER));
		paragraph.add(new Paragraph("Type  : " + reclamation.getType(), COURIER_SMALL));
		paragraph.add(new Paragraph("Priority : " + reclamation.getPriority(), COURIER_SMALL));
		paragraph.add(new Paragraph("Description : " + reclamation.getDescription(), COURIER_SMALL));
		paragraph.add(new Paragraph("Picture : " + reclamation.getPicture(), COURIER_SMALL));
		paragraph.add(new Paragraph("Sending date : " + reclamation.getSendingDate(), COURIER_SMALL));
		paragraph
				.add(new Paragraph("Last modification date : " + reclamation.getLastModificationDate(), COURIER_SMALL));
		document.add(paragraph);
	}

	private static void leaveEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
