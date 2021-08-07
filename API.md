# 接口需求

| 接口名称 | 接口地址               | 返回        | 功能                                                         | 备注                            | 进度   |
| -------- | ---------------------- | ----------- | ------------------------------------------------------------ | ------------------------------- | ------ |
| 注册     | /user/register         | msg, result | 接收表单并进行合法性检查，注册成功后需要将token存储在cookie中，其他状况需要返回错误信息 | username不可重复                | 已完成 |
| 登录     | /user/login            | msg, result | 接收表单并进行合法性检查，登录成功后需要将token存储在cookie中，其他状况需要返回错误信息 | 可同时允许uid和username参与校验 | 已完成 |
| 修改密码 | /user/update/password  | msg, result | 接收表单并进行合法性检查，修改成功后需要分配新的token，其他状况需要返回错误信息 | HTTP POST                       | 已分派 |
| 修改信息 | /user/update/info      | msg, result | 接收表单并进行合法性检查，其他状况需要返回错误信息           | HTTP POST                       | 已分派 |
| 读取信息 | /user/load/info        |             | 根据token查询并返回部分用户信息，其他状况需要返回错误信息    | HTTP GET                        | 已分派 |
| 读取头像 | /user/load/avatar      |             |                                                              |                                 |        |
| 登出     | /user/security/logout  |             | 清除当前token，并设置login_time使token失效                   | HTTP GET                        | 已分派 |
| 刷新凭证 | /user/security/refresh |             | 分配新的token以延长凭证时间                                  | HTTP GET                        | 已分派 |
| 获取公钥 | /user/security/key     |             | 派发公钥                                                     | HTTP GET                        | 已分派 |
|          |                        |             |                                                              |                                 |        |
|          |                        |             |                                                              |                                 |        |
|          |                        |             |                                                              |                                 |        |
|          |                        |             |                                                              |                                 |        |
|          |                        |             |                                                              |                                 |        |
|          |                        |             |                                                              |                                 |        |
|          |                        |             |                                                              |                                 |        |
|          |                        |             |                                                              |                                 |        |

