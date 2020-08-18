package jwachala.project.reservationsapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
// TRAFIAJA TAM PRZEWOZY Z PRZEDAWNIONA DATA LUB Z FLAGA (Z obiektu CarrierModel) realized = true
@Repository
public interface CarrierHistoryRepository extends JpaRepository<CarrierModel, String> {
    List<CarrierModel> findByCompanyName(String comapnyName);
}
