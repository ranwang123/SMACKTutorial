package Data

object Observation{

  @SerialVersionUID(1L)
  sealed trait Obs extends Serializable

  case class Entry(id:Int, value: String) extends Obs

  object Entry {
    def apply(array:Array[String]):Entry ={
      Entry(
        array(0).toInt,
        array(1)
      )
    }
  }
}