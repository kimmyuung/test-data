package kmh.testdata.testdata.domain.constant;

import kmh.testdata.testdata.domain.TableSchema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 *  특정 {@link TableSchema}의 단위 필드 정보
 *  이 필드들이 모여서 테이블 스키마를 구섷한다.*
 * @author KMH
 */

@Getter
@RequiredArgsConstructor
public enum MockDataType {

    STRING(Set.of("minLength", "maxLength"), null),
    NUMBER(Set.of("min", "max", "decimals"), null),
    BOOLEAN(Set.of(), null),
    DATETIME(Set.of("from", "to"), null),
    ENUM(Set.of("elements"), null),

    SENTENCE(Set.of("minSentences", "maxSentences"), STRING),
    PARAGRAPH(Set.of("minParagraphs", "maxParagraphs"), STRING),
    UUID(Set.of(), STRING),
    EMAIL(Set.of(), STRING),
    CAR(Set.of(), STRING),
    ROW_NUMBER(Set.of("start, step"), NUMBER),
    NAME(Set.of(), STRING),
    ;

    private final Set<String> requiredOptions;
    private final MockDataType baseType;

    private static final List<MockDataTypeObject> objects = Stream.of(values())
            .map(MockDataType::toObject)
            .toList();


    public static List<MockDataTypeObject> toObjects() {
        return objects;
    }

    public boolean isBaseType() { return baseType == null; }
    public MockDataType jsonType() { return isBaseType() ? this : baseType; }

    public MockDataTypeObject toObject() {
        return new MockDataTypeObject(
                this.name(),
                this.requiredOptions,
                this.baseType == null ? null : this.baseType.name()
        );
    }


    public record MockDataTypeObject(
            String name,
            Set<String> requiredOptions,
            String baseType
    ) {}

}