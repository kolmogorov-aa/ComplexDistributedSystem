package ru.ulmc.school.storm

import org.apache.storm.Config
import org.apache.storm.LocalCluster
import org.apache.storm.StormSubmitter
import org.apache.storm.generated.StormTopology
import org.apache.storm.topology.TopologyBuilder
import ru.ulmc.school.api.entity.TweetMsg
import ru.ulmc.school.storm.Names.FILTER_BOLT
import ru.ulmc.school.storm.Names.SOURCE_SPOUT
import ru.ulmc.school.storm.Names.STORE_BOLT
import ru.ulmc.school.storm.Names.TRUMP_BOLT
import ru.ulmc.school.storm.bolts.FilterBolt
import ru.ulmc.school.storm.bolts.StoreBolt
import ru.ulmc.school.storm.bolts.TrumpBolt
import ru.ulmc.school.storm.jms.DefaultJmsHolder
import ru.ulmc.school.storm.jms.DefaultConverter
import ru.ulmc.school.storm.spouts.ArtemisSpout


fun main(args: Array<String>) {
    StormApp.start()
}

object StormApp {
    private val TOPOLOGY_NAME: String = "demo_topology"

    fun start() {
        val config = Config()
        config.registerSerialization(TweetMsg::class.java)
        config.setDebug(true)
        config[Config.TOPOLOGY_MAX_SPOUT_PENDING] = 500


        val remote = java.lang.Boolean.valueOf(System.getProperty("remote", "false"))

        val topology = createTopology()
        if (remote) {
            config.setNumWorkers(20)
            StormSubmitter.submitTopology(TOPOLOGY_NAME, config, topology)
        } else {
            config.setDebug(false)
            val cluster = LocalCluster()
            cluster.submitTopology(TOPOLOGY_NAME, config, topology)
        }
    }

    @Throws(Exception::class)
    private fun createTopology(): StormTopology {
        val artemisSpout = ArtemisSpout()
        val topologyBuilder = TopologyBuilder()

        topologyBuilder
                .setSpout(SOURCE_SPOUT, artemisSpout)
                .setNumTasks(1)

        topologyBuilder.setBolt(FILTER_BOLT, FilterBolt())
                .shuffleGrouping(SOURCE_SPOUT)

        topologyBuilder.setBolt(TRUMP_BOLT, TrumpBolt())
                .shuffleGrouping(FILTER_BOLT)

        topologyBuilder.setBolt(STORE_BOLT, StoreBolt())
                .noneGrouping(TRUMP_BOLT)

        return topologyBuilder.createTopology()
    }

}