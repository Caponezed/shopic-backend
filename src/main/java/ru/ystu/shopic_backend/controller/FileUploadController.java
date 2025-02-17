package ru.ystu.shopic_backend.controller;

import java.io.IOException;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.ystu.shopic_backend.exception.StorageFileNotFoundException;
import ru.ystu.shopic_backend.service.StorageService;


@AllArgsConstructor
@RestController
@RequestMapping("/api/files")
public class FileUploadController {

	@Autowired
	private final StorageService storageService;

	@GetMapping
	public List<String> getAllSavedFileLinks() throws IOException {
		var uploadedFilesLinks = storageService
				.loadAll()
				.map(path ->
					MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
					"serveFile", path.getFileName().toString()).build().toUri().toString()
				)
				.toList();

		return uploadedFilesLinks;
	}

	@GetMapping("{filename}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);

		if (file == null) return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping
	public ResponseEntity<String> saveFile(@RequestParam("file") MultipartFile file) {
		storageService.store(file);

		var responseString = "Файл '" + file.getOriginalFilename() + "' был успешно загружен на сервер";
		return new ResponseEntity<String>(responseString, HttpStatus.CREATED);
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}
