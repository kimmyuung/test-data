package kmh.testdata.testdata.dto;

import kmh.testdata.testdata.domain.MockData;
import kmh.testdata.testdata.domain.constant.MockDataType;

public record MockDataDto(
        Long id,
        MockDataType mockDataType,
        String mockDataValue
) {

    public static MockDataDto fromEntity(MockData entity) {
        return new MockDataDto(
                entity.getId(),
                entity.getMockDataType(),
                entity.getMockDataValue()
        );
    }

}
