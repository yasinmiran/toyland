package dev.yasint.toyland.dtos.response.user;

import dev.yasint.toyland.models.enumerations.ERole;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.CustomerRepository;
import dev.yasint.toyland.repositories.DriverRepository;
import dev.yasint.toyland.repositories.MerchantRepository;
import dev.yasint.toyland.services.CustomerService;
import dev.yasint.toyland.services.DriverService;
import dev.yasint.toyland.services.MerchantService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDetailDTOFactory {

    private final BeanFactory beanFactory;

    @Autowired
    public UserDetailDTOFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public UserDetailDTO<?> createDetailsObject(User user) {
        ERole role = user.getRoles().stream().toList().get(0).getName();
        switch (role) {
            case CUSTOMER -> {
                return new CustomerDetailDTO(
                        user,
                        beanFactory.getBean(CustomerRepository.class),
                        beanFactory.getBean(CustomerService.class)
                );
            }
            case MERCHANT -> {
                return new MerchantDetailDTO(
                        user,
                        beanFactory.getBean(MerchantRepository.class),
                        beanFactory.getBean(MerchantService.class)
                );
            }
            case DRIVER -> {
                return new DriverDetailDTO(
                        user,
                        beanFactory.getBean(DriverRepository.class),
                        beanFactory.getBean(DriverService.class)
                );
            }
        }
        return null;
    }

}