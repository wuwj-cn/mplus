# 更新用户

### 功能说明
更新指定用户的信息

### URI
#### URI格式  
PUT /system/v1/users/{userId}  
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| userId | 是 | String | 用户ID |

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
PUT http://{Endpoint}/system/v1/users/00001
```
##### 请求body样例
```json
{
	"nickName": "test",
	"email": "11@qq.com",
	"orgId": "0"
}
```

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | String | 更新的用户ID |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": "00001",
	"message": "success",
	"request_id": "95e2074b15f34bab90133c68e1d28c46"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)