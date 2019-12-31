# 更新模块

### 功能说明
更新指定模块信息

### URI
#### URI格式  
PUT /system/v1/modules/{moduleCode}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| moduleCode | 是 | String | 模块编码 |

### 请求消息
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| moduleName | 是 | String | 模块名称 |
| moduleDesc | 否 | String | 模块描述 |

#### 请求样例  
```
PUT http://{Endpoint}/system/v1/modules/01
```
##### 请求body样例
```json
{
	"moduleName": "01",
	"moduleDesc": "module desc"
}
```
### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | String | 更新的模块ID |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": "4028b8816f5c4fac016f5c5116c00000",
	"message": "success",
	"request_id": "ef011023180a4f0abdfe06ca0b43f6b2"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)