package com.mobdev.wm.service;
import com.mobdev.wm.entity.Character;
import com.mobdev.wm.entity.Location;
import com.mobdev.wm.entity.Origin;
import com.mobdev.wm.entity.Resultado;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ApiServiceImpl implements ApiService {

    private final WebClient webClient;
    @Value("${api.url}")
    private String CHAHRACTER_API;

    public ApiServiceImpl(WebClient.Builder builder) {
       webClient = builder.baseUrl(CHAHRACTER_API).build();
    }

    @Override
    public Resultado findAndResultadoById(String id) {
        Resultado resultado = new Resultado();
        Origin origin = new Origin();
        try {
            //Se consulta el id del personaje en base al parametro ingresado
            Character character = webClient.get()
                    .uri(CHAHRACTER_API+"/character/"+id)
                    .retrieve()
                    .bodyToMono(Character.class)
                    .block();
            //Se consulta el url de la localidad del Planeta para obtener los residentes
            Location location = webClient.get()
                    .uri(character.getOrigin().getUrl())
                    .retrieve()
                    .bodyToMono(Location.class)
                    .block();

            //Se setea los valores para mostrar la respuesta de la API
            resultado.setId(character.getId());
            resultado.setName(character.getName());
            resultado.setStatus(character.getStatus());
            resultado.setSpecies(character.getSpecies());
            resultado.setType(character.getType());
            resultado.setEpisode_count(character.getEpisode().size());

            origin.setName(character.getOrigin().getName());
            origin.setUrl(character.getOrigin().getUrl());
            origin.setDimension(location.getDimension());
            origin.setResidents(location.getResidents());
            resultado.setOrigin(origin);

        } catch (Exception we) {
            throw new ServiceException(we.getMessage(), we.getCause());
        }
        return resultado;
    }
}
