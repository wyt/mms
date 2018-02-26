package org.wangyt.mms.util.security;

import java.util.concurrent.atomic.AtomicInteger;

public class ProfileUtil {

    private static AtomicInteger counter = new AtomicInteger(0);

    public static long getAtomicCounter() {
        if (counter.get() > 999999) {
            counter.set(1);
        }
        long time = System.currentTimeMillis();
        long returnValue = time * 100 + counter.incrementAndGet();
        return returnValue;
    }

    public static void main(String[] args) {
         
        System.out.println(ProfileUtil.getAtomicCounter());
    }
    
     
}