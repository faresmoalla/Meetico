package tn.esprit.meetico.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.google.zxing.WriterException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import tn.esprit.meetico.entity.Request;
import tn.esprit.meetico.repository.RequestRepository;

@Component
public class PDFGenerator {

	@Value("C:/Meetico/")
	private String pdfDir;

	@Value("dd MMMM yyyy HH:mm:ss")
	private String localDateFormat;

	@Value("C:/Meetico/meetico.png")
	private String logoImgPath;

	@Autowired
	private RequestRepository requestRepository;

	private static Font COURIER = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);
	private static Font COURIER_MEDIUM = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);
	private static Font COURIER_SMALL = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);

	public void generatePdf(Request request) throws WriterException, IOException {
		Document document = new Document(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(document,
					new FileOutputStream(
							pdfDir + (request.getNic().toString().length() == 8 ? request.getNic().toString()
									: "0" + request.getNic().toString()) + ".pdf"));
			document.open();
			addLogo(document);
			addTitle(document);
			addBody(document, request);
			addQRCode(document, request);
			document.close();
			request.setConverted(true);
			requestRepository.save(request);
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
	}

	private void addLogo(Document document) {
		try {
			Image img = Image.getInstance(logoImgPath);
			img.scalePercent(60, 60);
			img.setAlignment(Element.ALIGN_RIGHT);
			document.add(img);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	private void addTitle(Document document) throws DocumentException {
		String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(localDateFormat));
		Paragraph p1 = new Paragraph();
		leaveEmptyLine(p1, 3);
		p1.add(new Paragraph("Employee Invitation", COURIER));
		leaveEmptyLine(p1, 1);
		p1.add(new Paragraph("Invitation generated on " + localDateString, COURIER_MEDIUM));
		document.add(p1);
	}

	private void addBody(Document document, Request request) throws DocumentException {
		Paragraph paragraph = new Paragraph();
		leaveEmptyLine(paragraph, 3);
		paragraph.add((new Paragraph("\nNational Identity Card : " + request.getNic(), COURIER_SMALL)));
		paragraph.add((new Paragraph("\nFirst Name : " + request.getFirstName(), COURIER_SMALL)));
		paragraph.add((new Paragraph("\nLast Name : " + request.getLastName(), COURIER_SMALL)));
		paragraph.add((new Paragraph("\nGender : " + request.getGender(), COURIER_SMALL)));
		document.add(paragraph);
	}

	private void addQRCode(Document document, Request request) throws DocumentException, WriterException, IOException {
		QRCodeGenerator.generateQRCodeImage(request.getNic().toString(), 100, 100,
				"./src/main/resources/" + request.getEmail() + ".png");
		try {
			Paragraph p1 = new Paragraph();
			leaveEmptyLine(p1, 2);
			document.add(p1);
			Image img = Image.getInstance("./src/main/resources/" + request.getEmail() + ".png");
			img.setAlignment(Element.ALIGN_RIGHT);
			document.add(img);
			File file = new File("./src/main/resources/" + request.getEmail() + ".png");
			file.delete();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	private static void leaveEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

}
