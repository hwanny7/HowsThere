package B107.server.meerkat.socket;


import B107.server.meerkat.dto.socket.Message;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


// 7


@Service
@RequiredArgsConstructor
@Slf4j
public class SocketService {

	private final SocketIOServer server;

	/*
	 서버에서 브라우저로 보내
	 sendSocketOffer()
	 */
	public void sendSocketOffer(SocketIOClient senderClient, Message message) throws InterruptedException {
		System.out.println("SocketService - sendSocketOffer()");

		for (
				SocketIOClient client : senderClient.getNamespace().getRoomOperations(message.getRoomName()).getClients()) {
			if (!client.getSessionId().equals(senderClient.getSessionId())) {
					client.sendEvent("offer", message.getDatas());
			}
		}
	}

	/*
	서버에서 브라우저로 보내
	sendSocketAnswer()
	*/
	public void sendSocketAnswer(SocketIOClient senderClient, Message message) {
		System.out.println("SocketService - sendSocketAnswer()");

		for (
				SocketIOClient client : senderClient.getNamespace().getRoomOperations(message.getRoomName()).getClients()) {
			if (!client.getSessionId().equals(senderClient.getSessionId())) {
				client.sendEvent("answer", message.getDatas());
			}
		}
	}

	public void sendSocketIce(SocketIOClient senderClient, Message message) {
		System.out.println("SocketService - sendSocketIce()");

		for (
				SocketIOClient client : senderClient.getNamespace().getRoomOperations(message.getRoomName()).getClients()) {
			if (!client.getSessionId().equals(senderClient.getSessionId())) {
				client.sendEvent("ice", message.getDatas());
			}
		}
	}

}

