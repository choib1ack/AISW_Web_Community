package com.aisw.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration // 설정 파일 명시
@EnableJpaAuditing // Jpa 감시 활성화
public class JpaConfig {

}
