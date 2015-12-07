package ru.ulmc.poll.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.ulmc.poll.conf.reference.ConfigBeanUI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Configurable
public class ConfigInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    ConfigBeanUI configBeanUI;

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        modelAndView.getModelMap().put("config", configBeanUI);
    }

}
