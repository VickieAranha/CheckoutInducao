package Routes;

import Services.PaymentTools;
import Services.PreferenceTools;
import com.google.gson.Gson;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static spark.Spark.*;

public class Routes {
  public void init() {
    Logger log = LoggerFactory.getLogger(Routes.class);
    path("Payment", () -> {
      get("/MethodAllowed", (req, res) -> new PaymentTools().getAllPaymentsMethods());
      get("/:id", (req, res) -> new PaymentTools().createPayment(req.params(":id")));
      get("/pix/:id", (req, res) -> new PaymentTools().createdPix(":id"));
    });
    path("Preferences", () -> {
      get("", (req, res) -> new PreferenceTools().createPreference());
      post("/:id", (req, res) ->
          new PreferenceTools().updatePreference(req.params(":id"), new Gson().fromJson(req.body(), PreferenceRequest.class)));
    });

    exception(MPException.class, (e, req, res) ->
    {
      //TODO: criar responseApi para mapear retornos de erros
      log.error(e.fillInStackTrace().getMessage());
    });
    exception(MPApiException.class, (e, req, res) ->
    {
      //TODO: criar responseApi para mapear retornos de erros
      log.error(e.fillInStackTrace().getMessage());
    });
    /*exception(Exception.class, (e, req, res) ->
        log.error(e.getMessage()));*/
  }
}


