class Solution : AtomicCounter {
    private val root = Node(0)
    private val last : ThreadLocal<Node> = ThreadLocal.withInitial {
        root
    }

    override fun getAndAdd(x: Int): Int {
        while (true) {
            val node = Node(last.get().value + x)
            last.set(last.get().next.decide(node))
            if (last.get() == node) {
                return node.value - x;
            }
        }
    }

    private class Node(val value: Int) {
        val next = Consensus<Node>()
    }
}
