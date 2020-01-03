# 更新菜单

### 功能说明
更新模块下指定的菜单信息

### URI
#### URI格式  
PUT /system/v1/menus/{menuCode}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| menuCode | 是 | String | 菜单编码 |

### 请求消息
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| moduleCode | 是 | String | 模块编码 |
| menuName | 否 | String | 菜单名称 |
| url | 否 | String | 菜单URL |
| parentMenuCode | 否 | String | 上级菜单编码 |

#### 请求样例  
```
PUT http://{Endpoint}/system/v1/menus/010101
```
##### 请求body样例
```json
{
	"moduleCode": "01",
	"menuName": "010101",
	"url": "/menus/b",
	"parentMenuCode": "0101"
}
```
### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | String | 更新的菜单ID |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": "4028b8816f608cb7016f608edb770001",
	"message": "success",
	"request_id": "e130dfc4d5734f71baf71da12b9d1c75"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)