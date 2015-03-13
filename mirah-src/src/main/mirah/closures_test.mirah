interface SomeA
  def eval
  end
end

class A
    def methodB(block: SomeA):void
        block.eval
    end
end

class B
   def self_method
      puts "uhu"
   end

   def test
    a = A.new
    b = self
    a.methodB do
        b.self_method
    end
   end

   def self.main(args: String[]):void
        B.new.test
   end
end