package cn.piflow.bundle.hive

import cn.piflow._
import cn.piflow.conf.ConfigurableStop
import cn.piflow.conf.bean.PropertyDescriptor
import cn.piflow.conf.util.MapUtil
import org.apache.spark.sql.SparkSession



class SelectHiveQL extends ConfigurableStop {

  var hiveQL:String = _

  def perform(in: JobInputStream, out: JobOutputStream, pec: JobContext): Unit = {
    val spark = pec.get[SparkSession]()

    import spark.sql
    val df = sql(hiveQL)
    df.show()

    out.write(df)
  }

  def initialize(ctx: ProcessContext): Unit = {

  }

  def setProperties(map : Map[String, Any]): Unit = {
    hiveQL = MapUtil.get(map,"hiveQL").asInstanceOf[String]
  }

  override def getPropertyDescriptor(): List[PropertyDescriptor] = {
    var descriptor : List[PropertyDescriptor] = List()
    val hiveQL = new PropertyDescriptor().name("hiveQL").displayName("HiveQL").defaultValue("").required(true)
    descriptor = hiveQL :: descriptor
    descriptor
  }
}

