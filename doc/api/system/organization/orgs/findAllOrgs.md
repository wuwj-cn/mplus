# 查询所有机构列表

### 功能说明
查询指定机构下属的组织机构列表信息

### URI
#### URI格式  
GET /system/v1/orgs/{orgId}/tree

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| orgId | 是 | String | 机构ID |

### 请求消息
#### 参数说明  
无

#### 请求样例 
```
GET http://{Endpoint}/system/v1/orgs/01/tree
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | 树状Org结构体数组 | 请参见 [Org结构体说明](#org结构体说明) |

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
| children | Org结构体 | 下级机构列表 |

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": {
		"children": [
			{
				"children": [],
				"fullName": "0101",
				"orgId": "0101",
				"orgName": "0101-t",
				"parentOrgId": "01",
				"parentOrgName": "test",
				"remark": ""
			},
			{
				"children": [],
				"fullName": "0101",
				"orgId": "0103",
				"orgName": "0103",
				"parentOrgId": "01",
				"parentOrgName": "test",
				"remark": ""
			}
		],
		"fullName": "test",
		"orgId": "01",
		"orgName": "test",
		"parentOrgId": "0",
		"parentOrgName": "ROOT",
		"remark": ""
	},
	"message": "success",
	"request_id": "72dffff6dffd4f278b1f7aef03ddac17"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)