package pl.maciek.MainVerticle.methods;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.maciek.MainVerticle.Entity.Item;
import pl.maciek.MainVerticle.Entity.ItemList;
import pl.maciek.MainVerticle.Entity.User;
import pl.maciek.MainVerticle.MainVerticle;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class ItemMethods {
    private static final Logger LOG= LoggerFactory.getLogger(ItemMethods.class);

    public static void run(Router router){
        final HashMap<UUID, ItemList> itemListPerUserId=new HashMap<UUID,ItemList>();
        router.get("/items/:userid").handler(routingContext -> {
            UUID userid= UUID.fromString(routingContext.pathParam("userid"));
            LOG.debug("{} for userid {}",routingContext.normalizedPath(), userid);
            var itemList = Optional.ofNullable(itemListPerUserId.get(userid));
            if(itemList.isEmpty()){
            routingContext.response()
                    .setStatusCode(HttpResponseStatus.NOT_FOUND.code())
                    .end(new JsonObject()
                            .put("message","itemlist"+userid+" not availabe")
                            .put("path",routingContext.normalizedPath())
                            .toBuffer());
            return;
            }
            routingContext.response().end(itemList.get().toJsonObject().toBuffer());





        });

        router.post("/items/:name/:userid").handler(routingContext -> {
            String name= routingContext.pathParam("name");
            UUID userid= UUID.fromString(routingContext.pathParam("userid"));
            Item item=new Item(name,userid);
            LOG.debug("{} name and id {}",routingContext.normalizedPath(),name,userid);
            JsonObject jsonObject=new JsonObject();
            jsonObject.put("id",item.getId())
            .put("name",item.getName())
            .put("userid",item.getUserid());
            MongoClient mongoClient = MongoClients.create(MainVerticle.connectionString);
            MongoDatabase database = mongoClient.getDatabase("MongoDB");
            MongoCollection<Document> collection = database.getCollection("Users");
            BasicDBObject query = new BasicDBObject("id", userid);
            Document doc = new Document("ID", item.getId())
                    .append("name",item.getName())
                    .append("userid",item.getUserid() );
            collection.insertOne(doc);


            routingContext.response().end(jsonObject.toBuffer());
        });
    }

}
