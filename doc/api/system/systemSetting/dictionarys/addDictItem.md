# 新增指定字典数据

### 功能说明
新增保存字典的数据信息

### URI
#### URI格式  
POST /system/v1/dict/types/{typeCode}/items

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| typeCode | 是 | String | 字典类型编码 |

### 请求消息
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| itemCode | 是 | String | 字典编码 |
| itemLabel | 是 | String | 字典标签 |
| itemValue | 是 | String | 字典键值 |
| buildIn | 是 | String | 是否系统内置 |
#### 请求样例  
```
POST http://{Endpoint}/system/v1/dict/types/data_state/items
```
##### 请求body样例
```json
{
	"itemCode": "NORMAL",
	"itemLabel": "正常",
	"itemValue": "0",
	"buildIn": true
}
```
### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | String | 新增的字典数据ID |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": "402809816efe9a38016efe9a76780000",
	"message": "success",
	"request_id": "271f7686760b458c8369f8e1b1643a2b"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)