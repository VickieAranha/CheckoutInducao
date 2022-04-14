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
  private static final Logger log = LoggerFactory.getLogger(Routes.class);
  public void init() {
    after((req,res )-> {
      res.type("application/json;charset=UTF-8");
    } );
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
      logError(e);
    });
    exception(MPApiException.class, (e, req, res) ->
    {
      logError(e);
    });


  }
  public void logError(Exception e){
    log.error("Erro imprevisto: " , e);
  }
}


