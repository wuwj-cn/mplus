# 查询指定机构下属用户

### 功能说明
根据组织机构查找其下属所有用户列表信息

### URI
#### URI格式  
GET /system/v1/orgs/{orgId}/users

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| orgId | 是 | String | 机构ID |

### 请求消息
#### 参数说明  
无

#### 请求样例 
```
GET http://{Endpoint}/system/v1/orgs/1/users
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | User结构体数组 | 请参见 [User结构体说明](#user结构体说明) |

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
	"data": [
		{
			"email": "wuwj@123.com",
			"mobile": "",
			"nickName": "123",
			"orgId": "0",
			"orgName": "ROOT",
			"userAccount": "",
			"userId": "00001",
			"userName": "123",
			"userStatus": "0"
		},
		{
			"email": "wuwj@123.com",
			"mobile": "",
			"nickName": "123",
			"orgId": "0",
			"orgName": "ROOT",
			"userAccount": "",
			"userId": "00002",
			"userName": "1234",
			"userStatus": "0"
		}
	],
	"message": "success",
	"request_id": "b54699d869664517a93667f0148a1e72"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)