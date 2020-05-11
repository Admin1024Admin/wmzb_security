package com.l024.wmzbsecuritycore.properties;

/**
 *
 */

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 让SecurityProperties生效
 * 使用 @ConfigurationProperties 注解的类生效。
 */
@Configuration
@EnableConfigurationProperties(value = {SecurityProperties.class})
public class SecurityCoreConfig {
}
