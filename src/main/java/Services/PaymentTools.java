package Services;

import com.google.gson.Gson;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.merchantorder.MerchantOrderCreateRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentOrderRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.client.paymentmethod.PaymentMethodClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.payment.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class PaymentTools {
  Logger log = LoggerFactory.getLogger(PaymentTools.class);

  public PaymentTools() {
    Autentication.setup();
  }

  public String createPayment(String prefId) throws MPException, MPApiException {
    PaymentClient client = new PaymentClient();
    Payment result = new Payment();
    PaymentCreateRequest paymentCreateRequest = fillObject(prefId);

    try {
      result = client.create(paymentCreateRequest);
    } catch (Exception e) {
      return "Error in payment create request :" + ((MPApiException) e).getApiResponse().getContent();
    }
    return new Gson().toJson(result);
  }

  public String createdPix(String prefId) throws MPException, MPApiException {
    PaymentClient client = new PaymentClient();
    MerchantOrderClient moClient = new MerchantOrderClient();
    MerchantOrder merchantOrder;
    merchantOrder = moClient.create(MerchantOrderCreateRequest.builder().preferenceId(prefId).build());
    PaymentCreateRequest paymentCreateRequest =
        PaymentCreateRequest.builder()
            .transactionAmount(new BigDecimal("290"))
            .description("Título do produto")
            .paymentMethodId("pix")
            .dateOfExpiration(OffsetDateTime.of(2023, 1, 10, 10, 10, 10, 0, ZoneOffset.UTC))
            .payer(
                PaymentPayerRequest.builder()
                    .email("test@test.com")
                    .firstName("Test")
                    .identification(
                        IdentificationRequest.builder().type("CPF").number("19119119100").build())
                    .build())
            .order(
                PaymentOrderRequest.builder()
                    .id(merchantOrder.getId()).build()

            )
            .build();

    return new Gson().toJson(client.create(paymentCreateRequest)); // erro id invalido

  }

  public String getAllPaymentsMethods() throws MPException, MPApiException {

    PaymentMethodClient client = new PaymentMethodClient();
    return new Gson().toJson(client.list().getResults());
  }

  public PaymentCreateRequest fillObject(String prefId) throws MPException, MPApiException {
    MerchantOrderClient moClient = new MerchantOrderClient();
    MerchantOrder merchantOrder;
    merchantOrder = moClient.create(MerchantOrderCreateRequest.builder().preferenceId(prefId).build());

    PaymentCreateRequest obj =  PaymentCreateRequest.builder()
        .dateOfExpiration(OffsetDateTime.now())
        .statementDescriptor("compras mercadopago")
        .paymentMethodOptionId("bolbradesco")
        .paymentMethodId("ticket")
        .transactionAmount(new BigDecimal("290"))
        .payer(
            PaymentPayerRequest.builder()
                .firstName("Gabriela")
                .lastName("bertoluzo")
                .identification(
                    IdentificationRequest.builder()
                        .type("CPF")
                        .number("22438306041").build())
                .build())
        .order(
            PaymentOrderRequest.builder()
                .type("mercadopago")
                .id(merchantOrder.getId()
                ).build()
        ).build();
    return obj;

  }

}
