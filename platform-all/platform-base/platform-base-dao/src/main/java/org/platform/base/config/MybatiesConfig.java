package org.platform.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
/**
 * spring 加载dao处理
 * @author zhang kui
 */
@Configuration
@ImportResource(locations="classpath*:application-dao.xml")
public class MybatiesConfig {

}
