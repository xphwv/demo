package com.xphwv.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xupan on 2018/5/18
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
//        phantom();
        Object obj = new Object();
        ReferenceQueue<Object> refQueue = new ReferenceQueue<Object>();
        SoftReference<Object> softRef = new SoftReference<Object>(obj, refQueue);
        System.out.println(softRef.get()); //java.lang.Object@2503dbd3
        System.out.println(refQueue.poll());// null

        // 清除强引用,触发GC
        obj = null;
        System.gc();

        System.out.println(softRef.get());
        while (true) {
            Thread.sleep(2000);
            Reference<?> reference = refQueue.poll();
            System.out.println(reference);
            if (reference != null) {
                System.exit(0);
            }
        }
    }

    private static void phantom() {
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue();
        List<LargeObjectFinalizer> references = new ArrayList();
        List<Object> largeObjects = new ArrayList();

        for (int i = 0; i < 10; ++i) {
            Object largeObject = new Object();
            largeObjects.add(largeObject);
            references.add(new LargeObjectFinalizer(largeObject, referenceQueue));
        }

        largeObjects = null;
        System.gc();

        Reference<?> referenceFromQueue;
        for (PhantomReference<Object> reference : references) {
            System.out.println(reference.isEnqueued());
        }

        while ((referenceFromQueue = referenceQueue.poll()) != null) {
            ((LargeObjectFinalizer) referenceFromQueue).finalizeResources();
            referenceFromQueue.clear();
        }
    }

    static class LargeObjectFinalizer extends PhantomReference<Object> {

        public LargeObjectFinalizer(
                Object referent, ReferenceQueue<? super Object> q) {
            super(referent, q);
        }

        public void finalizeResources() {
            // free resources
            System.out.println("clearing ...");
        }
    }
}
