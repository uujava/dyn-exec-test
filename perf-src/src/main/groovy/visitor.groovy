import ru.programpark.tests.dao.NodeVisitor
import ru.programpark.tests.dao.Node

public class TestVizitor implements NodeVisitor {
    private int attr = 0;
    private long summ =0;

    public void attr(int attr) {
        this.attr = attr
    }

    public void visit(Node node) {
        this.summ += node.getValue(attr)
    }

    public long getSumm() {
        return summ
    }
}

new TestVizitor()