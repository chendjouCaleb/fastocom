package app;

import app.identity.IdentityConfiguration;
import app.repository.configuration.DataConfiguration;
import app.security.SecurityConfiguration;
import app.service.ServiceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

public class ServletDispatcherInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Nullable
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{DataConfiguration.class, SecurityConfiguration.class, IdentityConfiguration.class, ServiceConfiguration.class };
    }

    @Nullable
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MvcConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(getMultipartConfigElement());
    }
    @Bean
    private MultipartConfigElement getMultipartConfigElement() {
        return new MultipartConfigElement( null, 5000000, 5000000, 0);
    }
}
