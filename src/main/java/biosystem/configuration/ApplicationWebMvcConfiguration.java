package biosystem.configuration;

import biosystem.web.interceptors.FooterInterceptor;
import biosystem.web.interceptors.NavbarInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationWebMvcConfiguration implements WebMvcConfigurer {
    private final FooterInterceptor footerInterceptor;
    private final NavbarInterceptor navbarInterceptor;

    @Autowired
    public ApplicationWebMvcConfiguration(FooterInterceptor footerInterceptor, NavbarInterceptor navbarInterceptor) {
        this.footerInterceptor = footerInterceptor;
        this.navbarInterceptor = navbarInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.footerInterceptor);
        registry.addInterceptor(this.navbarInterceptor);
    }
}
