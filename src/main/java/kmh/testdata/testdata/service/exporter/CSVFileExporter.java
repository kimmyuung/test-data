package kmh.testdata.testdata.service.exporter;

import kmh.testdata.testdata.domain.constant.ExportFileType;
import kmh.testdata.testdata.service.generator.MockDataGeneratorContext;
import org.springframework.stereotype.Component;

@Component
public class CSVFileExporter extends DelimiterBasedFileExporter implements MockDataFileExporter {

    public CSVFileExporter(MockDataGeneratorContext mockDataGeneratorContext) {
        super(mockDataGeneratorContext);
    }

    @Override
    public String getDelimiter() {
        return ",";
    }

    @Override
    public ExportFileType getType() {
        return ExportFileType.CSV;
    }

}
