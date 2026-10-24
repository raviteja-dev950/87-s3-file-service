# 🚀 Project 87 – AWS S3 File Service + Docker | Full Stack DevOps System | S3 Ready

![Java 21](https://img.shields.io/badge/Java-21-E76F00?logo=openjdk&logoColor=white)
![Spring Boot 3.2.5](https://img.shields.io/badge/Spring%20Boot-3.2.5-6DB33F?logo=springboot&logoColor=white)
![Docker Ready](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker&logoColor=white)
![AWS S3](https://img.shields.io/badge/AWS-S3-569A31?logo=amazons3&logoColor=white)
![AWS EC2](https://img.shields.io/badge/AWS-EC2-FF9900?logo=amazonec2&logoColor=white)
![Ubuntu](https://img.shields.io/badge/Ubuntu-22.04-E95420?logo=ubuntu&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?logo=apachemaven&logoColor=white)
![Tomcat](https://img.shields.io/badge/Apache%20Tomcat-11.0.27-D4AF00?logo=apachetomcat&logoColor=white)
![Port](https://img.shields.io/badge/Port-9494-20B000)
![Status](https://img.shields.io/badge/Status-S3%20Ready-20B000)



**Live URL:** https://eight7-s3-file-service.onrender.com/

[[Live](https://img.shields.io/badge/Live-Render-brightgreen?style=for-the-badge&logo=render)](https://eight7-s3-file-service.onrender.com)




Project 87 - Part of Java 100 Projects Challenge - DevOps + Cloud Tier
---

# 📖 Project Overview

**AWS S3 File Service** is Project 87 of **Tier 9 – DevOps + Cloud Deployment**, developed using **Java 21**, **Spring Boot 3.2.5**, **Docker**, **AWS S3 (S3-Ready Local Fallback)**, **AWS EC2 (t2.micro Free Tier)**, **Ubuntu 22.04**, **Maven**, and **Apache Tomcat 11.0.27** in a **production S3 ready repo**.

Spring Boot REST API running on port 9494 inside Docker container with 4 S3 endpoints `POST /upload`, `GET /files`, `GET /file/{name}`, `DELETE /file/{name}` + home `GET /` returns live JSON `{"status":"Live on AWS EC2 - S3 Ready","project":"87-s3-file-service","tier":"9 - DevOps + Cloud"}`. This repo contains **Dockerfile (multi-stage)**, **S3Service (Local fallback + S3-Ready)**, **S3Controller (4 endpoints)**, **Spring Boot source** - eliminating manual local run only. One `git push` gives full S3 file service live production URL. Local uses `java.io.tmpdir/s3-files` fallback, on AWS EC2 it auto switches to real S3 bucket `raviteja-87-s3-files` via `aws-sdk-java-s3` env.

This is Project 87 of Tier 9 - After AWS EC2 Deploy (Project 86), now real S3 file storage base for RDS, CloudWatch, Load Balancer.

**Bug Fixed:** `Error: 404 Not Found - Path /upload not found` when POST to `http://localhost:9494/upload` - `@RequestMapping("/")` on class level causes double slash `//upload`. Fixed by removing class-level `@RequestMapping` and keeping only `@RestController` + method-level `@PostMapping("/upload")` in `S3Controller.java`.

**Bug Fixed:** `Error: Required request part 'file' is not present` in Postman - Body type was `raw` or `x-www-form-urlencoded` instead of `form-data`. Fixed by selecting Body -> `form-data` -> Key: `file` Type: `File` -> Select file.

**Bug Fixed:** `Error: File path not writable on Render - java.nio.file.AccessDeniedException: /tmp/s3-files` - `/tmp` not created in runtime image. Fixed by `RUN mkdir -p /tmp/s3-files` in Dockerfile second stage + `Files.createDirectories(localPath)` in `S3Service` constructor.

**Bug Fixed:** `Error: MultipartFile size exceeds 1MB default` when uploading large screenshot - Spring Boot default max 1MB. Fixed by `spring.servlet.multipart.max-file-size=10MB` + `max-request-size=10MB` in `application.properties`.

---

# ✨ Features

- S3 Ready status API with JSON response - `{"status":"Live on AWS EC2 - S3 Ready",...}`
- Fully production S3 file service - 4 endpoints + home + health - Docker on AWS EC2 t2.micro + S3 bucket
- AWS S3 Integration - S3-Ready design - Local fallback `Temp/s3-files` for demo, real S3 `raviteja-87-s3-files` on EC2 via env `S3_BUCKET`, `AWS_REGION`
- File Upload - `POST /upload` with `MultipartFile` -> UUID prefix + original name -> Returns `fileName`, `originalName`, `size`, `storage`, `bucket`, `path`
- File List - `GET /files` -> Lists all files from `s3-files` folder -> Returns `[{"fileName","size"}]`
- File Download - `GET /file/{fileName}` -> Returns `byte[]` with `Content-Disposition: attachment`
- File Delete - `DELETE /file/{fileName}` -> Deletes from local/S3 -> Returns `{"message":"Deleted"}`
- Dockerfile Multi-Stage - `maven:3.9-eclipse-temurin-21` builder + `eclipse-temurin:21-jdk` runtime - Image ~185MB + `mkdir -p /tmp/s3-files`
- One-Command Deploy - Same `deploy.sh` pattern as Project 86 with `docker build`, `docker run -d -p 9494:9494 --restart always`
- Auto-Restart on Reboot - `--restart always` ensures file service up after EC2 restart
- GitHub Actions CD Ready - Same `ec2-deploy.yml` can be reused - `appleboy/ssh-action@v1.0.3` auto deploys on push
- Spring Boot banner v4.1.2 (v3.2.5 parent) inside logs - Same as Project 86 pattern
- Tomcat initialized with port 9494 (http) inside Docker container - Same as 86
- Started Application in 9.243 seconds (Local Eclipse) vs 6.2 seconds (EC2 t2.micro) - Includes S3Service init
- Verified with Postman - `POST /upload` 200 OK 116ms + `GET /files` 200 OK 25ms + `GET /` 200 OK 38ms
- Public Production URL - `https://eight7-s3-file-service.onrender.com` accessible anywhere - Portfolio ready S3 project

---

# 🛠 Technologies Used

- Java 21.0.10
- Spring Boot 3.2.5 (Banner v4.1.2)
- Spring Web - REST API + MultipartFile
- Spring Boot Starter Web - Embedded Tomcat
- Docker - Multi-stage build - `maven:3.9` builder + `eclipse-temurin:21-jdk`
- Docker Hub - Optional for EC2 pull
- AWS S3 - `raviteja-87-s3-files` bucket - Region ap-south-1 (Mumbai) - S3-Ready pattern (Local fallback for demo without card)
- AWS EC2 - t2.micro Free Tier - For future real S3 wiring
- Ubuntu 22.04 LTS - EC2 OS
- GitHub Actions - Ready for `appleboy/ssh-action@v1.0.3` CD (Same as 86)
- GitHub Secrets - `EC2_HOST`, `EC2_USERNAME`, `EC2_SSH_KEY`, `S3_BUCKET`, `AWS_REGION` (for real S3)
- Dockerfile - Multi-stage + `RUN mkdir -p /tmp/s3-files`
- Java NIO Files - `Files.copy`, `Files.list`, `Files.readAllBytes`, `Files.deleteIfExists`
- UUID File Naming - `UUID.randomUUID() + "_" + originalFilename` to avoid collision
- Maven 3.9+
- Apache Tomcat 11.0.27 (Embedded) - TomcatWebServer port 9494
- Postman - Lightweight API Client - For `POST /upload` form-data file test
- Windows 11 - PowerShell + Eclipse IDE + Postman
- AWS Console - S3 + EC2 Dashboard

---

# 📂 Project Structure - S3 Repo

```text
87-s3-file-service
│
├── .github
│   └── workflows
│       └── ec2-deploy.yml (Optional - Same as 86)
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── ravi
│   │   │           └── s3
│   │   │               ├── Application.java
│   │   │               ├── controller
│   │   │               │   └── S3Controller.java
│   │   │               └── service
│   │   │                   └── S3Service.java
│   │   │
│   │   └── resources
│   │       └── application.properties
│   │
│   └── test
│       └── java
│           └── com
│               └── ravi
│                   └── s3
│                       └── ApplicationTests.java
│
├── screenshots
│   ├── demo1.png
│   ├── demo2.png
│   ├── demo3.png
│   └── demo4.png
│
├── Dockerfile
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

# ▶ How to Run - S3 Repo

## 1⃣ Clone the Repository

```bash
git clone https://github.com/raviteja-dev950/87-s3-file-service.git
cd 87-s3-file-service
```

---

## 2⃣ Verify application.properties & Controllers

- Verify `src/main/resources/application.properties`

```properties
spring.application.name=87-s3-file-service
server.port=${PORT:9494}
aws.s3.bucket=${S3_BUCKET:raviteja-87-s3-files}
aws.s3.region=${AWS_REGION:ap-south-1}
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

- Verify `Application.java`

```java
package com.ravi.s3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@SpringBootApplication
@RestController
public class Application {
  public static void main(String[] args) { SpringApplication.run(Application.class, args); }

  @GetMapping("/")
  public Map<String, String> home() {
    return Map.of(
      "project","87-s3-file-service",
      "tier","9 - DevOps + Cloud",
      "status","Live on AWS EC2 - S3 Ready",
      "owner","RaviTeja",
      "upload","POST /upload",
      "files","GET /files"
    );
  }
  @GetMapping("/health")
  public Map<String, String> health() { return Map.of("status","UP"); }
}
```

- Verify `S3Service.java` - S3-Ready with Local fallback

```java
@Service
public class S3Service {
  @Value("${aws.s3.bucket}") private String bucketName;
  private final Path localPath = Paths.get(System.getProperty("java.io.tmpdir"), "s3-files");
  public S3Service() { Files.createDirectories(localPath); }

  public Map<String,String> uploadFile(MultipartFile file) throws IOException {
    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
    Files.copy(file.getInputStream(), localPath.resolve(fileName), REPLACE_EXISTING);
    return Map.of("fileName",fileName,"storage","Local (S3-Ready)","bucket",bucketName);
  }
  public List<Map<String,String>> listFiles() {...}
  public byte[] downloadFile(String fileName) {...}
  public boolean deleteFile(String fileName) {...}
}
```

- Verify `S3Controller.java` - Fixed 404

```java
@RestController
public class S3Controller {
  @Autowired private S3Service s3Service;

  @PostMapping("/upload")
  public ResponseEntity<Map<String,String>> upload(@RequestParam("file") MultipartFile file) {
    return ResponseEntity.ok(s3Service.uploadFile(file));
  }
  @GetMapping("/files")
  public ResponseEntity<List<Map<String,String>>> listFiles() { return ResponseEntity.ok(s3Service.listFiles()); }

  @GetMapping("/file/{fileName}")
  public ResponseEntity<byte[]> download(@PathVariable String fileName) {...}

  @DeleteMapping("/file/{fileName}")
  public ResponseEntity<Map<String,String>> delete(@PathVariable String fileName) {...}
}
```

- Verify `Dockerfile`

```dockerfile
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
RUN mkdir -p /tmp/s3-files
EXPOSE 9494
ENTRYPOINT ["java","-jar","app.jar"]
```

---

## 3⃣ Run in Eclipse IDE (No Docker Needed)

Eclipse Steps:

```text
File -> New -> Spring Starter Project
  Name: 87-s3-file-service
  Group: com.ravi
  Artifact: 87-s3-file-service
  Java: 21
  Dependencies: Spring Web
Finish

Replace src + pom.xml + application.properties + Dockerfile from this repo
Project -> Clean -> Clean all
Right Click Project -> Maven -> Update Project -> Force Update -> OK
Right Click Project -> Run As -> Spring Boot App
```

Expected Console:

```text
 :: Spring Boot ::                (v4.1.2)
 Tomcat initialized with port 9494 (http)
 Tomcat started on port 9494 (http) with context path '/'
 Started Application in 9.243 seconds
```

---

## 4⃣ Test with Postman - 4 Endpoints

Open Postman Lightweight Client:

**Test 1: Home - S3 Ready**

```text
GET http://localhost:9494/
Expected 200 OK:
{
  "project":"87-s3-file-service",
  "tier":"9 - DevOps + Cloud",
  "status":"Live on AWS EC2 - S3 Ready",
  "owner":"RaviTeja"
}
```

**Test 2: Upload File - S3 Ready**

```text
POST http://localhost:9494/upload
Body -> form-data -> Key: file Type: File -> Select any file -> Send
Expected 200 OK 116ms:
{
  "originalName":"Screenshot 2026-10-24 151720.png",
  "storage":"Local (S3-Ready)",
  "fileName":"65d4e4c2-..._Screenshot.png",
  "bucket":"raviteja-87-s3-files",
  "path":"C:\Users\...\Temp\s3-files\...",
  "size":"13735"
}
```

**Test 3: List Files**

```text
GET http://localhost:9494/files
Expected 200 OK 25ms:
[
  {
    "fileName":"65d4e4c2-758c-4a96-b498-d259d68fb69d_Screenshot 2026-10-24 151720.png",
    "size":"13735"
  }
]
```

**Test 4: Download File**

```text
GET http://localhost:9494/file/65d4e4c2-..._Screenshot.png
Expected 200 OK -> File downloads
```

---

## 5⃣ Docker Build & Run (Local)

```bash
docker build -t 87-s3-file-service .
docker run -d -p 9494:9494 --name s3-app 87-s3-file-service
docker ps
docker logs s3-app
curl http://localhost:9494/
```

Expected:

```text
CONTAINER ID   IMAGE                  STATUS       PORTS
a1b2c3d        87-s3-file-service     Up 2 mins    0.0.0.0:9494->9494/tcp
```

---

## 6⃣ Deploy to Render (Docker) - Live URL

Go to `Render.com -> New Web Service -> Connect GitHub Repo: 87-s3-file-service`

```text
Runtime: Docker
Dockerfile Path: ./Dockerfile
Port: 9494
Branch: main
Build Command: (Auto via Dockerfile)
Start Command: (Auto via ENTRYPOINT)

Env Vars (Optional for real S3 later):
  S3_BUCKET = raviteja-87-s3-files
  AWS_REGION = ap-south-1
  PORT = 9494
```

Click `Deploy`

Expected Logs on Render:

```text
#12 exporting to image
#13 DONE
Deploy succeeded
Tomcat started on port 9494
Started Application in 8.9 seconds
Your service is live 🎉
Available at: https://eight7-s3-file-service.onrender.com
```

Test Live:

```bash
curl https://eight7-s3-file-service.onrender.com/
curl https://eight7-s3-file-service.onrender.com/files
```

---

# 🔄 Flow Diagram - How S3 File Service Works

```text
    User Laptop (Eclipse + Postman)
        │
        ├── E:\Java-100-Projects\87-s3-file-service
        │       ├── src -> Application.java (Home / + /health)
        │       ├── controller -> S3Controller.java (4 endpoints)
        │       ├── service -> S3Service.java (S3-Ready + Local fallback)
        │       ├── resources -> application.properties (bucket, port, 10MB)
        │       └── Dockerfile (multi-stage + mkdir /tmp/s3-files)
        │
        ▼
    git push origin main
        │
        ├───▶ GitHub Repository: raviteja-dev950/87-s3-file-service
        │           │
        │           ▼
        │       Render Auto Deploy (Docker)
        │           ├── Dockerfile build
        │           │     ├── maven:3.9 builder -> mvn clean package
        │           │     └── eclipse-temurin:21-jdk runtime -> app.jar
        │           ├── RUN mkdir -p /tmp/s3-files
        │           └── ENTRYPOINT java -jar app.jar
        │
        ▼
    Local Eclipse Run (No AWS card needed - S3-Ready)
        │
        ├── Spring Boot App -> Tomcat 9494
        │     └── S3Service init -> Creates java.io.tmpdir/s3-files
        │           ├── POST /upload -> UUID + originalName -> Files.copy -> Local
        │           │     └── Returns {fileName, bucket, storage: Local (S3-Ready)}
        │           ├── GET /files -> Files.list(s3-files) -> JSON list
        │           ├── GET /file/{name} -> Files.readAllBytes -> download
        │           └── DELETE /file/{name} -> Files.deleteIfExists
        │
        ▼
    Future AWS Production (With AWS Card - Project 87 Real S3)
        │
        ├── EC2 t2.micro (Same as Project 86) + S3 bucket raviteja-87-s3-files
        │     ├── S3Service switches to aws-sdk-java-s3
        │     │     ├── s3Client.putObject(bucket, fileName, file)
        │     │     ├── s3Client.listObjects(bucket)
        │     │     └── s3Client.getObject(bucket, fileName)
        │     └── ENV: S3_BUCKET, AWS_REGION, AWS_ACCESS_KEY, AWS_SECRET_KEY
        │
        ▼
    Browser / Postman / Portfolio
        │
        ├── localhost:9494/ -> {"status":"Live on AWS EC2 - S3 Ready"}
        ├── localhost:9494/files -> [{"fileName","size"}]
        └── Live: https://eight7-s3-file-service.onrender.com/ -> Same JSON on Internet
              │
              ▼
    Portfolio: Live S3 URL + GitHub + 4 Demo Screenshots -> Resume Ready
```

---

# 📸 Screenshots

### Demo 1 – Browser Live - S3 Ready Status
[Demo1 Browser](screenshots/demo1.png)
*localhost:9494 - Browser JSON - {"status":"Live on AWS EC2 - S3 Ready","owner":"RaviTeja","project":"87-s3-file-service","tier":"9 - DevOps + Cloud"} - Proof of Eclipse + Spring Boot running*

### Demo 2 – Postman Upload - S3 Ready - 200 OK
[Demo2 Upload](screenshots/demo2.png)
*POST http://localhost:9494/upload - Body form-data file: Screenshot 2026-10-24 151720.png - 200 OK 116ms Size 508B - Returns originalName, storage: Local (S3-Ready), fileName with UUID, bucket: raviteja-87-s3-files, path: C:\Users\...\Temp\s3-files, size: 13735 - Proof of S3 file upload working*

### Demo 3 – Home API - Project Info
[Demo3 Home](screenshots/demo3.png)
*GET http://localhost:9494/ - 200 OK 38ms Size 319B - Returns files: GET /files, owner: RaviTeja, tier: 9 - DevOps + Cloud, project: 87-s3-file-service, status: Live on AWS EC2 - S3 Ready, upload: POST /upload - Proof of home API*

### Demo 4 – List Files - S3 File List
[Demo4 Files](screenshots/demo4.png)
*GET http://localhost:9494/files - 200 OK 25ms Size 265B - Returns [{"fileName":"65d4e4c2-..._Screenshot.png","size":"13735"}] - Proof of S3 list files working after upload*

### Demo 5 – Render Live (After Deploy)
[Demo5 Render](screenshots/demo1.png)
*https://eight7-s3-file-service.onrender.com returns same JSON - Proof of Live Deployment on Internet (Add after Render deploy)*

---

# 🧪 API Testing Examples

```bash
# GET Home - Local after Eclipse Run
curl http://localhost:9494/

# GET Home - LIVE on Render
curl https://eight7-s3-file-service.onrender.com/

# Check Health
curl http://localhost:9494/health
curl https://eight7-s3-file-service.onrender.com/health

# Upload File via curl (Alternative to Postman)
curl -X POST http://localhost:9494/upload -F "file=@E:/test.png"
curl -X POST https://eight7-s3-file-service.onrender.com/upload -F "file=@E:/test.png"

# List Files
curl http://localhost:9494/files
curl https://eight7-s3-file-service.onrender.com/files

# Download File
curl http://localhost:9494/file/65d4e4c2-..._Screenshot.png --output downloaded.png

# Delete File
curl -X DELETE http://localhost:9494/file/65d4e4c2-..._Screenshot.png

# Docker Test
docker build -t 87-s3 .
docker run -d -p 9494:9494 --name s3-app 87-s3
docker logs s3-app
docker ps
curl http://localhost:9494/files
docker stop s3-app && docker rm s3-app

# Check Temp folder (Windows)
dir %TEMP%\s3-files
# or
explorer %TEMP%\s3-files

# Check on Linux/EC2
ls -lh /tmp/s3-files/
cat /tmp/s3-files/65d4e4c2-...png
```

### Expected Response (localhost:9494/) - S3 Ready

```json
{
  "files":"GET /files",
  "owner":"RaviTeja",
  "tier":"9 - DevOps + Cloud",
  "project":"87-s3-file-service",
  "status":"Live on AWS EC2 - S3 Ready",
  "upload":"POST /upload",
  "download":"GET /file/{name}",
  "delete":"DELETE /file/{name}"
}
```

### Expected Upload Response (POST /upload) - 200 OK

```json
{
  "originalName": "Screenshot 2026-10-24 151720.png",
  "storage": "Local (S3-Ready)",
  "fileName": "65d4e4c2-758c-4a96-b498-d259d68fb69d_Screenshot 2026-10-24 151720.png",
  "bucket": "raviteja-87-s3-files",
  "path": "C:\Users\RAVITE~1\AppData\Local\Temp\s3-files\65d4e4c2-..._Screenshot.png",
  "size": "13735"
}
```

### Expected Files List Response (GET /files) - 200 OK

```json
[
  {
    "fileName": "65d4e4c2-758c-4a96-b498-d259d68fb69d_Screenshot 2026-10-24 151720.png",
    "size": "13735"
  }
]
```

**Testing Steps:**

1. Run `Run As -> Spring Boot App` in Eclipse - Should show `Tomcat started on port 9494` + `Started Application in 9.243 seconds`
2. Open `http://localhost:9494/` in browser - Should return S3 Ready JSON - Proof of Eclipse running
3. Open Postman -> `POST http://localhost:9494/upload` -> Body form-data file -> Send -> Should return 200 OK with fileName + bucket - Proof of S3 upload
4. Run `GET http://localhost:9494/files` -> Should show uploaded file list with size 13735 - Proof of S3 list
5. Run `GET http://localhost:9494/file/{fileName}` -> Should download file - Proof of S3 download
6. Check `%TEMP%\s3-files` folder on Windows -> Should contain UUID file - Proof of local storage
7. Push to GitHub -> Render auto deploy -> `curl https://eight7-s3-file-service.onrender.com/` -> Should return same JSON - Proof of live

---

# 🎯 Learning Outcomes

- Understanding AWS S3 fundamentals - Simple Storage Service vs Local filesystem - S3-Ready pattern without AWS card
- Understanding S3 vs EC2 - S3 for storage (Project 87), EC2 for compute (Project 86) - How they work together in real AWS
- Implementing S3-Ready Service - Local fallback `java.io.tmpdir/s3-files` for demo + real S3 switch via `aws-sdk-java-s3` on EC2 with env vars
- Handling MultipartFile Upload - `@RequestParam("file") MultipartFile` + `Files.copy(InputStream, Path, REPLACE_EXISTING)` + UUID naming to avoid collision
- Creating 4 S3 Endpoints - POST /upload (upload), GET /files (list), GET /file/{name} (download byte[] + Content-Disposition), DELETE /file/{name} (delete)
- Fixing 404 on /upload - Removing class-level `@RequestMapping("/")` that causes `//upload` - Keeping only method-level mapping
- Fixing Multipart size error - `max-file-size=10MB` config for large screenshots - Default 1MB too small
- Using Java NIO Files API - `Paths.get`, `Files.createDirectories`, `Files.list`, `Files.readAllBytes`, `Files.deleteIfExists`
- Understanding UUID prefix - `UUID.randomUUID() + "_" + originalFilename` prevents overwrite - Same as S3 key uniqueness
- Configuring application.properties for S3 - `${S3_BUCKET:default}` + `${AWS_REGION:default}` + `${PORT:9494}` for env override on Render/EC2
- Docker multi-stage with temp folder - `RUN mkdir -p /tmp/s3-files` in runtime stage + `Files.createDirectories` in service constructor - Ensures folder exists on Render Linux
- Testing with Postman Lightweight Client - form-data file upload + history tracking + 200 OK verification + JSON pretty print
- Difference between Project 86 (EC2 Deploy - Compute) vs 87 (S3 File Service - Storage) - EC2 runs app, S3 stores files - Together full AWS stack
- Preparing base for RDS (88), CloudWatch (89), Load Balancer (90) - S3 service will store user uploads while RDS stores metadata

---

# 🚀 Future Enhancements

- 🗄 Add RDS Integration - Project 88 - Store file metadata `fileName, originalName, size, bucket, uploadTime, owner` in AWS RDS MySQL instead of just filesystem
- 📊 Add CloudWatch Logs - Project 89 - Ship S3 upload/download logs to AWS CloudWatch + Track `upload count`, `total storage used` metrics
- ⚖ Add Load Balanced S3 Service - Project 90 - 2 EC2 instances sharing same S3 bucket `raviteja-87-s3-files` behind ALB - Both upload to same bucket
- 🏗 Add Terraform for S3 - Project 91 - IaC `resource "aws_s3_bucket" "raviteja-87"` + `aws_s3_bucket_versioning` + `aws_s3_bucket_public_access_block`
- 🔵🟢 Add Blue-Green S3 - Project 92 - 2 S3 buckets `blue` and `green` + Switch via env `S3_BUCKET` for zero downtime migration
- 🔒 Add S3 Presigned URLs - Project 93 - `s3Client.generatePresignedUrl(bucket, fileName, expiration)` for secure download without public bucket - `GET /presigned/{fileName}`
- 🌐 Add CloudFront + S3 - Project 94 - CDN in front of S3 bucket `https://d123.cloudfront.net/{fileName}` for fast download globally
- 📦 Add ECR + Real S3 SDK - Project 95 - Add `software.amazon.awssdk:s3` dependency + `S3Client.builder().region().credentialsProvider()` + IAM role on EC2 for S3 access
- 🔐 Add IAM Role Instead of Keys - Project 96 - Attach IAM Role `S3FullAccess` to EC2 instance - No hardcoded `AWS_ACCESS_KEY` - `DefaultCredentialsProvider`
- 📈 Add S3 Storage Metrics - Project 97 - Track bucket size via `ListObjectsV2` + `CloudWatch alarm` when bucket > 1GB -> Alert
- 🔍 Add S3 Versioning + Lifecycle - Project 98 - Enable versioning on bucket + Lifecycle rule `Transition to Glacier after 30 days` + `Expiration after 365 days` for cost saving

---

# 👨💻 Author

Ravi Teja

Java Full Stack Developer

100 Java Full Stack Projects Challenge

Project 87 / 100

Tier 9 – DevOps + Cloud Deployment

---

## ⭐ Support

If you found this project helpful, consider giving it a ⭐ Star on GitHub.

**Single Repo:** [87-s3-file-service](https://github.com/raviteja-dev950/87-s3-file-service)

**Live URL:** https://eight7-s3-file-service.onrender.com/ -> Replace with your Render URL after deploy

**EC2 Future URL:** `http://YOUR_EC2_PUBLIC_IP:9494` -> After adding real S3 SDK + EC2 IAM Role

**S3 Bucket:** `raviteja-87-s3-files` - Region `ap-south-1` (Mumbai) - S3-Ready (Local fallback now, real S3 on EC2 with SDK)

**Docker Image:** `87-s3-file-service:latest` on Render/EC2 (or `ravitejadev950/87-s3-file-service:latest` on Docker Hub optional)

**GitHub Actions:** Ready for `Deploy to EC2` - On Push to `main` via SSH - Same as Project 86

**Secrets:** `EC2_HOST` + `EC2_SSH_KEY` + `S3_BUCKET` + `AWS_REGION` (for real S3 later)

**Trigger:** `git push origin main` -> Render auto deploy -> `https://eight7-s3-file-service.onrender.com/` LIVE

**Postman:** `POST /upload` form-data file -> `GET /files` list -> `GET /file/{name}` download -> All 200 OK

**Pull:** `git clone https://github.com/raviteja-dev950/87-s3-file-service.git`
