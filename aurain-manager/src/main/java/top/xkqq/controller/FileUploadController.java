package top.xkqq.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.xkqq.service.FileUploadService;
import top.xkqq.vo.common.Result;
import top.xkqq.vo.common.ResultCodeEnum;

@Tag(name = " 文件上传接口")
@RestController
@RequestMapping("/admin/system/")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @RequestMapping("fileUpload")
    @Schema(description = "文件上传")
    public Result<String> fileUpload(@RequestParam(value = "file") MultipartFile multipartFile) {
        return Result.build(fileUploadService.fileUpload(multipartFile), ResultCodeEnum.SUCCESS);
    }

}
