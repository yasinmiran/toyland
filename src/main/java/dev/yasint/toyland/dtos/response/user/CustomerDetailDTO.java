package dev.yasint.toyland.dtos.response.user;

import dev.yasint.toyland.models.Role;
import dev.yasint.toyland.models.enumerations.ECustomerTier;
import dev.yasint.toyland.models.enumerations.ERole;
import dev.yasint.toyland.models.user.Contact;
import dev.yasint.toyland.models.user.Customer;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.CustomerRepository;
import dev.yasint.toyland.services.CustomerService;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomerDetailDTO implements UserDetailDTO<CustomerDetailDTO.Fields> {

    private final User user;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @Override
    public Fields getDetails() {
        Customer customer = customerRepository.findCustomerByUser(user);
        return Fields.builder()
                .id(user.getId())
                .customerId(customer.getId())
                .username(user.getUsername())
                .roles(customer.getUser()
                        .getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()))
                .contact(customer.getContact())
                .tier(customer.getTier())
                .build();
    }

    @Data
    @Builder
    static class Fields {
        private Long id;
        private Long customerId;
        private String username;
        private List<ERole> roles;
        private Contact contact;
        private ECustomerTier tier;
    }

}