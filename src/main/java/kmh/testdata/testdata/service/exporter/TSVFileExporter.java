package kmh.testdata.testdata.service.exporter;

import kmh.testdata.testdata.domain.constant.ExportFileType;
import kmh.testdata.testdata.service.generator.MockDataGeneratorContext;
import org.springframework.stereotype.Component;

@Component
public class TSVFileExporter extends DelimiterBasedFileExporter implements MockDataFileExporter {

    public TSVFileExporter(MockDataGeneratorContext mockDataGeneratorContext) {
        super(mockDataGeneratorContext);
    }

    @Override
    public String getDelimiter() {
        return "\t";
    }

    @Override
    public ExportFileType getType() {
        return ExportFileType.TSV;
    }

}
