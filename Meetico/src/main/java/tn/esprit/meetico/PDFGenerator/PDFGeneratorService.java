package tn.esprit.meetico.PDFGenerator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import tn.esprit.meetico.entity.Publication;
import tn.esprit.meetico.repository.PublicationRepository;
import tn.esprit.meetico.service.PublicationServiceImpl;








public class PDFGeneratorService {
	@Autowired
	PublicationServiceImpl pubServ;
	@Autowired
	public static PublicationRepository pubRepo;
	
	private static Logger logger = LoggerFactory.getLogger(PDFGeneratorService.class);
	
	static String  logoImgPath ="C:\\Users\\Aminous\\Documents\\Workspace STS 4.13.0\\Meetico-Backend-Amine-Zied-Radhwen\\src\\main\\resources\\static\\meetico.png";

	private static void addLogo(Document document) throws DocumentException, com.itextpdf.text.DocumentException {
		try {
			Image img = Image.getInstance(logoImgPath);
			img.scalePercent(25, 25);
			img.setAlignment(Element.ALIGN_RIGHT);
			document.add(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/*
	public static Date currentSqlDate = new Date(System.currentTimeMillis());
	public static List<Publication> listp2 = pubRepo.getPubToday(currentSqlDate);
	*/
	
	
	
	public static  ByteArrayInputStream customerPDFReport(List<Publication> listp) {
		
		
		 
		 
		 
		
		
		
		Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
        	
        	PdfWriter.getInstance(document, out);
            document.open();
            addLogo(document);
			// Add Text to PDF file ->
			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.RED);
			Paragraph para = new Paragraph( "Les Publication d'aujourdhui", font);
			
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			document.add(Chunk.NEWLINE);
			
			
			Font font2 = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.RED);
			
			Paragraph para2 = new Paragraph( "Les Publication D'aujourd'hui", font2);
			para.setAlignment(Element.ALIGN_LEFT);
			document.add(para2);
			document.add(Chunk.NEWLINE);
			
			
			
        	PdfPTable table = new PdfPTable(5);
        	// Add PDF Table Header ->
			Stream.of("Date", "Content ", "Nombre Likes","Nombres Dislikes","user")
			    .forEach(headerTitle -> {
			          PdfPCell header = new PdfPCell();
			          Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			          header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			          header.setHorizontalAlignment(Element.ALIGN_CENTER);
			          header.setBorderWidth(2);
			          header.setPhrase(new Phrase(headerTitle, headFont));
			          table.addCell(header);
			    });
			
			
			//List<Publication> listp = pubRepo.findAll;
			
			
			   Date currentSqlDate = new Date(System.currentTimeMillis());
			 for (Publication pub: listp	) {
				
	            if(pub.getDate().getDay()==(currentSqlDate.getDay())  && pub.getDate().getMonth()==(currentSqlDate.getMonth())
	            		
	            		 && pub.getDate().getYear()==(currentSqlDate.getYear())	)
	            		 {
	            	
	           
	            
	            	PdfPCell date = new PdfPCell(new Phrase(String.valueOf(pub.getDate())));
	            
	            	date.setPaddingLeft(4);
	            	date.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            	date.setHorizontalAlignment(Element.ALIGN_CENTER);
	                table.addCell(date);

	                PdfPCell content = new PdfPCell(new Phrase(pub.getContents()));
	                content.setPaddingLeft(4);
	                content.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                content.setHorizontalAlignment(Element.ALIGN_LEFT);
	                table.addCell(content);

	                
	                PdfPCell nblikes = new PdfPCell(new Phrase(String.valueOf(pub.getLikes().size())   ));
	                nblikes.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                nblikes.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                nblikes.setPaddingRight(4);
	                table.addCell(nblikes);
	                
	                
	                PdfPCell nbdislike = new PdfPCell(new Phrase(String.valueOf(pub.getDislikes().size())   ));
	                nbdislike.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                nbdislike.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                nbdislike.setPaddingRight(4);
	                table.addCell(nbdislike);
	                
	                PdfPCell user = new PdfPCell(new Phrase(String.valueOf(pub.getUserr().getFirstName())   ));
	                user.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                user.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                user.setPaddingRight(4);
	                table.addCell(user);
	                
	            }
	                
	                
	            }
            
            
            
            
            
            document.add(table);
            
            document.close();
            
            
          
            
            
        }catch(DocumentException e) {
        	logger.error(e.toString());
        }
        
		return new ByteArrayInputStream(out.toByteArray());
	}
	
	
	
	
	
	
}