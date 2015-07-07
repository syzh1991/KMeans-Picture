import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
import org.apache.spark.mllib.recommendation.Rating
/**
 * Created by zhang on 2015/6/16.
 */
object ALSTest extends App{
  System.setProperty("hadoop.home.dir", "C:\\Users\\zhang\\Downloads\\hadoop")
  System.setProperty("HADOOP_USER_NAME", "root")

  val logFile = "hdfs://Naruto.ccntgrid.zju.edu:8020/user/test/mllib/als/test.data" // Should be some file on your system
  val conf = new SparkConf().setMaster("local").setAppName("ALS Test")
  val sc = new SparkContext(conf)

  // Load and parse the data
  val data = sc.textFile(logFile)
  val ratings = data.map(_.split(',') match { case Array(user, item, rate) =>
    Rating(user.toInt, item.toInt, rate.toDouble)
  })

  // Build the recommendation model using ALS
  val rank = 5
  val numIterations = 5
  val model = ALS.train(ratings, rank, numIterations, 0.01)

  // Evaluate the model on rating data
  val usersProducts = ratings.map { case Rating(user, product, rate) =>
    (user, product)
  }
  val predictions =
    model.predict(usersProducts).map { case Rating(user, product, rate) =>
      ((user, product), rate)
    }
  val ratesAndPreds = ratings.map { case Rating(user, product, rate) =>
    ((user, product), rate)
  }.join(predictions)
  val MSE = ratesAndPreds.map { case ((user, product), (r1, r2)) =>
    val err = (r1 - r2)
    err * err
  }.mean()
  println("Mean Squared Error = " + MSE)

  // Save and load model
  //model.save(sc, "myModelPath")
  //val sameModel = MatrixFactorizationModel.load(sc, "myModelPath")

}
