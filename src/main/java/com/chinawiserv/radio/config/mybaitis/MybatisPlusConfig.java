package com.chinawiserv.radio.config.mybaitis;

import java.io.IOException;

import javax.sql.DataSource;
import org.apache.ibatis.io.DefaultVFS;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisMapperRefresh;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;

//@Configuration
public class MybatisPlusConfig {

	@Autowired
	private DataSource dataSource;

	@Autowired
	ApplicationContext context;

	/* 开发时可以打开这个配置用于实时刷新mapper*,打包的时候关闭减少性能消耗 */
	@Bean
	public MybatisMapperRefresh getMybatisMapperRefresh() throws Exception {
		MybatisSqlSessionFactoryBean sqlSessionFactoryBean = this.context.getBean(MybatisSqlSessionFactoryBean.class);
		MybatisMapperRefresh refresh = new MybatisMapperRefresh(this.context.getResources("classpath:mapper/*/*.xml"), sqlSessionFactoryBean.getObject(), true);
		return refresh;
	}

	/**
	 * myBaits 配置
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean
	public MybatisSqlSessionFactoryBean getSqlSessionFactoryBean() throws IOException {
		MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
		sqlSessionFactoryBean.setMapperLocations(this.context.getResources("classpath:mapper/*/*.xml"));
		sqlSessionFactoryBean.setDataSource(this.dataSource);
		sqlSessionFactoryBean.setVfs(DefaultVFS.class);
		return sqlSessionFactoryBean;
	}

	@Bean
	public MapperScannerConfigurer initMapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.chinawiserv.radio.business.mapper");
		// mapperScannerConfigurer.setAnnotationClass(Repository.class);
		return mapperScannerConfigurer;
	}

	/**
	 * mybatis-plus分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor page = new PaginationInterceptor();
		page.setDialectType("mysql");
		return page;
	}
}
