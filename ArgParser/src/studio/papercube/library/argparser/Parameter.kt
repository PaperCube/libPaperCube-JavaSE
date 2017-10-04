package studio.papercube.library.argparser

class Parameter {
    companion object {
        @JvmStatic fun resolve(arr: Array<String>): Parameter {
            for(i in arr.indices) arr[i] = arr[i].toLowerCase()
            val para = Parameter()
            val split = partitionArgs(arr)
            for (argList in split) {
                when (argList.size) {
                    0 -> {
                    }
                    1 -> para.flags.add(argList[0])
                    2 -> para.singleValueParameters.put(argList[0], argList[1])
                    else -> {
                        para.multipleValueParameters.put(argList[0], argList.subList(1, argList.size))
                    }
                }
            }
            return para
        }

        @JvmStatic fun partitionArgs(arr: Array<String>):List<List<String>> {
            val LEADING_PREFIX = "-"
            val wholeArg = ArrayList<List<String>>()
            var currentIndex = 0
            val length = arr.size
            wholeArg@ while (currentIndex < length) {
                val partialArg = ArrayList<String>()
                if (arr[currentIndex].startsWith(LEADING_PREFIX)) partialArg.add(arr[currentIndex++])
                else partialArg.add("\\unnamed")

                partialArg@ while (currentIndex < length) {
                    if (arr[currentIndex].startsWith(LEADING_PREFIX)) {
                        break@partialArg
                    } else {
                        partialArg.add(arr[currentIndex++])
                    }
                }

                wholeArg.add(partialArg)
            }
            return wholeArg
        }

        @Deprecated("Method renamed to partitionArgs", ReplaceWith("partitionArgs(arr)", "studio.papercube.library.argparser.Parameter.Companion.partitionArgs"))
        @JvmStatic
        fun splitArgs(arr: Array<String>) = partitionArgs(arr)
    }

    private val flags = ArrayList<String>()

    private val singleValueParameters = HashMap<String, String>()

    private val multipleValueParameters = HashMap<String, List<String>>()

    fun hasFlag(key: String) = key in flags || singleValueParameters.containsKey(key) || multipleValueParameters.containsKey(key)

    fun getSingleValue(key: String): String? {
        return singleValueParameters[key] ?: multipleValueParameters[key]?.get(1)
    }

    fun getMultipleValue(key: String): List<String>? {
        return multipleValueParameters[key] ?: singleValueParameters[key]?.let { listOf(it) }
    }

    fun getUnnamedArg(): List<String>? {
        return multipleValueParameters["\\unnamed"]
    }

}