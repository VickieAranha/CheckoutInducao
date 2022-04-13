package Services;

import com.mercadopago.MercadoPagoConfig;

public class Autentication {
  public static void setup() {
    MercadoPagoConfig.setAccessToken(Autentication.getToken());
  }

  public static String getToken() {
    return "TEST-4845112937733789-040521-50a58d714eb0b7b18cc50f6a981c50b0-92290066";
  }
}
