# 新增岗位

### 功能说明
新增保存对应机构下岗位信息

### URI
#### URI格式  
POST /system/v1/orgs/{orgCode}/posts

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| orgCode | 是 | String | 机构编码 |

### 请求消息
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| postCode | 是 | String | 岗位编码 |
| postName | 是 | String | 岗位名称 |

#### 请求样例  
```
POST http://{Endpoint}/system/v1/orgs/0101/posts
```
##### 请求body样例
```json
{
	"postCode": "dev",
	"postName": "研发"
}
```
### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | String | 新增的岗位ID |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": "402881846f2e4799016f2e48048d0000",
	"message": "success",
	"request_id": "708629f69fab46b49b459bfc393a36d9"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)