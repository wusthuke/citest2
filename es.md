### ES terms query 结论

| 数据量 | terms | es_time | mysql_time |
|:------|:------|:--------|:-----------|
| 150W  | 10    | 3ms     |            |
| 150W  | 100   | 3ms     |            |
| 150W  | 1000  | 7ms     |            |
| 150W  | 10000 | 23ms    |            |
| 150W  | 20000 | 40~80ms |            |
| 300W  | 10    | 2ms     |            |
| 300W  | 100   | 2.5ms   |            |
| 300W  | 1000  | 5ms     |            |
| 300W  | 10000 | 24ms    |            |
| 300W  | 20000 | 49ms    |            |
| 300W  | 10    | 2ms     |            |
| 300W  | 100   | 2.5ms   |            |
| 300W  | 1000  | 5ms     |            |
| 300W  | 10000 | 24ms    |            |
| 300W  | 20000 | 49ms    |            |