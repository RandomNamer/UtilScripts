package homeworkautomation.graph


object Huffman {
    private const val plainText =
        "Debbie Hart is going to swim across the English Channel tomorrow. She is going to set out from the French coast at five o'clock in the morning. Debbie is only eleven years old and she hopes to set up a new world record. She is a strong swimmer and many people feel that she is sure to succeed. Debbie's father will set out with her in a small boat. Mr. Hart has trained his daughter for years. Tomorrow he will be watching her anxiously as she swims the long distance to England. Debbie intends to take short rests every two hours. She will have something to drink but she will not eat any solid food. Most of Debbie's school friends will be waiting for her on the English coast. Among them will be Debbie's mother, who swam the Channel herself when she was a girl."
    private const val BRANCH: Char = '#'

    fun run() {
        println(stat(plainText))
        println(huffman(plainText))
    }

    private fun huffman(text: String) {
        println("Inputted text with length ${text.length}")
        var sortedNodes = stat(plainText).map { SimpleBiTree.Node(null, null, it) }.toMutableList()
        while (sortedNodes.size > 1) {
            val curRoot = SimpleBiTree.Node(
                sortedNodes[0],
                sortedNodes[1],
                BRANCH to sortedNodes[0].payload.second + sortedNodes[1].payload.second
            )
            repeat(2) { sortedNodes.removeAt(0) }
            sortedNodes.add(0, curRoot)
            sortedNodes.sortBy { it.payload.second }
        }
//        assert(sortedNodes.size == 1)
        SimpleBiTree(sortedNodes[0]).run {
            printTree { payload ->
                "(" + when (payload.first) {
                    '\n' -> "/n"
                    ' ' -> "/s"
                    else -> payload.first.toString()
                } + ", ${payload.second} )"
            }
            println(huffmanEncoding())
            println("Huffman length is ${huffmanSize()} bit, ${huffmanSize()/8} Byte whereas original length is ${plainText.length} Byte and ${plainText.length * 8} bits")
        }
    }

    private fun stat(text: String, needSort: Boolean = true): List<Pair<Char, Int>> {
        val res = mutableMapOf<Char, Int>();
        text.toCharArray().forEach { char ->
            res[char]?.let { res.replace(char, it + 1) }
            res.putIfAbsent(char, 1)
        }
        return if (needSort) res.toList().sortedBy { it.second } else res.toList()
    }

    private fun SimpleBiTree<Pair<Char, Int>>.huffmanEncoding(): Map<Char, String>{
        val res = mutableListOf<Pair<Char, String>>()
        huffmanCounter(root, res)
        return res.toMap()
    }

    private fun SimpleBiTree<Pair<Char, Int>>.huffmanSize(): Int{
        val huffmanCodec = this.huffmanEncoding()
        var totalLength = 0
        stat(plainText).forEach{
            huffmanCodec[it.first]?.length?.let { len -> totalLength += len * it.second  }
        }
        return totalLength
    }

    private fun huffmanCounter(node: SimpleBiTree.Node<Pair<Char, Int>>, record: MutableList<Pair<Char, String>>, route:String = "",){
        if (node.payload.first != BRANCH) record.add(node.payload.first to route)
        node.lchild?.let { huffmanCounter(it, record, route + "0") }
        node.rchild?.let { huffmanCounter(it, record, route + "1") }
    }
}

open class SimpleBiTree<T>(rootNode: Node<T>) {
    data class Node<T>(var lchild: Node<T>?, var rchild: Node<T>?, var payload: T) {
        fun isLeaf() = lchild == null && rchild == null
    }

    val root = rootNode

    fun printTree(node: Node<T> = root, indentation: Int = 0, payloadFormatter: ((T) -> String)? = null) {
//        println("starting print...")
        println()
        repeat(indentation) { print("  ") }
        if (payloadFormatter != null) {
            print("|-${payloadFormatter(node.payload)}")
        } else print("|-${node.payload}")
        node.lchild?.let {
            printTree(it, indentation + 1, payloadFormatter)
        }
        node.rchild?.let {
            printTree(it, indentation + 1, payloadFormatter)
        }
    }

}