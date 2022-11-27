package niky.cloudsave.controllers;

import niky.cloudsave.data.FileDB;
import niky.cloudsave.message.ResponseFile;
import niky.cloudsave.message.ResponseMessage;
import niky.cloudsave.security.UserObject;
import niky.cloudsave.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FileController {
    @Autowired
    private FileService fileService;
    // Upload a file to the database.
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestBody MultipartFile file, @AuthenticationPrincipal UserObject user) {
        String message = "";
        try {
            fileService.store(file, user);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    // Download all files
   /* @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = fileService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }*/
    // Download all files connected to a user
    @GetMapping("/myfiles")
    public ResponseEntity<List<ResponseFile>> getMyFiles(@AuthenticationPrincipal UserObject user) {
        List<ResponseFile> files = fileService.getMyFiles(user).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
    // Download a file, La bara till Authentication Principle
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileDB fileDB = fileService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    @DeleteMapping("/file/{id}")
    public ResponseEntity<ResponseMessage> deleteFile(@AuthenticationPrincipal UserObject user,@PathVariable String id) {
        var fileUser = fileService.getFile(id).getUser().getUserId();
        var realUser = user.getUser().getUserId();
        if(fileUser.toString().equals(realUser.toString())){
            fileService.deleteFile(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("File deleted successfully!"));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("You CANNOT DO THIS PPC!"));
    }
}
