# 查询指定岗位信息

### 功能说明
查询指定机构下的指定岗位详细信息

### URI
#### URI格式  
GET /system/v1/orgs/{orgCode}/posts/{postCode}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| orgCode | 是 | String | 机构编码 |
| postCode | 是 | String | 岗位编码 |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
GET http://{Endpoint}/system/v1/orgs/0101/posts/dev
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | Post结构体数组 | 请参见 [Post结构体说明](#post结构体说明) |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

##### Post结构体说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| postCode | String | 岗位编码 |
| postName | String | 岗位名称 |
| orgCode | String | 所属机构编码 |
| orgName | String | 所属机构名称 |
| createTime | String | 创建时间 |
| modifyTime | String | 更新时间 |

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": {
		"createTime": "2019-12-22 23:44:29",
		"modifyTime": "2019-12-23 00:07:01",
		"orgCode": "0101",
		"orgName": "0101",
		"postCode": "dev",
		"postName": "研发工程师"
	},
	"message": "success",
	"request_id": "7773c436dfe6411b814ca8175fdc5dd6"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)