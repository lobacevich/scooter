package by.senla.training.lobacevich.scooter.mapper;

import by.senla.training.lobacevich.scooter.dto.PointDto;
import by.senla.training.lobacevich.scooter.entity.Point;
import org.springframework.stereotype.Component;

@Component
public class PointMapper {

    public PointDto pointToDto(Point point) {
        return PointDto.builder()
                .id(point.getId())
                .address(point.getAddress())
                .latitude(point.getLatitude())
                .longitude(point.getLongitude())
                .cityId(point.getCity().getId())
                .phoneNumber(point.getPhoneNumber())
                .build();
    }
}
