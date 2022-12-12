package tests.Entitytests;

import com.sun.tools.javac.Main;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.codehaus.plexus.util.dag.Vertex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.maciek.MainVerticle.MainVerticle;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class TestUserApi {
    private static final Logger LOG= LoggerFactory.getLogger(TestUserApi.class);

    @Test
    void add_and_return_user(Vertx vertx, VertxTestContext context) throws Throwable{
        WebClient client=WebClient.create(vertx,new WebClientOptions().setDefaultPort(MainVerticle.PORT));
        String login="Maciek";
        String password="maciek";
        JsonObject jsonObject=new JsonObject();
        jsonObject.put("login",login)
                        .put("password",password);

        client.get("/register"+login+password)
                .sendJsonObject(jsonObject)
                .onComplete(context.succeeding(response->{
                    JsonObject responseBody = response.bodyAsJsonObject();
                    assertEquals(login, responseBody.getString("login"));
                    assertEquals(password, responseBody.getString("password"));
                    assertEquals(200, response.statusCode());
                    context.completeNow();
                }));

    }
    @Test
    void return_user(Vertx vertx,VertxTestContext context) throws Throwable{
        WebClient client=WebClient.create(vertx,new WebClientOptions().setDefaultPort(MainVerticle.PORT));
        String login="Maciek";
        String password="maciek";
        JsonObject jsonObject=new JsonObject();
        jsonObject.put("login",login)
                .put("password",password);
        client.get("/register"+login+password)
                .sendJsonObject(jsonObject)
                .onComplete(context.succeeding(response->{
                    JsonObject responseBody = response.bodyAsJsonObject();
                    assertEquals(login, responseBody.getString("login"));
                    assertEquals(password, responseBody.getString("password"));
                    context.completeNow();
                }));
    }



}


