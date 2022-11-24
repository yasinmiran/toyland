package dev.yasint.toyland.services;

import dev.yasint.toyland.models.user.Contact;
import dev.yasint.toyland.models.user.Customer;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.ContactRepository;
import dev.yasint.toyland.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public ContactServiceImpl(
            ContactRepository contactRepository,
            CustomerRepository customerRepository) {
        this.contactRepository = contactRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Contact updateContact(User user, Contact partial) {
        Customer customer = customerRepository.findCustomerByUser(user);
        Contact contact = customer.getContact();
        System.out.println(partial);
        contact.setAddressLine1(partial.getAddressLine1());
        contact.setAddressLine2(partial.getAddressLine2());
        contact.setCounty(partial.getCounty());
        contact.setCountry(partial.getCountry());
        contact.setPostcode(partial.getPostcode());
        contact.setMobileNo(partial.getMobileNo());
        return contactRepository.save(contact);
    }

}
