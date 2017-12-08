package com.ecquaria.oauth2.config;

import com.ecquaria.oauth2.Entity.PrivilegeEntity;
import com.ecquaria.oauth2.feign.PrivilegeValidFeign;
import com.ecquaria.oauth2.feign.TokenFeign;
import com.ecquaria.oauth2.service.PrivilegeService;
import com.ecquaria.oauth2.service.ResourceLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.expression.OAuth2SecurityExpressionMethods;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;

@Component("mySecurity")
public class MySecurity {

    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private ResourceLinkService resourceLinkService;

    //这里应该注入用户和该用户所拥有的权限（权限在登录成功的时候已经缓存起来，
    // 当需要访问该用户的权限是，直接从缓存取出！），然后验证该请求是否有权限，有就返回true，
    // 否则则返回false不允许访问该Url。
    //而且这里还传入了request,我也可以使用request获取该次请求的类型。
    //根据restful风格我们可以使用它来控制我们的权限，例如当这个请求是post请求，
    // 证明该请求是向服务器发送一个新建资源请求，我们可以使用request.getMethod()来获取该请求的方式，
    // 然后在配合角色所允许的权限路径进行判断和授权操作！
    public boolean check(OAuth2SecurityExpressionMethods oauth2, Authentication authentication, HttpServletRequest request){

        System.out.println("--------------------------------------------------------");
        //System.out.println(oauth2.hasScope("scope0"));
        //System.out.println(oauth2.hasScope("scope1"));
        //只在token是以password模式获得的时候，client才会有authority
        //以client_credentials模式获取token时，oauth2对象中client没有authority
        //System.out.println(oauth2.clientHasRole("ROLE_B"));

        String username=authentication.getName();
        System.out.println("username: "+username);

        String resourceUrl=request.getRequestURI().replaceFirst("/resource/","");
        resourceUrl= URLDecoder.decode(URLDecoder.decode(resourceUrl));
        System.out.println("url: "+resourceUrl);

        return canAccess(username,resourceUrl,authentication);
    }

    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.client-secret}")
    private String secret;
    @Autowired
    private TokenFeign tokenFeign;
    @Autowired
    private PrivilegeValidFeign privilegeValidFeign;

    private boolean canAccess(String username,String resourceUrl,Authentication authentication){

        /*boolean tag=false;
        PrivilegeEntity privilege=resourceLinkService.findPrivilegeByResourceUid(resourceUrl);
        if(privilege==null){
            System.out.println("do not have specified privilege.");
            return tag;
        }
        System.out.println("required privilege: "+privilege.getId());

        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator=authorities.iterator();
        while(iterator.hasNext()){
            String role=iterator.next().getAuthority().replaceFirst("ROLE_","");

            System.out.println("role: "+role);
            System.out.println("assigned privilege:");

            List<PrivilegeEntity> privilegeList=privilegeService.findPrivilegesByRoleId(role);
            if(!CollectionUtils.isEmpty(privilegeList)){
                for(PrivilegeEntity privilegeEntity:privilegeList){
                    System.out.println("    privilege: "+privilegeEntity.getId());
                    if(privilegeEntity.getId().equals(privilege.getId())){
                        tag=true;
                        break;
                    }
                }
            }
            if(tag){
                break;
            }
        }

        if(!tag){
            System.out.println("do not have specified privilege.");
        }
        return tag;*/

        String token=getTokenFromAuthorizationServer();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Authorization","Bearer "+token);
        ResponseEntity<Boolean> result=privilegeValidFeign.hasPrivilege(httpHeaders);
        return result.getBody();
    }

    private String getTokenFromAuthorizationServer(){
        Map<String,String> parameters=new HashMap<>();
        parameters.put("grant_type","client_credentials");

        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Authorization","Basic "+Base64.getEncoder().encodeToString((clientId+":"+secret).getBytes()));

        ResponseEntity<OAuth2AccessToken> responseEntity=tokenFeign.getAccessToken(httpHeaders,parameters);
        OAuth2AccessToken oAuth2AccessToken=responseEntity.getBody();
        return oAuth2AccessToken.getValue();
    }

}
