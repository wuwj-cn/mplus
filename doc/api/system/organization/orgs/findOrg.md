# 查询指定机构信息

### 功能说明
根据机构ID查询指定组织机构的详细信息

### URI
#### URI格式  
GET /system/v1/orgs/{orgId}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| orgId | 是 | String | 机构ID |

### 请求消息
#### 参数说明  
无

#### 请求样例 
```
GET http://{Endpoint}/system/v1/orgs/01
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | Org结构体数组 | 请参见 [Org结构体说明](#org结构体说明) |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

##### Org结构体说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| orgId | String | 机构ID|
| orgName | String | 机构简称 |
| fullName | String | 机构全称 |
| parentOrgId | String | 上级机构ID |
| parentOrgName | String | 上级机构简称 |
| remark | String | 备注 |

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": {
		"fullName": "0101",
		"orgId": "0101",
		"orgName": "0101-t",
		"parentOrgId": "01",
		"parentOrgName": "test",
		"remark": ""
	},
	"message": "success",
	"request_id": "f0f5708e3ba34f399c9e0e6fa355ad33"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)