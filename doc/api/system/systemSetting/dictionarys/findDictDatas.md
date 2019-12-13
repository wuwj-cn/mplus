# 查询指定字典标签列表

### 功能说明
查询指定字典下属的标签数据信息

### URI
#### URI格式  
GET /system/v1/dicts/{dictType}/data

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| dictType | 是 | String | 字典类型 |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
GET http://{Endpoint}/system/v1/dicts/common_date_state/data
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | DictData结构体数组 | 请参见 [DictData结构体说明](#dictdata结构体说明) |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

##### DictData结构体说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| dictDataId | String | 标签ID |
| dictLabel | String | 字典标签 |
| dictValue | String | 字典键值 |
| buildIn | boolean | 是否系统内置 |
| status | String | 状态，正常 or 停用 |
| remark | String | 备注 |
| dataState | String | 数据状态，正常 or 删除 |
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
			"createTime": "2019-12-13 11:30:40",
			"dataState": "0",
			"dictDataId": "402809816efd4cb0016efd4ef3980002",
			"dictLabel": "正常",
			"dictValue": "0",
			"modifyTime": "2019-12-13 11:30:40",
			"remark": "",
			"status": "0"
		}
	],
	"message": "success",
	"request_id": "fc4bce7e81ad4f9ebfe091986eae897e"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)