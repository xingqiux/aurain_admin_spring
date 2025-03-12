package top.xkqq.service.impl;

import cn.hutool.core.date.DateUtil;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.xkqq.common.exception.AurainException;
import top.xkqq.properties.RainYunProperties;
import top.xkqq.service.FileUploadService;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private RainYunProperties rainYunProperties;

    /**
     * 上传文件到雨云对象存储服务
     *
     * @param multipartFile
     */
    @Override
    public String fileUpload(MultipartFile multipartFile) {


        try {
            // 文件和 s3 判空
            if (multipartFile == null) {

                throw new AurainException(501, "文件为空");
            }
            AmazonS3 s3Client = RainYunInit();

            // 判断是否有这个桶，如果没有创建一个
            if (!s3Client.doesBucketExistV2(rainYunProperties.getBucketName())) {
                s3Client.createBucket(rainYunProperties.getBucketName());
            } else {
                System.out.println(rainYunProperties.getBucketName() + " 已存在");
            }


            // 设置存储对象名称
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            String uuid = UUID.randomUUID().toString().replace("-", "");

            // 20230801/443e1e772bef482c95be28704bec58a901.jpg
            String fileName = dateDir + "/" + uuid + multipartFile.getOriginalFilename();

            System.out.println(fileName);

            // 设定文件元数据
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    rainYunProperties.getBucketName(),  // 桶名称
                    fileName,   // 文件名称
                    multipartFile.getInputStream(), // 文件的输入流
                    metadata    // 文件元数据
            );

            // 上传文件
            s3Client.putObject(putObjectRequest);


            // 构建文件访问 URL 返回
            return rainYunProperties.getEndPointUrl() + "/" + rainYunProperties.getBucketName() + "/" + fileName;

            // 错误处理
        } catch (AurainException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private AmazonS3 RainYunInit() {
        try {
            // 创建 AWS 凭证对象，使用提供的访问密钥和密钥
            BasicAWSCredentials credentials = new BasicAWSCredentials(rainYunProperties.getAccessKey(), rainYunProperties.getSecretKey());

            // 配置客户端连接参数
            ClientConfiguration clientConfiguration = new ClientConfiguration();
            clientConfiguration.setProtocol(
                    rainYunProperties.getEndPointUrl().startsWith("https") ?
                            Protocol.HTTPS : Protocol.HTTP
            );


            // 构建 S3 客户端对象
            AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                    .withClientConfiguration(clientConfiguration)       //配置连接信息
                    .withCredentials(new AWSStaticCredentialsProvider(credentials)) // 提供 AWS 凭证
                    .withPathStyleAccessEnabled(true)
                    .withEndpointConfiguration(new AmazonS3ClientBuilder.EndpointConfiguration(
                            rainYunProperties.getEndPointUrl(),
                            Regions.DEFAULT_REGION.getName())) // 配置终端节点
                    .build();

            // 打印连接成功信息
            System.out.println("连接 OSS 成功");

            return amazonS3;

        } catch (Exception e) {
            System.err.println("RainYun初始化失败: " + e.getMessage());
            e.printStackTrace();
            throw new AurainException(501, "RainYun初始化失败");
        }

    }
}
