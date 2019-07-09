package com.ali.ossdeam.copy;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CopyObjectRequest;
import com.aliyun.oss.model.CopyObjectResult;
import com.aliyun.oss.model.ObjectMetadata;

public class CopyObjectOss {
    public static void main(String[] args) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIBSVt9w3LSFby";
        String accessKeySecret = "5Z6Q0M1jYy53mTZx76NHcZ7Fw2rgBA";

        String sourceBucketName = "zhangsenoss";
        String sourceObjectName = "5.jpg";
        String destinationBucketName = "zhangsenhuabei2";
        String destinationObjectName = "change5.jpg";

// 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

// 创建CopyObjectRequest对象。
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(sourceBucketName, sourceObjectName, destinationBucketName, destinationObjectName);

// 设置新的文件元信息。
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("text/html");
        copyObjectRequest.setNewObjectMetadata(meta);

// 复制文件。
        CopyObjectResult result = ossClient.copyObject(copyObjectRequest);
        System.out.println("ETag: " + result.getETag() + " LastModified: " + result.getLastModified());

// 关闭OSSClient。
        ossClient.shutdown();
    }
}
