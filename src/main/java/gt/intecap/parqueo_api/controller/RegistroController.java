
package gt.intecap.parqueo_api.controller;

import gt.intecap.parqueo_api.model.Registro;
import gt.intecap.parqueo_api.model.Vehiculo;
import gt.intecap.parqueo_api.model.Espacio;

import gt.intecap.parqueo_api.repository.RegistroRepository;
import gt.intecap.parqueo_api.repository.VehiculoRepository;
import gt.intecap.parqueo_api.repository.EspacioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/registros")
public class RegistroController {

    private final RegistroRepository registroRepository;
    private final VehiculoRepository vehiculoRepository;
    private final EspacioRepository espacioRepository;

    public RegistroController(
            RegistroRepository registroRepository,
            VehiculoRepository vehiculoRepository,
            EspacioRepository espacioRepository) {

        this.registroRepository = registroRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.espacioRepository = espacioRepository;
    }

    @GetMapping
    public List<Registro> listar() {
        return registroRepository.findAll();
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public Registro guardar(@RequestBody Registro registro) {

        if (registro.getVehiculo() == null ||
                registro.getVehiculo().getId() == null ||
                registro.getEspacio() == null ||
                registro.getEspacio().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe indicar vehiculo y espacio");
        }

        Long vehiculoId = registro.getVehiculo().getId();
        Long espacioId = registro.getEspacio().getId();

        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Vehiculo no encontrado"));

        Espacio espacio = espacioRepository.findById(espacioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Espacio no encontrado"));

        if (!"DISPONIBLE".equals(espacio.getEstado())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Espacio ocupado");
        }

        registro.setId(null);
        registro.setVehiculo(vehiculo);
        registro.setEspacio(espacio);
        registro.setHoraEntrada(null);

        espacio.setEstado("OCUPADO");
        espacioRepository.save(espacio);

        return registroRepository.save(registro);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {

        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Registro no encontrado"));

        Espacio espacio = registro.getEspacio();

        registroRepository.delete(registro);

        espacio.setEstado("DISPONIBLE");
        espacioRepository.save(espacio);
    }
}
