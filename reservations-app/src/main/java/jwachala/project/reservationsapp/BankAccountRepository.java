package jwachala.project.reservationsapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountModel,String> {
    BankAccountModel findByEmail(String email);
}
