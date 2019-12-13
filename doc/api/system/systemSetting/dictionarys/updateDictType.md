# 更新字典类型

### 功能说明
更新指定字典类型信息，仅非系统内置字典类型可做修改更新

### URI
#### URI格式  
PUT /system/v1/dict/types/{typeCode}

#### 参数说明  
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| typeCode | String | 字典类型编码 |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
PUT http://{Endpoint}/system/v1/dicts/data_state
```
##### 请求body样例
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| typeName | 否 | String | 字典类型名称 |
| remark | 否 | String | 备注 |

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | String | 更新的字典类型ID |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": "402809816ef41f0a016ef421208a0000",
	"message": "success",
	"request_id": "8d45250b3ba24e6dbdfd128f864d97c4"
}
```
##### 异常响应
```json
{
	"code": 500,
	"data": null,
	"message": "this object is build-in, it's not allow to update",
	"request_id": "f8b0875e44be4d2a9a915f728a77ea0b"
}
```
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)