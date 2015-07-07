import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkContext, SparkConf}
import com.kmeans.utils.Pic2Vec
/**
 * Created by zhang on 2015/7/6.
 */
object KMeansTest extends App {
  //def main(args: Array[String]) {
  System.setProperty("hadoop.home.dir", "C:\\Users\\zhang\\Downloads\\hadoop")
  System.setProperty("HADOOP_USER_NAME", "root")
  val conf = new SparkConf()
    //.setMaster("spark://naruto.ccntgrid.zju.edu:7077")
    .setMaster("local")
    .setAppName("KMeansTest")
  //.setJars(Seq(System.getenv("SPARK_TEST_JAR")))
  //.setSparkHome(System.getenv("SPARK_HOME"))
  //.set("spark.executor.memory", "512m")
  //.set("spark.executor.core", "1")
  val sc = new SparkContext(conf)

  // Load training data in LIBSVM format.
  //val data = sc.textFile("hdfs://naruto.ccntgrid.zju.edu/user/test/dat.txt")
  //val parsedData = data.map(s => Vectors.dense(s.split('\t').map(_.toDouble))).cache()
  val pic = "D:\\Eclipse\\Pic2Vec\\im.jpg"
  val data = com.kmeans.utils.Pic2Vec.getImageGRB(pic).toArray()
  //val parsedData = data.foreach(s => Vectors.dense(s.toString.split(',').map(_.toDouble)))
  val parsedData = sc.parallelize(data,1).map(s => Vectors.dense(s.toString.split(',').map(_.toDouble)))

  // Cluster the data into two classes using KMeans
  val numClusters = 2
  val numIterations = 20
  val clusters = KMeans.train(parsedData, numClusters, numIterations)
  val result = clusters.predict(parsedData).map(a => a + 1).toArray()
  val matrix = com.kmeans.utils.Vec2Pic.read2vec(result)
  com.kmeans.utils.Vec2Pic.createMatrixImage(matrix, "D:\\test.jpg")
  //result.foreach(print)
  //result.saveAsTextFile("hdfs://naruto.ccntgrid.zju.edu/user/test/dat_result.txt")


}
