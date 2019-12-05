# 响应结果说明

### 功能说明
对发送请求处理后的响应结果进行统一定义与说明

### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| request_id | String | 请求ID |
| code | String | 请求状态码 |
| message | String | 请求响应错误信息，允许为空 |
| data | Object | 请求响应结果，当服务端返回对象结构体时会转换为json格式字符串 |

### 正常响应样例
```json
{
    "request_id": "aad0860d089c482b943971f802a6718e",
    "code": "MP:0000",
    "data": {"id": "xxx"}
}
```

### 异常响应样例
```json
{
    "request_id": "aad0860d089c482b943971f802a6718e",
    "code": "MP:9000",
    "message": "invalid token"
}
```