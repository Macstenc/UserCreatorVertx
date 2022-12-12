package tests.Entitytests;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.maciek.MainVerticle.MainVerticle;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class TestItemApi {
    private static final Logger LOG= LoggerFactory.getLogger(TestItemApi.class);

    @Test
    void return_items_by_uuid(Vertx vertx, VertxTestContext context) throws Throwable{
        WebClient client=WebClient.create(vertx,new WebClientOptions().setDefaultPort(MainVerticle.PORT));
        UUID uuid=UUID.fromString("00000000-0000-007b-0000-00000000007b");
        client.get("/items"+uuid)
                .send()
                .onComplete(context.succeeding(response->{
                    JsonArray json=response.bodyAsJsonArray();
                    assertEquals(uuid, json.encode());
                    context.completeNow();
                }));
    }
}
