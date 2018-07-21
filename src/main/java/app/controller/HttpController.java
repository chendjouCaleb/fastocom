package app.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

public abstract class HttpController {
    protected String uriForActionName(String name, Object... values){
        MvcUriComponentsBuilder.MethodArgumentBuilder uri = MvcUriComponentsBuilder.fromMappingName(name);

        for (int i = 0; i < values.length; i++){
            uri.arg(i, values[i]);
        }
        return uri.build();
    }

    protected String uriFromActionName(String actionName, Object... args){
        String url = MvcUriComponentsBuilder.fromMethodName(this.getClass(), actionName, args).toUriString();
        return url;
    }

    protected ModelAndView ModelAndView(String view){
        return new ModelAndView(view);
    }
}
