package win.rainchan.aishot.aishot.until

import java.io.File
import java.util.*

object LocalGalleryList {
    public fun getFilesAllName(path: String?): MutableList<String> {
        //传入指定文件夹的路径　　　　
        var file = File(path);
        val files: Array<File> = file.listFiles()
        val imagePaths: MutableList<String> = ArrayList()
        for (i in files.indices) {
            if (checkIsImageFile(files[i].path)) {
                imagePaths.add(files[i].path)
            }
        }
        return imagePaths
    }
    private fun checkIsImageFile(fName: String): Boolean {
        var isImageFile = false
        //获取拓展名
        val fileEnd = fName.substring(
            fName.lastIndexOf(".") + 1,
            fName.length
        ).lowercase(Locale.getDefault())
        isImageFile =
            fileEnd == "jpg" || fileEnd == "png" || fileEnd == "gif" || fileEnd == "jpeg" || fileEnd == "bmp"
        return isImageFile
    }

}