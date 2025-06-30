package kmh.testdata.testdata.dto.request;

import kmh.testdata.testdata.domain.constant.MockDataType;
import kmh.testdata.testdata.dto.SchemaFieldDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// TODO: 스프링 부트 3.4를 쓸 수 있게 되면, record로 되돌리는 것을 검토하기
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class SchemaFieldRequest {

    private String fieldName;
    private MockDataType mockDataType;
    private Integer fieldOrder;
    private Integer blankPercent;
    private String typeOptionJson;
    private String forceValue;

    public SchemaFieldDto toDto() {
        return SchemaFieldDto.of(
                fieldName,
                mockDataType,
                fieldOrder,
                blankPercent,
                typeOptionJson,
                forceValue
        );
    }

}