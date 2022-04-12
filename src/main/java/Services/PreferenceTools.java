package Services;

import com.google.gson.Gson;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PreferenceTools {
  Logger log = LoggerFactory.getLogger(PreferenceTools.class);
  public  String updatePreference(String id, PreferenceRequest pref) throws MPException, MPApiException {
    MercadoPagoConfig.setAccessToken(Autentication.getToken());
    Preference result = new PreferenceClient().update(id, pref);
    return new Gson().toJson(result);
  }

  public  String createPreference() throws MPException, MPApiException {
    MercadoPagoConfig.setAccessToken(Autentication.getToken());
    List<PreferenceItemRequest> items = addItems();
    PreferenceRequest request = PreferenceRequest.builder().items(items).build();
    Preference result =  new PreferenceClient().create(request);
    return new Gson().toJson(result);
  }

  public  List<PreferenceItemRequest> addItems() {
    List<PreferenceItemRequest> items = new ArrayList<>();
    PreferenceItemRequest item =
        PreferenceItemRequest.builder()
            .title("Meu produto 1")
            .quantity(1)
            .unitPrice(new BigDecimal("100"))
            .build();
    items.add(item);
    PreferenceItemRequest item2 =
        PreferenceItemRequest.builder()
            .title("Meu produto 2")
            .quantity(1)
            .unitPrice(new BigDecimal("150"))
            .build();
    items.add(item2);
    return items;
  }


}
