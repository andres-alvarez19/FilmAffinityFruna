package ufro.dci.filmaffinityfruna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.RoleEntity;
import ufro.dci.filmaffinityfruna.repository.RoleRepository;
import ufro.dci.filmaffinityfruna.utils.MessageConstant;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleEntity findRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException(MessageConstant.ROL_NOT_FOUND));
    }

    public boolean existsByRoleName(String roleName) {
        return roleRepository.existsByName(roleName);
    }
}