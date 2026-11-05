
package gt.intecap.parqueo_api.controller;

import gt.intecap.parqueo_api.model.Espacio;
import gt.intecap.parqueo_api.repository.EspacioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/espacios")
public class EspacioController {

    private final EspacioRepository repository;

    public EspacioController(EspacioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Espacio> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Espacio guardar(@RequestBody Espacio espacio) {
        return repository.save(espacio);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
