package com.ravi.s3.controller;

import com.ravi.s3.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@RestController
public class S3Controller {

	@Autowired
	private S3Service s3Service;

	@PostMapping("/upload")
	public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
		try {
			if (file.isEmpty()) return ResponseEntity.badRequest().body(Map.of("error", "File empty"));
			return ResponseEntity.ok(s3Service.uploadFile(file));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
		}
	}

	@GetMapping("/files")
	public ResponseEntity<List<Map<String, String>>> listFiles() {
		try { return ResponseEntity.ok(s3Service.listFiles()); }
		catch (Exception e) { return ResponseEntity.status(500).build(); }
	}

	@GetMapping("/file/{fileName}")
	public ResponseEntity<byte[]> download(@PathVariable String fileName) {
		try {
			byte[] data = s3Service.downloadFile(fileName);
			return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(data);
		} catch (Exception e) { return ResponseEntity.notFound().build(); }
	}

	@DeleteMapping("/file/{fileName}")
	public ResponseEntity<Map<String, String>> delete(@PathVariable String fileName) {
		try {
			boolean deleted = s3Service.deleteFile(fileName);
			return deleted ? ResponseEntity.ok(Map.of("message", "Deleted: " + fileName))
					: ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
		}
	}
}