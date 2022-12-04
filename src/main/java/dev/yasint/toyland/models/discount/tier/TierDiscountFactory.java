package dev.yasint.toyland.models.discount.tier;

import dev.yasint.toyland.models.enumerations.ECustomerTier;
import dev.yasint.toyland.models.discount.BaseDiscount;
import dev.yasint.toyland.models.discount.Discount;
import dev.yasint.toyland.models.user.Customer;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class is a factory design pattern implementation used 
 * to get the discounts of the various tiers.
 * 
 * The decorator design pattern is also demonstrated here
 */

@Component
public class TierDiscountFactory {

    private final CustomerRepository customerRepository;

    @Autowired
    public TierDiscountFactory(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Discount tierDiscount(User user, double price) {

        Customer customerByUser = customerRepository.findCustomerByUser(user);
        ECustomerTier tier = customerByUser.getTier();

        switch (tier) {
            case NONE -> {
                return new BaseDiscount(price);
            }
            case GOLD -> {
                return new GoldTier(new BaseDiscount(price));
            }
            case BRONZE -> {
                return new BronzeTier(new BaseDiscount(price));
            }
            case SILVER -> {
                return new SilverTier(new BaseDiscount(price));
            }
        }

        return new BaseDiscount(price);

    }

}
