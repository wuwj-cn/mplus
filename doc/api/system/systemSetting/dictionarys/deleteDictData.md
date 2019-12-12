# 删除指定字典标签

### 功能说明
删除指定系统字典的标签数据信息

### URI
#### URI格式  
DELETE /system/v1/dicts/{dictId}/data/{dictDataId}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| dictId | 是 | String | 字典ID |
| dictDataId | 是 | String | 字典标签ID |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
DELETE http://{Endpoint}/system/v1/dicts/402809816ef90f69016ef9118af30000/data/402809816ef90f69016ef914400a0001
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | String | 更新的字典下属标签ID |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": "402809816ef90f69016ef914400a0001",
	"message": "success",
	"request_id": "b84d811bc42f43ca9aafed533b3400e9"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)