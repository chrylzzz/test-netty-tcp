package com.sdsoon.netty2.kryo;

import com.esotericsoftware.kryo.Kryo;

/**
 * 创建一个ThreadLocalKryoFactory继承KryoFactory，用来为每个线程创建一个Kryo对象，原因是由于Kryo 不是线程安全的。
 * 每个线程都应该有自己的 Kryo，Input 和 Output 实例。此外，
 * bytes[] Input 可能被修改，然后在反序列化期间回到初始状态，因此不应该在多线程中并发使用相同的 bytes[]。
 * <p>
 * Created By Chr on 2019/4/25/0025.
 */
public class ThreadLocalKryoFactory extends KryoFactory {

    //Kryo 实例的创建/初始化是相当昂贵的，所以在多线程的情况下，您应该线程池化 Kryo 实例。
    //简单的解决方案是使用 ThreadLocal 将 Kryo实例绑定到 Threads
    private final ThreadLocal<Kryo> holder = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            return createKryo();
        }
    };

    public Kryo getKryo() {
        return holder.get();
    }
}
