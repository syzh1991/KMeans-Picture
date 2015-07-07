# KMeans-Picture
程序分为三部分：
1、java将图片转换成rgb矩阵
2、spark-mllib中kmeans对矩阵进行处理，得到标记矩阵
3、用java将标记矩阵还原成图片
其中，1和3打包供2使用
