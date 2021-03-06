package jwachala.project.reservationsapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrierOrderRepository extends JpaRepository<CarrierOrderModel, String> {
    List<CarrierOrderModel> findByCarrierId(String id);

    @Query(value = "SELECT * FROM CARRIER_ORDER_MODEL WHERE CARRIER_ORDER_MODEL.CARRIER_ID = (SELECT ID FROM CARRIER_MODEL WHERE COMPANY_NAME = ?1)", nativeQuery = true)
    List<CarrierOrderModel> findCarrierOrdersByCompanyName(String companyName);

    @Query(value = "SELECT * FROM CARRIER_ORDER_MODEL WHERE CARRIER_ORDER_MODEL.CARRIER_ID = (SELECT ID FROM CARRIER_MODEL WHERE COMPANY_NAME = ?1 AND START_CITY = ?2)", nativeQuery = true)
    List<CarrierOrderModel> findCarrierOrdersByCompanyNameAndCity(String companyName, String city);

    CarrierOrderModel findByEmailAndCarrierId(String email, String id);

    @Query(value = "SELECT * FROM CARRIER_ORDER_MODEL WHERE EMAIL = ?1 AND PAID ='FALSE'", nativeQuery = true)
    Iterable<CarrierOrderModel> findUnpaidOrders(String email);

    List<CarrierOrderModel> findByPaid(boolean bool);

}
