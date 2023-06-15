package able.tech.api.vehicle;

public record DataListingVehicle(
        Long id,
        String marca,
        String modelo,
        String placa) {
    public DataListingVehicle(Vehicle vehicle){
        this(vehicle.getId(), vehicle.getMarca(), vehicle.getModelo(), vehicle.getPlaca());
    }
}
