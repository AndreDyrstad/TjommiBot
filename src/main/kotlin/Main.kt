import discord4j.core.DiscordClient
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Mono

fun main() {
    DiscordClient.create("")
        .withGateway { client: GatewayDiscordClient ->
            client.on(MessageCreateEvent::class.java) { event: MessageCreateEvent ->
                val message = event.message
                if (message.content.equals("!ping")) {
                    return@on message.channel
                        .flatMap { channel: MessageChannel ->
                            channel.createMessage(
                                "Pong!"
                            )
                        }
                }
                Mono.empty()
            }
        }.block()
}