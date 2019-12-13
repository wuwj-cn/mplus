# 查询指定字典类型信息

### 功能说明
查询指定字典类型详细信息

### URI
#### URI格式  
GET /system/v1/dict/types/{typeCode}

#### 参数说明  
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| typeCode | String | 字典类型编码 |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
PUT http://{Endpoint}/system/v1/dict/types/data_state
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | Dict结构体 | 请参见 [Dict结构体说明](#dict结构体说明) |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

##### Dict结构体说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| typeCode | String | 字典类型编码 |
| typeName | String | 字典类型名称 |
| buildIn | boolean | 是否系统内置 |
| status | String | 状态，正常 or 停用 |
| remark | String | 备注 |

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": {
		"buildIn": true,
		"createTime": "2019-12-13 16:11:43",
		"dataState": "0",
		"modifyTime": "2019-12-13 16:11:43",
		"remark": "",
		"status": "0",
		"typeCode": "data_state",
		"typeName": "数据状态"
	},
	"message": "success",
	"request_id": "c3fa60b9ea034ddb8ce8c0ebc0fda8ec"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)