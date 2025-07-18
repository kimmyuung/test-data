package kmh.testdata.testdata.domain;

import jakarta.persistence.*;
import kmh.testdata.testdata.domain.constant.MockDataType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 *  특정 {@link MockDataType}에 대응하는 가짜 데이터
 *  알고리즘으로 생성하지 않는 {@link MockDataType} 의 경우, 이 가짜 데이터를 랜덤으로 뽑아 출력
 *
 * @author KMH
 */

@Getter
@ToString
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"mockDataType" , "mockDataValue"})
})
public class MockData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false) private MockDataType mockDataType;
    @Setter @Column(nullable = false) private String mockDataValue;

    protected MockData() {}

    public MockData(MockDataType mockDataType, String mockDataValue) {
        this.mockDataType = mockDataType;
        this.mockDataValue = mockDataValue;
    }

    public static MockData of(MockDataType mockDataType, String mockDataValue) {
        return new MockData(mockDataType, mockDataValue);
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if (!(o instanceof MockData that)) return false;

        if (getId() == null) {
            return Objects.equals(this.getMockDataType(), that.getMockDataType()) &&
                    Objects.equals(this.getMockDataValue(), that.getMockDataValue());
        }
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        if(getId() == null ) {
            return Objects.hash(getMockDataType(), getMockDataValue());
        }

        return Objects.hash(id, mockDataType, mockDataValue);
    }
}
