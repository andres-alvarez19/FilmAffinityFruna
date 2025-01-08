package ufro.dci.filmaffinityfruna.config.component;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ufro.dci.filmaffinityfruna.model.entity.RoleEntity;
import ufro.dci.filmaffinityfruna.repository.RoleRepository;


@AllArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            RoleEntity role = new RoleEntity();
            role.setName("ROLE_USER");
            roleRepository.save(role);
        }
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            RoleEntity role = new RoleEntity();
            role.setName("ROLE_ADMIN");
            roleRepository.save(role);
        }
    }

}
