package ink.umb.cdn.web.pic.config;

import win.rainchan.aishot.aishot.spider.Bean.CosBucket
import com.qcloud.cos.COSClient
import com.qcloud.cos.ClientConfig
import com.qcloud.cos.auth.BasicCOSCredentials
import com.qcloud.cos.model.Bucket
import com.qcloud.cos.model.CreateBucketRequest
import com.qcloud.cos.region.Region

object CosClient {
    private const val secretId = "AKIDQfeMnxrUqvkHXVDenfGadPu85k8F5eww"
    private const val secretKey = "lFRM1PWMvDEyQcXBXdS0GGzRcW9VaPyG"
    private val cred = BasicCOSCredentials(secretId,secretKey)
    private val region = Region("ap-nanjing")
    private val clientConfig = ClientConfig(region)
    val client = COSClient(cred,clientConfig)
    private var listBuckets = client.listBuckets()
    fun getBucket(bucketName: String): CosBucket {
        for (bucket in listBuckets){
            if(bucket.name == bucketName){
                return CosBucket(bucket)
            }
        }
        throw NullPointerException("存储桶不存在")
    }
    fun getListBuckets():List<Bucket>{
        return listBuckets
    }
    private fun flushBuckets(){
        this.listBuckets = client.listBuckets()
    }
    fun addBucket(createBucketRequest: CreateBucketRequest): Bucket? {
        val bucket = client.createBucket(createBucketRequest)
        flushBuckets()
        return bucket
    }
    fun deleteBucket(bucketName:String){
        client.deleteBucket(bucketName)
    }
}
