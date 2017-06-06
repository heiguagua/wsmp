package com.chinawiserv.radio.config.mybaitis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.spring.MybatisMapperRefresh;

@Configuration
@MapperScan("com.chinawiserv.radio.business.mapper")
public class MybatisPlusConfig {

	@Autowired
	ApplicationContext context;

	/* 开发时可以打开这个配置用于实时刷新mapper*,打包的时候关闭减少性能消耗 */
	@Bean
	public MybatisMapperRefresh getMybatisMapperRefresh() throws Exception {
		SqlSessionFactory sqlSessionFactory = this.context.getBean(SqlSessionFactory.class);
		MybatisMapperRefresh refresh = new MybatisMapperRefresh(sqlSessionFactory,1,1,true);
		return refresh;
	}
}