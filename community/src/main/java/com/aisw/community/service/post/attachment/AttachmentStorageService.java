package com.aisw.community.service.post.attachment;

import com.aisw.community.advice.exception.AttachmentStorageException;
import com.aisw.community.advice.exception.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class AttachmentStorageService {
    private final Path fileStorageLocation = Paths.get("/Users/wonchang/desktop/files/").toAbsolutePath().normalize();

    @Autowired
    public AttachmentStorageService(){
        try{
            Files.createDirectories(this.fileStorageLocation);
        } catch(Exception e) {
//            throws new AttachmentStorageException("No directories");
        }
    }

    public String storeFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(fileName.contains("..")){
                throw new AttachmentStorageException("Invalid File Name");
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (Exception e){
            throw new AttachmentStorageException("Could not store file " + fileName);
        }
    }

    public Resource loadFileAsResource(String fileName){
        try{
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()){
                return resource;
            }
            else{
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException e){
            throw new MyFileNotFoundException("File not found " + fileName);
        }
    }
}
