package win.rainchan.aishot.aishot

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import win.rainchan.aishot.aishot.cloudai.RecommendImage

@RunWith(AndroidJUnit4::class)
class CloudPredictTest {

    @Test
    fun testPredict() {
        val data = arrayOf(
            arrayOf(0.443107008934021, 0.513418972492218, 0.5877135396003723),
            arrayOf(0.4209764301776886, 0.5412319898605347, 0.6293677091598511),
            arrayOf(0.41709238290786743, 0.5032067894935608, 0.8169424533843994),
            arrayOf(0.43400558829307556, 0.5827043652534485, 0.7698672413825989),
            arrayOf(0.4245045781135559, 0.5053665637969971, 0.615940272808075),
            arrayOf(0.5324029326438904, 0.5812812447547913, 0.46479350328445435),
            arrayOf(0.5353257656097412, 0.5416815280914307, 0.4798232614994049),
            arrayOf(0.6952898502349854, 0.4979976415634155, 0.23118004202842712),
            arrayOf(0.6922236084938049, 0.5016087889671326, 0.26456204056739807),
            arrayOf(0.7890175580978394, 0.38849392533302307, 0.4100069999694824),
            arrayOf(0.7819538116455078, 0.3928080201148987, 0.23782801628112793),
            arrayOf(0.8287458419799805, 0.5163382291793823, 0.39897581934928894),
            arrayOf(0.8269912600517273, 0.5010375380516052, 0.4079165458679199),
            arrayOf(0.9965953230857849, 0.5384706854820251, 0.17280957102775574),
            arrayOf(0.9923803806304932, 0.5383150577545166, 0.1688224971294403),
            arrayOf(1.0193126201629639, 0.4518904387950897, 0.02227894961833954),
            arrayOf(1.0057803392410278, 0.44581159949302673, 0.04381632059812546)
        )
        runBlocking {
            val bitMap = RecommendImage.getImage(data)
            Assert.assertTrue(bitMap.height > 0)
            bitMap.recycle()
        }

    }
}