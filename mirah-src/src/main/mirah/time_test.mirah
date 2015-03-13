package ru.programpark.tests.mirah
import java.util.Date

class TimeTest
    implements Runnable
	@@AAA = 'test'
	
    def run:void
       a = Date.new
       @duration = a.getTime - Date.new.getTime
    end

end