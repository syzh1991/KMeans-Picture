import org.apache.spark._
import org.apache.spark.rdd._
import org.apache.spark.SparkContext._
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}
/**
 * Created by zhang on 2015/6/16.
 */
object Word2VecTest {

  System.setProperty("hadoop.home.dir", "C:\\Users\\zhang\\Downloads\\hadoop")
  System.setProperty("HADOOP_USER_NAME", "root")

  val logFile = "hdfs://Naruto.ccntgrid.zju.edu:8020/user/test/mllib/als/test.data" // Should be some file on your system
  val conf = new SparkConf().setMaster("local").setAppName("ALS Test")
  val sc = new SparkContext(conf)

  val input = sc.textFile(logFile).map(line => line.split(" ").toSeq)

  val word2vec = new Word2Vec()

  val model = word2vec.fit(input)

  val synonyms = model.findSynonyms("china", 40)

  for((synonym, cosineSimilarity) <- synonyms) {
    println(s"$synonym $cosineSimilarity")
  }

}
