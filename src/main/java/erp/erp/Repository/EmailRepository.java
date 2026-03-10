package erp.erp.Repository;

import erp.erp.controller.EmailController;
import erp.erp.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EmailRepository extends JpaRepository<Email, Long> {
}

