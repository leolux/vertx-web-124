package whatever;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.impl.LoggerFactory;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.User;


public class MyAuthProviderImpl implements AuthProvider {

  private final Vertx vertx;

  private Logger logger = LoggerFactory.getLogger(MyAuthProviderImpl.class);

  public MyAuthProviderImpl(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {
    vertx.executeBlocking(blocking -> {
      try {
        // Simulate external API
        Thread.sleep(100);
      } catch (Exception e) {
        e.printStackTrace();
      }
      blocking.complete();
    }, ar -> {
      System.out.println("User authenticated succesfull");
      resultHandler.handle(Future.succeededFuture(new MyUser(this)));
    });
  }


  public void hasPermission(String permission, Handler<AsyncResult<Boolean>> resultHandler) {
    vertx.executeBlocking(blocking -> {
      try {
        // Simulate external API
        Thread.sleep(100);
      } catch (Exception e) {
        e.printStackTrace();
      }
      blocking.complete();
    }, ar -> {
      resultHandler.handle(Future.succeededFuture(true));
    });
  }

}
