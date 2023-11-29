package com.example.hazelcasttest;

import com.hazelcast.config.Config;
import com.hazelcast.config.ExecutorConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {
    public static final String MAP_TEST_NAME = "test123";
    public static final String EXECUTOR_TEST_NAME = "executor123";
    @Value("${hz.instance.name}")
    private String instanceName;

    @Value("${hz.network.port}")
    private String port;

    @Value("${hz.network.members}")
    private String member;

    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config()
                .setClusterName(instanceName)
                .setNetworkConfig(new NetworkConfig()
                        .setPort(Integer.parseInt(port))
                        .setJoin(new JoinConfig()
                                .setMulticastConfig(new MulticastConfig().setEnabled(false))
                                .setTcpIpConfig(new TcpIpConfig()
                                        .setEnabled(true)
                                        .addMember(member)
                                )
                        )
                );
        config.addExecutorConfig(new ExecutorConfig(EXECUTOR_TEST_NAME));
        config.addMapConfig(new MapConfig(MAP_TEST_NAME));
        return Hazelcast.newHazelcastInstance(config);
    }

}
