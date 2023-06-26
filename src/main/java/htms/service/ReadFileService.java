package htms.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReadFileService {
    List<String[]> readDataFromCsv(MultipartFile file);
}
