package whatever;

import io.vertx.core.Vertx;



public class Start {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(Server.class.getName());
  }
}
