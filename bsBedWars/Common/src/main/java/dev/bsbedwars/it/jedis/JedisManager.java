package dev.bsbedwars.it.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
public class JedisManager {

    private final JedisPool jedisPool;

    public JedisManager(String host, int port) {
        this.jedisPool = new JedisPool(host, port);
    }


    public void send(String specificSubChannel, String content) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.publish(specificSubChannel, content);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(MessageListener listener, String... subChannels) {

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {

                    listener.onMessageReceived(channel, message);

                }
            }, subChannels);
        }

    }

    public void close() {
        jedisPool.close();
    }


    public interface MessageListener {
        void onMessageReceived(String channel, String message);
    }


}
