package kmh.testdata.testdata.repository;

import kmh.testdata.testdata.domain.SchemaField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchemaFieldRepository extends JpaRepository<SchemaField, Long> {
}
