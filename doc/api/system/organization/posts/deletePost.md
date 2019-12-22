# 删除岗位

### 功能说明
更新对应机构下岗位信息

### URI
#### URI格式  
DELETE /system/v1/orgs/{orgCode}/posts/{postCode}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| orgCode | 是 | String | 机构编码 |
| postCode | 是 | String | 岗位编码 |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
DELETE http://{Endpoint}/system/v1/orgs/0101/posts/dev
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | String | 删除的岗位ID |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": "402881846f2e4799016f2e48048d0000",
	"message": "success",
	"request_id": "0ac2543aa10b432fa8926d77f7853aa4"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)