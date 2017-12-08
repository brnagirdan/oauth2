package com.ecquaria.oauth2.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PrivilegeFilter extends ZuulFilter{

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        boolean hasPermission=validate(ctx.getRequest());
        if(!hasPermission){
            //过滤该请求，不往下级服务去转发请求，到此结束
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("YOU DO NOT HAVE PRIVILEGE!");
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
        }
        return null;
    }

    private boolean validate(HttpServletRequest request){

        String username=request.getParameter("username");
        String password=request.getParameter("password");
        //System.out.println(request.getHeader("Authorization"));
        System.out.println(request.getRequestURI());
        return true;
    }

}
