# 查询指定用户信息

### 功能说明
根据用户名称查找对应用户详细信息

### URI
#### URI格式  
GET /system/v1/users/{userId}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| userId | 是 | String | 用户ID |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
GET http://{Endpoint}/system/v1/users/00001
```
##### 请求body样例
无

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
	"code": 9000,
	"data": {
		"email": "11@qq.com",
		"mobile": "",
		"nickName": "test",
		"orgId": "0",
		"orgName": "ROOT",
		"userAccount": "",
		"userId": "00001",
		"userName": "wuwj",
		"userStatus": "0"
	},
	"message": "success",
	"request_id": "f4e1888523c5461f93f207e04e5be215"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)