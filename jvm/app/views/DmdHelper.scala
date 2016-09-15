package views

/**
  * Created by jms on 15/09/16.
  */
object DmdHelper {

  def boolean(v: Option[String]): String = {
    v match {
      case Some("0001") => "Yes"
      case _ => "No"
    }
  }

}
