# 更新指定字典标签

### 功能说明
更新指定系统字典的标签数据信息

### URI
#### URI格式  
PUT /system/v1/dicts/{dictId}/data/{dictDataId}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| dictId | 是 | String | 字典ID |
| dictDataId | 是 | String | 字典数据ID |

### 请求消息
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| dictLabel | 是 | String | 字典标签 |
| dictValue | 是 | String | 字典键值 |

#### 请求样例  
```
PUT http://{Endpoint}/system/v1/dicts/402809816ef90f69016ef9118af30000/data/402809816ef90f69016ef914400a0001
```
##### 请求body样例
```json
{
	"dictLabel": "正常-test",
	"dictValue": "0"
}
```
### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | String | 更新的字典下属具体数据ID |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": "402809816ef90f69016ef914400a0001",
	"message": "success",
	"request_id": "d55630c428b14186a1ebb3fe00fa6f1f"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)