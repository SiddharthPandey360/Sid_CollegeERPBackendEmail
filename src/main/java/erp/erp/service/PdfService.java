package erp.erp.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import erp.erp.Repository.StudentRepository;
import erp.erp.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private StudentRepository studentRepository;

    public String generateLowAttendancePdf(double percentage) throws IOException {
        List<Student> students = studentRepository.findByAttendancePercentageLessThan(percentage);

        // Create reports directory if not exists
        Path reportsDir = Paths.get("reports");
        if (!Files.exists(reportsDir)) {
            Files.createDirectories(reportsDir);
        }

        String fileName = "low_attendance_" + percentage + ".pdf";
        String filePath = reportsDir.resolve(fileName).toString();

        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Students with Attendance Below " + percentage + "%"));

        // Create table with 4 columns
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 3, 2})).useAllAvailableWidth();
        table.addHeaderCell("ID");
        table.addHeaderCell("Name");
        table.addHeaderCell("Email");
        table.addHeaderCell("Attendance %");

        for (Student student : students) {
            table.addCell(String.valueOf(student.getId()));
            table.addCell(student.getName());
            table.addCell(student.getEmail());
            table.addCell(String.format("%.2f", student.getAttendancePercentage()));
        }

        document.add(table);
        document.close();

        return filePath;
    }
}