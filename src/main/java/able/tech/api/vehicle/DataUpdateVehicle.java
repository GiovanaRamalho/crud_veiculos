package able.tech.api.vehicle;

import jakarta.validation.constraints.NotNull;

public record DataUpdateVehicle(

        Long id,
        String modelo,
        String marca,
        String placa) {
}
