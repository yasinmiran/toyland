package dev.yasint.toyland.services;

import dev.yasint.toyland.models.user.Contact;
import dev.yasint.toyland.models.user.User;

public interface ContactService {

    Contact updateContact(User user, Contact partial);

}
