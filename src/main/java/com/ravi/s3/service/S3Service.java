package com.ravi.s3.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class S3Service {

	@Value("${aws.s3.bucket}")
	private String bucketName;

	private final Path localPath = Paths.get(System.getProperty("java.io.tmpdir"), "s3-files");

	public S3Service() {
		try { Files.createDirectories(localPath); } catch (Exception e) {}
	}

	public Map<String, String> uploadFile(MultipartFile file) throws IOException {
		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		Path target = localPath.resolve(fileName);
		Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
		return Map.of(
			"fileName", fileName,
			"originalName", file.getOriginalFilename(),
			"size", String.valueOf(file.getSize()),
			"storage", "Local (S3-Ready)",
			"bucket", bucketName,
			"path", target.toString()
		);
	}

	public List<Map<String, String>> listFiles() throws IOException {
		if (!Files.exists(localPath)) return List.of();
		return Files.list(localPath).map(p -> {
			try {
				return Map.of("fileName", p.getFileName().toString(), "size", String.valueOf(Files.size(p)));
			} catch (Exception e) { return Map.of("fileName", p.getFileName().toString()); }
		}).collect(Collectors.toList());
	}

	public byte[] downloadFile(String fileName) throws IOException {
		return Files.readAllBytes(localPath.resolve(fileName));
	}

	public boolean deleteFile(String fileName) throws IOException {
		return Files.deleteIfExists(localPath.resolve(fileName));
	}
}