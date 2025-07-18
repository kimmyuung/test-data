package kmh.testdata.testdata.service.exporter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kmh.testdata.testdata.domain.constant.ExportFileType;
import kmh.testdata.testdata.domain.constant.MockDataType;
import kmh.testdata.testdata.dto.SchemaFieldDto;
import kmh.testdata.testdata.dto.TableSchemaDto;
import kmh.testdata.testdata.service.generator.MockDataGeneratorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@DisplayName("[Logic] Json 파일 출력기 테스트")
@ExtendWith(MockitoExtension.class)
class JsonFileExporterTest {

    @InjectMocks private JsonFileExporter sut;

    @Mock private MockDataGeneratorContext mockDataGeneratorContext;
    @Spy private ObjectMapper mapper;

    @DisplayName("이 파일 출력기의 유형은 JSON이다.")
    @Test
    void givenNothing_whenCheckingType_thenReturnsJsonType() {
        // Given

        // When & Then
        assertThat(sut.getType()).isEqualTo(ExportFileType.JSON);
    }

    @DisplayName("테이블 스키마 정보와 행 수가 주어지면, JSON 형식의 문자열을 생성한다.")
    @Test
    void givenSchemaAndRowCount_whenExporting_thenReturnsJsonFormattedString() throws Exception {
        // Given
        TableSchemaDto dto = TableSchemaDto.of(
                "test_schema",
                "uno",
                null,
                Set.of(
                        SchemaFieldDto.of("id", MockDataType.ROW_NUMBER, 1, 0, null, null),
                        SchemaFieldDto.of("name", MockDataType.NAME, 2, 0, null, null),
                        SchemaFieldDto.of("created_at", MockDataType.DATETIME, 5, 0, null, null),
                        SchemaFieldDto.of("age", MockDataType.NUMBER, 3, 0, null, null),
                        SchemaFieldDto.of("car", MockDataType.CAR, 4, 0, null, null)
                )
        );
        int rowCount = 10;
        given(mockDataGeneratorContext.generate(eq(MockDataType.ROW_NUMBER), any(), any(), any())).willReturn("1");
        given(mockDataGeneratorContext.generate(eq(MockDataType.NAME), any(), any(), any())).willReturn("test-name");
        given(mockDataGeneratorContext.generate(eq(MockDataType.DATETIME), any(), any(), any())).willReturn("2024-01-02T03:04:05");
        given(mockDataGeneratorContext.generate(eq(MockDataType.NUMBER), any(), any(), any())).willReturn(null);
        given(mockDataGeneratorContext.generate(eq(MockDataType.CAR), any(), any(), any())).willReturn("test-횬다이");

        // When
        String result = sut.export(dto, rowCount);

        // Then
        System.out.println(result); // 관찰용
        assertThat(result)
                .startsWith("[")
                .endsWith("]")
                .contains("""
                        {"id":1,"name":"test-name","age":null,"car":"test-횬다이","created_at":"2024-01-02T03:04:05"}"""
                );
        then(mockDataGeneratorContext).should(times(rowCount * dto.schemaFields().size())).generate(any(), any(), any(), any());
        then(mapper).should().writeValueAsString(any());
    }

    @DisplayName("JSON 변환에 실패하면, 빈 문자열을 반환한다.")
    @Test
    void givenSchemaAndRowCount_whenFailingToJsonFormatting_thenReturnsEmptyString() throws Exception {
        // Given
        TableSchemaDto dto = TableSchemaDto.of("test_schema", "uno", null, Set.of(SchemaFieldDto.of("name", MockDataType.NAME, 1, 0, null, null)));
        int rowCount = 10;
        given(mockDataGeneratorContext.generate(any(), any(), any(), any())).willReturn("test-value");
        willThrow(JsonProcessingException.class).given(mapper).writeValueAsString(any());

        // When
        String result = sut.export(dto, rowCount);

        // Then
        assertThat(result).isEqualTo("");
        then(mockDataGeneratorContext).should(times(rowCount * dto.schemaFields().size())).generate(any(), any(), any(), any());
        then(mapper).should().writeValueAsString(any());
    }

}