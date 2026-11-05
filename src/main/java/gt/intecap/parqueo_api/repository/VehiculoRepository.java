package gt.intecap.parqueo_api.repository;

import gt.intecap.parqueo_api.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository
        extends JpaRepository<Vehiculo, Long> {
}
