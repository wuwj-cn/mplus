# 查询字典列表

### 功能说明
根据条件查询字典列表信息，以分页形式返回数据

### URI
#### URI格式 
GET /system/v1/dicts/page?pageNumber={pageNumber}&pageSize={pageSize}&dictName={dictName}&
dictType={dictType}&buildIn={buildIn}&dataState={dataState}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| pageNumber | 是 | int | 页号 |
| pageSize | 是 | String | 每页条数 |
| dictName | 否 | String | 字典名称 |
| dictType | 否 | String | 字典类型 |
| buildIn | 否 | boolean | 是否系统内置 |
| dataState | 否 | String | 数据状态 |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
http://localhost:9002/system/v1/dicts?pageNumber=1&pageSize=10
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
| dictId | String | 字典ID |
| dictName | String | 字典名称 |
| dictType | String | 字典类型 |
| buildIn | boolean | 是否系统内置 |
| remark | String | 备注 |

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": {
		"content": [
			{
				"buildIn": true,
				"dictDataList": [],
				"dictId": "402809816ef44827016ef44f7d1c0000",
				"dictName": "数据状态",
				"dictType": "common_data_state",
				"remark": ""
			}
		],
		"pageNumber": 1,
		"pageSize": 10,
		"totalElements": 1,
		"totalPages": 1
	},
	"message": "success",
	"request_id": "7361eff3bf694743a0e8da748bb15a28"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)