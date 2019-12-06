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
| pageNumber | 是 | int | 页号 |
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