package kmh.testdata.testdata.dto.request;

import kmh.testdata.testdata.dto.TableSchemaDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

// TODO: 스프링 부트 3.4를 쓸 수 있게 되면, record로 되돌리는 것을 검토하기
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Data
public class TableSchemaRequest {

    private String schemaName;
    private List<SchemaFieldRequest> schemaFields;

    public TableSchemaDto toDto(String userId) {
        return TableSchemaDto.of(
                schemaName,
                userId,
                null,
                schemaFields.stream()
                        .map(SchemaFieldRequest::toDto)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

}
