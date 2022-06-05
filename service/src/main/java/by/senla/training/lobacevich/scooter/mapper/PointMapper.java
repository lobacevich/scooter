package by.senla.training.lobacevich.scooter.mapper;

import by.senla.training.lobacevich.scooter.dto.PointDto;
import by.senla.training.lobacevich.scooter.entity.Point;
import org.springframework.stereotype.Component;

@Component
public class PointMapper {

    public PointDto pointToDto(Point point) {
        PointDto pointDto = new PointDto();
        pointDto.setId(point.getId());
        pointDto.setAddress(point.getAddress());
        pointDto.setLatitude(point.getLatitude());
        pointDto.setLongitude(point.getLongitude());
        pointDto.setCityId(point.getCity().getId());
        pointDto.setPhoneNumber(point.getPhoneNumber());
        return pointDto;
    }
}
