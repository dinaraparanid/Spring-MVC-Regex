package com.paranid5.mvc_regex_spring.di

import com.paranid5.mvc_regex_spring.SubstringView
import com.paranid5.mvc_regex_spring.model.SubstringRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.paranid5.mvc_regex_spring.di")
class Config {
    @Bean
    fun substringRepository() = SubstringRepository()

    @Bean
    fun substringView() = SubstringView()
}