package whatever;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.AuthHandler;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.FormLoginHandler;
import io.vertx.ext.web.handler.RedirectAuthHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;

public class Server extends AbstractVerticle {

  String loginHTML = "<html>\n" + "<body>\n" + "<h2>Please login {{foo}}</h2><br>\n" + "<form action=\"/login\" method=\"post\">\n" + "  <div>\n"
      + "    <label>Username:</label>\n" + "    <input type=\"text\" name=\"username\"/>\n" + "  </div>\n" + "  <div>\n"
      + "    <label>Password:</label>\n" + "    <input type=\"password\" name=\"password\"/>\n" + "  </div>\n" + "  <div>\n"
      + "    <input type=\"submit\" value=\"Log In\"/>\n" + "  </div>\n" + "</form>\n" + "</body>\n" + "</html>";

  /*
   * Open the URL 
   * http://localhost:8888/protected/whatever
   */

  @Override
  public void start() {
    Router router = Router.router(vertx);

    // Cookie Handler
    CookieHandler cookieHandler = CookieHandler.create();
    router.route().handler(cookieHandler);

    // Session Handler
    SessionStore store = LocalSessionStore.create(vertx);
    SessionHandler sessionHandler = SessionHandler.create(store);
    router.route().handler(sessionHandler);

    // Auth
    AuthProvider authProvider = new MyAuthProviderImpl(vertx);
    AuthHandler authHandler = RedirectAuthHandler.create(authProvider, "/loginpage");
    authHandler.addAuthority("whatever");
    router.route("/protected/*").handler(authHandler);
    router.route("/login/*").handler(BodyHandler.create());
    router.route("/login").handler(FormLoginHandler.create(authProvider));

    // Serve static login page
    router.route().method(HttpMethod.GET).path("/loginpage").handler(rc -> {
      rc.response().putHeader("content-type", "text/html").end(loginHTML);
    });

    HttpServer server = vertx.createHttpServer();
    server.requestHandler(router::accept).listen(8888);
  }

}
