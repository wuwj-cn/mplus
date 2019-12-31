# 查询模块列表

### 功能说明
根据查询条件查找对应的模块列表信息，以分页结果方式返回

### URI
#### URI格式  
GET /system/v1/modules/page?pageNumber={pageNumber}&pageSize={pageSize}&moduleCode={moduleCode}&moduleName={moduleName}
&status={status}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| pageNumber | 是 | int | 页号，**注意** 页号从1开始 |
| pageSize | 是 | int | 每页大小 |
| moduleCode | 否 | String | 模块编码 |
| moduleName | 否 | String | 模块名称 |
| status | 否 | String | 状态 |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
GET http://{Endpoint}/system/v1/modules/page?pageNumber=1&pageSize=10&moduleCode=01
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | Page&#60;Module&#62;结构体 | 请参见 [Page结构体说明](#page结构体说明) |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

##### Page结构体说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| totalElements | int | 查询结果总数量 |
| totalPages | int | 总页数 |
| pageNumber | int | 当前页号 |
| pageSize | int | 每页大小 |
| content | Module结构体数组 | 请参见 [Module结构体说明](#module结构体说明) |

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
		"content": [
			{
				"createTime": "2019-12-31 22:16:56",
				"modifyTime": "2019-12-31 22:50:55",
				"moduleCode": "01",
				"moduleDesc": "module desc",
				"moduleName": "01",
				"status": "",
				"version": ""
			}
		],
		"pageNumber": 1,
		"pageSize": 10,
		"totalElements": 1,
		"totalPages": 1
	},
	"message": "success",
	"request_id": "63ada81d561a49079ff64f85744c766f"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)