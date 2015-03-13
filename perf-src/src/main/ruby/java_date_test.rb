class JavaDateTime
	java_import 'java.lang.Runnable'
	java_import 'java.util.Date'
	include Runnable

	@@test_data=1 #avoid jit optimization

	def run
		a = Date.new
		@@test_data = a.getTime() - Date.new.getTime()
	end
end

JavaDateTime.new