package gt.intecap.parqueo_api.repository;

import gt.intecap.parqueo_api.model.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspacioRepository
        extends JpaRepository<Espacio, Long> {
}

