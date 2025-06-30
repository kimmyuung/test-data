package kmh.testdata.testdata.service;

import kmh.testdata.testdata.domain.TableSchema;
import kmh.testdata.testdata.domain.constant.ExportFileType;
import kmh.testdata.testdata.dto.TableSchemaDto;
import kmh.testdata.testdata.repository.TableSchemaRepository;
import kmh.testdata.testdata.service.exporter.MockDataFileExporterContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SchemaExportService {

    private final MockDataFileExporterContext mockDataFileExporterContext;
    private final TableSchemaRepository tableSchemaRepository;

    public String export(ExportFileType fileType, TableSchemaDto dto, Integer rowCount) {
        if (dto.userId() != null) {
            tableSchemaRepository.findByUserIdAndSchemaName(dto.userId(), dto.schemaName())
                    .ifPresent(TableSchema::markExported);
        }

        return mockDataFileExporterContext.export(fileType, dto, rowCount);
    }

}
