#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common.config;

import java.io.InputStream;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 * 从 classpath 下的 redisson.yaml 创建 RedissonClient。
 * Spring Session 通过 RedissonConnectionFactory 使用该客户端。
 */
public class RedissonClientFactoryBean
		implements FactoryBean<RedissonClient>, InitializingBean, DisposableBean {

	private Resource configLocation;

	private boolean lazyInitialization = false;

	private RedissonClient redissonClient;

	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}

	public void setLazyInitialization(boolean lazyInitialization) {
		this.lazyInitialization = lazyInitialization;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.configLocation == null) {
			throw new IllegalArgumentException("configLocation must not be null");
		}
		Config config;
		try (InputStream inputStream = this.configLocation.getInputStream()) {
			config = Config.fromYAML(inputStream);
		}
		config.setLazyInitialization(this.lazyInitialization);
		this.redissonClient = Redisson.create(config);
	}

	@Override
	public RedissonClient getObject() {
		return this.redissonClient;
	}

	@Override
	public Class<?> getObjectType() {
		return RedissonClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void destroy() {
		if (this.redissonClient != null && !this.redissonClient.isShutdown()) {
			this.redissonClient.shutdown();
		}
	}

}
