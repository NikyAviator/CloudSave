package niky.cloudsave.services;
import java.io.IOException;
import java.util.stream.Stream;
import niky.cloudsave.data.FileDB;
import niky.cloudsave.repositories.FileRepository;
import niky.cloudsave.security.UserObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;
    public FileDB store(MultipartFile file, UserObject user) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), user.getUser());

        return fileRepository.save(fileDB);
    }

    public FileDB getFile(String id) {
        return fileRepository.findById(id).get();
    }

    public Stream<FileDB> getAllFiles() {
        return fileRepository.findAll().stream();
    }

}
