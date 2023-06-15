package able.tech.api.vehicle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "vehicles")
@Entity(name = "Vehicle")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Vehicle {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String modelo;
    private String marca;
    private String placa;

    public Vehicle(DataRegisterVehicle data) {
        this.modelo = data.modelo();
        this.marca = data.marca();
        this.placa = data.placa();
    }

    public void updateInfomations(DataUpdateVehicle data) {

        if (data.modelo() != null){
            this.modelo = data.modelo();
        }
        if (data.marca() != null){
            this.marca = data.marca();
        }
        if (data.placa() != null) {
            this.placa = data.placa();
        }
    }
}
