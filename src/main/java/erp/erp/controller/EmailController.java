package erp.erp.controller;

import erp.erp.model.Email;
import erp.erp.service.EmailService;
import erp.erp.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailService sid;

    @Autowired
    PdfService pdfService;

    @PostMapping("/send-email")
    public String sendEmail(@RequestBody Email email){
       String to = email.getTo();
         String subject = email.getSubject();
            String body = email.getBody();
        sid.send(to, subject, body);
        return "Email sent successfully";
    }

}