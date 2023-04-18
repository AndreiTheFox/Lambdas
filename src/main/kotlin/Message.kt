data class Message(
    val type: String,
    var id: Int = 0,                            //Id сообщения
 //   val fromId: Int = 0,                        //Id автора сообщения
  //  val dateCreated: Long = 0L,                 //Дата отправки сообщения
    var content: String = "",                   //Содержание
    var markForDelete: Boolean = false,         //Пометка об удалении
    var alreadyRead: Boolean = when (type) {
        "incoming" -> false           //Пометка о прочтении false = не прочитано для входящих сообщений
         else ->  true           //Пометка о прочтении для Исходящих сообщений всегда "прочитаны"
        }
)
{
    override fun  toString(): String {
        return "Текст сообщения: $content \n Удалено?: $markForDelete  Прочитано?: $alreadyRead \n"
    }
}

class MessageNotFoundException(message: String) : RuntimeException(message)
