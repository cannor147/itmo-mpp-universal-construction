import java.util.concurrent.ThreadLocalRandom

class Solution : AtomicCounter {
    private val root = Node(0)
    private val last : ThreadLocal<Node> = ThreadLocal.withInitial {
        root
    }

    override fun getAndAdd(x: Int): Int {
        while (true) {
            val old = last.get().value
            val res = old + x
            val node = Node(old)
            last.set(last.get().next.decide(node))
            if (last.get().id == node.id) {
                return res;
            }
        }
    }

    private class Node(val value: Int) {
        val id = ThreadLocalRandom.current().nextInt()
        val next = Consensus<Node>()
    }
}
