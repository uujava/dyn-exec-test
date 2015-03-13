package ru.programpark.tests.mirah
import ru.programpark.tests.dao.NodeVisitor
import ru.programpark.tests.dao.Node

class TestVisitor
    implements NodeVisitor

    def initialize
      @summ = 0
    end

    def attr(attr:int):void
        @attr = attr
    end

    def visit(node:Node):void
        @summ += Number(node.getValue(attr)).longValue()
    end

    def getSumm:long
        @summ
    end
end