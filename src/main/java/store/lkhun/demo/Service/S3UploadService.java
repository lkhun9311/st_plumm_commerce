package store.lkhun.demo.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class S3UploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String s3UploadFile(MultipartFile file, String dirName) throws IOException {
        File uploadFile = convert(file).orElseThrow(() -> new IllegalArgumentException("파일 업로드에 실패했습니다."));
        String orginalFilename = file.getOriginalFilename();

        return upload(uploadFile, dirName, orginalFilename);
    }

    private String upload(File uploadFile, String dirName, String orginalFilename) {
        String fileName = dirName + "/" + UUID.randomUUID() + orginalFilename;
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);

        return uploadImageUrl;
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 정상적으로 삭제되었습니다.");
        } else {
            log.info("파일이 정상적으로 삭제되지 않았습니다.");
        }
    }

    private String putS3(File uploadFile, String fileName) {
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            log.error("AWS S3에 이미지 업로드 실패했습니다.");
            log.error(e.getMessage());
            removeNewFile(uploadFile);
            throw new RuntimeException();
        }
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    public void removeImageFolderInS3(String folderName) {
        ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request().withBucketName(bucket).withPrefix(folderName + "/");
        ListObjectsV2Result listObjectsV2Result = amazonS3Client.listObjectsV2(listObjectsV2Request);

        for (S3ObjectSummary objectSummary : listObjectsV2Result.getObjectSummaries()) {
            DeleteObjectRequest request = new DeleteObjectRequest(bucket, objectSummary.getKey());
            amazonS3Client.deleteObject(request);
            log.info("[S3] 이미지 폴더 삭제" + " - 폴더명 " + folderName + " (" + objectSummary.getKey() + ")");
        }
    }
}
