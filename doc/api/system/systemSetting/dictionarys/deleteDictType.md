# 删除字典类型

### 功能说明
删除指定字典类型信息

### URI
#### URI格式  
DELETE /system/v1/dict/types/{typeCode}

#### 参数说明  
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| typeCode | String | 字典类型编码 |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
DELETE http://{Endpoint}/system/v1/dicts/data_state
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | String | 删除的字典类型ID |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": "402809816ef41f0a016ef421208a0000",
	"message": "success",
	"request_id": "012e645d06184381bd46f88a23374da9"
}
```
##### 异常响应
```json
{
	"code": 500,
	"data": null,
	"message": "this object is build-in, it's not allow to delete",
	"request_id": "ad71c4da5b4248308bafd54b1bdbed60"
}
```
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)