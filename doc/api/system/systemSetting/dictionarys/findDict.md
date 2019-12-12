# 查询指定字典信息

### 功能说明
查询指定字典详细信息

### URI
#### URI格式  
GET /system/v1/dicts/{dictId}

#### 参数说明  
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| dictId | String | 字典ID |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
PUT http://{Endpoint}/system/v1/dicts/{dictId}
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | Dict结构体 | 请参见 [Dict结构体说明](#dict结构体说明) |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

##### Dict结构体说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| dictId | String | 字典ID |
| dictName | String | 字典名称 |
| dictType | String | 字典类型 |
| buildIn | boolean | 是否系统内置 |
| status | String | 状态，正常 or 停用 |
| remark | String | 备注 |

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": "0103",
	"message": "success",
	"request_id": "4e9218fb4fcb4bd69c9fc34e057d0d6b"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)