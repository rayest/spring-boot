# spring-boot-elasticsearch

* 关于配置文件 elasticsearch.yml
> 请在 elasticsearch 的安装目录中找到该文件，配置节点等信息

* 启动 elasticsearch
> /Users/lirui/Documents/tools/for-code/elasticSearch/elasticsearch-5.6.0/bin
> ./elasticsearch
> 注意：确保 x-path 插件随 elasticsearch 一同下载。否则可以通过插件式方式单独安装该插件


* 启动项目服务 Application.class
> ./gradlew bootRun

* 使用 Kibana 可视化数据
> 下载并安装至: /Users/lirui/Documents/tools/for-code/kibana-5.6.0-darwin-x86_64
> 启动：./bin/kibana
> 访问：http://localhost:5601
> 注意：确保 kibana 版本和 elasticsearch 版本兼容，否则 kibana 会启动失败