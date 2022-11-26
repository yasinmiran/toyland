package dev.yasint.toyland.services;

import dev.yasint.toyland.models.user.Customer;
import dev.yasint.toyland.models.user.Payment;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.CustomerRepository;
import dev.yasint.toyland.repositories.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            CustomerRepository customerRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Payment updatePayment(User user, Payment partial) {
        Customer customer = customerRepository.findCustomerByUser(user);
        Payment payment = customer.getPayment();
        payment.setName(partial.getName());
        payment.setNumber(partial.getNumber());
        payment.setCvv(partial.getCvv());
        payment.setExpireDate(partial.getExpireDate());
        return paymentRepository.save(payment);
    }

}
