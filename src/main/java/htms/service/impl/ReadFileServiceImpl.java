package htms.service.impl;

import com.opencsv.CSVReader;
import htms.service.ReadFileService;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.List;

@Service
@Transactional
public class ReadFileServiceImpl implements ReadFileService {
    public void readDataFromUploadFile(MultipartFile file) {
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (extension != null && extension.equals("csv")) {
                readDataFromCsv(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String[]> readDataFromCsv(MultipartFile file) {
        try {
            InputStreamReader inputStream = new InputStreamReader(file.getInputStream());
            CSVReader csvReader = new CSVReader(inputStream);
            csvReader.skip(1); // skip the first line is the header
            return csvReader.readAll();
        } catch (Exception e) {
            // todo: handle exception
            e.printStackTrace();
            throw new RuntimeException("Import failed " + e.getMessage());
        }
    }
}
