# 分页数据结构说明

### 功能说明
对请求分页数据的响应结果进行统一定义与说明

### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| totalElements | int | 查询结果总数量 |
| totalPages | int | 总页数 |
| pageNumber | int | 当前页号 |
| pageSize | int | 每页大小 |
| content | T结构体数组 | 参见具体的返回结构体说明 |

### 正常响应样例
```json
{
	"code": 9000,
	"data": {
		"content": [
			{
				"buildIn": true,
				"dictDataList": [],
				"dictId": "402809816ef44827016ef44f7d1c0000",
				"dictName": "数据状态",
				"dictType": "common_data_state",
				"remark": ""
			}
		],
		"pageNumber": 1,
		"pageSize": 10,
		"totalElements": 1,
		"totalPages": 1
	},
	"message": "success",
	"request_id": "7361eff3bf694743a0e8da748bb15a28"
}
```

### 异常响应样例
```json
{
    "request_id": "aad0860d089c482b943971f802a6718e",
    "code": "9400",
    "message": "invalid token"
}
```