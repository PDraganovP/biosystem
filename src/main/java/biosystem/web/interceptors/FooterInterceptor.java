package biosystem.web.interceptors;

import biosystem.web.annotations.PageFooter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FooterInterceptor extends HandlerInterceptorAdapter {
    private static final String FOOTER_TEXT = "ПДП";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        } else {
            if (handler instanceof HandlerMethod) {
                PageFooter methodAnnotation = ((HandlerMethod) handler).getMethodAnnotation(PageFooter.class);

                if (methodAnnotation != null) {
                    modelAndView
                            .addObject("footerText", FOOTER_TEXT + " " + methodAnnotation.value());  /*+ " - " + methodAnnotation.value())*/
                }
            }
        }
    }
}
