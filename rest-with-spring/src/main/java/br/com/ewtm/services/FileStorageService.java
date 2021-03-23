package br.com.ewtm.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.ewtm.config.FileStorageConfig;
import br.com.ewtm.exception.FileStorageException;
import br.com.ewtm.exception.MyFileNotFoundException;

@Service
public class FileStorageService {

	private final Path fileStoraLocation;
	
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		
		this.fileStoraLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(this.fileStoraLocation);
		} catch( Exception e) {
			throw new FileStorageException("Could not create the directory where the uploaded files"
					+ " will be stored",e);
		}
	}
	
	public String  storeFile(MultipartFile file) {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			if(fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalide path sequence" + fileName);
			}
			
			Path targetLocation = this.fileStoraLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation,StandardCopyOption.REPLACE_EXISTING);
			
			return fileName;
			
		}catch (Exception e) {
			throw new FileStorageException("Could not store file" + fileName  + "Please try again",e);
		}
		
	}
	
	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStoraLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			
			if(resource.exists()) {
				return resource;
			}else {
				throw new MyFileNotFoundException("File not found" + fileName);
			}
			
		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found" + fileName,e);
		}
		
	}
	
}
