class Node
	attr_accessor :children
	attr_accessor :parent

	def initialize
		@parent = nil
		@attrs = []
		puts "node: "
	end

	def parent= parent
		@parent = parent
		parent.children << self
	end

	def getValue(attrId)
		@attrs[attrId]
	end

	def visit_all visitor
		visitor.accept(self)
		@children.each { |c| c.visit_all visitor }
	end

	def self.root
		@@root
	end

	def self.init_tree
		@@root = Node.new
		fill_level @@root, [5, 20, 10, 1000], 0
	end

	def self.fill_level parent, cardinality, level
		level_size = cardinality[level]
		puts "level #{level} #{level_size}"
		level_size.times do

			child = Node.new
			child.random_attrs
			child.parent = parent
			fill_level child, cardinality, level+1
		end
	end

	def random_attrs
		ATTRS.each do |attr|
			@attrs[attr.order] = attr.next
		end
		puts "attrs: #{@attrs.inspect}"
	end

	class Attr
		@@order = 0

		attr_reader :order

		def initialize &block
			@order = @@order
			@@order+=1
			@generator = block
		end

		def next
			@generator.call
		end
	end

	@@node_count = 0

	ID = Attr.new do
		n = @@node_count
		@@node_count +=1
		return n
	end

	CHILDREN = Attr.new do
		return []
	end

	PARENT = Attr.new do
		nil
	end

	RANDOM = Attr.new do
		return rand 1000000000
	end

	DATE =Attr.new do
		Time.new
	end

	BOOLEAN = Attr.new do
		((rand 1) > 0.5) ? true : false
	end

	DOUBLE = Attr.new do
		rand
	end

	CLSID = Attr.new do
		rand 1000
	end

	ATTRS = [ID, CHILDREN, PARENT, RANDOM, DATE, BOOLEAN, DOUBLE, CLSID]
end

Node.init_tree
visitor = Object.new
def visitor.accept node
	value = node.value DOUBLE.order
	@summ = (0|| @summ) + value
	@count = (0 || @count) + 1
end

def visitor.print
	puts "sum: #{@summ} count: #{@count}"
end

Node.root.visit_all visitor
visitor.print