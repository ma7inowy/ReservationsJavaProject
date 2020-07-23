package jwachala.project.reservationsapp;

import java.util.List;

public interface CarrierHistoryService {
    List<CarrierModel> getHistoryCarriersbyCompanyName(String companyName);

    List<CarrierOrderModel> refreshHistory();

    Iterable<CarrierModel> getCarrierHistoryList();
}
