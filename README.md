# ElasticSearch 实践

1. [官方文档](https://www.elastic.co/guide/en/elasticsearch/client/java-api/6.7/index.html)
2. [在线地址](http://www.thetnteam.com:9100/)

## 接口查询

### 查询所有

```json
{
    "query": {
        "match_all": {}
    },
    "from":1,
    "size":1
}
```



### 条件查询

```json
{
    "query": {
        "match": {
        	"title": "Spring"
        }
    },
    "sort":[
    {"date":{"order":"desc"}}
    ]
}
```

### 聚合查询

```json
{
    "aggs": {
        "group_by_word_count": {
            "terms": {
                "field": "word_count"
            }
        },
        "group_by_publish_date": {
            "terms": {
                "field": "publish_date"
            }
        },
        "grades_word_count": {
            "stats": {
                "field": "word_count"
            }
        }
    }
}
```

### 子条件查询

#### Query Context

在查询过程中，除了判断文档是否满足查询条件外，ES还会计算一个_score来标识匹配的程序，旨在判断目标文档和查询条件匹配的有多好

- 全文本查询 针对文本类型数据

```json
1.习语匹配 讲title分开来
{
    "query": {
        "match_phrase": {
        	"title": "Spring算法"
        }
    }
}
2.多个字段模糊匹配 匹配author,title字段中有Spring
{
    "query": {
        "multi_match": {
        	"query": "Spring",
        	"fields":["author","title"]
        }
    }
}
3.查询字符串  包含Spring和Cloud 或者算法的字段
{
    "query": {
    	"query_string":{
    		"query":"(Spring AND Cloud) OR 算法 OR cyj"
    	}
    }
}
4.限定字段名去查询
{
    "query": {
        "query_string": {
            "query": "(Spring AND Cloud) OR 算法 OR cyj",
            "fields": [
                "title",
                "author"
            ]
        }
    }
}
4.范围查询
{
    "query": {
        "range": {
            "word_count": {
                "gte(大于等于)": 0,
                "lte（小于等于）": 30
            }
        }
    }
}
```

#### Filter Context

在查询过程中，只判断该文档是否满足条件，只有Yes或者No(主要用于过滤数据)

```josn
{
    "query": {
        "bool": {
            "filter": {
                "term": {
                    "word_count": 40
                }
            }
        }
    }
}
```

### 复合查询

```json
1.should 满足其中之一就可以
{
    "query": {
        "bool": {
        	"should":[
        		{
        			"match":{
        				"author":"cyj"
        			}
        		},
        		{
        			"match":{
        				"title":"Spring"
        			}
        		}
        		]
        }
    }
}
2.must为必须都满足
{
    "query": {
        "bool": {
        	"must":[
        		{
        			"match":{
        				"author":"cyj"
        			}
        		},
        		{
        			"match":{
        				"title":"Spring"
        			}
        		}
        		]
        }
    }
}
3.bool 和 filter结合
{
    "query": {
        "bool": {
            "must": [
                {
                    "match": {
                        "author": "cyj"
                    }
                },
                {
                    "match": {
                        "title": "Spring"
                    }
                }
            ],
            "filter": [
                {
                    "term": {
                        "word_count": 1000
                    }
                }
            ]
        }
    }
}
4.must_not 绝对不匹配
{
    "query": {
        "bool": {
            "must_not": {
                "term": {
                    "author": "cyj"
                }
            }
        }
    }
}
```