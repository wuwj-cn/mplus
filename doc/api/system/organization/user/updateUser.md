# 更新用户

### 功能说明
更新指定用户的信息

### URI
#### URI格式  
PUT /system/v1/users/{userName}  
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| userName | 是 | String | 用户名 |

### 请求消息
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| orgId | 是 | String | 归属机构ID |
| nickName | 否 | String | 用户昵称 |
| userAccount | 否 | String | 用户账号 |
| email | 否 | String | 邮箱 |
| mobile | 否 | String | 手机号 |
| userStatus| 否 | String | 用户状态 |

#### 请求样例  
```
PUT http://{Endpoint}/system/v1/users/1
```
##### 请求body样例
```json
{
  "orgId": "01",
  "nickName": "test",
  "email": "test@qq.com",
  "userStatus": "0"
}
```

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | User结构体 | 请参见 [User结构体说明](#user结构体说明) |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

##### User结构体说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| userId | String | 用户ID |
| userName | String | 用户名 |
| nickName | String | 用户昵称 |
| userAccount | String | 用户账号 |
| email | String | 邮箱 |
| mobile | String | 手机号 |
| orgId | String | 归属机构ID |
| orgName | String | 归属机构名称 |
| userStatus | String | 用户状态 |

#### 响应样例
##### 正常响应
```json
{
  "request_id": "0f1ab304fcc945819abab3dcec89f194",
  "code": "MP:0000",
  "data": {
  }
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)