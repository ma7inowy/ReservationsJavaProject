package jwachala.project.reservationsapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrierOrderRepository extends JpaRepository<CarrierOrderModel, String> {
    List<CarrierOrderModel> findByCarrierId(String id);
}
