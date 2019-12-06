# 新增用户

### 功能说明
新增保存用户信息

### URI
#### URI格式  
POST /system/v1/users

#### 参数说明  
无

### 请求消息
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| userName | 是 | String | 用户名称 |
| nickName | 否 | String | 用户昵称 |
| orgId | 是 | String | 归属机构ID |
| email | 否 | String | 用户邮箱 |
#### 请求样例  
```
POST http://{Endpoint}/system/v1/users
```
##### 请求body样例
```json
{
  "userName": "wuwj",
  "nickName": "wuwj",
  "orgId": "001",
  "email": "wuwj@qq.com"
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
| id | String | 用户ID |
| userName | String | 用户名称 |
| nickName | String | 用户昵称 |
| email | String | 用户邮箱 |
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