# 更新指定字典数据

### 功能说明
更新指定字典类型的字典数据信息，仅非系统内置字典数据可做修改更新

### URI
#### URI格式  
PUT /system/v1/dict/types/{typeCode}/items/{itemCode}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| typeCode | 是 | String | 字典类型编码 |
| itemCode | 是 | String | 字典数据编码 |

### 请求消息
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| itemLabel | 是 | String | 字典标签 |
| itemValue | 是 | String | 字典键值 |

#### 请求样例  
```
PUT http://{Endpoint}/system/v1/dict/types/data_state/items/NORMAL
```
##### 请求body样例
```json
{
	"itemLabel": "正常",
	"itemValue": "0"
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
```json
{
	"code": 500,
	"data": null,
	"message": "this object is build-in, it's not allow to update",
	"request_id": "3c817a48a46a4271a81ffb3931e1b0bd"
}
```
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)