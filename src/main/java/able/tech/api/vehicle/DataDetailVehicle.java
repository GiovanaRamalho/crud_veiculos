package able.tech.api.vehicle;

public record DataDetailVehicle(Long id, String marca, String modelo, String placa) {

    public DataDetailVehicle(Vehicle vehicle){
        this(vehicle.getId(), vehicle.getMarca(), vehicle.getModelo(), vehicle.getPlaca());
    }
}
