#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common.service;

import java.time.LocalDateTime;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${package}.common.entity.DemoItem;

/**
 * 演示 Spring 事务 + Hibernate Session + 二级缓存。
 */
@Service
public class DemoItemService {

	private final SessionFactory sessionFactory;

	public DemoItemService(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 每个方法都是一个独立事务，因此两次调用会使用不同的 Hibernate Session，
	 * 第二次读取才能命中二级缓存。
	 */
	@Transactional
	public DemoItem loadOrCreate(Long id, String name) {
		Session session = this.sessionFactory.getCurrentSession();
		DemoItem item = session.find(DemoItem.class, id);
		if (item == null) {
			item = new DemoItem(name, LocalDateTime.now());
			session.persist(item);
		}
		return item;
	}

	@Transactional(readOnly = true)
	public DemoItem load(Long id) {
		return this.sessionFactory.getCurrentSession().find(DemoItem.class, id);
	}

	public Statistics getStatistics() {
		return this.sessionFactory.getStatistics();
	}

}
