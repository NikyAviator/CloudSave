package niky.cloudsave.services;

import niky.cloudsave.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    
}
