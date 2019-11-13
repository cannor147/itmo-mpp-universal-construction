class Solution : AtomicCounter {
    private val root = Node(0)
    private val last : ThreadLocal<Node> = ThreadLocal.withInitial {
        root
    }

    override fun getAndAdd(x: Int): Int {
        var old : Int
        while (true) {
            old = last.get().value
            val node = Node(old + x)
            last.set(last.get().next.decide(node))
            if (last.get() == node) {
                return old
            }
        }
    }

    private class Node(val value: Int) {
        val next = Consensus<Node>()
    }
}
