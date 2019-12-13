# 查询指定字典数据列表

### 功能说明
查询指定字典类型下属的数据列表信息

### URI
#### URI格式  
GET /system/v1/dict/types/{typeCode}/items

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| typeCode | 是 | String | 字典类型编码 |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
GET http://{Endpoint}/system/v1/dict/types/data_state/items
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | DictItem结构体数组 | 请参见 [DictItem结构体说明](#dictitem结构体说明) |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

##### DictItem结构体说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| itemCode | String | 字典编码 |
| itemLabel | String | 字典标签 |
| itemValue | String | 字典键值 |
| buildIn | boolean | 是否系统内置 |
| status | String | 状态，正常 or 停用 |
| createTime | String | 创建时间 |
| modifyTime | String | 更新时间 |

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": [
		{
			"buildIn": true,
			"createTime": "2019-12-13 17:32:46",
			"dataState": "0",
			"itemCode": "NORMAL",
			"itemLabel": "正常",
			"itemValue": "0",
			"modifyTime": "2019-12-13 17:32:46",
			"status": "0"
		}
	],
	"message": "success",
	"request_id": "d56f3c5a65254a6f8efd1b6f3cf9b2a0"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)