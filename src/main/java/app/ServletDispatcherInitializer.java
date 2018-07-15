package app;

import app.identity.IdentityConfiguration;
import app.repository.configuration.DataConfiguration;
import app.security.SecurityConfiguration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletDispatcherInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Nullable
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{DataConfiguration.class, SecurityConfiguration.class, IdentityConfiguration.class };
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
}
