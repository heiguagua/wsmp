package com.chinawiserv.wsmp.websocket

import java.net.URI
import javax.websocket.ClientEndpoint
import javax.websocket.CloseReason
import javax.websocket.ContainerProvider
import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session

@ClientEndpoint
class WSClient {

  private var session: Session = _;

  def this(endpointURI: String) {
    this();
    this.connectToServer(endpointURI);
  }

  @OnOpen
  def onOpen(userSession: Session) {
    this.session = userSession;
  }

  @OnClose
  def onClose(userSession: Session, reason: CloseReason) {
    this.session = null;
  }

  @OnMessage
  def onMessage(message: String) {
  }

  def connectToServer(endpointURI: String): Unit = {
    ContainerProvider.getWebSocketContainer().connectToServer(this, new URI(endpointURI));
  }

  def sendMessage(message: String): Boolean = {
    var result = false;
    try {
      if (this.session != null && this.session.isOpen) {
        this.session.getAsyncRemote.sendText(message);
        result = true;
      }
    }
    catch {
      case e : Exception => result = false;
    }
    return result;
  }

}
