package pl.maciek.MainVerticle.jwt;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;

public class AuthJWT {

    public void setup(Vertx vertx, Router route) throws Exception {

        JWTAuthOptions authConfig = new JWTAuthOptions()
                .setKeyStore(new KeyStoreOptions()
                        .setType("jceks")
                        .setPath("keystore.jceks")
                        .setPassword("password"));

        JWTAuth authProvider = JWTAuth.create(vertx, authConfig);
        authProvider.authenticate(
                        new JsonObject()
                                .put("token", "BASE64-ENCODED-STRING")
                                .put("options", new JsonObject()
                                        .put("audience", new JsonArray().add("paulo@server.com"))))
                .onSuccess(user -> System.out.println("User: " + user.principal()))
                .onFailure(err -> {
                    // Failed!
                });
    }

}
