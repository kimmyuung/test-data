package kmh.testdata.testdata.service.exporter;

import kmh.testdata.testdata.dto.SchemaFieldDto;
import kmh.testdata.testdata.dto.TableSchemaDto;
import kmh.testdata.testdata.service.generator.MockDataGeneratorContext;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public abstract class DelimiterBasedFileExporter implements MockDataFileExporter {

    private final MockDataGeneratorContext mockDataGeneratorContext;

    /**
     * 파일 열 구분자로 사용할 문자열을 반환한다.
     *
     * @return 파일 열 구분자
     */
    public abstract String getDelimiter();

    @Override
    public String export(TableSchemaDto dto, Integer rowCount) {
        StringBuilder sb = new StringBuilder();

        // 헤더 만들기
        sb.append(dto.schemaFields().stream()
                .sorted(Comparator.comparing(SchemaFieldDto::fieldOrder))
                .map(SchemaFieldDto::fieldName)
                .collect(Collectors.joining(getDelimiter()))
        );
        sb.append("\n");

        // 데이터 부분
        IntStream.range(0, rowCount).forEach(i -> {
            sb.append(dto.schemaFields().stream()
                    .sorted(Comparator.comparing(SchemaFieldDto::fieldOrder))
                    .map(field -> mockDataGeneratorContext.generate(
                            field.mockDataType(),
                            field.blankPercent(),
                            field.typeOptionJson(),
                            field.forceValue()
                    ))
                    .map(v -> v == null ? "" : v)
                    .collect(Collectors.joining(getDelimiter()))
            );
            sb.append("\n");
        });

        return sb.toString();
    }

}