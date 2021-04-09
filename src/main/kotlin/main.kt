import com.simple.search.dto.Rank
import com.simple.search.service.SearchService
import java.nio.file.Path
import java.util.*
import java.util.stream.Collectors
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    require(args.isNotEmpty()) { "No directory given to index." }

    val rankingService = SearchService()
    val filePaths: List<Path> = rankingService.findFilePaths(args[0])!!
    val fileWithContent: Map<String, Rank> = rankingService.processFileContent(filePaths)

    Scanner(System.`in`).use { keyboard ->
        while (true) {
            print("search > ")
            val line = keyboard.nextLine()
            if (line.equals("quit", ignoreCase = true)) {
                exitProcess(1)
            }
            val searchCriteria = Arrays.stream(line.split(" ").toTypedArray())
                .map { obj: String -> obj.toLowerCase() }.collect(Collectors.toList())
            val fileRankings: Map<String, Int> =
                rankingService.calculateRank(fileWithContent, searchCriteria)
            if (fileRankings.isEmpty()) {
                println("no matches found")
            }
            fileRankings.forEach { (filename: String, percentage: Int) ->
                println(
                    "$filename : $percentage%"
                )
            }
        }
    }
}