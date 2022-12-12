package pl.maciek.MainVerticle.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import java.util.Base64;

import java.util.UUID;

@Data
@Getter
@NoArgsConstructor
public class User {
    private UUID id;
    private String login;
    private String password;

    public User(String login, String password) {
        this.id=UUID.randomUUID();
        this.login = login;
        Base64.Encoder encoder = Base64.getEncoder();
        this.password = encoder.encodeToString(password.getBytes());
    }
    public UUID getUuidByLogin(String login){
        if(this.login.equals(login)) {
            return this.id;
        }else return null;
    }


}
