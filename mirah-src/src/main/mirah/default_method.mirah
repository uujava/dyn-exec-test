interface DefaultMethod
  def self.check:void
     puts "test static check"
  end
#  def check:void
#       puts "test instance check"
#  end

end

class DefaultMethodIncluded
    implements DefaultMethod

    def describe
       check
       DefaultMethod.check
    end

    def main(args: String[]):void
       DefaultMethodIncluded.new.describe
    end
end