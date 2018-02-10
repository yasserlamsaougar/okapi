import org.minicluster.App
import org.minicluster.Injector

fun main(args: Array<String>) {
    val injector = Injector()
    val app = App(injector.kodein)
    app.start()
}