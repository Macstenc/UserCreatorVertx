package pl.maciek.MainVerticle.methods;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.maciek.MainVerticle.Entity.User;
import pl.maciek.MainVerticle.MainVerticle;


import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class UserMethods {
    private static final Logger LOG= LoggerFactory.getLogger(UserMethods.class);


    public static void run(Router router) {
        router.get("/register/:login/:password").handler(routingContext -> {
            String login=routingContext.pathParam("login");
            String password=routingContext.pathParam("password");
            User user=new User(login,password);
            LOG.debug("{} login and password {}",routingContext.normalizedPath(),login,password);
            JsonObject jsonObject=new JsonObject();
            jsonObject.put("id",user.getId());
            jsonObject.put("login",user.getLogin());
            jsonObject.put("Password",user.getPassword());
            var object= Optional.of(jsonObject);
            if(jsonObject.isEmpty()){
                routingContext.response()
                        .setStatusCode(HttpResponseStatus.NOT_FOUND.code())
                        .end(new JsonObject()
                                .put("message","login and password for account"+login+password+"not available")
                                .put("path",routingContext.normalizedPath())
                                .toBuffer()
                        );
                return;
            }
            MongoClient mongoClient = MongoClients.create(MainVerticle.connectionString);
            MongoDatabase database = mongoClient.getDatabase("MongoDB");
            MongoCollection<Document> collection = database.getCollection("Users");
            Document doc = new Document("ID", user.getId())
                    .append("login",user.getLogin())
                    .append("password",user.getPassword() );
            collection.insertOne(doc);
            routingContext.response().end(jsonObject.toBuffer());

        });
    router.post("/register/:login/:password").handler(routingContext -> {
        String login=routingContext.pathParam("login");
        String password=routingContext.pathParam("password");
        User user=new User(login,password);
        LOG.debug("{} loggin and password {}",routingContext.normalizedPath(),login,password);
        JsonObject jsonObject=new JsonObject();
        jsonObject.put("id",user.getId());
        jsonObject.put("login",user.getLogin());
        jsonObject.put("Password",user.getPassword());

        routingContext.response().end(jsonObject.toBuffer());


    });

    }

}
