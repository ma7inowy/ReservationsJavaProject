package jwachala.project.reservationsapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CarrierOrderComparatorTests {
    @Test
    public void shouldCompareTwoCarrierOrders(){
        CarrierOrderModel carrierOrderModel = new CarrierOrderModel();
        carrierOrderModel.setPaid(true);
        carrierOrderModel.setOrderDate(LocalDate.now());

        CarrierOrderModel carrierOrderModel2 = new CarrierOrderModel();
        carrierOrderModel2.setPaid(false);
        carrierOrderModel2.setOrderDate(LocalDate.now().minusDays(5));

        CarrierOrderComparator sut = new CarrierOrderComparator();
        //1 o1.isPaid() && !o2.isPaid()
        var actual = sut.compare(carrierOrderModel,carrierOrderModel2);
        Assertions.assertThat(actual).isEqualTo(-1);
        //2 !o1.isPaid() && o2.isPaid()
        actual = sut.compare(carrierOrderModel2,carrierOrderModel);
        Assertions.assertThat(actual).isEqualTo(1);
        //3 o1.getOrderDate().isAfter(o2.getOrderDate())
        carrierOrderModel2.setPaid(true);
        actual = sut.compare(carrierOrderModel,carrierOrderModel2);
        Assertions.assertThat(actual).isEqualTo(1);

        //4 o1.getOrderDate().isBefore(o2.getOrderDate())
        actual = sut.compare(carrierOrderModel2,carrierOrderModel);
        Assertions.assertThat(actual).isEqualTo(-1);

        //5 o1 == o2
        carrierOrderModel2.setOrderDate(LocalDate.now());
        actual = sut.compare(carrierOrderModel2,carrierOrderModel);
        Assertions.assertThat(actual).isEqualTo(0);
    }
}
