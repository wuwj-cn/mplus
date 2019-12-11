# 新增字典

### 功能说明
新增保存系统字典信息

### URI
#### URI格式  
POST /system/v1/dicts

#### 参数说明  
无

### 请求消息
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| dictName | 是 | String | 字典名称 |
| dictType | 是 | String | 字典类型 |
| buildIn | 是 | String | 是否系统内置 |
| remark | 否 | String | 备注 |
#### 请求样例  
```
POST http://{Endpoint}/system/v1/dicts
```
##### 请求body样例
```json
{
	"dictName": "数据状态",
	"dictType": "common_data_state",
	"buildIn": true
}
```
### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | String | 新增的字典ID |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": "402809816ef41f0a016ef421208a0000",
	"message": "success",
	"request_id": "d84d6a78757746d891a4c4925477e996"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)