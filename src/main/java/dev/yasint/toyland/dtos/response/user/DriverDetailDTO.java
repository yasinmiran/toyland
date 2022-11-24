package dev.yasint.toyland.dtos.response.user;

import dev.yasint.toyland.models.user.User;
import dev.yasint.toyland.repositories.DriverRepository;
import dev.yasint.toyland.services.DriverService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class DriverDetailDTO implements UserDetailDTO<Map<String, Object>> {

    private final User user;
    private final DriverRepository driverRepository;
    private final DriverService driverService;

    @Override
    public Map<String, Object> getDetails() {
        return null;
    }

}