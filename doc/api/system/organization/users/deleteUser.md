# 删除用户

### 功能说明
删除指定的用户信息

### URI
#### URI格式  
DELETE /system/v1/users/{userName}  
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| userName | 是 | String | 用户名 |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
DELETE http://{Endpoint}/system/v1/users/wuwj
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | null | 无响应数据内容 |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": null,
	"message": "success",
	"request_id": "06c2fbb044c6486688425b09aea27612"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)