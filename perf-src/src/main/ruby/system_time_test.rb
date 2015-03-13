class SystemTimeTest
	java_import 'java.lang.Runnable'
	java_import 'java.lang.System'
	include Runnable

	@@test_data=1 #avoid jit optimization

	def run
		a = System.current_time_millis
		@@test_data = a - System.current_time_millis
	end
end

SystemTimeTest.new