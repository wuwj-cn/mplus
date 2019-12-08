# 分页查询指定机构下属用户

### 功能说明
根据组织机构分页查找其下属所有用户列表信息

### URI
#### URI格式  
GET /system/v1/orgs/{orgId}/users/page?pageNumber={pageNumber}&pageSize={pageSize}&userName={userName}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| orgId | 是 | String | 机构ID |
| pageNumber | 是 | int | 页号，**注意** 页号从1开始 |
| pageSize | 是 | int | 每页大小 |
| userName | 否 | String | 用户名 |

### 请求消息
#### 请求样例 
```
GET http://{Endpoint}/system/v1/orgs/1/users/page?pageNumber=1&pageSize=20&userName=wuwj
```

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | Page<User>结构体 | 请参见 [Page结构体说明](#page结构体说明) |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

##### Page结构体说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| totalElements | int | 查询结果总数量 |
| totalPages | int | 总页数 |
| pageNumber | int | 当前页号 |
| pageSize | int | 每页大小 |
| users | User结构体数组 | 请参见 [User结构体说明](#user结构体说明) |

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
		"content": [
			{
				"email": "11@qq.com",
				"mobile": "",
				"nickName": "test",
				"orgId": "0",
				"orgName": "ROOT",
				"userAccount": "",
				"userId": "00020",
				"userName": "wuwj",
				"userStatus": "0"
			},
			{
				"email": "wuwj@123.com",
				"mobile": "",
				"nickName": "123",
				"orgId": "0",
				"orgName": "ROOT",
				"userAccount": "",
				"userId": "00004",
				"userName": "1232",
				"userStatus": "0"
			},
			{
				"email": "wuwj@123.com",
				"mobile": "",
				"nickName": "123",
				"orgId": "0",
				"orgName": "ROOT",
				"userAccount": "",
				"userId": "00003",
				"userName": "1231",
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
			},
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
			}
		],
		"pageNumber": 0,
		"pageSize": 10,
		"totalElements": 5,
		"totalPages": 1
	},
	"message": "success",
	"request_id": "55181474887b441982432395e0295e68"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)