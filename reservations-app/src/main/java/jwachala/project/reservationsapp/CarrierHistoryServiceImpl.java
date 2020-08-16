package jwachala.project.reservationsapp;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// BAZA DANYCH HISTORII PRZEWOZOW
// TRAFIAJA TAM PRZEWOZY Z PRZEDAWNIONA DATA LUB Z FLAGA (Z obiektu CarrierModel) realized = true

@NoArgsConstructor
@Service
public class CarrierHistoryServiceImpl implements CarrierHistoryService {

    private List<CarrierModel> carrierHistoryList;
    private CarrierService carrierService;
    private CarrierOrderService carrierOrderService;

   @Autowired
    private CarrierHistoryRepository carrierHistoryRepository;

    public CarrierHistoryServiceImpl(CarrierHistoryRepository carrierHistoryRepository, CarrierService carrierService, CarrierOrderService carrierOrderService) {
        this.carrierHistoryRepository = carrierHistoryRepository;
        this.carrierService = carrierService;
        this.carrierOrderService = carrierOrderService;
    }

    @Autowired
    public CarrierHistoryServiceImpl(CarrierService carrierService, CarrierOrderService carrierOrderService) {
        this.carrierService = carrierService;
        this.carrierOrderService = carrierOrderService;
    }

    @Override
    public List<CarrierModel> getHistoryCarriersbyCompanyName(String companyName) {
//        List<CarrierModel> carrierListbyCompanyName = new ArrayList<>();
//        for (var carrier : carrierHistoryList) {
//            if (carrier.getCompanyName().toLowerCase().equals(companyName.toLowerCase())) {
//                carrierListbyCompanyName.add(carrier);
//            }
//        }
//        return carrierListbyCompanyName;
        return carrierHistoryRepository.findByCompanyName(companyName);
    }

    @Override
    public List<CarrierModel> refreshHistory() {
//        List<CarrierModel> cMList = carrierService.getCarrierList();
//        List<CarrierModel> carriersforRemoveList = new ArrayList<>();
//
//        // przenoszenie obiektow ofert przewozow z CarrierRepository -> CarrierHistory
//        for (int i = 0; i< cMList.size();i++) {
//            if (cMList.get(i).getDate().compareTo(LocalDate.now()) < 0 || cMList.get(i).isRealized()) {
//                carrierHistoryList.add(cMList.get(i));
//                carriersforRemoveList.add(cMList.get(i));
//            }
//        }
//
//        // usuwanie starych zleceń/biletow
//        List<CarrierOrderModel> CarrierOrderforRemoveList = new ArrayList<>();
//        for(CarrierOrderModel coM : carrierOrderService.getCarrierOrderListIterable()){
//            for(CarrierModel cM : carriersforRemoveList){
//                if(coM.getCarrierId().equals(cM.getId()))
//                    CarrierOrderforRemoveList.add(coM);
//            }
//        }
//
//        //usuwanie starych przewozów
//        for(CarrierModel i : carriersforRemoveList)
//            carrierService.getCarrierList().remove(i);
//
//        // usuwanie starych biletow / zlecen z bazy
//        for(CarrierOrderModel i : CarrierOrderforRemoveList){
//            carrierOrderService.removeCarrierOrder(i);
//        }
//
//        return carriersforRemoveList;

//        List<CarrierModel> cMList = carrierService.getCarrierRepository().findAll();
        Iterable<CarrierModel> cMList = carrierService.getAllCarriers();
        List<CarrierModel> carriersforRemoveList = new ArrayList<>();

        // przenoszenie obiektow ofert przewozow z CarrierRepository -> CarrierHistory
        for (CarrierModel cM : cMList) {
            if (cM.getDate().compareTo(LocalDate.now()) < 0 || cM.isRealized()) {
                carrierHistoryRepository.save(cM);
                carriersforRemoveList.add(cM);
            }
        }

        // usuwanie starych zleceń/biletow
        List<CarrierOrderModel> CarrierOrderforRemoveList = new ArrayList<>();
        for(CarrierOrderModel coM : carrierOrderService.getCarrierOrderListIterable()){
            for(CarrierModel cM : carriersforRemoveList){
                if(coM.getCarrierId().equals(cM.getId()))
                    CarrierOrderforRemoveList.add(coM);
            }
        }

        //usuwanie starych przewozów
        for(CarrierModel i : carriersforRemoveList)
            carrierService.deleteCarrier(i);

        // usuwanie starych biletow / zlecen z bazy
        for(CarrierOrderModel i : CarrierOrderforRemoveList){
            carrierOrderService.removeCarrierOrder(i);
        }

        return carriersforRemoveList;
    }

    @Override
    public Iterable<CarrierModel> getCarrierHistoryList() {
        return carrierHistoryList;
    }
}
