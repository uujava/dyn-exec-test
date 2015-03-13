class TestVizitor
	java_import 'ru.programpark.tests.dao.NodeVisitor'
	include NodeVisitor

	def initialize
		@summ = 0
	end

	def attr attr
		@attr = attr
	end

	def visit node
		@summ +=node.getValue(@attr)
	end

	def total
		@summ
	end
end

TestVizitor.new