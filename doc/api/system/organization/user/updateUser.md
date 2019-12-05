# 更新用户

### 功能说明
更新指定用户的信息

### URI
#### URI格式  
PUT /system/v1/user/{user_id}  
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| user_id | 是 | String | 用户ID |

### 请求消息
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| orgId | 否 | String | 归属机构ID |
| nickName | 否 | String | 用户昵称 |
| email | 否 | String | 用户邮箱 |
| userStatus| 否 | String | 用户状态 |

#### 请求样例  
```
PUT http://{Endpoint}/system/v1/user/1
```
##### 请求body
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
请参考 [响应结果说明](result.md#要素说明)  

##### data数据结构体说明  
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| id | String | 用户ID |
| userName | String | 用户名称 |
| niciName | String | 用户昵称 |
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
请参考 [响应结果说明](result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](status.md)

### 错误码
请参考 [错误码说明](errorcode.md)