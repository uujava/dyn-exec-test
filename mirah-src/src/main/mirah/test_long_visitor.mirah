package ru.programpark.tests.mirah
import ru.programpark.tests.dao.NodeVisitor
import ru.programpark.tests.dao.Node

class TestLongVisitor
    implements NodeVisitor

    def initialize
      @summ = long(0)
    end

    def attr(attr:int):void
        @attr = attr
    end

    def visit(node:Node):void
        @summ += node.getLongValue(attr)
    end

    def getSumm:long
        @summ
    end
end