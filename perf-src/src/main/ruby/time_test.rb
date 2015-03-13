class TimeTest
	java_import 'java.lang.Runnable'
	include Runnable
	@@test_data=1 #avoid jit optimization
	def run
		a = Time.new
		@@test_data = Time.new - a
	end
end

TimeTest.new