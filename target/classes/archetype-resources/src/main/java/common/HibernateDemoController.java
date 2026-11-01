#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.stat.Statistics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ${package}.common.entity.DemoItem;
import ${package}.common.service.DemoItemService;

/**
 * 演示 Hibernate 二级缓存：三次独立事务读取同一实体，后两次应命中 Redisson 缓存。
 *
 * <p>访问 /api/hibernate 可对比 secondLevelCacheHitCount 的变化。</p>
 */
@RestController
@RequestMapping("/api/hibernate")
public class HibernateDemoController {

	private final DemoItemService demoItemService;

	public HibernateDemoController(DemoItemService demoItemService) {
		this.demoItemService = demoItemService;
	}

	@GetMapping
	public Map<String, Object> demo(@RequestParam(name = "id", defaultValue = "1") long id) {
		Statistics statistics = this.demoItemService.getStatistics();

		// 第一次事务：不存在时插入；存在时可能直接命中缓存
		DemoItem loaded = this.demoItemService.loadOrCreate(id, "cache-demo");
		Long actualId = loaded.getId();
		long hitAfterLoad = statistics.getSecondLevelCacheHitCount();
		long missAfterLoad = statistics.getSecondLevelCacheMissCount();

		// 第二个事务：若上一步只做了 insert，本次会查询数据库并写入二级缓存
		DemoItem warm = this.demoItemService.load(actualId);
		long hitAfterWarm = statistics.getSecondLevelCacheHitCount();
		long putAfterWarm = statistics.getSecondLevelCachePutCount();

		// 第三个事务：命中 Redisson 二级缓存，不再发出 SELECT
		DemoItem cached = this.demoItemService.load(actualId);
		long hitAfterCached = statistics.getSecondLevelCacheHitCount();

		Map<String, Object> result = new LinkedHashMap<>();
		result.put("loaded", toMap(loaded));
		result.put("warmLoad", toMap(warm));
		result.put("cachedLoad", toMap(cached));
		result.put("hitAfterLoad", hitAfterLoad);
		result.put("hitAfterWarmLoad", hitAfterWarm);
		result.put("hitAfterCachedLoad", hitAfterCached);
		result.put("missAfterLoad", missAfterLoad);
		result.put("putAfterWarmLoad", putAfterWarm);
		result.put("entityLoadCount", statistics.getEntityLoadCount());
		result.put("prepareStatementCount", statistics.getPrepareStatementCount());
		return result;
	}

	private Map<String, Object> toMap(DemoItem item) {
		Map<String, Object> value = new LinkedHashMap<>();
		value.put("id", item.getId());
		value.put("name", item.getName());
		value.put("createdAt", item.getCreatedAt() == null ? null
				: item.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		return value;
	}

}
