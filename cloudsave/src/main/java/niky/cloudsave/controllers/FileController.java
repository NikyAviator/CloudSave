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

    /**
     * @param file - An object of the type MultipartFile. File is saved to db.
     * @param user - An object of the UserObject, token is taken from the Bearer token. User is saved to db.
     * @return returning a message depenpding on if we succeeded with the upload or not.
     *
     * */

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

    /**
     *
     * @param user - An object of the UserObject, token is taken from the Bearer token.
     * @return - returns a list of all files that are connected to the logged-in user.
     */

    @GetMapping("/myfiles")
    public ResponseEntity<List<ResponseFile>> getMyFiles(@AuthenticationPrincipal UserObject user) {
        List<ResponseFile> files = fileService.getAllFiles(user).map(dbFile -> {
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

    /**
     *
     * @param id - takes in the id of the file that needs to be shown.
     * @param user - An object of the UserObject, token is taken from the Bearer token.
     * @return - returns an array of byte[] (binary form)
     */

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id, @AuthenticationPrincipal UserObject user) {
        FileDB fileDB = fileService.getFile(id);
        var fileUser = fileService.getFile(id).getUser().getUserId();
        var realUser = user.getUser().getUserId();
        if(fileUser.toString().equals(realUser.toString())){
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                    .body(fileDB.getData());
        }else{
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    /**
     *
     * @param user - takes in the user object that owns the file as a pathvariable.
     * @param id - An object of the UserObject, token is taken from the Bearer token.
     *           We check if the file belongs to the logged-in user
     * @return - Returns response message and deletes the file if the user is logged in
     *          else, it returns a message that we cannot do this!
     */

    @DeleteMapping("/file/{id}")
    public ResponseEntity<ResponseMessage> deleteFile(@AuthenticationPrincipal UserObject user,@PathVariable String id) {
        var fileUser = fileService.getFile(id).getUser().getUserId();
        var realUser = user.getUser().getUserId();
        if(fileUser.toString().equals(realUser.toString())){
            fileService.deleteFile(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("File deleted successfully!"));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("You CAN NOT DO THIS!"));
    }
}
