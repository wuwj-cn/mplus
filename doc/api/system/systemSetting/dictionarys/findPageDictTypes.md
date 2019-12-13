# 查询字典类型列表

### 功能说明
根据条件查询字典类型列表信息，以分页形式返回数据

### URI
#### URI格式 
GET /system/v1/dict/types/page?pageNumber={pageNumber}&pageSize={pageSize}&dictName={dictName}&
dictType={dictType}&buildIn={buildIn}&status={status}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| pageNumber | 是 | int | 页号 |
| pageSize | 是 | String | 每页条数 |
| dictName | 否 | String | 字典名称 |
| dictType | 否 | String | 字典类型 |
| buildIn | 否 | boolean | 是否系统内置 |
| status | 否 | String | 状态 |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
GET http://{Endpoint}/system/v1/dict/types/page?pageNumber=1&pageSize=10
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | Page&#60;Dict&#62;结构体 | 请参见<br/>[Page结构体说明](../../../common/response/page.md)<br/>[Dict结构体说明](#dict结构体说明) |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

##### Dict结构体说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| typeCode | String | 字典类型编码 |
| typeName | String | 字典类型名称 |
| buildIn | boolean | 是否系统内置 |
| status | String | 状态，正常 or 停用 |
| remark | String | 备注 |
| createTime | String | 创建时间 |
| modifyTime | String | 更新时间 |

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": {
		"content": [
			{
				"buildIn": true,
				"createTime": "2019-12-13 16:11:43",
				"dataState": "0",
				"modifyTime": "2019-12-13 16:11:43",
				"remark": "",
				"status": "0",
				"typeCode": "data_state",
				"typeName": "数据状态"
			}
		],
		"pageNumber": 1,
		"pageSize": 10,
		"totalElements": 1,
		"totalPages": 1
	},
	"message": "success",
	"request_id": "c96ea193ec2140d3b2cdd59b2b6aba11"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)