# StockAnalysis
K-line graph analysis for the stock market.<br>
This is a team work for the final project in this semester.

## Members
+ QinJiangbo
+ WuWenan
+ Tangmengdi
+ Lugefei
+ LiMeng
+ Maozhijun
+ Yangpiao

## Usages
1. change the path of the images in the server.properties file
2. the sample config is as follows: 

>STOCK_DATA=/Users/Richard/Documents/KCharts/data/<br>
KCHART_IMAGES=/Users/Richard/Documents/KCharts/images/<br>
KCHART_COMPRESSED_IMAGES=/Users/Richard/Documents/KCharts/compressed/

# 项目部署说明
## 部署环境：Intellij IDEA
## 部署步骤（两种方式）：
第一步（第一种）： 在线部署，打开Intellij IDEA， 新建项目，选择从Github导入项目，输入地址：git@github.com:QinJiangbo/StockAnalysis.git 或者 https://github.com/QinJiangbo/StockAnalysis.git。

第一步（第二种）：本地部署，打开Intellij IDEA或者Eclipse JavaEE，新建一个Web项目，然后将下载到本地的项目文件拷贝到相应的包里面去。`注意`: 这里Intellij IDEA和Eclipse JavaEE可能有点不一样，IDEA里面Web文件夹叫web，而JavaEE里面叫WebRoot，注意这个区分就可以了。

第二步：将之前提交的附件文件夹就是DATASET拷贝到桌面或者任意指定的文件夹。

第三步：这里比如拷贝到Windows系统的D盘。则进入项目的`config`包中，修改`server.properties`，将对应的参数更改为对应的D盘中的位置，注意，路径统一采用/斜线分隔，且每个路径的末尾都要加上/表示其是一个目录，否则会报错。

第四步：启动项目。打开项目地址。先点击导入数据集，然后进行相关操作。

第五步：比如导入数据集以后，选择一个文件编号SZ600012，然后选择一个算法，比如SIFT算法，将其权值设置为1，然后点击计算，等待一小会儿结果就会出现。点击左右按钮可以查看相似的股票图像。

第六步：如果实在还有问题，可以联系第四组组长。
