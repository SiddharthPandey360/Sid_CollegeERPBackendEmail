package erp.erp.service;
import erp.erp.Repository.StudentRepository;
import erp.erp.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private StudentRepository studentRepository;

    public void send(String to, String subject, String text){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("unitedcollege@leadder.sbs");
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
    public List<Student> liststudentbelowpercentage(double percentage){
        List<Student> students = studentRepository.findByAttendancePercentageLessThan(percentage);
        for(Student student : students){
            System.out.println("Student Name: " + student.getName() + ", Attendance Percentage: " + student.getAttendancePercentage());
        }
        return students;
    }

    public void getStudentInfoWhosePercentageIsLessThan(double percentage){
        List<Student> students = studentRepository.findByAttendancePercentageLessThan(percentage);
        for(Student student : students){
            String to = student.getEmail();
            String subject = "Low Attendance Alert";
            String text = "Dear " + student.getName() + ",\n\n" +
                    "Our records indicate that your attendance percentage is " + student.getAttendancePercentage() + "%, which is below the required threshold of " + percentage + "%.\n" +
                    "Please take the necessary steps to improve your attendance.\n\n" +
                    "Best regards,\n" +
                    "United College Administration";

            CompletableFuture.runAsync(() -> send(to, subject, text));
        }
    }
}