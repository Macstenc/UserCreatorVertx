package pl.maciek.MainVerticle.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Data
@Getter
@NoArgsConstructor
public class Item {
    UUID id;
    UUID userid;
    String name;

    public Item(String name,UUID userid) {
        this.id=UUID.randomUUID();
        this.name = name;
        this.userid=userid;
    }

}
