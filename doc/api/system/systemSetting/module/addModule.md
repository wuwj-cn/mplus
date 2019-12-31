# 新增模块

### 功能说明
新增保存指定模块信息

### URI
#### URI格式  
POST /system/v1/modules

#### 参数说明  
无

### 请求消息
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| moduleCode | 是 | String | 模块编码 |
| moduleName | 是 | String | 模块名称 |
| moduleDesc | 否 | String | 模块描述 |

#### 请求样例  
```
POST http://{Endpoint}/system/v1/modules
```
##### 请求body样例
```json
{
	"moduleCode": "01",
	"moduleName": "01"
}
```
### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | String | 新增的模块ID |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": "4028b8816f5c4fac016f5c5116c00000",
	"message": "success",
	"request_id": "bb3f43da5f9a41919667db8c1f07c6e3"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)