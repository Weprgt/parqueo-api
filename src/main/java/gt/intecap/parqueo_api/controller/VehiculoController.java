
package gt.intecap.parqueo_api.controller;

import gt.intecap.parqueo_api.model.Vehiculo;
import gt.intecap.parqueo_api.repository.VehiculoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoRepository repository;

    public VehiculoController(VehiculoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Vehiculo> listar() {
        return repository.findAll();
    }
}
