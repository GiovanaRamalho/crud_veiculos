package able.tech.api.controller;

import able.tech.api.repository.VehicleRepository;
import able.tech.api.vehicle.DataDetailVehicle;
import able.tech.api.vehicle.DataRegisterVehicle;
import able.tech.api.vehicle.Vehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;



@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters

class VehicleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DataRegisterVehicle> dataRegisterVehicleJson;
    @Autowired
    private JacksonTester<DataDetailVehicle> dataDetailVehicleJson;

    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private VehicleController vehicleController;

    @Mock
    private VehicleRepository repository;


    @Test
    @DisplayName("Deve devolver codigo 400 ao nao enviar o json com os dados")
    void register_com_json_vazio() throws Exception {
        var response = mvc.perform(post("/vehicles"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve devolver codigo 201 ao enviar o json com os dados")
    void register_com_json_valido() throws Exception {
        var response = mvc.perform(
                        post("/vehicles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dataRegisterVehicleJson.write(
                                        new DataRegisterVehicle("Uno", "Fiat", "AB500")
                                ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var responseJson = dataDetailVehicleJson.write(
                new DataDetailVehicle(null, "Fiat", "Uno", "AB500")
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(responseJson);
    }


    @Test
    @DisplayName("Deve retornar código 400 ao não enviar o ID válido para exclusão")
    void delete_veiculo_sem_id_valido() throws Exception {
        var response = mvc.perform(delete("/vehicles/id_invalido"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar código 204 ao excluir um veículo existente")
    void delete_veiculo_existente() throws Exception {

        Vehicle vehicle = new Vehicle();

        var response = mvc.perform(delete("/vehicles/{id}", vehicle.getId()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());

    }
}