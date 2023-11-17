package hdang09.config;

import hdang09.filter.AuthorizationFilter;
import hdang09.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig {

    @Autowired
    private JwtUtil jwtUtil;

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authenticationTokenFilter() {
        FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthorizationFilter(jwtUtil));
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }

}
