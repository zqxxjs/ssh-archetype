#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Hibernate 二级缓存演示实体。
 *
 * <p>@Cacheable + @Cache 让 Hibernate 把实体放入 RedissonRegionFactory 管理的
 * 二级缓存；第二个事务再次读取同一主键时不会发出 SELECT。</p>
 */
@Entity
@Table(name = "demo_item")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DemoItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 64)
	private String name;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	public DemoItem() {
	}

	public DemoItem(String name, LocalDateTime createdAt) {
		this.name = name;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
