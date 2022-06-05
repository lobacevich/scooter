package by.senla.training.lobacevich.scooter.mapper;

import by.senla.training.lobacevich.scooter.dto.CityDto;
import by.senla.training.lobacevich.scooter.entity.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public CityDto cityToDto(City city) {
        CityDto cityDto = new CityDto();
        cityDto.setName(city.getName());
        return cityDto;
    }
}
