package win.rainchan.aishot.aishot.ai.ml

import android.graphics.Bitmap
import win.rainchan.aishot.aishot.ai.data.Person

interface PoseDetector : AutoCloseable {

    fun estimateSinglePose(bitmap: Bitmap): Person

    fun lastInferenceTimeNanos(): Long
}
