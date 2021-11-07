package win.rainchan.aishot.aishot.spider.Bean

import com.qcloud.cos.exception.CosClientException
import com.qcloud.cos.exception.CosServiceException
import com.qcloud.cos.model.*
import ink.umb.cdn.web.pic.config.CosClient
import java.io.File
import java.net.URL
import java.util.*


class CosBucket {
    private lateinit var bucketName: String
    private lateinit var bucket:Bucket
    private var listObjectRequest:ListObjectsRequest = ListObjectsRequest()
    private var appId = "1304224524"

    private fun initListObjRequest(){
        listObjectRequest.bucketName = bucketName
        listObjectRequest.prefix = "images/"
        listObjectRequest.delimiter = "/"
        listObjectRequest.maxKeys = 10
    }
    constructor(bucketName: String)  {
        val createBucketRequest = CreateBucketRequest("$bucketName-$appId")
        try {
            bucket = CosClient.addBucket(createBucketRequest)!!
        }catch(e:CosServiceException){
            e.printStackTrace()
        }catch(e: CosClientException){
            e.printStackTrace()
        }
        initListObjRequest()
    }
    constructor(bucket:Bucket){
        this.bucket = bucket
        this.bucketName = bucket.name
        initListObjRequest()
    }
    public fun putObjRequest(file:File): URL? {
        val prefix: String = file.name.substring(file.name.lastIndexOf(".") + 1)
        val key = "images/${UUID.randomUUID()}.$prefix"
        val putObjRequest = PutObjectRequest(bucketName,key,file)
        CosClient.client.putObject(putObjRequest)
        return CosClient.client.getObjectUrl(bucketName,key)
    }
    fun getObjList(number: Int): MutableList<COSObjectSummary>? {
        var objListener = CosClient.client.listObjects(listObjectRequest)
        for (i in 0..number) {
            if (objListener.isTruncated) break
            listObjectRequest.marker = objListener.nextMarker
        }
        objListener = CosClient.client.listObjects(listObjectRequest)
        return objListener.objectSummaries
    }

    fun deleteObjRequest(key:String){
        CosClient.client.deleteObject(key,bucketName)
    }

}