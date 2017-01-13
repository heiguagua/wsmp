package com.chinawiserv.wsmp.websocket

import java.net.URI
import javax.websocket._

@ClientEndpoint
class WSClient {

  private var session: Session = _;
  private var endpointURI: String = _;

  def this(endpointURI: String) {
    this();
    this.endpointURI = endpointURI;
    this.connectToServer();
  }

  @OnOpen
  def onOpen(userSession: Session) {
    this.session = userSession;
  }

  @OnClose
  def onClose(userSession: Session, reason: CloseReason) {
    this.session = null;
    println("WebSocker.onClose:"+reason.getReasonPhrase);
  }

  @OnMessage
  def onMessage(message: String): Unit = {
  }

  @OnError
  def onError(throwable: Throwable, session: Session) {
    this.session = null;
    println("WebSocker.onError:"+throwable.getMessage);
  }

  def connectToServer(): Unit = {
    try {
      ContainerProvider.getWebSocketContainer().connectToServer(this, new URI(this.endpointURI));
    }
    catch {
      case e: Exception => println("WSClient.connectToServer:"+e.getMessage);
    }
  }

  def sendMessage(message: String): Boolean = {
    var result = false;
    try {
      if (this.session != null && this.session.isOpen) {
        this.session.getBasicRemote.sendText(message);
        println("sendMessage="+message);
        result = true;
      }
      else {
        this.connectToServer();
      }
    }
    catch {
      case e : Exception => {
        result = false;
        e.printStackTrace();
      };
    }
    return result;
  }

}
