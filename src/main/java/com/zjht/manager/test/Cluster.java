package com.zjht.manager.test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class Cluster {
    private static JedisCluster jc = null;

    public static void main(String[] args) {
        jc = CreateCluster();
        jc.set("foo", "bar");
        String value = jc.get("foo");
        System.out.println(value);
    }

    private static JedisCluster CreateCluster() {
        Set<HostAndPort> jedisnodes = new HashSet<HostAndPort>();
        jedisnodes.add(new HostAndPort("172.16.94.248", 7000));
        jedisnodes.add(new HostAndPort("172.16.94.248", 7001));
        jedisnodes.add(new HostAndPort("172.16.94.248", 7002));
        jedisnodes.add(new HostAndPort("172.16.94.248", 7003));
        jedisnodes.add(new HostAndPort("172.16.94.248", 7004));
        jedisnodes.add(new HostAndPort("172.16.94.248", 7005));
        return new JedisCluster(jedisnodes);
    }
}