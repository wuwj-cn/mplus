# 查询指定模块信息

### 功能说明
查询指定模块详细信息

### URI
#### URI格式  
GET /system/v1/modules/{moduleCode}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| moduleCode | 是 | String | 模块编码 |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
GET http://{Endpoint}/system/v1/modules/01
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | Module结构体 | 请参见 [Module结构体说明](#module结构体说明) |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

##### Module结构体说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| moduleCode | String | 模块编码 |
| moduleName | String | 模块名称 |
| moduleDesc | String | 模块描述 |
| status | String | 状态 |
| version | String | 版本号 |
| createTime | String | 创建时间 |
| modifyTime | String | 修改时间 |

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": {
		"createTime": "2019-12-31 22:16:56",
		"modifyTime": "2019-12-31 22:50:55",
		"moduleCode": "01",
		"moduleDesc": "module desc",
		"moduleName": "01",
		"status": "",
		"version": ""
	},
	"message": "success",
	"request_id": "2fa6a4ae02cf4c58bc7cce091de160aa"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)