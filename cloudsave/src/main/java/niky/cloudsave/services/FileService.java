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

    /**
     *
     * @param file - an object of type MultipartFile
     * @param user - the user is the foreign key part of the file, so we know who stores what.
     * @return - call the built-in save method that stores our file in the database.
     *
     */
    public FileDB store(MultipartFile file, UserObject user) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), user.getUser());
        return fileRepository.save(fileDB);
    }

    /**
     *
     * These three methods calls the queries from the repository to perform given tasks.
     *
     */

    public FileDB getFile(String id) {
        return fileRepository.findById(id).get();
    }

    public Stream<FileDB> getAllFiles(UserObject user) {
        return fileRepository.findAllByUser(user.getUser()).stream();
    }

    public void deleteFile( String id) {
        fileRepository.deleteById(id);
    }
}
