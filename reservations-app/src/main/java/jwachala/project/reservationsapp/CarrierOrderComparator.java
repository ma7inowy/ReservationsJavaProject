package jwachala.project.reservationsapp;

import java.util.Comparator;

public class CarrierOrderComparator implements Comparator<CarrierOrderModel> {

    @Override
    public int compare(CarrierOrderModel o1, CarrierOrderModel o2) {
        if (o1.isPaid() && !o2.isPaid()) return -1;
        else if (!o1.isPaid() && o2.isPaid()) return 1;
        else if (o1.getOrderDate().isAfter(o2.getOrderDate())) return 1;
        else if (o1.getOrderDate().isBefore(o2.getOrderDate())) return -1;
        else return 0;
    }
}
