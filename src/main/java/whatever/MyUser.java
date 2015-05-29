package whatever;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AbstractUser;
import io.vertx.ext.auth.AuthProvider;

public class MyUser extends AbstractUser {
  
  private MyAuthProviderImpl authProvider;
  
  MyUser(MyAuthProviderImpl authProvider) {
    this.authProvider = authProvider;
  }

  @Override
  public JsonObject principal() {
    JsonObject p = new JsonObject();
    p.put("username", "doesnt");
    p.put("password", "matter");
    return p;
  }

  @Override
  public void setAuthProvider(AuthProvider authProvider) {
    if (authProvider instanceof MyAuthProviderImpl) {
      this.authProvider = (MyAuthProviderImpl) authProvider;
    } else {
      throw new IllegalArgumentException("Not a MyAuthProviderImpl");
    }

  }

  @Override
  protected void doIsPermitted(String permission, Handler<AsyncResult<Boolean>> ar) {
    authProvider.hasPermission(permission, ar);
  }

}
