package Services;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.merchantorder.MerchantOrderCreateRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentOrderRequest;
import com.mercadopago.client.paymentmethod.PaymentMethodClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.paymentmethod.PaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PaymentTools {
   Logger log = LoggerFactory.getLogger(PaymentTools.class);

  public  Payment createPayment(String prefId) throws MPException, MPApiException {
    PaymentClient client = new PaymentClient();
    MerchantOrder merchantOrder;
    MerchantOrderClient moClient = new MerchantOrderClient();
    Payment result = new Payment();
    merchantOrder = moClient.create(MerchantOrderCreateRequest.builder().preferenceId(prefId).build());
    PaymentCreateRequest paymentCreateRequest =
        PaymentCreateRequest.builder().order(
            PaymentOrderRequest.builder()
                .id(merchantOrder.getId()).build()
        ).build();

    result = client.create(paymentCreateRequest);
    PaymentMethodClient clientePayMeth = new PaymentMethodClient();
    clientePayMeth.list().getResults();
    return result;
  }

  public List<PaymentMethod> getAllPaymentsMethods() throws MPException, MPApiException {
    MercadoPagoConfig.setAccessToken(Autentication.getToken());
   // PaymentMethod pay = new PaymentMethod();
    PaymentMethodClient client = new PaymentMethodClient();
    return client.list().getResults();
  }

}
