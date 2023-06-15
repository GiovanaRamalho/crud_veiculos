package able.tech.api.controller;

import able.tech.api.repository.VehicleRepository;
import able.tech.api.vehicle.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("vehicles")
public class VehicleController {

    @Autowired
    private VehicleRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody DataRegisterVehicle data, UriComponentsBuilder uriBuilder){
        var vehicle = new Vehicle(data);
        repository.save(vehicle);

        var uri = uriBuilder.path("vehicles/{id}").buildAndExpand(vehicle.getId()).toUri();

        return ResponseEntity.created(uri).body(new DataDetailVehicle(vehicle));
    }


    @GetMapping
    public ResponseEntity<List<DataListingVehicle>>listin(){
        var list = repository.findAll().stream().map(DataListingVehicle::new).toList();

        return ResponseEntity.ok(list);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody DataUpdateVehicle data){
        var vehicle = repository.getReferenceById(data.id());
        vehicle.updateInfomations(data);

        return ResponseEntity.ok(new DataDetailVehicle(vehicle));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
