package dev.yasint.toyland.dtos;

import dev.yasint.toyland.models.User;
import lombok.Data;

@Data
public class UserDTO implements Transformable<User> {

    private String name;
    private String email;
    private String password;

    @Override
    public User transform() {
        User user = new User(null, this.email, this.password);
        user.setName(this.name);
        return user;
    }

}
