package tn.esprit.service;


import java.awt.Color;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

//import com.lowagie.text.*;
	//import com.lowagie.text.pdf.*;

import tn.esprit.entity.Trip;
	 
	 
	public class TripPDFExporter {
	    private List<Trip> listTrips;
	     
	    public TripPDFExporter(List<Trip> listTrips) {
	        this.listTrips = listTrips;
	    }
	 
	    private void writeTableHeader(PdfPTable table) {
	        PdfPCell cell = new PdfPCell();
	        
	        cell.setPadding(6);
	         
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        
	         
	        cell.setPhrase(new Phrase("Trip ID", font));
	         
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("destination", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("start date", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("end date", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("object", font));
	        table.addCell(cell);   
	        
	        cell.setPhrase(new Phrase("entrepreneur", font));
	        table.addCell(cell);  
	    }
	     
	    private void writeTableData(PdfPTable table) {
	        for (Trip trip : listTrips) {
	            table.addCell(String.valueOf(trip.getIdTrip()));
	            table.addCell(trip.getDestination());
	            table.addCell(trip.getStartDate().toString());
	            table.addCell(trip.getEndDate().toString());
	            table.addCell(trip.getObject());
	            table.addCell(trip.getUser().getEmail());
	            
	        }
	    }
	     
	    public void export(HttpServletResponse response) throws DocumentException, IOException {
	        Document document = new Document(PageSize.A4);
	        PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(BaseColor.BLUE);
	         
	        Paragraph p = new Paragraph("List of Trips", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	         
	        document.add(p);
	         
	        PdfPTable table = new PdfPTable(6);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f,1.5f});
	        table.setSpacingBefore(10);
	         
	        writeTableHeader(table);
	        writeTableData(table);
	         
	        document.add(table);
	         
	        document.close();
	         
	    }
	}

