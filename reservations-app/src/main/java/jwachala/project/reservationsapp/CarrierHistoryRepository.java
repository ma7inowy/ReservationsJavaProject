package jwachala.project.reservationsapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrierHistoryRepository extends JpaRepository<CarrierModel, String> {
    List<CarrierModel> findByCompanyName(String comapnyName);
}
