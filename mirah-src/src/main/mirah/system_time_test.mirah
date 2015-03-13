package ru.programpark.tests.mirah

class SystemTimeTest
    implements Runnable

    def run:void
       a = System.currentTimeMillis
       @duration = a - System.currentTimeMillis
    end

end