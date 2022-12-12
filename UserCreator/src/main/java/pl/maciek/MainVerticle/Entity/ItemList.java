package pl.maciek.MainVerticle.Entity;

import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemList {
    List<Item> itemList;

    public JsonObject toJsonObject(){
        return JsonObject.mapFrom(this);
    }
}
