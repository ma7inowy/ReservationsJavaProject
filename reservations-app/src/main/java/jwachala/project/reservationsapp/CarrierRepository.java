package jwachala.project.reservationsapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface CarrierRepository extends JpaRepository<CarrierModel, String> {
    List<CarrierModel> findByStartCity(String startCity);

    List<CarrierModel> findByStartCityAndDestinationCity(String startCity, String destinationCity);

    List<CarrierModel> findByCompanyName(String companyName);
}
