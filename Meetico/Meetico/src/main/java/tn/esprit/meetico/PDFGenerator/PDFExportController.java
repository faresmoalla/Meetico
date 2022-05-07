package tn.esprit.meetico.PDFGenerator;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.meetico.entity.Publication;
import tn.esprit.meetico.repository.PublicationRepository;




@CrossOrigin
@RestController
@RequestMapping("/pdf")
public class PDFExportController {
@Autowired
PublicationRepository pubRepo;
   

    @GetMapping(value = "/pdf",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> DriverReport() throws IOException {
        List<Publication>drivers = (List<Publication>) pubRepo.findAll();

        ByteArrayInputStream bis = PDFGeneratorService.customerPDFReport(drivers);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=customers.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}