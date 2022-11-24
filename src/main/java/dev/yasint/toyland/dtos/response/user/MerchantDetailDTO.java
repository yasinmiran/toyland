package dev.yasint.toyland.dtos.response.user;

import dev.yasint.toyland.models.Role;
import dev.yasint.toyland.models.enumerations.ERole;
import dev.yasint.toyland.models.user.Merchant;
import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.MerchantRepository;
import dev.yasint.toyland.services.MerchantService;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MerchantDetailDTO implements UserDetailDTO<MerchantDetailDTO.Fields> {

    private final User user;
    private final MerchantRepository merchantRepository;
    private final MerchantService merchantService;

    @Override
    public MerchantDetailDTO.Fields getDetails() {
        Merchant merchant = merchantRepository.findMerchantByUser(user);
        return MerchantDetailDTO.Fields.builder()
                .id(merchant.getUser().getId())
                .merchantId(merchant.getId())
                .username(merchant.getUser().getUsername())
                .roles(merchant.getUser()
                        .getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()))
                .productCount(merchant.getProducts().size())
                .verified(merchant.getVerified())
                .taxId(merchant.getTaxId())
                .build();
    }

    @Data
    @Builder
    public static class Fields {
        private Long id;
        private Long merchantId;
        private String username;
        private List<ERole> roles;
        private int productCount;
        private boolean verified;
        private String taxId;
    }

}
