##模拟使用spring security oauth2来实现server之间的安全认证

启动服务并访问 http://localhost:11110/client/test/{resource_uid}
eg: http://localhost:11110/client/test/System Management/Role Management

`本案例使用了egp_sit中的user_tbl,role_tbl,user_role_assign,privilege_tbl,role_privilege_assign,privilege_resource_link
这几张表来模拟用户对访问资源的权限管理，
resource_uid是privilege_resource_link 表的一个字段，
假设resource_uid字段值代表要访问的资源路径`

###模拟serviceA 向 serviceB 请求 resource_uid的资源
####流程：
#####1.serviceA 向认证服务器请求token
  `通过配置在serviceA中的client_id和secret来生成Authorization请求头`
  `并携带grant type参数，如果是grant type是password模式，则还需要username和password参数`
#####2.认证服务器验证serviceA的client_id，secret是否正确，以及client_id是否支持该grant type等来确认是否返回token
  `如果grant type是password模式,则根据请求中的某个具体用户的username去查询到user detail`
  `判断请求的password和查询到的password是否一致，不一致则拒绝返回token`
#####3.serviceA 带着获取到的token去向serviceB请求资源
#####4.serviceB 向认证服务器请求验证serviceA带来的token是否有效
  `serivceB会从认证服务器获取该token对应的token info，token info中包含有具体用户的role，serviceA的scope等信息，用来做权限验证`
#####5.serviceB 判断用户或serviceA 是否拥有访问该资源的权限来决定如何响应serviceA的请求