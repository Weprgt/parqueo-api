package gt.intecap.parqueo_api.repository;

import gt.intecap.parqueo_api.model.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository
        extends JpaRepository<Registro, Long> {
}
