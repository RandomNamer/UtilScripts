package dmzjapiexplore

import com.alibaba.fastjson.JSON
import java.util.Base64


object DmzjParamFinder {
    enum class ParamType {
        _ID,
    }
    @JvmStatic
    @JvmOverloads
    fun run (type: ParamType, additionalParam: Any? = null) {
        val result: String = when (type) {
            ParamType._ID -> getId()
        }
        println("Param ${type.toString().toLowerCase()}=${result}")
    }

    private fun getId(): String {
        val androidBase64NoWrap = Base64.getEncoder().withoutPadding()
        return androidBase64NoWrap.encodeToString(JSON.toJSONString(java.util.HashMap<Any, Any>()).toByteArray())
    }

}